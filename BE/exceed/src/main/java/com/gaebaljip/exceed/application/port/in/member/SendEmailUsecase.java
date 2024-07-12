package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

@Component
public interface SendEmailUsecase {

    void execute(SendEmailCommand sendEmailCommand);
}
