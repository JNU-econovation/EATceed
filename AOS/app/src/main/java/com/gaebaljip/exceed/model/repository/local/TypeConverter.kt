package com.gaebaljip.exceed.model.repository.local

import androidx.room.TypeConverter
import com.gaebaljip.exceed.MealTypeEnum

class TypeConverter {
    @TypeConverter
    fun toMealType(string: String): MealTypeEnum {
        val enum: MealTypeEnum = when (string) {
            MealTypeEnum.BREAKFAST.name -> MealTypeEnum.BREAKFAST
            MealTypeEnum.LUNCH.name -> MealTypeEnum.LUNCH
            MealTypeEnum.DINNER.name -> MealTypeEnum.DINNER
            MealTypeEnum.SNACK.name -> MealTypeEnum.SNACK
            else -> {
                MealTypeEnum.SNACK
            }
        }
        return enum
    }

    @TypeConverter
    fun toString(mealTypeEnum: MealTypeEnum): String{
        return mealTypeEnum.name
    }
}