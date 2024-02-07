package com.gaebaljip.exceed.screens.alarm

import com.gaebaljip.exceed.MealTypeEnum

data class AlarmInfo(
    val id: String,
    val hour: Int,
    val minute: Int,
    val mealType: MealTypeEnum,
    val weekFlag: Int,
    val isEnabled: Boolean,
    val imgDate: String,
    val imgString: String?
)