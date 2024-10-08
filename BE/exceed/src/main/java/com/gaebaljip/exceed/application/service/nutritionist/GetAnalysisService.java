package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;
import com.gaebaljip.exceed.application.port.in.nutritionist.*;
import com.gaebaljip.exceed.common.dto.GetDaysAnalysisDTO;
import com.gaebaljip.exceed.common.dto.NowMonthAnalysisCache;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAnalysisService implements GetAnalysisUsecase {

    private final GetDaysAnalysisUsecase getDaysAnalysisUsecase;
    private final GetMonthlyAnalysisUsecase getMonthlyAnalysisUsecase;

    @Override
    public GetMonthlyAnalysisResponse execute(GetAnalysisCommand command) {
        if (isAfterYearMonth(command.requestDate(), command.nowDate())) {
            return GetMonthlyAnalysisResponse.initFalse(command.requestDate());
        }
        if (isBeforeYearMonth(command.requestDate(), command.nowDate())) {
            return GetMonthlyAnalysisResponse.read(
                    getMonthlyAnalysisUsecase.executeToPast(
                            GetMonthlyAnalysisCommand.of(
                                    command.memberId(), command.requestDate())));
        }
        NowMonthAnalysisCache nowMonthAnalysisCache =
                NowMonthAnalysisCache.read(
                        getMonthlyAnalysisUsecase.executeToNow(
                                GetMonthlyAnalysisCommand.of(
                                        command.memberId(), command.requestDate())));
        GetDaysAnalysisDTO getDaysAnalysisDTO =
                getDaysAnalysisUsecase.execute(
                        GetDaysAnalysisCommand.of(
                                command.memberId(),
                                nowMonthAnalysisCache.updatedAt().atStartOfDay(),
                                command.nowDate().atTime(LocalTime.now())));
        return GetMonthlyAnalysisResponse.overWrite(
                nowMonthAnalysisCache.calorieAnalysisDTOS(),
                getDaysAnalysisDTO.calorieAnalysisDTOS());
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
