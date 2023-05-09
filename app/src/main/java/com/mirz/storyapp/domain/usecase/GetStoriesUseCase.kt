package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.contract.GetStoriesUseCaseContract
import com.mirz.storyapp.domain.interfaces.StoryRepository
import com.mirz.storyapp.domain.mapper.map
import kotlinx.coroutines.flow.map

class GetStoriesUseCase(private val storyRepository: StoryRepository) : GetStoriesUseCaseContract {
    override fun invoke() = storyRepository.getStories().map { pagingData ->
        pagingData.map()
    }

}