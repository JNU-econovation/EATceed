package com.gaebaljip.exceed.model.repository.remote

import com.gaebaljip.exceed.APIService
import com.gaebaljip.exceed.MainApplication
import com.gaebaljip.exceed.model.dto.request.ChattingRequestDTO
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object{
        private const val BASE_URL_MAIN = "http://43.203.82.233:8080/"
        private const val BASE_URL_CHAT = "http://127.0.0.1:8000/"

    }
    val retrofit_main = Retrofit.Builder()
        .baseUrl(BASE_URL_MAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofit_chat = Retrofit.Builder()
        .baseUrl(BASE_URL_CHAT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val service_main = retrofit_main.create(APIService::class.java)
    val service_chat = retrofit_chat.create(APIService::class.java)

    suspend fun createApi1(data: OnboardingRequestDTO): Result<Unit> {
        val result = service_main.sendOnboardData(data)

        return if (result.isSuccessful) {
            MainApplication.prefs.token = result.headers()["Authorization"]
            Result.success(Unit)
        } else {
            Result.failure(Exception("회원 정보 조회 실패"))
        }

    }

    suspend fun createApi2(data: ChattingRequestDTO): Result<Unit>{
        val result = service_chat.sendChatData(data)

        return if (result.isSuccessful) {
            MainApplication.prefs.token = result.headers()["Authorization"]
            Result.success(Unit)
        } else {
            Result.failure(Exception("답변 조회 실패"))
        }
    }

}