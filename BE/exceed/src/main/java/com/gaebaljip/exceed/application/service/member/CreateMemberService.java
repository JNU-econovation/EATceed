package com.gaebaljip.exceed.application.service.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.member.request.SignUpMemberRequest;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.CreateMemberUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.annotation.EventPublisherStatus;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.event.Events;
import com.gaebaljip.exceed.common.event.SignUpMemberEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements CreateMemberUsecase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Timer
    @Transactional
    @EventPublisherStatus
    public void execute(SignUpMemberRequest signUpMemberRequest) {
        if (!memberPort.existsByEmail(signUpMemberRequest.email())) {
            MemberEntity memberEntity =
                    MemberEntity.createMember(
                            signUpMemberRequest.email(),
                            bCryptPasswordEncoder.encode(signUpMemberRequest.password()));
            memberPort.command(memberEntity);
            Events.raise(SignUpMemberEvent.from(signUpMemberRequest.email()));
        }
    }
}
