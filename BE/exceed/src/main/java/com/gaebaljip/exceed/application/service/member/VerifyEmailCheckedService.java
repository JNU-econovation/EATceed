package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.port.in.member.VerifyEmailCheckedUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyEmailCheckedService implements VerifyEmailCheckedUsecase {

    private final MemberPort memberPort;

    @Override
    public Boolean execute(String email) {
        return memberPort.isChecked(email);
        // todo 캡슐화 하기 : Tell Don't Ask
    }
}
