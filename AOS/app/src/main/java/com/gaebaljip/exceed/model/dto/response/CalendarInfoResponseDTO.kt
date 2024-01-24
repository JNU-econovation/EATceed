package com.gaebaljip.exceed.model.dto.response

data class CalendarInfoResponseDTO(
    val getAchieves: List<CalendarAchieveInfoDTO>
)

data class CalendarAchieveInfoDTO(
    val isVisited: Boolean,
    val date: String,
    val calorieRate: Double,
    val proteinAchieve: Boolean,
    val fatAchieve: Boolean,
    val carbohydrateAchieve: Boolean
)