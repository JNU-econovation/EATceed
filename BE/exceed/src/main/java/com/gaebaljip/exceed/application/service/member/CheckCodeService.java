package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.member.request.CheckMemberRequest;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.CheckCodeUsecase;
import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.Encryption;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.exception.member.ExpiredCodeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckCodeService implements CheckCodeUsecase {

    private final Encryption encryption;
    private final CodePort codePort;
    private final MemberPort memberPort;

    @Override
    @Transactional
    @Timer
    public void execute(CheckMemberRequest checkMemberRequest) {
        String code =
                codePort.query(checkMemberRequest.email())
                        .orElseThrow(() -> ExpiredCodeException.EXECPTION);
        String decrypt = encryption.decrypt(checkMemberRequest.code());
        if (encryption.match(decrypt, code)) {
            MemberEntity member = memberPort.findMemberByEmail(checkMemberRequest.email());
            member.updateChecked();
        }
    }
}
