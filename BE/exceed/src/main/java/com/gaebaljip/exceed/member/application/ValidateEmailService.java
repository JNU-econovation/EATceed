package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.member.application.port.in.ValidateEmailCommand;
import com.gaebaljip.exceed.member.application.port.in.ValidateEmailUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.exception.AlreadyCheckedEmailException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateEmailService implements ValidateEmailUsecase {

    private final MemberPort memberPort;

    @Override
    @Transactional
    public void execute(ValidateEmailCommand command) {
        if (memberPort.findEmailOrChecked(command.email())) {
            throw AlreadyCheckedEmailException.Exception;
        }
    }
}
