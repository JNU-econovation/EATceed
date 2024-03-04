package com.gaebaljip.exceed.meal.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockGuestUser;
import com.gaebaljip.exceed.dto.response.GetMealFoodResponse;
import com.gaebaljip.exceed.dto.response.GetMealResponse;
import com.gaebaljip.exceed.meal.application.port.out.PresignedUrlPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpecificMealIntegrationTest extends IntegrationTest {

    @MockBean
    private PresignedUrlPort getPresignedUrlPort;

    @Test
    @WithMockGuestUser
    void getMeal() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/meal")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        //then
        ApiResponse.CustomBody<GetMealResponse> getMealResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetMealResponse>>() {
        });
        Double maintainCalorie = getMealResponseCustomBody.getResponse().maintainMeal().calorie();
        Double targetCalorie = getMealResponseCustomBody.getResponse().targetMeal().calorie();

        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        resultActions.andExpect(status().isOk())
                .andDo(document("get-meal-success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("response.maintainMeal.calorie").type(JsonFieldType.NUMBER).description("유지 칼로리"),
                                fieldWithPath("response.maintainMeal.carbohydrate").type(JsonFieldType.NUMBER).description("유지 탄수화물"),
                                fieldWithPath("response.maintainMeal.protein").type(JsonFieldType.NUMBER).description("유지 단백질"),
                                fieldWithPath("response.maintainMeal.fat").type(JsonFieldType.NUMBER).description("유지 지방"),
                                fieldWithPath("response.targetMeal.calorie").type(JsonFieldType.NUMBER).description("목표 칼로리"),
                                fieldWithPath("response.targetMeal.carbohydrate").type(JsonFieldType.NUMBER).description("목표 탄수화물"),
                                fieldWithPath("response.targetMeal.protein").type(JsonFieldType.NUMBER).description("목표 단백질"),
                                fieldWithPath("response.targetMeal.fat").type(JsonFieldType.NUMBER).description("목표 지방"),
                                fieldWithPath("response.currentMeal.calorie").type(JsonFieldType.NUMBER).description("현재 칼로리"),
                                fieldWithPath("response.currentMeal.carbohydrate").type(JsonFieldType.NUMBER).description("현재 탄수화물"),
                                fieldWithPath("response.currentMeal.protein").type(JsonFieldType.NUMBER).description("현재 단백질"),
                                fieldWithPath("response.currentMeal.fat").type(JsonFieldType.NUMBER).description("현재 지방"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러 정보")
                        )));
    }

    @Test
    @WithMockGuestUser
    void getMealFood() throws Exception {

        given(getPresignedUrlPort.query(any(Long.class), any(Long.class))).willReturn("http://test.com/test.jpeg");

        //when
        String date = "2024-03-04";
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/meal/" + date)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ApiResponse.CustomBody<GetMealFoodResponse> getMealFoodResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetMealFoodResponse>>() {
        });
        Double maintainCalorie = getMealFoodResponseCustomBody.getResponse().getMealResponse().maintainMeal().calorie();
        Double targetCalorie = getMealFoodResponseCustomBody.getResponse().getMealResponse().targetMeal().calorie();
        int size = getMealFoodResponseCustomBody.getResponse().mealRecords().size();

        //then
        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        Assertions.assertThat(size).isGreaterThan(0);
        resultActions.andExpect(status().isOk())
                .andDo(document("get-meal-food-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
