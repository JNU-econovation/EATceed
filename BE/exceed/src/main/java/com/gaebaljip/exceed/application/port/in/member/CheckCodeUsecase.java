package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.request.CheckMemberRequest;

@Component
public interface CheckCodeUsecase {
    void execute(CheckMemberRequest checkMemberRequest);
}
