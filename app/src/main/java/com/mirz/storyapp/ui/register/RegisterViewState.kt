package com.mirz.storyapp.ui.register

import com.mirz.storyapp.utils.ResultState

data class RegisterViewState(
    val resultRegisterUser: ResultState<String> = ResultState.Idle()
)