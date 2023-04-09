package com.mirz.storyapp.domain.interfaces

import com.mirz.storyapp.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    val userData: Flow<UserEntity>
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun clearUser()
}