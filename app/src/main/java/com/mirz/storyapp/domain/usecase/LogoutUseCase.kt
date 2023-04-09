package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.interfaces.UserPreferenceRepository

class LogoutUseCase(private val userPreferenceRepository: UserPreferenceRepository) {
    suspend operator fun invoke() {
        userPreferenceRepository.clearUser()
    }
}