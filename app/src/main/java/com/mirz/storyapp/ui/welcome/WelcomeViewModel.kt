package com.mirz.storyapp.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mirz.storyapp.domain.usecase.GetUserUseCase
import com.mirz.storyapp.utils.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _welcomeState = MutableStateFlow(WelcomeViewState())
    val welcomeState = _welcomeState.asStateFlow()

    init {
        getIsLoggedIn()
    }

    private fun getIsLoggedIn() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                delay(3000)
                _welcomeState.update {
                    it.copy(resultIsLoggedIn = ResultState.Success(user.token.isNotEmpty()))
                }
            }
        }
    }

    class Factory(
        private val getUserUseCase: GetUserUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
                return WelcomeViewModel(getUserUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}