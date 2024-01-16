package com.gaebaljip.exceed.screens.alarm

import com.gaebaljip.exceed.MealTypeEnum

data class AlarmInfo(
    val id: String,
    val hour: Int,
    val minute: Int,
    val mealType: MealTypeEnum,
    val weekFlag: Int,
    val isEnabled: Boolean
) {
    fun translation(info: AlarmInfoEntity): AlarmInfo {
        val enum: MealTypeEnum = when (info.mealType) {
            MealTypeEnum.BREAKFAST.name -> MealTypeEnum.BREAKFAST
            MealTypeEnum.LAUNCH.name -> MealTypeEnum.LAUNCH
            MealTypeEnum.DINNER.name -> MealTypeEnum.DINNER
            MealTypeEnum.SNACK.name -> MealTypeEnum.SNACK
            else -> {
                MealTypeEnum.SNACK
            }
        }
        return AlarmInfo(
            info.id,
            info.hour,
            info.minute,
            enum,
            info.weekFlag,
            info.isEnabled
        )
    }
}

data class AlarmInfoEntity(
    val id: String,
    val hour: Int,
    val minute: Int,
    val mealType: String,
    val weekFlag: Int,
    val isEnabled: Boolean
) {
    fun translation(info: AlarmInfo): AlarmInfoEntity {
        return AlarmInfoEntity(
            info.id,
            info.hour,
            info.minute,
            info.mealType.name,
            info.weekFlag,
            info.isEnabled
        )
    }
}