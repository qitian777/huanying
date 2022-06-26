package com.tian.userserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.serverapi.pojo.EmailMsg;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.exception.GlobalException;
import com.tian.userserver.config.redis.RedisService;
import com.tian.userserver.mapper.UserMapper;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IUserAuthorityService;
import com.tian.userserver.service.IUserService;
import com.tian.userserver.util.EmailCodeUtil;
import com.tian.userserver.util.Encrypt;
import com.tian.userserver.util.JwtTokenUtil;
import com.tian.userserver.vo.LoginUser;
import com.tian.userserver.vo.UserVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserAuthorityService userAuthorityService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmailCodeUtil emailCodeUtil;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    // jwt的失效时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * @Author QiGuang
     * @Description 登录
     * @Param
     */
    @Override
    public Map<String, Object> doLogin(LoginUser loginUser, HttpServletRequest request) {
        // 验证验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isBlank(captcha) || !captcha.equals(loginUser.getCaptcha()))
            throw new GlobalException(RespBeanEnum.CAPTCHA_ERROR);
        // 解密密码
        String password = Encrypt.AESdecrypt(loginUser.getPassword());

        User user = getUserByUsername(loginUser.getUsername());
        // 校验用户
        if (user == null || !passwordEncoder.matches(password, user.getPassword()))
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.generateToken(user);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        user.setPassword(null);
        tokenMap.put("user", user);
        // redis失效时间
        Long redisExpiration = 86400L;
        if (loginUser.getRememberMe()) redisExpiration = expiration;
        redisService.set("token_" + user.getUsername(), token, redisExpiration);
        return tokenMap;
    }

    /**
     * @Author QiGuang
     * @Description 通过用户名获取用户信息
     * @Param
     */
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        if (user != null) {
            // 若用户存在获取其权限
            String authority = userAuthorityService.getAuthorityByRoleId(user.getRoleId());
            user.setAuthorities(authority);
        }
        return user;
    }

    /**
     * @Author QiGuang
     * @Description 检查用户名是否已被注册
     * @Param
     */
    @Override
    public Long checkUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return baseMapper.selectCount(wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 检查邮箱是否已被注册
     * @Param
     */
    @Override
    public Long checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return baseMapper.selectCount(wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 获取邮箱验证码
     * @Param
     */
    @Override
    public int getEmailCode(String email) {
        // 邮箱是否已注册
        if (checkEmail(email) > 0) return -1;
        EmailMsg emailMsg = emailCodeUtil.generatorAndSaveEmailCode(email);
        String msg = JSONObject.toJSONString(emailMsg);
        amqpTemplate.convertAndSend("email-queue", msg);
        return 1;
    }

    /**
     * @Author QiGuang
     * @Description 创建用户
     * @Param
     */
    @Override
    public int createUser(UserVo userVo, HttpServletRequest request) {
        // 验证验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isBlank(captcha) || !captcha.equals(userVo.getCaptcha()))
            throw new GlobalException(RespBeanEnum.CAPTCHA_ERROR);
        int insert = 0;
        try {
            boolean result = emailCodeUtil.matchesEmailCode(userVo.getEmail(), userVo.getEmailCode());
            if (!result) throw new GlobalException(RespBeanEnum.EMAIL_CODE_ERROR);
            User user = exchangeUser(userVo);
            insert = baseMapper.insert(user);
        } catch (Exception e) {
//            System.out.println(e);
            throw new GlobalException(RespBeanEnum.CREATE_USER_FAIL);
        }
        return insert;
    }

    /**
     * @Author QiGuang
     * @Description 更新用户信息
     * @Param
     */
    @Override
    public int updateUserInfo(UserVo userVo) {
        if (StringUtils.isNotBlank(userVo.getPassword())) {
            String password = Encrypt.AESdecrypt(userVo.getPassword());
            User u = getUserByUsername(userVo.getUsername());
            if (!passwordEncoder.matches(password, u.getPassword()))
                throw new GlobalException(RespBeanEnum.PASSWORD_ERROR);
        }
        User user = exchangeUser(userVo);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        return baseMapper.update(user, wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 更新用户头像
     * @Param
     */
    @Override
    public int updatePicture(String newFileName, String name) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("picture", newFileName)
                .eq("username", name);
        return baseMapper.update(null, wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 搜索好友
     * @Param
     */
    @Override
    public List<Map<String, Object>> searchFriend(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username", "nickname")
                .like("username", username)
                .or()
                .like("nickname", username);
        return baseMapper.selectMaps(wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 通过用户名获取好友id
     * @Param
     */
    @Override
    public List<Map<String, Object>> getFriendID(String username, String friendName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .eq("username", username)
                .or()
                .eq("username", friendName);
        return baseMapper.selectMaps(wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 将UserVo转为User
     * @Param
     */
    public User exchangeUser(UserVo userVo) {
        User user = new User();
        user.setUsername(userVo.getUsername());
        if (StringUtils.isNotBlank(userVo.getPassword())) {
            if (StringUtils.isNotBlank(userVo.getNewPassword()))
                user.setPassword(passwordEncoder.encode(userVo.getNewPassword()));
            if (userVo.getCaptcha() != null && userVo.getEmailCode() != null) {
                user.setPassword(passwordEncoder.encode(userVo.getPassword()));
            }
            user.setEmail(userVo.getEmail());
        }
        user.setNickname(userVo.getNickname());
        return user;
    }
}
