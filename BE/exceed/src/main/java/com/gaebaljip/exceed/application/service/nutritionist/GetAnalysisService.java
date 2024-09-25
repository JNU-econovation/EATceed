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
        if (isAfterYearMonth(command.requestDate(), command.nowDate())) {
            return GetMonthlyAnalysisResponse.initFalse(command.requestDate());
        }
        if (isBeforeYearMonth(command.requestDate(), command.nowDate())) {
            return GetMonthlyAnalysisResponse.read(
                    getMonthlyAnalysisUsecase.execute(
                            GetMonthlyAnalysisCommand.of(
                                    command.memberId(), command.requestDate())));
        }
        GetMonthlyAnalysisResponse getMonthlyAnalysisResponse =
                GetMonthlyAnalysisResponse.read(
                        getMonthlyAnalysisUsecase.execute(
                                GetMonthlyAnalysisCommand.of(
                                        command.memberId(), command.requestDate())));
        CalorieAnalysisDTO calorieAnalysisDTO =
                getDailyAnalysisUsecase.executeToCalorie(
                        GetDailyAnalysisCommand.of(
                                command.memberId(), command.nowDate().atTime(LocalTime.now())));
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
