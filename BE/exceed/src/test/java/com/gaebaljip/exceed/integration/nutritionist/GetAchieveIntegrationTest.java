package com.gaebaljip.exceed.integration.nutritionist;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;
import com.gaebaljip.exceed.common.exception.meal.MealError;

public class GetAchieveIntegrationTest extends IntegrationTest {
    @Test
    @DisplayName(
            "2023-12-01 08:00:00에 회원가입한 회원이 2023-12월의 월별 달성률 볼 경우"
                    + "2023-12월에 먹은 식사 기록이 없기 때문에"
                    + "모든 isVisited는 false"
                    + "모든 isCalorieAchieved는 false")
    @WithMockUser
    void when_getAchieves_expected_allFalse() throws Exception {
        // given
        LocalDate testData = LocalDate.of(2023, 12, 6);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/achieve/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));
        // then

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        ApiResponse.CustomBody<GetMonthlyAnalysisResponse> getAchieveListResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetMonthlyAnalysisResponse>>() {});

        int comparedSize =
                getAnalysisDTOS(getAchieveListResponseCustomBody).stream().toList().size();

        List<Boolean> isCalorieAchievedList =
                getAnalysisDTOS(getAchieveListResponseCustomBody).stream()
                        .map(calorieAnalysisDTO -> calorieAnalysisDTO.isCalorieAchieved())
                        .toList();

        List<Boolean> isVisitedList =
                getAnalysisDTOS(getAchieveListResponseCustomBody).stream()
                        .map(calorieAnalysisDTO -> calorieAnalysisDTO.isVisited())
                        .toList();

        assertAll(
                () -> assertTrue(comparedSize == testData.lengthOfMonth()),
                () ->
                        assertTrue(
                                isCalorieAchievedList.stream().collect(Collectors.toSet()).size()
                                                == 1
                                        && isCalorieAchievedList.get(0) == false),
                () ->
                        assertTrue(
                                isVisitedList.stream().collect(Collectors.toSet()).size() == 1
                                        && isVisitedList.get(0) == false));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName(
            "2023-12-01 08:00:00에 회원가입한 회원이 2024-6월의 월별 달성률 볼 경우"
                    + "응답이 2024-06-01 ~ 2024-06-30까지 존재하는 지 확인")
    @WithMockUser
    void when_getAchieves_success() throws Exception {
        // given
        LocalDate testData = LocalDate.of(2024, 06, 6);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/achieve/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));
        // then

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        ApiResponse.CustomBody<GetMonthlyAnalysisResponse> getAchieveListResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetMonthlyAnalysisResponse>>() {});

        int comparedSize =
                getAnalysisDTOS(getAchieveListResponseCustomBody).stream().toList().size();

        assertAll(() -> assertTrue(comparedSize == testData.lengthOfMonth()));
        resultActions.andExpect(status().isOk());
    }

    private List<CalorieAnalysisDTO> getAnalysisDTOS(
            ApiResponse.CustomBody<GetMonthlyAnalysisResponse> getAchieveListResponseCustomBody) {
        return getAchieveListResponseCustomBody.getResponse().calorieAnalysisDTOS();
    }

    @Test
    @DisplayName(
            "2023-11-01 08:00:00에 회원가입한 회원이 2023-10월의 월별 달성률을 보려고할 경우"
                    + "회원가입 하기 전 기록은 존재하지 않기 때문에 예외 메시지 응답")
    @WithMockUser
    void when_getAchieves_fail() throws Exception {
        // given
        LocalDate testData = LocalDate.of(2023, 10, 6);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/achieve/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value(MealError.INVALID_DATE_FOUND.getReason()));
    }
}
