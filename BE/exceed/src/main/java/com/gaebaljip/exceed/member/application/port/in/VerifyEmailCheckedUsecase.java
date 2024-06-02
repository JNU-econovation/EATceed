package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

@Component
public interface VerifyEmailCheckedUsecase {
    Boolean execute(String email);
}
