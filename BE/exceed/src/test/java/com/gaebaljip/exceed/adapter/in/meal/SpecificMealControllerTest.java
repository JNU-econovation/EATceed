package com.gaebaljip.exceed.adapter.in.meal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.application.port.in.meal.GetCurrentMealQuery;
import com.gaebaljip.exceed.application.port.in.meal.GetSpecificMealQuery;
import com.gaebaljip.exceed.application.port.in.member.GetMaintainMealUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetTargetMealUsecase;
import com.gaebaljip.exceed.application.service.nutritionist.GetAllCalorieAnalysisService;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.MaintainMealDTO;
import com.gaebaljip.exceed.common.dto.TargetMealDTO;

@WebMvcTest(GetMealController.class)
class SpecificMealControllerTest extends ControllerTest {

    @MockBean private GetMaintainMealUsecase getMaintainMealUsecase;

    @MockBean private GetTargetMealUsecase getTargetMealUsecase;

    @MockBean private GetCurrentMealQuery getCurrentMealQuery;

    @MockBean private GetSpecificMealQuery getSpecificMealQuery;

    @MockBean private GetAllCalorieAnalysisService getAllCalorieAnalysisService;

    @Test
    @WithMockUser
    void getMealNutrition() throws Exception {

        // given
        MaintainMealDTO maintainMealDTO = new MaintainMealDTO(100.0, 100.0, 100.0, 100.0);
        CurrentMealDTO currentMealDTO = new CurrentMealDTO(100.0, 100.0, 100.0, 100.0);
        TargetMealDTO targetMealDTO = new TargetMealDTO(100.0, 100.0, 100.0, 100.0);

        // when
        Mockito.when(getMaintainMealUsecase.execute(any())).thenReturn(maintainMealDTO);
        Mockito.when(getTargetMealUsecase.execute(any())).thenReturn(targetMealDTO);
        Mockito.when(getCurrentMealQuery.execute(any())).thenReturn(currentMealDTO);

        ResultActions resultActions =
                mockMvc.perform(get("/v1/meal").contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }
}
