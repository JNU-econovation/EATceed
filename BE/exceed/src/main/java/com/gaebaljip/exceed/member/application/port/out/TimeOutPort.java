package com.gaebaljip.exceed.member.application.port.out;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface TimeOutPort {
    void command(String email, String code);
    Optional<String> query(String email);
}
