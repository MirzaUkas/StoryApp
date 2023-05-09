package com.mirz.storyapp.domain.contract

import androidx.paging.PagingData
import com.mirz.storyapp.domain.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

interface GetStoriesUseCaseContract {
    operator fun invoke(): Flow<PagingData<StoryEntity>>
}