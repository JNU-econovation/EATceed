package com.gaebaljip.exceed.model.repository.local

import androidx.room.Room
import com.gaebaljip.exceed.MainApplication
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.screens.alarm.AlarmInfo

class LocalDataSource {
    private val db = Room.databaseBuilder(
        MainApplication.context,
        AlarmDatabase::class.java, "alarm"
    ).build()

    fun getAllAlarm(): List<AlarmInfo> {
        val dao = db.alarmDao()
        val list: List<AlarmEntity> = dao.getAllAlarm()
        return list.map { it.transform() }
    }

    fun getAlarmListByCurrentWeek(weekNum: Int): List<AlarmInfo> {
        val dao = db.alarmDao()
        val list: List<AlarmEntity> = dao.getTargetWeekALL(weekNum)
        return list.map { it.transform() }
    }

    fun updateAlarmTime(
        id: String,
        hour: Int,
        minute: Int,
        weekNum: Int,
        mealTypeEnum: MealTypeEnum
    ) {
        val dao = db.alarmDao()
        dao.updateAlarmByID(id, hour, minute, weekNum, mealTypeEnum)
    }

    fun updateAlarmImage(id: String, date: String, data: String?) {
        val dao = db.alarmDao()
        dao.updateAlarmImageByID(id, date, data)
    }

    fun insertAlarm(alarm: AlarmInfo) {
        val dao = db.alarmDao()
        with(alarm) {
            dao.insert(
                AlarmEntity(
                    id, hour, minute, mealType, weekFlag, isEnabled, imgDate, imgString
                )
            )
        }
    }

    fun deleteAlarm(id: String) {
        val dao = db.alarmDao()
        dao.delete(id)
    }
}