package com.mirz.storyapp.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mirz.storyapp.domain.entity.UserEntity
import com.mirz.storyapp.domain.interfaces.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserPreferenceImpl(private val dataStore: DataStore<Preferences>) : UserPreferenceRepository {

    private object Keys {
        val id = stringPreferencesKey("id")
        val name = stringPreferencesKey("name")
        val token = stringPreferencesKey("token")
    }

    private inline val Preferences.id
        get() = this[Keys.id] ?: ""
    private inline val Preferences.name
        get() = this[Keys.name] ?: ""
    private inline val Preferences.token
        get() = this[Keys.token] ?: ""

    override val userData: Flow<UserEntity> = dataStore.data.map {
        UserEntity(
            id = it.id,
            name = it.name,
            token = it.token
        )
    }.distinctUntilChanged()

    override suspend fun saveUser(userEntity: UserEntity) {
        dataStore.edit {
            it[Keys.id] = userEntity.id
            it[Keys.name] = userEntity.name
            it[Keys.token] = userEntity.token
        }
    }


    override suspend fun clearUser() {
        dataStore.edit {
            it.clear()
        }
    }

}