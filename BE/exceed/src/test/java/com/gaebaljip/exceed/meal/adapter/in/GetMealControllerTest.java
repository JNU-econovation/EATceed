package com.gaebaljip.exceed.meal.adapter.in;

import com.gaebaljip.exceed.common.CommonApiTest;
import com.gaebaljip.exceed.dto.CurrentMeal;
import com.gaebaljip.exceed.dto.MaintainMeal;
import com.gaebaljip.exceed.dto.TargetMeal;
import com.gaebaljip.exceed.meal.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetMealController.class)
class GetMealControllerTest extends CommonApiTest {

    @MockBean
    private GetMaintainMealUsecase getMaintainMealUsecase;

    @MockBean
    private GetTargetMealUsecase getTargetMealUsecase;

    @MockBean
    private GetCurrentMealQuery getCurrentMealQuery;


    @Test
    void getMealNutrition() throws Exception {

        //given
        MaintainMeal maintainMeal = new MaintainMeal(100.0, 100.0, 100.0, 100.0);
        CurrentMeal currentMeal = new CurrentMeal(100.0, 100.0, 100.0, 100.0);
        TargetMeal targetMeal = new TargetMeal(100.0, 100.0, 100.0, 100.0);

        //when
        Mockito.when(getMaintainMealUsecase.execute(any())).thenReturn(maintainMeal);
        Mockito.when(getTargetMealUsecase.execute(any())).thenReturn(targetMeal);
        Mockito.when(getCurrentMealQuery.execute(any(), any())).thenReturn(currentMeal);

        ResultActions resultActions = mockMvc.perform(get("/v1/meal/2023-12-07/nutrition")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());

    }
}