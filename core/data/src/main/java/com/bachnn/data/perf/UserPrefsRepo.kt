package com.bachnn.data.perf

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "user")


class UserPrefsRepo @Inject constructor(@ApplicationContext val context: Context) {
    private val UID = stringPreferencesKey("uid")
    val uid = context.dataStore.data.map { it[UID] ?: "" }

    suspend fun saveUid(uid: String) {
        context.dataStore.edit { it[UID] = uid }
    }

    suspend fun getUid(): String {
        return context.dataStore.data.map { it[UID] ?: "" }.first()
    }
}