package com.gaebaljip.exceed.application.service.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.auth.AuthUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.LoginResponseDTO;
import com.gaebaljip.exceed.common.dto.request.LoginRequest;
import com.gaebaljip.exceed.common.exception.auth.PasswordMismatchException;
import com.gaebaljip.exceed.common.security.domain.JwtManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthUsecase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtManager jwtManager;

    @Override
    public LoginResponseDTO execute(LoginRequest request) {
        MemberEntity member = memberPort.findCheckedMemberByEmail(request.email());
        if (!bCryptPasswordEncoder.matches(request.password(), member.getPassword())) {
            throw PasswordMismatchException.EXECPTION;
        }
        return LoginResponseDTO.builder()
                .accessToken(jwtManager.generateAccessToken(member.getId()))
                .refreshToken(jwtManager.generateRefreshToken(member.getId()))
                .build();
    }
}
