package com.gaebaljip.exceed.dto;

public record GetMealNutritionResponse(
        MaintainMeal maintainMeal,
        TargetMeal targetMeal,
        CurrentMeal currentMeal) {

}
