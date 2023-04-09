package com.mirz.storyapp.ui.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mirz.storyapp.domain.usecase.GetStoriesUseCase
import com.mirz.storyapp.domain.usecase.GetUserUseCase
import com.mirz.storyapp.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoryViewModel(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _storyState = MutableStateFlow(StoryViewState())
    val storyState = _storyState.asStateFlow()

    fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase().collect { stories ->
                _storyState.update {
                    it.copy(resultStories = stories)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun getUser() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                _storyState.update {
                    it.copy(username = user.name)
                }
            }
        }
    }

    class Factory(
        private val getStoriesUseCase: GetStoriesUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val logoutUseCase: LogoutUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
                return StoryViewModel(getStoriesUseCase, getUserUseCase, logoutUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}