package com.gaebaljip.exceed.application.service.member;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.EmailConfirmedUseCase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailConfirmedService implements EmailConfirmedUseCase {
    private final MemberPort memberPort;
    @Override
    @Transactional
    public void execute(String email) {
        MemberEntity member = memberPort.findMemberByEmail(email);
        member.updateChecked();
    }
}
