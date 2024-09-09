package com.gaebaljip.exceed.application.service.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.port.in.member.GetCodeUsecase;
import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.common.Encryption;
import com.gaebaljip.exceed.common.exception.member.RedirectException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCodeService implements GetCodeUsecase {

    private final Encryption encryption;
    private final CodePort codePort;

    /**
     * 이메일에 해당하는 code를 조회한 후 암호화하여 반환
     *
     * @param email
     * @return
     * @thorws RedirectException Redis에 저장된 email과 password가 만료될 시 RedirectException 발생
     */
    @Override
    public String execute(String email) {
        Optional<String> code = codePort.query(email);
        if (code.isPresent()) {
            return encryption.encrypt(code.get());
        }
        throw new RedirectException();
        // 응답 화면에 메시지만 반환하려고 일부로 이렇게 한 것
    }
}
