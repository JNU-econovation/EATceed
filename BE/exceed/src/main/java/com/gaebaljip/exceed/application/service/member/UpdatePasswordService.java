package com.gaebaljip.exceed.application.service.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.UpdatePasswordUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePasswordService implements UpdatePasswordUsecase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void execute(String email, String password) {
        MemberEntity member = memberPort.findMemberByEmail(email);
        member.updatePassword(bCryptPasswordEncoder.encode(password));
    }
}
