package com.mirz.storyapp.data.repository

import com.mirz.storyapp.data.source.remote.ApiServices
import com.mirz.storyapp.domain.interfaces.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepositoryImpl(private val api: ApiServices) : AuthRepository {

    override fun register(email: String, password: String, name: String) = flow {
        emit(
            api.register(
                hashMapOf(
                    Pair("name", name),
                    Pair("password", password),
                    Pair("email", email),
                )
            )
        )
    }.flowOn(Dispatchers.IO)

    override fun login(email: String, password: String) = flow {
        emit(
            api.login(
                hashMapOf(
                    Pair("password", password),
                    Pair("email", email),
                )
            )
        )
    }.flowOn(Dispatchers.IO)

}