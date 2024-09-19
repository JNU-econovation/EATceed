package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetDailyAnalysisCommand;
import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetMonthlyAnalysisCommand;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetAnalysisCommand;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetAnalysisUsecase;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisUsecase;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetMonthlyAnalysisUsecase;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAnalysisService implements GetAnalysisUsecase {

    private final GetDailyAnalysisUsecase getDailyAnalysisUsecase;
    private final GetMonthlyAnalysisUsecase getMonthlyAnalysisUsecase;

    @Override
    public GetMonthlyAnalysisResponse execute(GetAnalysisCommand command) {
        LocalDate nowDate = LocalDate.now();
        if (isAfterYearMonth(command.date(), nowDate)) {
            return GetMonthlyAnalysisResponse.initFalse(command.date());
        }
        if (isBeforeYearMonth(command.date(), nowDate)) {
            return getMonthlyAnalysisUsecase.execute(
                    GetMonthlyAnalysisCommand.of(command.memberId(), command.date()));
        }
        GetMonthlyAnalysisResponse getMonthlyAnalysisResponse =
                getMonthlyAnalysisUsecase.execute(
                        GetMonthlyAnalysisCommand.of(command.memberId(), command.date()));
        CalorieAnalysisDTO calorieAnalysisDTO =
                getDailyAnalysisUsecase.executeToCalorie(
                        GetDailyAnalysisCommand.of(
                                command.memberId(), command.date().atTime(LocalTime.now())));
        return GetMonthlyAnalysisResponse.overWrite(getMonthlyAnalysisResponse, calorieAnalysisDTO);
    }

    private Boolean isAfterYearMonth(LocalDate targetDate, LocalDate referenceDate) {
        return targetDate.getYear() > referenceDate.getYear()
                || (targetDate.getYear() == referenceDate.getYear()
                        && targetDate.getMonthValue() > referenceDate.getMonthValue());
    }

    private Boolean isBeforeYearMonth(LocalDate targetDate, LocalDate referenceDate) {
        return targetDate.getYear() < referenceDate.getYear()
                || (targetDate.getYear() == referenceDate.getYear()
                        && targetDate.getMonthValue() < referenceDate.getMonthValue());
    }
}
