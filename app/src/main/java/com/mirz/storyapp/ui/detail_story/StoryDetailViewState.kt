package com.mirz.storyapp.ui.detail_story

import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.utils.ResultState

data class StoryDetailViewState(
    val resultStory: ResultState<StoryEntity> = ResultState.Idle()
)