package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.interfaces.StoryRepository
import com.mirz.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.File

class AddStoryUseCase(private val storyRepository: StoryRepository) {
    operator fun invoke(file: File, description: String): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading())
        storyRepository.addStory(file, description).catch {
            emit(ResultState.Error(message = it.message.toString()))
        }.collect {
            emit(ResultState.Success(it.message))
        }
    }
}
