package com.gaebaljip.exceed.achieve.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AchieveModel {

    private List<DailyRecord> dailyRecords;
    private DailyTarget dailyTarget;

    public static AchieveModel createAchieveModel(List<DailyRecord> dailyRecords, DailyTarget dailyTarget) {
        return AchieveModel.builder()
                .dailyRecords(dailyRecords)
                .dailyTarget(dailyTarget)
                .build();
    }

    public boolean evaluateProteinAchieve(LocalDate date) {
        return dailyTarget.getTargetProtein() - getCurrentProtein(date) <= 0;
    }

    public boolean evaluateFatAchieve(LocalDate date) {
        return dailyTarget.getTargetProtein() - getCurrentFat(date) <= 0;
    }

    public boolean evaluateCarbohydrateAchieve(LocalDate date) {
        return dailyTarget.getTargetCarbohydrate() - getCurrentCarbohydrate(date) <= 0;
    }

    public double calculateCalorieAchieveRate(LocalDate date) {
        return getCurrentCalorie(date) / dailyTarget.getTargetCalorie();
    }

    private Double getCurrentProtein(LocalDate date) {
        return getRecord(date)
                .map(DailyRecord::getCurrentProtein)
                .orElse(0.0);
    }

    private Double getCurrentFat(LocalDate date) {
        return getRecord(date)
                .map(DailyRecord::getCurrentFat)
                .orElse(0.0);
    }

    private Double getCurrentCarbohydrate(LocalDate date) {
        return getRecord(date)
                .map(DailyRecord::getCurrentCarbohydrate)
                .orElse(0.0);
    }

    private Double getCurrentCalorie(LocalDate date) {
        return getRecord(date)
                .map(DailyRecord::getCurrentCalorie)
                .orElse(0.0);
    }

    private Optional<DailyRecord> getRecord(LocalDate date) {
        return dailyRecords.stream()
                .filter(dailyRecord -> date.equals(dailyRecord.getDate()))
                .findFirst();
    }


}
