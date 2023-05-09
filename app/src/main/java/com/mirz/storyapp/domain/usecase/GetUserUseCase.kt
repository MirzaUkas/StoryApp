package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.contract.GetUserUseCaseContract
import com.mirz.storyapp.domain.entity.UserEntity
import com.mirz.storyapp.domain.interfaces.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(private val userPreferenceRepository: UserPreferenceRepository) :
    GetUserUseCaseContract {
    override fun invoke(): Flow<UserEntity> = userPreferenceRepository.userData
}