package com.pedro0210.hobbylobby.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pedro0210.hobbylobby.data.datastore.UserData
import com.pedro0210.hobbylobby.data.repository.SettingsRepo
import com.pedro0210.hobbylobby.dataStore
import com.pedro0210.hobbylobby.presentation.event.ProfileEvent
import com.pedro0210.hobbylobby.presentation.model.SocialMedia
import com.pedro0210.hobbylobby.presentation.state.ProfileCreationState
import com.pedro0210.hobbylobby.presentation.state.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferences: UserData,
): ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()


    private lateinit var username: String
    private lateinit var pfp: String

    init{
        viewModelScope.launch {
            username = preferences.getUsername()
                .first()
            pfp = preferences.getPfp()
                .first()
            _settingsState.update {
                it.copy(
                    username = username,
                    pfp = pfp
                )
            }

        }
    }



    fun onLogoutClick() {
        viewModelScope.launch {
            preferences.logOut()
        }
    }

    private fun onSocialAdd(social: SocialMedia) {
        viewModelScope.launch {

        }
    }

    companion object {
        fun provideFactory() : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])

                SettingsViewModel(
                    UserData(application.dataStore),
                )
            }
        }
    }

}
