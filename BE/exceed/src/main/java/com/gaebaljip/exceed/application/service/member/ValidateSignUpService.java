package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.port.in.member.ValidateSignUpCommand;
import com.gaebaljip.exceed.application.port.in.member.ValidateSignUpUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.annotation.EventPublisherStatus;
import com.gaebaljip.exceed.common.event.Events;
import com.gaebaljip.exceed.common.event.IncompleteSignUpEvent;
import com.gaebaljip.exceed.common.exception.member.AlreadySignUpMemberException;
import com.gaebaljip.exceed.common.exception.member.EmailNotVerifiedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateSignUpService implements ValidateSignUpUsecase {

    private final MemberPort memberPort;

    @Override
    @EventPublisherStatus
    @Transactional(readOnly = true)
    public void execute(ValidateSignUpCommand command) {
        Boolean isRegistered = memberPort.existsByEmail(command.email());
        if (!isRegistered) {
            return;
        }
        Boolean isChecked = memberPort.isChecked(command.email());
        if (isChecked) {
            throw AlreadySignUpMemberException.EXECPTION;
        }
        Events.raise(IncompleteSignUpEvent.from(command.email(), command.password()));
        throw EmailNotVerifiedException.EXECPTION;
    }
}
