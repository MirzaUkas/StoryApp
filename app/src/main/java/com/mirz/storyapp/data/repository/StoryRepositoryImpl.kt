package com.mirz.storyapp.data.repository

import com.mirz.storyapp.data.source.remote.ApiServices
import com.mirz.storyapp.domain.interfaces.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryRepositoryImpl(private val api: ApiServices) : StoryRepository {
    override fun getStories() = flow {
        emit(
            api.stories()
        )
    }.flowOn(Dispatchers.IO)

    override fun getStory(id: String) = flow {
        emit(
            api.storyDetail(id)
        )
    }.flowOn(Dispatchers.IO)

    override fun addStory(file: File, description: String) = flow {
        val requestBody = MultipartBody.Part.createFormData(
            "photo", file.name, file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        emit(
            api.addStory(requestBody, description.toRequestBody("text/plain".toMediaType()))
        )
    }.flowOn(Dispatchers.IO)

}