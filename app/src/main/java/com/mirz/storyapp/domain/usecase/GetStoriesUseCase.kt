package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.contract.GetStoriesUseCaseContract
import com.mirz.storyapp.domain.interfaces.StoryRepository
import com.mirz.storyapp.domain.mapper.map
import kotlinx.coroutines.flow.map

class GetStoriesUseCase(private val storyRepository: StoryRepository) {
    operator fun invoke(): Flow<ResultState<List<StoryEntity>>> = flow {
        emit(ResultState.Loading())
        storyRepository.getStories().map {
            it.listStory.map { story ->
                StoryEntity(
                    id = story.id,
                    name = story.name,
                    description = story.description,
                    photoUrl = story.photoUrl,
                )
            }
        }.catch {
            emit(ResultState.Error(message = it.message.toString()))
        }.collect {
            emit(ResultState.Success(it))
        }
    }

}