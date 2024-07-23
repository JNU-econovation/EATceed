package com.gaebaljip.exceed.adapter.in.member;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.adapter.in.member.request.OnBoardingMemberRequest;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.WithMockUser;

class OnBoardingControllerTest extends ControllerTest {

    @Test
    @DisplayName("온보딩 성공")
    @WithMockUser
    void when_onBoarding_expected_success() throws Exception {
        // given

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", 61.0, 65.0, 26, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("온보딩-유효하지 않은 activity 입력시 오류 발생")
    @WithMockUser
    void when_onBoarding_invalidActivity_expected_exception() throws Exception {
        // given

        String invalidValue = "ACTIVE";

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", 61.0, 65.0, 26, invalidValue, "뭐든 잘 먹습니다.");

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.error.reason").value(invalidValue + "는 올바르지 않은 값입니다."));
        resultActions.andDo(document("onBoarding-fail"));
    }

    @Test
    @DisplayName("온보딩 실패 - 키가 null일 때")
    @WithMockUser
    void when_onBoarding_NullHeight_expected_exception() throws Exception {
        // given

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        null, "MALE", 61.0, 65.0, 26, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("키를 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("온보딩 실패 - 몸무게가 null일 때")
    @WithMockUser
    void when_onBoarding_nullWeight_expected_exception() throws Exception {
        // given

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", null, 65.0, 26, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("몸무게를 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("온보딩 실패 - 목표 몸무게가 null일 때")
    @WithMockUser
    void when_onBoarding_nullTargetWeight_expected_exception() throws Exception {
        // given

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", 61.0, null, 26, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("목표 몸무게를 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("온보딩 실패 - 나이가 null일 때")
    @WithMockUser
    void when_onBoarding_nullAge_expected_exception() throws Exception {
        // given

        OnBoardingMemberRequest request =
                new OnBoardingMemberRequest(
                        171.0, "MALE", 61.0, 65.0, null, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/members/detail")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("나이를 " + ValidationMessage.NOT_NULL));
    }
}
