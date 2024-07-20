package com.gaebaljip.exceed.adapter.in.nutritionist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.application.port.in.nutritionist.GetCalorieAnalysisUsecase;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;

@WebMvcTest(GetAnalysisController.class)
class GetAchieveControllerTest extends ControllerTest {

    @MockBean private GetCalorieAnalysisUsecase getAchieveUsecase;

    @Test
    @DisplayName("분석 조회 성공" + "요청 시 날짜 포맷 확인")
    @WithMockUser
    void when_getAnalysis_expected_success() throws Exception {

        LocalDate date = LocalDate.of(2023, 12, 8);

        ResultActions resultActions =
                mockMvc.perform(get("/v1/achieve/" + date).contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }
}
