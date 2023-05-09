package com.mirz.storyapp.domain.interfaces

import androidx.paging.PagingData
import com.google.android.gms.maps.model.LatLng
import com.mirz.storyapp.data.response.GeneralResponse
import com.mirz.storyapp.data.response.StoryDetailResponse
import com.mirz.storyapp.data.response.StoryListResponse
import com.mirz.storyapp.data.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoryRepository {
    fun getStories(): Flow<PagingData<StoryResponse>>
    fun getStory(id: String): Flow<StoryDetailResponse>
    fun addStory(file: File, description: String, latLng: LatLng?): Flow<GeneralResponse>
    fun getStoriesLocation(id: Int): Flow<StoryListResponse>
}