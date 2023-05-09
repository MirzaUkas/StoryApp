package com.mirz.storyapp.fake

import com.mirz.storyapp.domain.contract.GetUserUseCaseContract
import com.mirz.storyapp.domain.entity.UserEntity
import com.mirz.storyapp.utils.FakeFlowDelegate
import kotlinx.coroutines.flow.Flow

class FakeGetUserUseCase : GetUserUseCaseContract {

    val fakeDelegate = FakeFlowDelegate<UserEntity>()

    override fun invoke(): Flow<UserEntity> = fakeDelegate.flow

}