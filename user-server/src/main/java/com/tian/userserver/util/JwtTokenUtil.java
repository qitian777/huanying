package com.tian.userserver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: jwt工具类
 * @Author QiGuang
 * @Date 2022/6/13
 * @Version 1.0
 */
@Component
public class JwtTokenUtil {
    // 荷载claim的名称
    private static final String CLAIM_KEY_USERNAME = "sub";
    // 荷载的创建时间
    private static final String CLAIM_KEY_CREATED = "created";
    // jwt令牌的秘钥
    @Value("${jwt.secret}")
    private String secret;
    // jwt的失效时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * @Author QiGuang
     * @Description 根据用户信息生成token
     * @Param
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * @Author QiGuang
     * @Description 从token获取用户信息
     * @Param
     */
    public String getUserNameFromToken(String token){
        String username = null;
        Claims claims = getClaimsFromToken(token);
        try {
            username = claims.getSubject();
        } catch (Exception e) {
        }
        return username;
    }

    /**
     * @Author QiGuang
     * @Description token是否有效
     * @Param
     */
    public Boolean validateToken(String token,UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * @Author QiGuang
     * @Description 判断token是否可以被刷新
     * @Param
     */
    public Boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * @Author QiGuang
     * @Description 刷新token
     * @Param
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * @Author QiGuang
     * @Description 判断token是否失效
     * @Param
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        //如果token有效的时间在当前时间之前就表示实效
        return expireDate.before(new Date());
    }

    /**
     * @Author QiGuang
     * @Description 从token中获取实效时间
     * @Param
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * @Author QiGuang
     * @Description 根据token获取荷载
     * @Param
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * @Author QiGuang
     * @Description 根据荷载生成JWTToken
     * @Param
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpiration())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * @Author QiGuang
     * @Description 生成token实效时间
     * @Param
     */
    private Date generateExpiration() {
        // 当前时间+配置时间
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

}

