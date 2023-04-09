package com.mirz.storyapp.domain.interfaces

import com.mirz.storyapp.data.response.GeneralResponse
import com.mirz.storyapp.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(email: String, password: String, name: String): Flow<GeneralResponse>
    fun login(email: String, password: String): Flow<LoginResponse>
}