package com.mirz.storyapp.domain.contract

import com.mirz.storyapp.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface GetUserUseCaseContract {
    operator fun invoke(): Flow<UserEntity>
}