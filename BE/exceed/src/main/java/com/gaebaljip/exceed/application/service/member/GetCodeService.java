package com.gaebaljip.exceed.application.service.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.port.in.member.GetCodeUsecase;
import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.common.Encryption;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCodeService implements GetCodeUsecase {

    private final Encryption encryption;
    private final CodePort codePort;

    @Override
    public String execute(String email) {
        Optional<String> code = codePort.query(email);
        if (code.isPresent()) {
            return encryption.encrypt(code.get());
        }
        return null;
    }
}
