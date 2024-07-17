package com.gaebaljip.exceed.integration.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.adapter.out.jpa.member.MemberRepository;
import com.gaebaljip.exceed.adapter.out.redis.RedisUtils;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.event.SendEmailEvent;
import com.gaebaljip.exceed.common.exception.member.MemberError;
import com.gaebaljip.exceed.common.dto.request.SignUpMemberRequest;

@RecordApplicationEvents
class SignUpMemberIntegrationTest extends IntegrationTest {

    @Autowired ApplicationEvents events;
    @Autowired RedisUtils redisUtils;
    @Autowired MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        redisUtils.flushDB();
    }

    @Test
    @DisplayName(
            "회원가입 : 성공"
                    + "회원 생성 여부 확인"
                    + "이메일 인증 이벤트 발생 여부 확인"
                    + "redis에 인증 코드 저장 여부 확인"
                    + "회원가입 API 응답 코드 확인")
    void when_signUpMember_expected_createMemberAndPublishEvent() throws Exception {
        // given
        SignUpMemberRequest request = getSignUpMemberRequest();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());

        long count = events.stream(SendEmailEvent.class).count();
        assertAll(
                () ->
                        assertEquals(
                                memberRepository.findByEmail(request.email()).get().getChecked(),
                                false),
                () -> assertEquals(1, count),
                () -> assertNotNull(redisUtils.getData(request.email())));
    }

    @Test
    @DisplayName("회원가입 : 실패" + "이미 가입된 이메일인 경우")
    void when_signUpMember_then_Fail() throws Exception {

        // given
        SignUpMemberRequest request = getSignUpMemberRequest();
        MemberEntity memberEntity =
                MemberEntity.builder().email(request.email()).password(request.password()).build();
        memberRepository.save(memberEntity);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value(MemberError.ALREADY_SIGN_UP_MEMBER.getReason()));
    }

    private SignUpMemberRequest getSignUpMemberRequest() {
        return SignUpMemberRequest.builder()
                .email("sonny1234444!@gmail.com")
                .password("sonny123!@@")
                .build();
    }
}
