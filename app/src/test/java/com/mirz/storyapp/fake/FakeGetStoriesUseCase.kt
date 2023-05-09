package com.mirz.storyapp.fake

import androidx.paging.PagingData
import com.mirz.storyapp.domain.contract.GetStoriesUseCaseContract
import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.utils.FakeFlowDelegate
import kotlinx.coroutines.flow.Flow

class FakeGetStoriesUseCase : GetStoriesUseCaseContract {

    val fakeDelegate = FakeFlowDelegate<PagingData<StoryEntity>>()

    override fun invoke(): Flow<PagingData<StoryEntity>> = fakeDelegate.flow

}