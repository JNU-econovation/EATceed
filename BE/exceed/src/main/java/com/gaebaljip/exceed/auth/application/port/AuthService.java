package com.gaebaljip.exceed.auth.application.port;

import com.gaebaljip.exceed.auth.application.port.in.AuthUsecase;
import com.gaebaljip.exceed.auth.exception.PasswordMismatchException;
import com.gaebaljip.exceed.dto.request.LoginRequest;
import com.gaebaljip.exceed.dto.response.LoginResponse;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.security.domain.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthUsecase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtManager jwtManager;
    @Override
    public LoginResponse execute(LoginRequest request) {
        MemberEntity member = memberPort.findMemberByEmail(request.email());
        if(!bCryptPasswordEncoder.encode(request.password()).equals(member.getPassword())){
            throw new PasswordMismatchException();
        }
        return LoginResponse.builder()
                .accessToken(jwtManager.generateAccessToken(member.getId()))
                .refreshToken(jwtManager.generateRefreshToken(member.getId()))
                .build();
    }
}
