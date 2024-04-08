package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.SignUpMemberRequest;

@Component
public interface CreateMemberUsecase {
    void execute(SignUpMemberRequest signUpMemberRequest);
}
