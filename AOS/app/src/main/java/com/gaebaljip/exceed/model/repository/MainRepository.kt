package com.gaebaljip.exceed.model.repository

import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.model.repository.local.LocalDataSource
import com.gaebaljip.exceed.screens.alarm.AlarmInfo

object MainRepository {
    private val localDataSource = LocalDataSource()

    fun getAllAlarm():List<AlarmInfo>{
        return localDataSource.getAllAlarm()
    }
    fun getAlarmListByCurrentWeek(weekNum: Int):List<AlarmInfo>{
        return localDataSource.getAlarmListByCurrentWeek(weekNum)
    }

    fun updateAlarm(id: String, hour:Int, minute:Int, weekNum:Int, mealTypeEnum: MealTypeEnum) {
        localDataSource.updateAlarmTime(id, hour, minute, weekNum, mealTypeEnum)
    }

    fun updateAlarmImage(id: String, date: String, data: String?) {
        localDataSource.updateAlarmImage(id, date, data)
    }

    fun insertAlarm(alarm: AlarmInfo) {
        localDataSource.insertAlarm(alarm)
    }

    fun deleteAlarm(id: String) {
        localDataSource.deleteAlarm(id)
    }
}