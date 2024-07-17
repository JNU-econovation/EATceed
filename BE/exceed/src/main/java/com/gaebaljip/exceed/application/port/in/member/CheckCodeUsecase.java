package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.member.request.CheckMemberRequest;

@Component
public interface CheckCodeUsecase {
    void execute(CheckMemberRequest checkMemberRequest);
}
