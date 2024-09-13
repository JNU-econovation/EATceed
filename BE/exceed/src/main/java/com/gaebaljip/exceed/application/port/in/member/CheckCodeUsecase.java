package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

@Component
public interface CheckCodeUsecase {
    void execute(String email, String encrypt);
}
