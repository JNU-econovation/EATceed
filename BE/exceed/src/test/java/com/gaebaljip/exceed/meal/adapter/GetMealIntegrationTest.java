package com.gaebaljip.exceed.meal.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.dto.response.GetMealFoodResponse;
import com.gaebaljip.exceed.dto.response.GetMealResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetMealIntegrationTest extends IntegrationTest {

    @Test
    void getMeal() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/meal")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        //then
        ApiResponse.CustomBody<GetMealResponse> getMealResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetMealResponse>>() {
        });
        Double maintainCalorie = getMealResponseCustomBody.getResponse().maintainMeal().maintainCalorie();
        Double targetCalorie = getMealResponseCustomBody.getResponse().targetMeal().targetCalorie();

        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        resultActions.andExpect(status().isOk());
    }

    @Test
    void getMealFood() throws Exception {

        //when
        String date = "2023-12-10";
        ResultActions resultActions = mockMvc.perform(get("/v1/meal/" + date)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        ApiResponse.CustomBody<GetMealFoodResponse> getMealFoodResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetMealFoodResponse>>() {
        });
        Double maintainCalorie = getMealFoodResponseCustomBody.getResponse().getMealResponse().maintainMeal().maintainCalorie();
        Double targetCalorie = getMealFoodResponseCustomBody.getResponse().getMealResponse().targetMeal().targetCalorie();
        int size = getMealFoodResponseCustomBody.getResponse().dailyMeals().size();
        //then
        Assertions.assertThat(maintainCalorie).isGreaterThan(0);
        Assertions.assertThat(targetCalorie).isGreaterThan(maintainCalorie);
        Assertions.assertThat(size).isGreaterThan(0);
        resultActions.andExpect(status().isOk());
    }
}
