package com.gaebaljip.exceed.application.service.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.adapter.out.redis.RedisAdapter;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.auth.AuthUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.EatCeedStaticMessage;
import com.gaebaljip.exceed.common.dto.HttpRequestDTO;
import com.gaebaljip.exceed.common.dto.TokenDTO;
import com.gaebaljip.exceed.common.exception.auth.NotFoundRefreshTokenException;
import com.gaebaljip.exceed.common.exception.auth.PasswordMismatchException;
import com.gaebaljip.exceed.common.security.domain.JwtManager;
import com.gaebaljip.exceed.common.security.domain.JwtResolver;
import com.gaebaljip.exceed.common.security.exception.InvalidJwtException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthUsecase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtManager jwtManager;
    private final JwtResolver jwtResolver;
    private final RedisAdapter redisAdapter;

    @Override
    public TokenDTO login(LoginRequest request) {
        MemberEntity member = memberPort.findCheckedMemberByEmail(request.email());
        if (!bCryptPasswordEncoder.matches(request.password(), member.getPassword())) {
            throw PasswordMismatchException.EXECPTION;
        }
        TokenDTO tokenDTO =
                TokenDTO.builder()
                        .accessToken(jwtManager.generateAccessToken(member.getId()))
                        .refreshToken(jwtManager.generateRefreshToken(member.getId()))
                        .build();
        jwtManager.saveRefreshToken(
                EatCeedStaticMessage.REDIS_REFRESH_TOKEN_KEY + member.getId().toString(),
                tokenDTO.refreshToken());
        return tokenDTO;
    }

    @Override
    public TokenDTO reIssueToken(
            String accessToken, String refreshToken, HttpRequestDTO requestDTO) {
        if (jwtManager.validateRefreshToken(refreshToken, requestDTO)) {
            return reissueToken(accessToken);
        }
        throw InvalidJwtException.EXECPTION;
    }

    private TokenDTO reissueToken(String requestAccessToken) {
        String accessToken = jwtResolver.extractToken(requestAccessToken);
        String accessTokenMemberId = jwtResolver.getMemberIdFromToken(accessToken);
        String refreshToken =
                redisAdapter
                        .query(EatCeedStaticMessage.REDIS_REFRESH_TOKEN_KEY + accessTokenMemberId)
                        .orElseThrow(() -> NotFoundRefreshTokenException.EXECPTION);
        String refreshTokenMemberId = jwtResolver.getMemberIdFromToken(refreshToken);

        if (accessTokenMemberId.equals(refreshTokenMemberId)) {
            return TokenDTO.builder()
                    .accessToken(
                            jwtManager.generateAccessToken(Long.parseLong(accessTokenMemberId)))
                    .refreshToken(
                            jwtManager.generateRefreshToken(Long.parseLong(refreshTokenMemberId)))
                    .build();
        }
        throw InvalidJwtException.EXECPTION;
    }

    @Override
    public void logout(String memberId) {
        redisAdapter.delete(EatCeedStaticMessage.REDIS_REFRESH_TOKEN_KEY + memberId);
    }
}
