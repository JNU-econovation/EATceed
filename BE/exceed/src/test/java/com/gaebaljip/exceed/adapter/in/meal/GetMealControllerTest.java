package com.gaebaljip.exceed.adapter.in.meal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.MaintainMealDTO;
import com.gaebaljip.exceed.common.dto.TargetMealDTO;

class GetMealControllerTest extends ControllerTest {

    @Test
    @WithMockUser
    void when_getMeal_expected_success() throws Exception {

        // given
        MaintainMealDTO maintainMealDTO = new MaintainMealDTO(100.22, 100.22, 100.22, 100.22);
        CurrentMealDTO currentMealDTO = new CurrentMealDTO(100.666, 100.666, 100.666, 100.666);
        TargetMealDTO targetMealDTO = new TargetMealDTO(100.444, 100.444, 100.444, 100.444);

        // when
        Mockito.when(getMaintainNutritionUsecase.execute(any())).thenReturn(maintainMealDTO);
        Mockito.when(getTargetNutritionUsecase.execute(any())).thenReturn(targetMealDTO);
        Mockito.when(getCurrentMealQuery.execute(any())).thenReturn(currentMealDTO);

        ResultActions resultActions =
                mockMvc.perform(get("/v1/meal").contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.response.maintainMealDTO.calorie").value(100.22),
                jsonPath("$.response.currentMealDTO.protein").value(100.67),
                jsonPath("$.response.targetMealDTO.fat").value(100.44));
    }
}
