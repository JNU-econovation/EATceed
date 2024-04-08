package com.gaebaljip.exceed.meal.adapter.in;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.common.CommonApiTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.dto.response.MaintainMeal;
import com.gaebaljip.exceed.dto.response.TargetMeal;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;

@WebMvcTest(GetMealController.class)
class SpecificMealControllerTest extends CommonApiTest {

    @MockBean private GetMaintainMealUsecase getMaintainMealUsecase;

    @MockBean private GetTargetMealUsecase getTargetMealUsecase;

    @MockBean private GetCurrentMealQuery getCurrentMealQuery;

    @MockBean private GetSpecificMealQuery getSpecificMealQuery;

    @Test
    @WithMockUser
    void getMealNutrition() throws Exception {

        // given
        MaintainMeal maintainMeal = new MaintainMeal(100.0, 100.0, 100.0, 100.0);
        CurrentMeal currentMeal = new CurrentMeal(100.0, 100.0, 100.0, 100.0);
        TargetMeal targetMeal = new TargetMeal(100.0, 100.0, 100.0, 100.0);

        // when
        Mockito.when(getMaintainMealUsecase.execute(any())).thenReturn(maintainMeal);
        Mockito.when(getTargetMealUsecase.execute(any())).thenReturn(targetMeal);
        Mockito.when(getCurrentMealQuery.execute(any())).thenReturn(currentMeal);

        ResultActions resultActions =
                mockMvc.perform(get("/v1/meal").contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }
}
