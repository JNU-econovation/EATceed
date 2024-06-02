package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

@Component
public interface ValidateEmailUsecase {
    void execute(ValidateEmailCommand command);
}
