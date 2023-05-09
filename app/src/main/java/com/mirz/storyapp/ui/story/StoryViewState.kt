package com.mirz.storyapp.ui.story

import androidx.paging.PagingData
import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.utils.ResultState

data class StoryViewState(
    val resultStories: PagingData<StoryEntity> = PagingData.empty(),
    val username: String = "",
)