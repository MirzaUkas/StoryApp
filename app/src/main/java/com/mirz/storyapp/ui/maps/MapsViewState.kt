package com.mirz.storyapp.ui.maps

import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.utils.ResultState

data class MapsViewState(
    val resultStories: ResultState<List<StoryEntity>> = ResultState.Idle(),
)