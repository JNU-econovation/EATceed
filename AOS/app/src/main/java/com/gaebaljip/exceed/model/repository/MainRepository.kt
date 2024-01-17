package com.gaebaljip.exceed.model.repository

import com.gaebaljip.exceed.APIService
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.model.dto.request.ChattingRequestDTO
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import com.gaebaljip.exceed.model.dto.response.OnboardingResponseDTO
import com.gaebaljip.exceed.model.repository.local.LocalDataSource
import com.gaebaljip.exceed.model.repository.remote.RemoteDataSource
import com.gaebaljip.exceed.screens.alarm.AlarmInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MainRepository {
    private val localDataSource = LocalDataSource()
    private val remoteDataSource = RemoteDataSource()

    fun getAllAlarm():List<AlarmInfo>{
        return localDataSource.getAllAlarm()
    }
    fun getAlarmListByCurrentWeek(weekNum: Int, imgDate: String):List<AlarmInfo>{
        return localDataSource.getAlarmListByCurrentWeek(weekNum, imgDate)
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

    suspend fun createApi1(data: OnboardingRequestDTO) : Result<Unit> {
        return remoteDataSource.createApi1(data)
    }

    suspend fun createApi2(data: ChattingRequestDTO) : Result<Unit> {
        return remoteDataSource.createApi2(data)
    }

}