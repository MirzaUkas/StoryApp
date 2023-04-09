package com.mirz.storyapp.domain.usecase

import com.mirz.storyapp.domain.interfaces.AuthRepository
import com.mirz.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(name: String, email: String, password: String): Flow<ResultState<String>> =
        flow {
            emit(ResultState.Loading())
            authRepository.register(
                email, password, name
            ).catch {
                emit(ResultState.Error(it.message.toString()))
            }.collect { result ->
                if (result.error) {
                    emit(ResultState.Error(result.message))
                } else {
                    emit(ResultState.Success(result.message))
                }
            }
        }
}