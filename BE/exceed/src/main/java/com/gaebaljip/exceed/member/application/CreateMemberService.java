package com.gaebaljip.exceed.member.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.dto.request.SignUpMemberRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.CreateMemberUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements CreateMemberUsecase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void execute(SignUpMemberRequest signUpMemberRequest) {
        if (!memberPort.findEmailOrChecked(signUpMemberRequest.email())) {
            MemberEntity memberEntity =
                    MemberEntity.builder()
                            .email(signUpMemberRequest.email())
                            .password(bCryptPasswordEncoder.encode(signUpMemberRequest.password()))
                            .checked(false)
                            .build();
            memberPort.command(memberEntity);
        }
    }
}
