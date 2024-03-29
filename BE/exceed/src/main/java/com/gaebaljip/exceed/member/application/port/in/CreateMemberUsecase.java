package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.request.SignUpMemberRequest;
import org.springframework.stereotype.Component;

@Component
public interface CreateMemberUsecase {
    void execute(SignUpMemberRequest signUpMemberRequest);
}
