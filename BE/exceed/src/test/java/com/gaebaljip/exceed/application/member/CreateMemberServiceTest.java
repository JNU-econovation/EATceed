package com.gaebaljip.exceed.application.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gaebaljip.exceed.adapter.in.member.request.SignUpMemberRequest;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.application.service.member.CreateMemberService;

@ExtendWith(MockitoExtension.class)
class CreateMemberServiceTest {

    @Mock private MemberPort memberPort;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks private CreateMemberService createMemberService;

    @Test
    @DisplayName(
            "회원가입이 처음이면, memberPort.command()가 호출되어야 한다"
                    + "bCryptPasswordEncoder.encode()가 호출되어야한다.")
    void when_NotExistEmail_then_CreateMember() {
        // given
        SignUpMemberRequest signUpMemberRequest =
                new SignUpMemberRequest(getEmail(), getPassword());
        given(memberPort.existsByEmail(anyString())).willReturn(false);

        // when
        createMemberService.execute(signUpMemberRequest);

        // then
        verify(memberPort).command(any(MemberEntity.class));
        verify(bCryptPasswordEncoder).encode(anyString());
    }

    private String getEmail() {
        return "abcd123!@gmail.com";
    }

    private String getPassword() {
        return "password123@@";
    }
}
