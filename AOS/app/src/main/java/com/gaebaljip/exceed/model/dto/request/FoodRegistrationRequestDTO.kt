package com.gaebaljip.exceed.model.dto.request

data class FoodRegistrationRequestDTO(
    val multiple: Double,
    val foodIds: List<Int>,
    val mealType: String,
    val fileName: String,
)