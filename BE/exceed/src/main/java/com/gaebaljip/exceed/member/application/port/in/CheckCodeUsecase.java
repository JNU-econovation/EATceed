package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.request.CheckMemberRequest;
import org.springframework.stereotype.Component;

@Component
public interface CheckCodeUsecase {
    void execute(CheckMemberRequest checkMemberRequest);
}
