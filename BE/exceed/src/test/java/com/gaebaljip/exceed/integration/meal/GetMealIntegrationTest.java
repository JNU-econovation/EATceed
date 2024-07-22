package com.gaebaljip.exceed.integration.meal;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.adapter.in.meal.response.GetMealFoodResponse;
import com.gaebaljip.exceed.adapter.in.meal.response.GetMealResponse;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.InitializeS3Bucket;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.MealRecordDTO;

@InitializeS3Bucket
public class GetMealIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("성공 : 오늘 먹은 식사 조회")
    @WithMockUser
    void when_getTodayMeal_expected_success() throws Exception {
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal")
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        // then
        ApiResponse.CustomBody<GetMealResponse> getMealResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetMealResponse>>() {});
        Double maintainCalorie =
                getMealResponseCustomBody.getResponse().maintainMealDTO().calorie();
        Double targetCalorie = getMealResponseCustomBody.getResponse().targetMealDTO().calorie();

        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-meal-success",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("success")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("성공 여부"),
                                        fieldWithPath("response.maintainMealDTO.calorie")
                                                .type(JsonFieldType.NUMBER)
                                                .description("유지 칼로리"),
                                        fieldWithPath("response.maintainMealDTO.carbohydrate")
                                                .type(JsonFieldType.NUMBER)
                                                .description("유지 탄수화물"),
                                        fieldWithPath("response.maintainMealDTO.protein")
                                                .type(JsonFieldType.NUMBER)
                                                .description("유지 단백질"),
                                        fieldWithPath("response.maintainMealDTO.fat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("유지 지방"),
                                        fieldWithPath("response.targetMealDTO.calorie")
                                                .type(JsonFieldType.NUMBER)
                                                .description("목표 칼로리"),
                                        fieldWithPath("response.targetMealDTO.carbohydrate")
                                                .type(JsonFieldType.NUMBER)
                                                .description("목표 탄수화물"),
                                        fieldWithPath("response.targetMealDTO.protein")
                                                .type(JsonFieldType.NUMBER)
                                                .description("목표 단백질"),
                                        fieldWithPath("response.targetMealDTO.fat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("목표 지방"),
                                        fieldWithPath("response.currentMealDTO.calorie")
                                                .type(JsonFieldType.NUMBER)
                                                .description("현재 칼로리"),
                                        fieldWithPath("response.currentMealDTO.carbohydrate")
                                                .type(JsonFieldType.NUMBER)
                                                .description("현재 탄수화물"),
                                        fieldWithPath("response.currentMealDTO.protein")
                                                .type(JsonFieldType.NUMBER)
                                                .description("현재 단백질"),
                                        fieldWithPath("response.currentMealDTO.fat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("현재 지방"),
                                        fieldWithPath("error")
                                                .type(JsonFieldType.NULL)
                                                .description("에러 정보"))));
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

        LocalDate testData = LocalDate.of(2023, 12, 05);

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
    void when_getSpecificMeal_expected_InValidDateFoundException() throws Exception {

        LocalDate testData = LocalDate.of(2023, 11, 01);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/meal/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(status().isBadRequest(), jsonPath("$.error.code").value(6321));
    }
}
