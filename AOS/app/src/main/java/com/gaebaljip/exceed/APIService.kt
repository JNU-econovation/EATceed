package com.gaebaljip.exceed

import android.telecom.Call
import com.gaebaljip.exceed.model.dto.request.ChattingRequestDTO
import com.gaebaljip.exceed.model.dto.response.ChattingResponseDTO
import com.gaebaljip.exceed.model.dto.request.FoodRegistrationRequestDTO
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import com.gaebaljip.exceed.model.dto.response.CalendarInfoResponseDTO
import com.gaebaljip.exceed.model.dto.response.FoodInfoResponseDTO
import com.gaebaljip.exceed.model.dto.response.FoodListPagingResponseDTO
import com.gaebaljip.exceed.model.dto.response.OnboardingResponseDTO
import com.gaebaljip.exceed.model.dto.response.RegisterFoodInfoResponseDTO
import com.gaebaljip.exceed.model.dto.response.common.CommonResponseDTO
import com.google.android.gms.measurement.api.AppMeasurementSdk.EventInterceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.Date

interface APIService {
    @POST("/v1/members-guest")
    suspend fun sendOnboardData(@Body data: OnboardingRequestDTO)
    : Response<CommonResponseDTO<OnboardingResponseDTO>>

    @POST("/v1/chat")
    suspend fun sendChatData(@Body data: ChattingRequestDTO)
    : Response<CommonResponseDTO<ChattingResponseDTO>>
    @GET("/v1/foods")
    suspend fun getFoodListWithLastItem(@Query("lastFoodName")lastItem: String, @Query("size")size: Int) : Response<CommonResponseDTO<FoodListPagingResponseDTO>>

    @GET("/v1/foods")
    suspend fun getFoodList() : Response<CommonResponseDTO<FoodListPagingResponseDTO>>

    @GET("/v1/food/{id}")
    suspend fun getFoodInfoById(@Path("id") foodId: Int): Response<CommonResponseDTO<FoodInfoResponseDTO>>
    @POST("/v1/meal")
    suspend fun registerRequest(@Body data: FoodRegistrationRequestDTO): Response<CommonResponseDTO<RegisterFoodInfoResponseDTO>>

    @Multipart
    @PUT
    suspend fun uploadFile(
        @Url url : String, @Part part : MultipartBody.Part
    ): Response<Unit>

    @GET("/v1/achieve/{date}")
    suspend fun getCalendarInfo(@Path("date") date: String) : Response<CommonResponseDTO<CalendarInfoResponseDTO>>
}