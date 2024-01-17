package com.gaebaljip.exceed.model.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gaebaljip.exceed.MealTypeEnum

@Dao
interface AlarmDAO {
    @Query("SELECT * FROM alarmentity")
    fun getAllAlarm(): List<AlarmEntity>
    @Query("SELECT * FROM alarmentity WHERE week_flag &(:weekNum) == :weekNum ORDER BY hour asc, minute asc ")
    fun getTargetWeekALL(weekNum : Int): List<AlarmEntity>

    @Query("UPDATE alarmentity SET hour = (:hour), minute = (:minute), week_flag = (:weekNum), meal_type = (:mealTypeEnum) WHERE id == (:id)")
    fun updateAlarmByID(id: String, hour:Int, minute:Int, weekNum: Int, mealTypeEnum: MealTypeEnum)

    @Query("UPDATE alarmentity SET img_date = (:date), img_string = (:data) WHERE id == (:id)")
    fun updateAlarmImageByID(id: String, date:String, data: String?)

    @Insert
    fun insert(alarm: AlarmEntity)

    @Query("DELETE FROM alarmentity WHERE id == (:id)")
    fun delete(id: String)
}