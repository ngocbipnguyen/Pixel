package com.bachnn.feature.viewpager.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.User
import com.bachnn.data.model.asExternalEntityToData
import com.bachnn.data.perf.UserPrefsRepo
import com.bachnn.data.repository.FirstUserRepository
import com.bachnn.feature.viewpager.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SettingsUiState {
    data class Success(
        val user: User
    ) : SettingsUiState

    object Loading : SettingsUiState
    data class Error(val error: String) : SettingsUiState
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val userRepository: FirstUserRepository,
    val userRepo: UserPrefsRepo
) : ViewModel() {

    var settingsUiState: SettingsUiState by mutableStateOf(SettingsUiState.Loading)


    init {
        try {
            settingsUiState = SettingsUiState.Loading
            viewModelScope.launch {
                val uid = userRepo.getUid()
                if (uid != "") {
                    val user = userRepository.getUserByUid(uid)
                    settingsUiState = SettingsUiState.Success(user)
                } else {
                    settingsUiState =
                        SettingsUiState.Error(context.getString(R.string.error_uid_not))
                }
            }
        } catch (e: Exception) {
            settingsUiState = SettingsUiState.Error(e.message.toString())
        }

    }

}