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

import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.response.CurrentMealDTO;
import com.gaebaljip.exceed.dto.response.MaintainMealDTO;
import com.gaebaljip.exceed.dto.response.TargetMealDTO;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;

@WebMvcTest(GetMealController.class)
class SpecificMealDTOControllerTest extends ControllerTest {

    @MockBean private GetMaintainMealUsecase getMaintainMealUsecase;

    @MockBean private GetTargetMealUsecase getTargetMealUsecase;

    @MockBean private GetCurrentMealQuery getCurrentMealQuery;

    @MockBean private GetSpecificMealQuery getSpecificMealQuery;

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
