package com.mirz.storyapp.domain.contract

import com.mirz.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface LoginUseCaseContract {
    operator fun invoke(email: String, password: String): Flow<ResultState<String>>
}