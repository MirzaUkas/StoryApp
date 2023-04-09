package com.mirz.storyapp.data.source.remote

import com.mirz.storyapp.data.response.GeneralResponse
import com.mirz.storyapp.data.response.LoginResponse
import com.mirz.storyapp.data.response.StoryDetailResponse
import com.mirz.storyapp.data.response.StoryListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {
    @POST("register")
    suspend fun register(
        @Body requestBody: HashMap<String, String>
    ): GeneralResponse

    @POST("login")
    suspend fun login(
        @Body requestBody: HashMap<String, String>
    ): LoginResponse

    @GET("stories")
    suspend fun stories(): StoryListResponse

    @GET("stories/{id}")
    suspend fun storyDetail(
        @Path("id") id: String
    ): StoryDetailResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): GeneralResponse
}