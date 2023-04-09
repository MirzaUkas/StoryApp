package com.mirz.storyapp.ui.login

import com.mirz.storyapp.utils.ResultState

data class LoginViewState(
    val resultVerifyUser: ResultState<String> = ResultState.Idle()
)
