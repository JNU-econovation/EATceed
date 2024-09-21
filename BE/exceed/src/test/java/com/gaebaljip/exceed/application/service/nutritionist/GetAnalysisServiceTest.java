package com.gaebaljip.exceed.application.service.nutritionist;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetAnalysisCommand;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisUsecase;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetMonthlyAnalysisUsecase;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

@ExtendWith(MockitoExtension.class)
class GetAnalysisServiceTest {

    @Mock private GetDailyAnalysisUsecase getDailyAnalysisUsecase;
    @Mock private GetMonthlyAnalysisUsecase getMonthlyAnalysisUsecase;

    @InjectMocks private GetAnalysisService getAnalysisService;

    @Test
    void when_sameYearMonth_overwrite_getMonthlyAnalysisResponse() throws JsonProcessingException {
        GetAnalysisCommand command =
                GetAnalysisCommand.of(LocalDate.of(2024, 6, 1), 1L, LocalDate.of(2024, 06, 26));

        CalorieAnalysisDTO calorieAnalysisDTO =
                createCalorieAnalysisDTO(LocalDate.of(2024, 6, 26), true);
        given(getDailyAnalysisUsecase.executeToCalorie(any())).willReturn(calorieAnalysisDTO);

        String value =
                GetMonthlyAnalysisResponse.write(
                        createMonthlyAnalysisResponse(
                                LocalDate.of(2024, 6, 1),
                                LocalDate.of(2024, 6, 30),
                                LocalDate.of(2024, 6, 25)));

        given(getMonthlyAnalysisUsecase.execute(any())).willReturn(value);

        GetMonthlyAnalysisResponse getMonthlyAnalysisResponse = getAnalysisService.execute(command);

        assertAll(
                () ->
                        assertTrue(
                                getMonthlyAnalysisResponse
                                        .calorieAnalysisDTOS()
                                        .get(25)
                                        .isCalorieAchieved()),
                () ->
                        assertTrue(
                                getMonthlyAnalysisResponse
                                        .calorieAnalysisDTOS()
                                        .get(25)
                                        .isVisited()));
    }

    public GetMonthlyAnalysisResponse createMonthlyAnalysisResponse(
            LocalDate startDate, LocalDate endDate, LocalDate boundDate) {
        List<CalorieAnalysisDTO> calorieAnalysisList =
                Stream.iterate(startDate, date -> date.plusDays(1))
                        .limit(startDate.until(endDate).getDays() + 1) // 날짜 범위 설정
                        .map(
                                date -> {
                                    boolean isVisited = !date.isAfter(boundDate);
                                    return createCalorieAnalysisDTO(date, isVisited);
                                })
                        .toList();
        return new GetMonthlyAnalysisResponse(calorieAnalysisList);
    }

    public CalorieAnalysisDTO createCalorieAnalysisDTO(LocalDate date, Boolean isTrue) {
        boolean isCalorieAchieved = isTrue;
        return new CalorieAnalysisDTO(isTrue, date, isCalorieAchieved);
    }
}
