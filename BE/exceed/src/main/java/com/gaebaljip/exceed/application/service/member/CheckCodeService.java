package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.port.in.member.CheckCodeUsecase;
import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.common.Encryption;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.exception.member.ExpiredCodeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckCodeService implements CheckCodeUsecase {

    private final Encryption encryption;
    private final CodePort codePort;

    @Override
    @Transactional
    @Timer
    public void execute(String email, String encrypt) {
        String code = codePort.query(email).orElseThrow(() -> ExpiredCodeException.EXECPTION);
        String decrypt = encryption.decrypt(encrypt);
        encryption.match(decrypt, code);
    }
}
