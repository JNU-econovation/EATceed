package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.member.application.port.in.VerifyEmailCheckedUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyEmailCheckedService implements VerifyEmailCheckedUsecase {

    private final MemberPort memberPort;

    @Override
    public Boolean execute(String email) {
        return memberPort.isChecked(email);
    }
}
