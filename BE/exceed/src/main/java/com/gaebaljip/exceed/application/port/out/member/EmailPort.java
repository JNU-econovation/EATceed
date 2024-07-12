package com.gaebaljip.exceed.application.port.out.member;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public interface EmailPort {
    void sendEmail(String to, String title, String template, Context context);
}
