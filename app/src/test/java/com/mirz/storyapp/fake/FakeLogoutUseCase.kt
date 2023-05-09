package com.mirz.storyapp.fake

import com.mirz.storyapp.domain.contract.LogoutUseCaseContract

class FakeLogoutUseCase : LogoutUseCaseContract {


    override suspend fun invoke() = Unit


}