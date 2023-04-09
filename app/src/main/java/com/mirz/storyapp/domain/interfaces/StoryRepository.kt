package com.mirz.storyapp.domain.interfaces

import com.mirz.storyapp.data.response.GeneralResponse
import com.mirz.storyapp.data.response.StoryDetailResponse
import com.mirz.storyapp.data.response.StoryListResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoryRepository {
    fun getStories(): Flow<StoryListResponse>
    fun getStory(id: String): Flow<StoryDetailResponse>
    fun addStory(file: File, description: String): Flow<GeneralResponse>
}