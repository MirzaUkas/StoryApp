package com.mirz.storyapp.utils

sealed class ResultState<T>(
    val data: T? = null,
    val message: String = "",
) {
    class Success<T>(data: T) : ResultState<T>(data)

    class Loading<T> : ResultState<T>()

    class Idle<T> : ResultState<T>()

    class Error<T>(message: String, data: T? = null) :
        ResultState<T>(data, message)
}
