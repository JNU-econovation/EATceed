package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

@Component
public interface ValidateEmailUsecase {
    void execute(ValidateEmailCommand command);
}
