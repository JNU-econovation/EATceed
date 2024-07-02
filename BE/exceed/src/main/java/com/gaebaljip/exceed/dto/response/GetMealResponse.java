package com.gaebaljip.exceed.dto.response;

public record GetMealResponse(
        MaintainMeal maintainMeal, TargetMeal targetMeal, CurrentMeal currentMeal) {}
