package com.gaebaljip.exceed.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.security.sasl.AuthenticationException;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtManager {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private static final String USER_AUTHORITY = "authority";

    private final Key key;

    public JwtManager(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String loginId, String authority) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(loginId)
                .setIssuedAt(now)
                .claim(USER_AUTHORITY, authority)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String loginId, String authority) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(loginId)
                .claim(USER_AUTHORITY, authority)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid Access Token.");
            throw new AuthenticationException("Invalid JWT signature.", e); // 토큰의 서명이 유효하지 않은 경우
        } catch (ExpiredJwtException e) {
            log.info("Expired Access token.");
            throw new AuthenticationException("Expired JWT token.", e); // 토큰이 만료된 경우
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            throw new AuthenticationException("Unsupported JWT token.", e); // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.info("Access Token is empty.");
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken) throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid Refresh Token", e);
            throw new AuthenticationException("Invalid JWT signature.", e); // 토큰의 서명이 유효하지 않은 경우
        } catch (ExpiredJwtException e) {
            log.info("Expired Refresh Token", e);
            throw new AuthenticationException("Expired JWT token.", e); // 토큰이 만료된 경우
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new AuthenticationException("Unsupported JWT token.", e); // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.info("Access Token is empty.", e);
        }
        return false;
    }


    private Claims parseClaims(String Token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(Token)
                .getBody();
    }

    public String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthConstants.TOKEN_TYPE.getValue())) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
