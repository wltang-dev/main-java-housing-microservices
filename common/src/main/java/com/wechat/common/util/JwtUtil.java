package com.wechat.common.util;


import com.wechat.common.enums.Role;
import io.jsonwebtoken.*;



import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "my-secret-key";
    private static final long EXPIRATION = 3600_000; // 1 小时

    // 生成 Token
    public static String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 解析 Token
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
