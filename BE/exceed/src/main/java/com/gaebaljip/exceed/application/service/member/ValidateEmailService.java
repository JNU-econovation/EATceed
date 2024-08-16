package com.gaebaljip.exceed.application.service.member;

import com.gaebaljip.exceed.common.annotation.Timer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.port.in.member.ValidateEmailCommand;
import com.gaebaljip.exceed.application.port.in.member.ValidateEmailUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.exception.member.AlreadySignUpMemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateEmailService implements ValidateEmailUsecase {

    private final MemberPort memberPort;

    @Override
    @Transactional
    @Timer
    public void execute(ValidateEmailCommand command) {
        if (memberPort.existsByEmail(command.email())) {
            throw AlreadySignUpMemberException.EXECPTION;
        }
    }
}
