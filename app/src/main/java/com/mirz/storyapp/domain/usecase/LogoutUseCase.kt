package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.contract.LogoutUseCaseContract
import com.mirz.storyapp.domain.interfaces.UserPreferenceRepository

class LogoutUseCase(private val userPreferenceRepository: UserPreferenceRepository) :
    LogoutUseCaseContract {
    override suspend fun invoke() = userPreferenceRepository.clearUser()
}