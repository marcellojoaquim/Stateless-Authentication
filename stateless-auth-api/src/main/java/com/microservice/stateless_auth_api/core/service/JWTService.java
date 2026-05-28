package com.microservice.stateless_auth_api.core.service;

import com.microservice.stateless_auth_api.core.model.User;
import com.microservice.stateless_auth_api.infra.exception.AuthenticationException;
import com.microservice.stateless_auth_api.infra.exception.ValidationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.*;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JWTService {

    private static final String EMPTY_STATE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final Integer ONE_DAY_IN_HOURS = 24;

    @Value("${app.token.secret-key}")
    private String secretKey;

    public String createToken(User user) {
        var data = new HashMap<String, String>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUserName());
        return Jwts
                .builder()
                .claims(data)
                .expiration(generateExpiresAt())
                .signWith(generateSign())
                .compact();
    }

    private Date generateExpiresAt() {
        return Date.from(
                LocalDateTime.now()
                        .plusHours(ONE_DAY_IN_HOURS)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public void validateAccessToken(String token) {
        var accessToken = extractToken(token);
        try {
            Jwts.parser()
                    .verifyWith(generateSign())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token "+e.getMessage());
        }
    }

    private String extractToken(String token) {
        if(isEmpty(token)) {
            throw new ValidationException("The access token wes not informed");
        }

        if(token.contains(EMPTY_STATE)) {
            return token.split(EMPTY_STATE) [TOKEN_INDEX];
        }
        return token;
    }
}
