package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record NowMonthAnalysisCache(
        LocalDate updatedAt, List<CalorieAnalysisDTO> calorieAnalysisDTOS) {

    public static NowMonthAnalysisCache of(
            LocalDate updatedAt,
            Map<LocalDate, Boolean> calorieAchievementByDate,
            Map<LocalDate, Boolean> visitByDate) {
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
        return new NowMonthAnalysisCache(updatedAt, analysisDTOList);
    }

    public static String write(NowMonthAnalysisCache cache) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        String value = "";
        try {
            value = om.writeValueAsString(cache);
        } catch (JsonProcessingException e) {
            log.error("Object -> String 파싱 에러 : {}", e.getMessage());
        }
        return value;
    }

    public static NowMonthAnalysisCache read(String value) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        NowMonthAnalysisCache response = null;
        try {
            response = om.readValue(value, NowMonthAnalysisCache.class);
        } catch (JsonProcessingException e) {
            log.error("Object -> String 파싱 에러 : {}", e.getMessage());
        }
        return response;
    }

    public static NowMonthAnalysisCache overWrite(
            NowMonthAnalysisCache nowMonthAnalysisCache, CalorieAnalysisDTO calorieAnalysisDTO) {
        List<CalorieAnalysisDTO> updated = new ArrayList<>();
        for (CalorieAnalysisDTO dto : nowMonthAnalysisCache.calorieAnalysisDTOS) {
            if (dto.date().equals(calorieAnalysisDTO.date())) {
                updated.add(calorieAnalysisDTO);
            } else {
                updated.add(dto);
            }
        }
        return new NowMonthAnalysisCache(LocalDate.now(), updated);
    }
}
