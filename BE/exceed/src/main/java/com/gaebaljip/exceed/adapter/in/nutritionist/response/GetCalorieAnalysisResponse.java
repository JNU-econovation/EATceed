package com.gaebaljip.exceed.adapter.in.nutritionist.response;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

public record GetCalorieAnalysisResponse(List<CalorieAnalysisDTO> calorieAnalysisDTOS) {

    public static GetCalorieAnalysisResponse of(
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
        return new GetCalorieAnalysisResponse(analysisDTOList);
    }
}
