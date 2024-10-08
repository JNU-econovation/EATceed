package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record GetDaysAnalysisDTO(List<CalorieAnalysisDTO> calorieAnalysisDTOS) {
    public static GetDaysAnalysisDTO of(
            Map<LocalDate, Boolean> calorieAchievementByDate, Map<LocalDate, Boolean> visitByDate) {
        List<CalorieAnalysisDTO> analysisDTOList =
                calorieAchievementByDate.keySet().stream()
                        .sorted()
                        .map(
                                date ->
                                        new CalorieAnalysisDTO(
                                                visitByDate.get(date),
                                                date,
                                                calorieAchievementByDate.get(date)))
                        .toList();
        return new GetDaysAnalysisDTO(analysisDTOList);
    }
}
