package com.gaebaljip.exceed.model.repository.remote

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.content.ContentResolver
import android.provider.OpenableColumns
import com.gaebaljip.exceed.APIService
import com.gaebaljip.exceed.MainApplication
import com.gaebaljip.exceed.model.dto.request.ChattingRequestDTO
import com.gaebaljip.exceed.MainApplication.Companion.context
import com.gaebaljip.exceed.model.dto.request.FoodRegistrationRequestDTO
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import com.gaebaljip.exceed.model.dto.response.CalendarAchieveInfoDTO
import com.gaebaljip.exceed.model.dto.response.CalendarInfoResponseDTO
import com.gaebaljip.exceed.model.dto.response.ChattingResponseDTO
import com.gaebaljip.exceed.model.dto.response.FoodNameAndId
import com.gaebaljip.exceed.model.dto.response.common.CommonResponseDTO
import com.google.gson.Gson
import com.gaebaljip.exceed.model.dto.response.HomeInfoResponseDTO
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.URI
import java.util.Date

class RemoteDataSource {
    companion object {
        private const val BASE_URL_MAIN = "http://43.202.172.135/"
        private const val BASE_URL_CHAT = "http://43.202.172.135/"

    }

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + MainApplication.prefs.token ?: "")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit_main = Retrofit.Builder()
        .baseUrl(BASE_URL_MAIN)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofit_chat = Retrofit.Builder()
        .baseUrl(BASE_URL_CHAT)
        .client(client)
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

    suspend fun createApi2(data: ChattingRequestDTO): Result<ChattingResponseDTO?> {
        val result = service_chat.sendChatData(data)

        return if (result.isSuccessful) {
            Result.success(result.body()?.response)
        } else {
            Result.failure(Exception("답변 조회 실패"))
        }
    }
    suspend fun getFoodListWith(lastItem: String?, size: Int, keyword:String): List<FoodNameAndId> {
        if(keyword==""){
            val result = if (lastItem != null) service_main.getFoodListWithLastItem(
                lastItem,
                20,
            ) else service_main.getFoodList(20)

            return if (result.isSuccessful) {
                result.body()!!.response!!.content
            } else {
                emptyList()
            }
        }else {
            val result = if (lastItem != null) service_main.getFoodListWithLastItem(
                lastItem,
                keyword,
                20
            ) else service_main.getFoodList(keyword,20)

            return if (result.isSuccessful) {
                result.body()!!.response!!.content
            } else {
                emptyList()
            }
        }
    }

    suspend fun registerRequest(data: FoodRegistrationRequestDTO): String? {
        val result = service_main.registerRequest(data)
        return if (result.isSuccessful) {
            result.body()!!.response!!.presignedUrl
        } else {
            null
        }
    }

    suspend fun uploadFile(url: String, fileUri: Uri): Boolean {

        val retrofitT = Retrofit.Builder()
            .baseUrl(BASE_URL_MAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val serviceT = retrofitT.create(APIService::class.java)

        val mediaType = "image/${queryName(context.contentResolver, fileUri)?.substringAfter(".")}"
        context.contentResolver.getType(fileUri)?.let { mimeType ->
            val requestBody = InputStreamRequestBody(context.contentResolver, fileUri)
            val result = serviceT.uploadFile(mediaType, url, requestBody)
            return result.isSuccessful
        }

        return false
    }

    private fun queryName(resolver: ContentResolver, uri: Uri): String? {
        val returnCursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    suspend fun getHomeData(): HomeInfoResponseDTO?{
        val result = service_main.getHomeData()
        return if (result.isSuccessful) {
            result.body()!!.response
        } else {
            null
        }
    }
    suspend fun updateCalendarData(date: String): List<CalendarAchieveInfoDTO>? {
        val result = service_main.getCalendarInfo(date)
        return if (result.isSuccessful) {
            result.body()!!.response!!.getAchieves
        } else {
            null
        }

    }
}