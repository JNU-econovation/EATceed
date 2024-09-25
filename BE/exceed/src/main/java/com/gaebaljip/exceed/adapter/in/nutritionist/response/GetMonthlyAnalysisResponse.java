package com.gaebaljip.exceed.adapter.in.nutritionist.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        return new GetMonthlyAnalysisResponse(
                IntStream.rangeClosed(1, date.lengthOfMonth())
                        .mapToObj(
                                day ->
                                        CalorieAnalysisDTO.from(
                                                LocalDate.of(date.getYear(), date.getMonth(), day)))
                        .toList());
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

    public static String write(GetMonthlyAnalysisResponse response) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        String value = "";
        try {
            value = om.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("Object -> String 파싱 에러 : {}", e.getMessage());
        }
        return value;
    }

    public static GetMonthlyAnalysisResponse read(String value) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        GetMonthlyAnalysisResponse response = null;
        try {
            response = om.readValue(value, GetMonthlyAnalysisResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Object -> String 파싱 에러 : {}", e.getMessage());
        }
        return response;
    }
}
