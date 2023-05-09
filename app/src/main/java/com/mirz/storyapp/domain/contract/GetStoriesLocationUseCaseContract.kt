package com.mirz.storyapp.domain.contract

import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface GetStoriesLocationUseCaseContract {
    operator fun invoke(): Flow<ResultState<List<StoryEntity>>>
}