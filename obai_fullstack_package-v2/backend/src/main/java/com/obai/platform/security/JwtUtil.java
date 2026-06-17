package com.obai.platform.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Algorithm algorithm;
    private final long expireSeconds;

    public JwtUtil(@Value("${obai.jwt.secret}") String secret, @Value("${obai.jwt.expire-seconds}") long expireSeconds) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expireSeconds = expireSeconds;
    }

    public String createToken(Long userId, String username) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("username", username)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(expireSeconds)))
                .sign(algorithm);
    }

    public Long verifyAndGetUserId(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return Long.parseLong(jwt.getSubject());
    }
}
