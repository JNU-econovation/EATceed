package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

@Component
public interface VerifyEmailCheckedUsecase {
    Boolean execute(String email);
}
