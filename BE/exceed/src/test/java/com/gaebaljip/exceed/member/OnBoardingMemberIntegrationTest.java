package com.gaebaljip.exceed.member;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
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
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.request.OnBoardingMemberRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;

class OnBoardingMemberIntegrationTest extends IntegrationTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @WithMockUser(memberId = 2)
    @Transactional
    void onBoarding() throws Exception {
        // given
        MemberEntity memberEntity =
                MemberEntity.builder()
                        .email("aaa@naver.com")
                        .password("aaaa1234@@")
                        .checked(false)
                        .build();
        memberRepository.save(memberEntity); // memberId = 2인 member 생성

        // when

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", 61.0, 65.0, 26, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        double height = memberRepository.findById(2L).get().getHeight();
        MemberEntity memberEntity1 = memberRepository.findById(2L).get();
        Double weight = memberEntity1.getWeight();

        Assertions.assertThat(weight).isEqualTo(61.0);
        Assertions.assertThat(height).isEqualTo(171.0);
        resultActions.andExpect(status().isCreated());
        resultActions
                .andExpect(status().isCreated())
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
}
