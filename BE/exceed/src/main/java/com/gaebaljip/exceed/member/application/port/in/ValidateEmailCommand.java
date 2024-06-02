package com.gaebaljip.exceed.member.application.port.in;

import javax.validation.constraints.Email;

public record ValidateEmailCommand(@Email(message = "이메일 형식이 올바르지 않습니다.") String email) {}
