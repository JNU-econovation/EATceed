package com.gaebaljip.exceed.member.application;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaebaljip.exceed.member.application.port.in.ValidateEmailCommand;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.exception.AlreadySignUpMemberException;

@ExtendWith(MockitoExtension.class)
class ValidateEmailServiceTest {

    @Mock private MemberPort memberPort;
    @InjectMocks private ValidateEmailService validateEmailService;

    @Test
    @DisplayName("이메일이 존재하지 않고, 이메일 인증을 수행하지 않았을 경우")
    void when_notExistEmail_isNotChecked_expected_void() {

        // given
        given(memberPort.isChecked(anyString())).willReturn(false);
        ValidateEmailCommand command = new ValidateEmailCommand(getEmail());

        // when, then
        Assertions.assertDoesNotThrow(() -> validateEmailService.execute(command));
    }

    @Test
    @DisplayName("이메일이 존재하면, 예외가 발생해야 한다")
    void when_existEmail_isNotChecked_then_exception() {

        // given
        given(memberPort.existsByEmail(anyString())).willReturn(true);
        ValidateEmailCommand command = new ValidateEmailCommand(getEmail());

        // when, then
        Assertions.assertThrows(
                AlreadySignUpMemberException.class, () -> validateEmailService.execute(command));
    }

    private String getEmail() {
        return "asadf1234!!@gmail.com";
    }
}
