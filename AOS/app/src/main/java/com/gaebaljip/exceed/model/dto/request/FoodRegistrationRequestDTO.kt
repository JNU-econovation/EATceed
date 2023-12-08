package com.gaebaljip.exceed.model.dto.request

import android.health.connect.datatypes.MealType

data class FoodRegistrationRequestDTO(
    val multiple: Double,
    val foodId: Int,
    val mealType: String,
)