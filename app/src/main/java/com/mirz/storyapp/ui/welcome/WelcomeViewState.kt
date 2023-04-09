package com.mirz.storyapp.ui.welcome

import com.mirz.storyapp.utils.ResultState

data class WelcomeViewState(
    val resultIsLoggedIn: ResultState<Boolean> = ResultState.Idle()
)
