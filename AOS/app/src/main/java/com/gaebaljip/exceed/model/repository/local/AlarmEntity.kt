package com.gaebaljip.exceed.model.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaebaljip.exceed.MealTypeEnum
@Entity
data class AlarmEntity(
    @PrimaryKey val id: String,
    val hour: Int,
    val minute: Int,
    @ColumnInfo(name = "meal_type") val mealType: MealTypeEnum,
    @ColumnInfo(name = "week_flag") val weekFlag: Int,
    @ColumnInfo(name = "is_enabled") val isEnabled: Boolean,
    @ColumnInfo(name = "img_date") val imgDate: String,
    @ColumnInfo(name = "img_string") val imgString: String?
)
