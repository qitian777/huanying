package com.tian.userserver.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Getter
@Setter
@ToString
@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("账号创建时间")
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("头像")
    private String picture;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("上次登录时间")
    private Date lastLogin;

    @ApiModelProperty("权限列表")
    @TableField(exist = false)
    Collection<GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * @Author QiGuang
     * @Description 传入字符串时自动转化为权限列表
     * @Param
     */
    public void setAuthorities(String authorities) {
        this.authorities = AuthorityUtils.createAuthorityList(authorities);
    }

    /**
     * @Author QiGuang
     * @Description 重写反序列化时用到的权限set方法
     * @Param
     */
    public void setAuthorities(List<Map<String,String>> authorities) {
        this.authorities=new ArrayList<>();
        for (Map<String,String> authority : authorities) {
            for (String value : authority.values()) {
                this.authorities.addAll(AuthorityUtils.createAuthorityList(value));
            }
        }
    }
}
