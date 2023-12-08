package com.gaebaljip.exceed.achieve.domain;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@Builder(toBuilder = true)
public class Achieve {

    public boolean evaluateProteinAchieve(double currentProtein, double targetProtein) {
        return targetProtein - currentProtein <= 0;
    }

    public boolean evaluateFatAchieve(double currentFat, double targetFat) {
        return targetFat - currentFat <= 0;
    }

    public boolean evaluateCarbohydrateAchieve(double currentCarbohydrate, double targetCarbohydrate) {
        return targetCarbohydrate - currentCarbohydrate <= 0;
    }

    public double calculateCalorieAchieveRate(double currentCalorie, double targetCalorie) {
        return currentCalorie / targetCalorie;
    }

}
