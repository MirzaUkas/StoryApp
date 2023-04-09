package com.mirz.storyapp.ui.add_story

import com.mirz.storyapp.utils.ResultState

data class AddStoryViewState(
    val resultAddStory: ResultState<String> = ResultState.Idle()
)
