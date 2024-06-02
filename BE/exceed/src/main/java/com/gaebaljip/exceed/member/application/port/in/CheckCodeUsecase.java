package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.CheckMemberRequest;

@Component
public interface CheckCodeUsecase {
    void execute(CheckMemberRequest checkMemberRequest);
}
