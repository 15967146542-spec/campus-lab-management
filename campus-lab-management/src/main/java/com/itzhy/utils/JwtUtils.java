package com.itzhy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 令牌工具类，负责令牌的生成与解析
 */
public class JwtUtils {

    // HS256 要求密钥长度不少于 256 位（32 字节）
    private static final String SECRET = "campus-lab-management-jwt-secret-key-2026";

    // 加密密钥，生成令牌和解析令牌必须使用同一个密钥
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 令牌有效期：12 小时
    private static final long EXPIRE_MILLIS = 12 * 60 * 60 * 1000L;

    // 工具类不允许创建实例
    private JwtUtils() {
    }

    /**
     * 生成 JWT 令牌
     *
     * @param dataMap 需要写入令牌的自定义声明，例如用户 id、username
     * @return 生成的 JWT 令牌字符串
     */
    public static String generateToken(Map<String, Object> dataMap) {
        return Jwts.builder().signWith(KEY, Jwts.SIG.HS256)//指定密钥及加密算法
                .claims(dataMap)//添加自定义声明
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_MILLIS))//设置过期时间
                .compact(); //生成JWT令牌
    }

    /**
     * 解析 JWT 令牌，并校验签名与有效期
     *
     * @param token 待解析的 JWT 令牌
     * @return 令牌中的载荷（自定义声明 + 标准声明）
     */
    public static Claims parseToken(String token) {
        //令牌被篡改会抛 SignatureException，已过期会抛 ExpiredJwtException
        return Jwts.parser()
                .verifyWith(KEY)//指定校验签名使用的密钥
                .build()
                .parseSignedClaims(token)//解析并校验令牌
                .getPayload();//获取载荷部分
    }
}
