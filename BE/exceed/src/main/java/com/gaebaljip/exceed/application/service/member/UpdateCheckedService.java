package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.UpdateCheckedUsecase;
import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCheckedService implements UpdateCheckedUsecase {
    private final CodePort codePort;
    private final MemberPort memberPort;

    @Override
    @Transactional
    public void execute(String email) {
        MemberEntity member = memberPort.findMemberByEmail(email);
        member.updateChecked();
        codePort.delete(email);
    }
}
