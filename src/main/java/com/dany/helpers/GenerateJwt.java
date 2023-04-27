package com.dany.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.dany.models.User;

public class GenerateJwt {

    private static final String SECRET_KEY = "A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z6";

    private static final long EXPIRATION_TIME_IN_HOURS = 5L;

    public static String generateJwt(User user) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("user", user);

        Instant now = Instant.now();

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS)))
                .signWith(hmacKey)
                .compact();
    }
}
