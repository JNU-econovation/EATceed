package com.gaebaljip.exceed.common.security.domain;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.redis.RedisAdapter;
import com.gaebaljip.exceed.common.dto.HttpRequestDTO;
import com.gaebaljip.exceed.common.dto.TokenDTO;
import com.gaebaljip.exceed.common.exception.auth.NotFoundRefreshTokenException;
import com.gaebaljip.exceed.common.security.exception.ExpiredJwtException;
import com.gaebaljip.exceed.common.security.exception.InvalidJwtException;
import com.gaebaljip.exceed.common.security.exception.UnSupportedJwtException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtManager {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60; // 3일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private final Key key;
    private RedisAdapter redisAdapter;

    public JwtManager(@Value("${jwt.secret}") String secretKey, RedisAdapter redisAdapter) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisAdapter = redisAdapter;
    }

    public String generateAccessToken(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String accessToken, HttpServletRequest request)
            throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            log.info(
                    "method ={}, URL = {}, time={}, message={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    "엑세스 토큰 검증 성공");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw InvalidJwtException.EXECPTION; // 토큰의 서명이 유효하지 않은 경우
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw ExpiredJwtException.EXECPTION; // 토큰이 만료된 경우
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw UnSupportedJwtException.EXECPTION; // 지원되지 않는 토큰
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw UnSupportedJwtException.EXECPTION; // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    e.getMessage());
        }
        return false;
    }

    public String generateRefreshToken(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRefreshToken(String refreshToken, HttpRequestDTO requestDTO)
            throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
            log.info(
                    "method ={}, URL = {}, time={}, message={}",
                    requestDTO.method(),
                    requestDTO.url(),
                    LocalDateTime.now(),
                    "리프레시 토큰 검증 성공");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    requestDTO.method(),
                    requestDTO.url(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw InvalidJwtException.EXECPTION; // 토큰의 서명이 유효하지 않은 경우
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    requestDTO.method(),
                    requestDTO.url(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw ExpiredJwtException.EXECPTION; // 토큰이 만료된 경우
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    requestDTO.method(),
                    requestDTO.url(),
                    LocalDateTime.now(),
                    e.getMessage());
            throw UnSupportedJwtException.EXECPTION; // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    requestDTO.method(),
                    requestDTO.url(),
                    LocalDateTime.now(),
                    e.getMessage());
        }
        return false;
    }

    public void saveRefreshToken(String memberId, String refreshToken) {
        redisAdapter.saveWithExpiration(memberId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public Claims parseClaims(String Token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token).getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public TokenDTO reissueToken(String accessToken) {
        String accessTokenMemberId = parseClaims(accessToken).getSubject();
        String refreshToken =
                redisAdapter
                        .query(accessTokenMemberId)
                        .orElseThrow(() -> NotFoundRefreshTokenException.EXECPTION);
        String refreshTokenMemberId = parseClaims(refreshToken).getSubject();

        if (accessTokenMemberId.equals(refreshTokenMemberId)) {
            return TokenDTO.builder()
                    .accessToken(generateAccessToken(Long.parseLong(accessTokenMemberId)))
                    .refreshToken(generateRefreshToken(Long.parseLong(refreshTokenMemberId)))
                    .build();
        }
        throw InvalidJwtException.EXECPTION;
    }
}
