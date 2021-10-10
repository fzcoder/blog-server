package com.fzcoder.opensource.blog.utils;

import com.fzcoder.opensource.blog.config.autoconfigure.JwtConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Frank Fang
 * @date: 2020/1/31 00:12
 * @description: JWT工具类
 * JWT是由三段组成的，分别是header（头）、payload（负载）和signature（签名）
 * 其中header中放{
 *   "alg": "HS512",
 *   "typ": "JWT"
 * } 表明使用的加密算法，和token的类型==>默认是JWT
 *
 */
@Component
public class JwtTokenUtil {

    // 请求头名称
    public static final String TOKEN_HEADER = "Authorization";

    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    // 添加角色的key
    private static final String ROLE_CLAIMS = "role";

    @Autowired
    private JwtConfigProperties properties;
 
    /**
     * description: 创建Token
     *
     * @param username
     * @param roles
     * @param isRememberMe
     * @return java.lang.String
     */
    public String createToken(String username, List<String> roles, Boolean isRememberMe) {
        long expiration = isRememberMe ? properties.getExpiration().get("remember")
                : properties.getExpiration().get("default");
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, roles);
        return Jwts.builder()
                //采用HS512算法对JWT进行的签名,PRIMARY_KEY是我们的密钥
                .signWith(SignatureAlgorithm.HS512, properties.getSecret())
                //设置角色名
                .setClaims(map)
                //设置发证人
                .setIssuer(properties.getIss())
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }
 
    /**
     * description: 从token中获取用户名
     *
     * @param token
     * @return java.lang.String
     */
    public String getUsername(String token){
        return getTokenBody(token).getSubject();
    }
 
    // 获取用户角色
	@SuppressWarnings("unchecked")
	public List<String> getUserRole(String token){
        return (List<String>) getTokenBody(token).get(ROLE_CLAIMS);
    }
 
    /**
     * description: 判断Token是否过期
     *
     * @param token
     * @return boolean
     */
    public boolean isExpiration(String token){
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e1) {
            // 当抛出ExpiredJwtException异常时表明Token已经过期
            return true;
        }
    }
 
    /**
     * description:　获取
     *
     * @param token
     * @return io.jsonwebtoken.Claims
     */
    private Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(
                        properties.getSecret()
                )
                .parseClaimsJws(token)
                .getBody();
    }
}