package com.mirz.storyapp.ui.add_story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mirz.storyapp.domain.usecase.AddStoryUseCase
import kotlinx.coroutines.flow.*
import java.io.File

class AddStoryViewModel(private val addStoryUseCase: AddStoryUseCase) : ViewModel() {
    private val _addStoryState = MutableStateFlow(AddStoryViewState())
    val addStoryState = _addStoryState.asStateFlow()

    fun addStory(file: File, description: String) {
        addStoryUseCase(file, description).onEach { result ->
            _addStoryState.update {
                it.copy(resultAddStory = result)
            }
        }.launchIn(viewModelScope)
    }

    class Factory(
        private val addStoryUseCase: AddStoryUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
                return AddStoryViewModel(addStoryUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}