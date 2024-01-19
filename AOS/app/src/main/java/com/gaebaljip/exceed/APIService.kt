package com.gaebaljip.exceed

import android.telecom.Call
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import com.gaebaljip.exceed.model.dto.response.OnboardingResponseDTO
import com.gaebaljip.exceed.model.dto.response.common.CommonResponseDTO
import com.google.android.gms.measurement.api.AppMeasurementSdk.EventInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
    @POST("/v1/members-guest")
    suspend fun sendOnboardData(@Body data: OnboardingRequestDTO)
    : Response<CommonResponseDTO<OnboardingResponseDTO>>
}