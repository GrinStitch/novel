package com.stitch.mynovel.core.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@ConditionalOnProperty("novel.jwt.secret")
public class JwtUtils {

    @Value("${novel.jwt.secret}")
    private String secret;

    /**
     * 定义系统标识头常量
     */
    private static final String HEADER_SYSTEM_KEY = "systemKeyHeader";

    /**
     * 根据用户ID生成JWT
     *
     * @param userId      用户ID
     * @param systemKey 系统标识
     * @return JWT
     */
    public String generateToken(Long userId, String systemKey) {
        return Jwts.builder()
                .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
                .setSubject(userId.toString())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * 解析JWT返回用户ID
     *
     * @param token     JWT
     * @param systemKey 系统标识
     * @return 用户ID
     */
    public Long parseToken(String token, String systemKey) {
        try {
            Jwt<Header, Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJwt(token);
            if (claims.getHeader().get(HEADER_SYSTEM_KEY).equals(systemKey)) {
                return Long.parseLong(claims.getBody().getSubject());
            }
        } catch (JwtException e) {
            log.warn("JWT解析失败:{}", token);
            // don't trust the JWT!
        }
        return null;
    }


}
