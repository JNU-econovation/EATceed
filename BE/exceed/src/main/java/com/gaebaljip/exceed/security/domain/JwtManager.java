package com.gaebaljip.exceed.security.domain;

import com.gaebaljip.exceed.security.exception.ExpiredJwtAuthenticationException;
import com.gaebaljip.exceed.security.exception.InvalidJwtAuthenticationException;
import com.gaebaljip.exceed.security.exception.UnsupportedAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class JwtManager {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3; // 3일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private final Key key;

    private static final String MEMBER_ID = "memberId";

    public JwtManager(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String loginId, Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(MEMBER_ID, memberId)
                .setSubject(loginId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String accessToken, HttpServletRequest request) throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            log.info("method ={}, URL = {}, time={}, message={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(), "엑세스 토큰 검증 성공");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            throw new InvalidJwtAuthenticationException(); // 토큰의 서명이 유효하지 않은 경우
        } catch (ExpiredJwtException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            throw new ExpiredJwtAuthenticationException(); // 토큰이 만료된 경우
        } catch (UnsupportedJwtException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            throw new UnsupportedAuthenticationException(); // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
        }
        return false;
    }

    public String generateRefreshToken(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(loginId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRefreshToken(String refreshToken, HttpServletRequest request) throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
            log.info("method ={}, URL = {}, time={}, message={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(), "리프레시 토큰 검증 성공");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            throw new InvalidJwtAuthenticationException(); // 토큰의 서명이 유효하지 않은 경우
        } catch (ExpiredJwtException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            throw new ExpiredJwtAuthenticationException(); // 토큰이 만료된 경우
        } catch (UnsupportedJwtException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            throw new UnsupportedAuthenticationException(); // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
        }
        return false;
    }

}
