package com.gaebaljip.exceed.nutritionist.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MealNutrient {

    private Double currentCalorie;
    private Double currentCarbohydrate;
    private Double currentProtein;
    private Double currentFat;
    private LocalDate date;
}