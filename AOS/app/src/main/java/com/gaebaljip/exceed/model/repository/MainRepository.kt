package com.gaebaljip.exceed.model.repository


import android.net.Uri
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.model.dto.request.ChattingRequestDTO
import com.gaebaljip.exceed.model.dto.request.FoodRegistrationRequestDTO
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import com.gaebaljip.exceed.model.dto.response.CalendarAchieveInfoDTO
import com.gaebaljip.exceed.model.dto.response.ChattingResponseDTO
import com.gaebaljip.exceed.model.dto.response.FoodNameAndId
import com.gaebaljip.exceed.model.dto.response.common.CommonResponseDTO
import com.gaebaljip.exceed.model.dto.response.HomeInfoResponseDTO
import com.gaebaljip.exceed.model.repository.local.LocalDataSource
import com.gaebaljip.exceed.model.repository.remote.RemoteDataSource
import com.gaebaljip.exceed.screens.alarm.AlarmInfo
import java.util.Date

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

    suspend fun createApi2(data: ChattingRequestDTO) : Result<ChattingResponseDTO?> {
        return remoteDataSource.createApi2(data)
    }
    suspend fun getFoodListWith(lastItem: String?, size: Int, keyword: String): List<FoodNameAndId> {
        return remoteDataSource.getFoodListWith(lastItem, size, keyword)
    }

    suspend fun registerRequest(data: FoodRegistrationRequestDTO):String? {
        return remoteDataSource.registerRequest(data)
    }

    suspend fun uploadFile(url: String, fileUri: Uri) : Boolean{
        return remoteDataSource.uploadFile(url, fileUri)
    }

    suspend fun updateCalendarData(date: String): List<CalendarAchieveInfoDTO>? {
        return remoteDataSource.updateCalendarData(date)
    }

    suspend fun getHomeInfo(): HomeInfoResponseDTO? {
        return remoteDataSource.getHomeData()
    }
}