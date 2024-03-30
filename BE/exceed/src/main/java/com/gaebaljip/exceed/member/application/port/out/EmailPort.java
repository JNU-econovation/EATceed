package com.gaebaljip.exceed.member.application.port.out;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public interface EmailPort {
    boolean sendEmail(String to, String title, String template, Context context);
}
