package com.itzhy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    // HS256 要求密钥长度不少于 256 位（32 字节）
    private static final String SECRET = "campus-lab-management-jwt-secret-key-2026";

    // 加密密钥，生成令牌和解析令牌必须使用同一个密钥
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    //生成JWT令牌
    @Test
    public void testGenerateJwt() {
        //创建JWT令牌，指定加密算法以及密钥
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");

        String jwt = Jwts.builder().signWith(KEY, Jwts.SIG.HS256)//指定密钥及加密算法
                .claims(dataMap)//添加自定义声明
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))//设置过期时间
                .compact(); //生成JWT令牌
        System.out.println(jwt);
    }


    //解析JWT令牌
    @Test
    public void testParseJWT(){
        //待解析的令牌，由 testGenerateJwt 生成（实际项目中这里是从请求头中获取的前端令牌）
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc5MTAxMzE3NH0.EtlOa-oHd_nmBlTs8pnjWuPqVXgs5ay6BmlH1QZH_pU";

        //解析令牌：用同一个密钥校验签名，并取出载荷（Claims）
        //令牌被篡改会抛 SignatureException，已过期会抛 ExpiredJwtException
        Claims claims = Jwts.parser()
                .verifyWith(KEY)//指定校验签名使用的密钥
                .build()
                .parseSignedClaims(token)//解析并校验令牌
                .getPayload();//获取载荷部分

        //取出自定义声明，指定类型时无需强制转换
        System.out.println("id：" + claims.get("id", Integer.class));
        System.out.println("username：" + claims.get("username", String.class));
        //取出标准声明
        System.out.println("过期时间：" + claims.getExpiration());
        System.out.println("全部载荷：" + claims);
    }
}
