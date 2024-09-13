package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.PasswordValidationUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.annotation.EventPublisherStatus;
import com.gaebaljip.exceed.common.event.Events;
import com.gaebaljip.exceed.common.event.SendEmailEvent;
import com.gaebaljip.exceed.common.exception.member.EmailNotVerifiedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordValidationService implements PasswordValidationUsecase {

    private final MemberPort memberPort;

    @Override
    @Transactional(readOnly = true)
    @EventPublisherStatus
    public void execute(String email) {
        MemberEntity member = memberPort.findMemberByEmail(email);
        if (!member.isSignUp()) {
            throw EmailNotVerifiedException.EXECPTION;
        }
        Events.raise(SendEmailEvent.of(email));
    }
}
