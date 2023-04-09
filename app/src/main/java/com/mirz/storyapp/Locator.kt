package com.mirz.storyapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.mirz.storyapp.data.repository.AuthRepositoryImpl
import com.mirz.storyapp.data.repository.StoryRepositoryImpl
import com.mirz.storyapp.data.source.local.UserPreferenceImpl
import com.mirz.storyapp.data.source.remote.RetrofitBuilder
import com.mirz.storyapp.domain.usecase.*
import com.mirz.storyapp.ui.add_story.AddStoryViewModel
import com.mirz.storyapp.ui.detail_story.StoryDetailViewModel
import com.mirz.storyapp.ui.login.LoginViewModel
import com.mirz.storyapp.ui.register.RegisterViewModel
import com.mirz.storyapp.ui.story.StoryViewModel
import com.mirz.storyapp.ui.welcome.WelcomeViewModel

object Locator {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing call: initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    // Data Store
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    // ViewModel Factory
    val loginViewModelFactory
        get() = LoginViewModel.Factory(
            loginUseCase = loginUseCase
        )
    val registerViewModelFactory
        get() = RegisterViewModel.Factory(
            registerUseCase = registerUseCase
        )
    val welcomeViewModelFactory
        get() = WelcomeViewModel.Factory(
            getUserUseCase = getUserUseCase
        )
    val storyViewModelFactory
        get() = StoryViewModel.Factory(
            getStoriesUseCase = getStoriesUseCase,
            getUserUseCase = getUserUseCase,
            logoutUseCase = logoutUseCase
        )
    val storyDetailViewModelFactory
        get() = StoryDetailViewModel.Factory(
            getStoryDetailUseCase = getStoryDetailUseCase
        )
    val addStoryViewModelFactory
        get() = AddStoryViewModel.Factory(
            addStoryUseCase = addStoryUseCase
        )

    // UseCases Injection
    private val loginUseCase get() = LoginUseCase(userPreferencesRepository, authRepository)
    private val registerUseCase get() = RegisterUseCase(authRepository)
    private val getUserUseCase get() = GetUserUseCase(userPreferencesRepository)
    private val getStoriesUseCase get() = GetStoriesUseCase(storyRepository)
    private val logoutUseCase get() = LogoutUseCase(userPreferencesRepository)
    private val getStoryDetailUseCase get() = GetStoryDetailUseCase(storyRepository)
    private val addStoryUseCase get() = AddStoryUseCase(storyRepository)

    // Repository Injection
    private val userPreferencesRepository by lazy {
        UserPreferenceImpl(requireApplication.dataStore)
    }
    private val authRepository by lazy {
        AuthRepositoryImpl(RetrofitBuilder(requireApplication.dataStore).apiService)
    }
    private val storyRepository by lazy {
        StoryRepositoryImpl(RetrofitBuilder(requireApplication.dataStore).apiService)
    }
}