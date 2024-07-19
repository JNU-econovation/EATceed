package com.gaebaljip.exceed.application.port.in.member;

import javax.validation.constraints.Email;

public record SendEmailCommand(@Email(message = "이메일 형식이 올바르지 않습니다.") String email) {}
