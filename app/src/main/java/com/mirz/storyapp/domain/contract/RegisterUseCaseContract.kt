package com.mirz.storyapp.domain.contract

import com.mirz.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface RegisterUseCaseContract {
    operator fun invoke(name: String, email: String, password: String): Flow<ResultState<String>>
}