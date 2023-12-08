package com.gaebaljip.exceed.dto;

public record GetMealResponse(
        MaintainMeal maintainMeal,
        TargetMeal targetMeal,
        CurrentMeal currentMeal) {

}
