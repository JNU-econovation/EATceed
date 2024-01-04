package com.gaebaljip.exceed.model.dto.response

import com.gaebaljip.exceed.model.dto.response.common.MealNutrition

data class CalendarDetailResponseDTO(
    val getMealResponse: MealNutrition,
    val dailyMeals: List<DailyMealInfo>
)

data class DailyMealInfo(
    val time: String,
    val mealType: String,
    val imageUri: String,
    val foods: List<FoodInfo>
)

data class FoodInfo(
    val id: Int,
    val name: Int,
)
