package com.gaebaljip.exceed.integration.meal;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.application.port.out.meal.PresignedUrlPort;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.dto.response.GetMealFoodResponse;
import com.gaebaljip.exceed.dto.response.GetMealResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GetMealIntegrationTest extends IntegrationTest {

    @MockBean private PresignedUrlPort getPresignedUrlPort;

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
    @DisplayName("성공 : 특정 날짜 식사 조회"
    + "특정 날짜에 방문하지 않았을 경우 isVisited가 false이고, 달성도 다 false로 나와야 한다.")
    @WithMockUser
    void when_getSpecificMeal_expected_success() throws Exception {

        LocalDate testData = LocalDate.of(2024, 6, 6);

        given(getPresignedUrlPort.query(any(Long.class), any(Long.class)))
                .willReturn("http://test.com/test.jpeg");

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

        AllAnalysisDTO allAnalysisDTO =
                getMealFoodResponseCustomBody.getResponse().allAnalysisDTO();

        // then

        boolean isVisited = allAnalysisDTO.isVisited();
        if (!isVisited) {
            assertFalse(allAnalysisDTO.isCalorieAchieved(), "CalorieAchieved가 true입니다.");
            assertFalse(allAnalysisDTO.isProteinAchieved(), "ProteinAchieved가 true입니다.");
            assertFalse(allAnalysisDTO.isFatAchieved(), "FatAchieved가 true입니다.");
            assertFalse(allAnalysisDTO.isCarbohydrateAchieved(), "CarbohydrateAchieved가 true입니다.");
        }
        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-meal-food-success",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
