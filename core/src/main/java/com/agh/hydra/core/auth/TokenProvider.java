package com.agh.hydra.core.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Slf4j
@UtilityClass
public class TokenProvider {

    private static final long EXPIRATION_TIME_SECONDS = 864_000_000; // 10 days
    private static final String SECRET = "IndianUdemyCoursesMoveMe";

    public static String getToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.nanoTime() + EXPIRATION_TIME_SECONDS))
                .signWith(HS512, SECRET)
                .compact();
    }

    public static String getUserId(@NotBlank String token) {
            try {
                var body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();

                var expiration = body.getExpiration().toInstant();

                if (!expiration.isBefore(Instant.now())) {
                    return body.getSubject();
                }
            } catch (SignatureException e) {
                log.info("Invalid token : {}", token);
            }
            return null;
    }
}
