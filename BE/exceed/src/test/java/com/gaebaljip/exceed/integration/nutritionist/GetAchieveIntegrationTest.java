package com.gaebaljip.exceed.integration.nutritionist;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetCalorieAnalysisResponse;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.exception.meal.MealError;

public class GetAchieveIntegrationTest extends IntegrationTest {
    @Test
    @DisplayName("2023-12-01 08:00:00에 회원가입한 회원이 2023-12월의 월별 달성률 볼 경우 성공")
    @Transactional
    @WithMockUser
    void when_getAchieves_success() throws Exception {
        // given
        LocalDate testData = LocalDate.of(2023, 12, 6);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/achieve/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));
        // then

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        ApiResponse.CustomBody<GetCalorieAnalysisResponse> getAchieveListResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetCalorieAnalysisResponse>>() {});

        int comparedSize =
                getAchieveListResponseCustomBody.getResponse().calorieAnalysisDTOS().stream()
                        .toList()
                        .size();

        Assertions.assertThat(comparedSize).isEqualTo(testData.lengthOfMonth());
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-achieves-success",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("2023-11-01 08:00:00에 회원가입한 회원이 2023-10월의 월별 달성률을 보려고할 때 예외 발생")
    @Transactional
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
