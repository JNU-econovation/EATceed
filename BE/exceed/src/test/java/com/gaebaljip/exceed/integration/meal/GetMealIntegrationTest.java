package com.gaebaljip.exceed.integration.meal;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.adapter.in.meal.response.GetMealFoodResponse;
import com.gaebaljip.exceed.adapter.in.member.response.GetMealAndWeightResponse;
import com.gaebaljip.exceed.adapter.out.jpa.member.MemberRepository;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.InitializeS3Bucket;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.MealRecordDTO;
import com.gaebaljip.exceed.common.exception.meal.MealError;
import com.gaebaljip.exceed.common.exception.member.MemberError;

@InitializeS3Bucket
public class GetMealIntegrationTest extends IntegrationTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("성공 : 오늘 먹은 식사 조회")
    @WithMockUser
    void when_getTodayMeal_expected_success() throws Exception {
        // given
        long memberId = 1L;

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal")
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        MemberEntity member = memberRepository.findById(memberId).get();

        // then
        ApiResponse.CustomBody<GetMealAndWeightResponse> getMealResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetMealAndWeightResponse>>() {});
        Double maintainCalorie =
                getMealResponseCustomBody.getResponse().maintainMealDTO().calorie();
        Double targetCalorie = getMealResponseCustomBody.getResponse().targetMealDTO().calorie();

        assertAll(
                () -> assertTrue(maintainCalorie > 0),
                () -> assertTrue(targetCalorie > maintainCalorie),
                () -> assertEquals(member.getWeight(), 76.0),
                () -> assertEquals(member.getTargetWeight(), 78.0));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("성공 : 2024년 6월 12일 기준 식사 조회" + "isVisited는 true이고, 식사 기록 또한 존재")
    @WithMockUser
    void when_getSpecificMeal_expected_success() throws Exception {

        LocalDate testData = LocalDate.of(2024, 6, 12);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ApiResponse.CustomBody<GetMealFoodResponse> getMealFoodResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetMealFoodResponse>>() {});
        Double maintainCalorie =
                getMealFoodResponseCustomBody
                        .getResponse()
                        .getMealResponse()
                        .maintainMealDTO()
                        .calorie();
        Double targetCalorie =
                getMealFoodResponseCustomBody
                        .getResponse()
                        .getMealResponse()
                        .targetMealDTO()
                        .calorie();

        List<MealRecordDTO> mealRecordDTOS =
                getMealFoodResponseCustomBody.getResponse().mealRecordDTOS();

        AllAnalysisDTO allAnalysisDTO =
                getMealFoodResponseCustomBody.getResponse().allAnalysisDTO();

        // then

        assertAll(
                () -> assertEquals(allAnalysisDTO.isVisited(), true),
                () -> assertTrue(maintainCalorie > 0),
                () -> assertTrue(targetCalorie > maintainCalorie),
                () -> assertTrue(mealRecordDTOS.size() >= 1));
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-meal-food-success",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("성공 : 2023년 12월 05일 기준 식사 조회" + "isVisited는 false이고, 식사 기록 존재하지 않는다.")
    @WithMockUser
    void when_getSpecificMeal_expected_isVisited_false_mealRecord_existed() throws Exception {

        LocalDate testData = LocalDate.of(2025, 12, 05);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ApiResponse.CustomBody<GetMealFoodResponse> getMealFoodResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetMealFoodResponse>>() {});
        AllAnalysisDTO allAnalysisDTO =
                getMealFoodResponseCustomBody.getResponse().allAnalysisDTO();

        List<MealRecordDTO> mealRecordDTOS =
                getMealFoodResponseCustomBody.getResponse().mealRecordDTOS();

        assertAll(
                () -> assertEquals(allAnalysisDTO.isVisited(), false),
                () -> assertTrue(mealRecordDTOS.size() == 0));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("실패 : 2023년 11월 1일 기준 식사 조회" + "회원가입 전이기 때문에 오류 발생")
    @WithMockUser
    void when_getSpecificMeal_createdAt_expected_InValidDateFoundException() throws Exception {

        MemberEntity memberEntity = memberRepository.findById(1L).get();
        LocalDate testData = memberEntity.getCreatedDate().toLocalDate();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.code").value(MealError.INVALID_DATE_FOUND.getCode()));
    }

    @Test
    @DisplayName("실패 : 회원가입 후 2일 후에 온보딩할 경우" + "예외 발생")
    @Sql("classpath:db/testData_signup_after_2days_onboarding.sql")
    @WithMockUser(memberId = 1L)
    void when_signUp_onBoarding_after_2_days_user_getSpecificMea_updatedAt() throws Exception {
        // given
        MemberEntity memberEntity = memberRepository.findById(1L).get();
        LocalDate testData = memberEntity.getUpdatedDate().toLocalDate();

        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().is5xxServerError(),
                jsonPath("$.error.code").value(MemberError.HISTORY_NOT_FOUND.getCode()));
    }
}
