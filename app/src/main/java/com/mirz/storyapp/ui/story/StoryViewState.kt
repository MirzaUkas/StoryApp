package com.mirz.storyapp.ui.story

import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.utils.ResultState

data class StoryViewState(
    val resultStories: ResultState<List<StoryEntity>> = ResultState.Idle(),
    val username: String = "",
)