package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

@Component
public interface ValidateSignUpUsecase {
    void execute(ValidateSignUpCommand command);
}
