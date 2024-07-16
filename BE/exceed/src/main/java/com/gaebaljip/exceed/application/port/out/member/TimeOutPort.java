package com.gaebaljip.exceed.application.port.out.member;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface TimeOutPort {
    void command(String email, String code);

    Optional<String> query(String email);
}
