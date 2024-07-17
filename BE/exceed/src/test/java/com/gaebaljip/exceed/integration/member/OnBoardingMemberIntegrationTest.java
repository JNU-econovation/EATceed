package com.gaebaljip.exceed.integration.member;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.adapter.in.member.request.OnBoardingMemberRequest;
import com.gaebaljip.exceed.adapter.out.jpa.member.MemberRepository;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;

class OnBoardingMemberIntegrationTest extends IntegrationTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @WithMockUser(memberId = 11)
    void when_onBoarding_expect_UpdatedMember() throws Exception {
        // given
        MemberEntity memberEntity = getMemberEntity();
        memberRepository.save(memberEntity); // memberId = 11인 member 생성

        // when

        OnBoardingMemberRequest request = getOnBoardingMemberRequest();

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then

        MemberEntity member = memberRepository.findById(11L).get();
        Double height = member.getHeight();
        Double weight = member.getWeight();

        assertAll(
                () -> {
                    Assertions.assertThat(weight).isEqualTo(request.weight());
                    Assertions.assertThat(height).isEqualTo(request.height());
                });

        resultActions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "onBoarding-success",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("height")
                                                .type(JsonFieldType.NUMBER)
                                                .description("키"),
                                        fieldWithPath("gender")
                                                .type(JsonFieldType.STRING)
                                                .description("MALE, FEMALE"),
                                        fieldWithPath("weight")
                                                .type(JsonFieldType.NUMBER)
                                                .description("몸무게"),
                                        fieldWithPath("targetWeight")
                                                .type(JsonFieldType.NUMBER)
                                                .description("타겟 몸무게"),
                                        fieldWithPath("age")
                                                .type(JsonFieldType.NUMBER)
                                                .description("나이"),
                                        fieldWithPath("activity")
                                                .type(JsonFieldType.STRING)
                                                .description(
                                                        """
                                        활동량\s
                                        NOT_ACTIVE: 활동이 적은 경우
                                        LIGHTLY_ACTIVE: 가벼운 활동을 하는 경우
                                        NORMAL_ACTIVE: 보통 활동을 하는 경우
                                        VERY_ACTIVE: 많은 활동을 하는 경우
                                        EXTREMELY_ACTIVE: 매우 많은 활동을 하는 경우"""),
                                        fieldWithPath("etc")
                                                .type(JsonFieldType.STRING)
                                                .description("기타"))));
    }

    private OnBoardingMemberRequest getOnBoardingMemberRequest() {
        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", 61.0, 65.0, 26, "NOT_ACTIVE", "뭐든 잘 먹습니다.");
        return request;
    }

    private MemberEntity getMemberEntity() {
        MemberEntity memberEntity =
                MemberEntity.builder()
                        .email("aaa@naver.com")
                        .password("aaaa1234@@")
                        .checked(false)
                        .build();
        return memberEntity;
    }
}
