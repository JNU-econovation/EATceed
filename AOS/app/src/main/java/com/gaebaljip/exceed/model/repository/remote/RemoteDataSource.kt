package com.gaebaljip.exceed.model.repository.remote

import com.gaebaljip.exceed.APIService
import com.gaebaljip.exceed.MainApplication
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object{
        private const val BASE_URL = "http://43.203.82.233:8080/"

    }
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(APIService::class.java)

    suspend fun createApi(data: OnboardingRequestDTO): Result<Unit> {
        val result = service.sendOnboardData(data)

        return if (result.isSuccessful) {
            MainApplication.prefs.token = result.headers()["Authorization"]
            Result.success(Unit)
        } else {
            Result.failure(Exception("회원 정보 조회 실패"))
        }

    }

}