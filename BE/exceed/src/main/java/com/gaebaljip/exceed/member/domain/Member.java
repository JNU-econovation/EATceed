package com.gaebaljip.exceed.member.domain;

import com.gaebaljip.exceed.member.exception.InvalidGenderException;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Member {

    private double height;
    private int gender;
    private double weight;
    private int age;
    private Activity activity;

    public static Member create(double height, int gender, double weight, int age, Activity activity) {
        return Member.builder()
                .height(height)
                .gender(gender)
                .weight(weight)
                .age(age)
                .activity(activity)
                .build();
    }

    double measureBMR() {
        if (gender == 1) {
            return 66.5 + (13.75 * weight) + (5.003 * height) - (6.75 * age);
        } else if (gender == 2) {
            return 655.1 + (9.563 * weight) + (1.85 * height) - (4.676 * age);
        } else {
            throw new InvalidGenderException();
        }
    }

    public double measureMaintainCarbohydrate() {
        return measureBMR() / 5;
    }

    public double measureMaintainProtein() {
        return measureBMR() / 3;
    }

    public double measureMaintainFat() {
        return measureBMR() / 2;
    }

    public double measureTDEE() {
        return measureBMR() * activity.getValue();
    }

    public double measureTargetCarbohydrate() {
        return measureMaintainCarbohydrate() / 5;
    }

    public double measureTargetProtein() {
        return measureMaintainProtein() / 3;
    }

    public double measureTargetFat() {
        return measureMaintainFat() / 2;
    }

    public double measureTargetCalorie() {
        return measureTDEE() + 500;
    }
}
