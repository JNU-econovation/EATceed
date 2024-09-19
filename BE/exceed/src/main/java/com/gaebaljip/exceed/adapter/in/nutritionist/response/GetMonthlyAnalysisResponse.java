package com.gaebaljip.exceed.adapter.in.nutritionist.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

public record GetMonthlyAnalysisResponse(List<CalorieAnalysisDTO> calorieAnalysisDTOS) {

    public static GetMonthlyAnalysisResponse of(
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
        return new GetMonthlyAnalysisResponse(analysisDTOList);
    }

    public static GetMonthlyAnalysisResponse initFalse(LocalDate date) {
        List<CalorieAnalysisDTO> calorieAnalysisDTOS = new ArrayList<>();
        for (int day = 1; day <= date.lengthOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(date.getYear(), date.getMonth(), day);
            CalorieAnalysisDTO dto =
                    CalorieAnalysisDTO.builder()
                            .isVisited(false)
                            .date(currentDate)
                            .isCalorieAchieved(false)
                            .build();
            calorieAnalysisDTOS.add(dto);
        }

        return new GetMonthlyAnalysisResponse(calorieAnalysisDTOS);
    }

    public static GetMonthlyAnalysisResponse overWrite(
            GetMonthlyAnalysisResponse getMonthlyAnalysisResponse,
            CalorieAnalysisDTO calorieAnalysisDTO) {
        List<CalorieAnalysisDTO> updatedList = new ArrayList<>();

        for (CalorieAnalysisDTO dto : getMonthlyAnalysisResponse.calorieAnalysisDTOS()) {
            if (dto.date().equals(calorieAnalysisDTO.date())) {
                updatedList.add(calorieAnalysisDTO); // 덮어씌우기
            } else {
                updatedList.add(dto); // 기존 값 유지
            }
        }

        return new GetMonthlyAnalysisResponse(updatedList);
    }
}
