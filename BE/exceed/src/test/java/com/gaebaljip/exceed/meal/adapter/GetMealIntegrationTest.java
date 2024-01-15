package com.gaebaljip.exceed.meal.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockGuestUser;
import com.gaebaljip.exceed.dto.response.GetMealFoodResponse;
import com.gaebaljip.exceed.dto.response.GetMealResponse;
import com.gaebaljip.exceed.meal.application.port.out.GetPresignedUrlPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetMealIntegrationTest extends IntegrationTest {

    @MockBean
    private GetPresignedUrlPort getPresignedUrlPort;

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
                .andDo(document("get-meal-success"));
    }

    @Test
    @WithMockGuestUser
    void getMealFood() throws Exception {

        given(getPresignedUrlPort.command(any(Long.class), any(Long.class))).willReturn("http://test.com/test.jpeg");

        //when
        String date = "2023-12-10";
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/meal/" + date)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ApiResponse.CustomBody<GetMealFoodResponse> getMealFoodResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetMealFoodResponse>>() {
        });
        Double maintainCalorie = getMealFoodResponseCustomBody.getResponse().getMealResponse().maintainMeal().calorie();
        Double targetCalorie = getMealFoodResponseCustomBody.getResponse().getMealResponse().targetMeal().calorie();
        int size = getMealFoodResponseCustomBody.getResponse().dailyMeals().size();

        //then
        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        Assertions.assertThat(size).isGreaterThan(0);
        resultActions.andExpect(status().isOk())
                .andDo(document("get-meal-food-success"));;
    }
}
