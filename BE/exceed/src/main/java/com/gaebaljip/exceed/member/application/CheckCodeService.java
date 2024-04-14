package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.common.Encryption;
import com.gaebaljip.exceed.dto.request.CheckMemberRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.CheckCodeUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.application.port.out.TimeOutPort;
import com.gaebaljip.exceed.member.exception.InvalidCodeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckCodeService implements CheckCodeUsecase {

    private final Encryption encryption;
    private final TimeOutPort timeOutPort;
    private final MemberPort memberPort;

    @Override
    @Transactional
    public void execute(CheckMemberRequest checkMemberRequest) {
        String decrypt = encryption.decrypt(checkMemberRequest.code());
        String code =
                timeOutPort
                        .query(checkMemberRequest.email())
                        .orElseThrow(() -> InvalidCodeException.EXECPTION);
        if (!code.equals(decrypt)) {
            throw InvalidCodeException.EXECPTION;
        } else {
            MemberEntity member = memberPort.findMemberByEmail(checkMemberRequest.email());
            member.updateChecked();
        }
    }
}
