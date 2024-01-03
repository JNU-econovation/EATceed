package com.gaebaljip.exceed.model.dto.response.common

data class MaintainMeal(
    val maintainCalorie: Double,
    val maintainCarbohydrate: Double,
    val maintainProtein: Double,
    val maintainFat: Double,
)

data class TargetMeal(
    val targetCalorie: Double,
    val targetCarbohydrate: Double,
    val targetProtein: Double,
    val targetFat: Double
)

data class CurrentMeal(
    val currentCalorie: Double,
    val currentCarbohydrate: Double,
    val currentProtein: Double,
    val currentFat: Double
)

data class MealNutrition(
    val maintainMeal: MaintainMeal,
    val targetMeal: TargetMeal,
    val currentMeal: CurrentMeal
)