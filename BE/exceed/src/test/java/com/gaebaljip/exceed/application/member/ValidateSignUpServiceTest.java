package com.gaebaljip.exceed.application.member;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaebaljip.exceed.application.port.in.member.ValidateSignUpCommand;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.application.service.member.ValidateSignUpService;
import com.gaebaljip.exceed.common.exception.member.AlreadySignUpMemberException;
import com.gaebaljip.exceed.common.exception.member.EmailNotVerifiedException;

@ExtendWith(MockitoExtension.class)
class ValidateSignUpServiceTest {

    @Mock private MemberPort memberPort;
    @InjectMocks private ValidateSignUpService validateEmailService;

    @Test
    @DisplayName("이메일이 존재하지 않고, 이메일 인증을 수행하지 않았을 경우")
    void when_notExistEmail_isNotChecked_expected_void() {

        // given
        given(memberPort.existsByEmail(anyString())).willReturn(false);
        ValidateSignUpCommand command = new ValidateSignUpCommand(getEmail(), getPassword());

        // when, then
        Assertions.assertDoesNotThrow(() -> validateEmailService.execute(command));
    }

    @Test
    @DisplayName("회원가입을 시도하였으나 이메일 인증을 하지 못 한 경우")
    void when_existEmail_isNotChecked_then_exception() {

        // given
        given(memberPort.existsByEmail(anyString())).willReturn(true);
        given(memberPort.isChecked(anyString())).willReturn(false);
        ValidateSignUpCommand command = new ValidateSignUpCommand(getEmail(), getPassword());

        // when, then
        Assertions.assertThrows(
                EmailNotVerifiedException.class, () -> validateEmailService.execute(command));
    }

    @Test
    @DisplayName("이미 회원가입이 된 회원")
    void when_already_signUp_then_exception() {

        // given
        given(memberPort.existsByEmail(anyString())).willReturn(true);
        given(memberPort.isChecked(anyString())).willReturn(true);
        ValidateSignUpCommand command = new ValidateSignUpCommand(getEmail(), getPassword());

        // when, then
        Assertions.assertThrows(
                AlreadySignUpMemberException.class, () -> validateEmailService.execute(command));
    }

    private String getEmail() {
        return "asadf1234!!@gmail.com";
    }

    private String getPassword() {
        return "asadf1234!!";
    }
}
