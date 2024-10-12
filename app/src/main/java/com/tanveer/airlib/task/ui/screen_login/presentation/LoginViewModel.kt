package com.tanveer.airlib.task.ui.screen_login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanveer.airlib.task.shared.business.use_cases.GetUsernameUseCase
import com.tanveer.airlib.task.shared.business.use_cases.SaveUsernameUseCase
import com.tanveer.airlib.task.shared.data.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val getUsernameUseCase: GetUsernameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserEntity(0,""))
    val uiState: StateFlow<UserEntity> = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername) // Update username
    }

    fun onLogin(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val username = _uiState.value.username
                // Log the username before saving
                Log.d("LoginViewModel", "Attempting to save username: $username")

                // Check if the username already exists
                if (!saveUsernameUseCase.isUsernameExists(username)) {
                    // Save the username in the Room database
                    saveUsernameUseCase.invoke(username)

                    // Log the username after saving
                    Log.d("LoginViewModel", "Username saved successfully: $username")

                    // Call onSuccess without passing username
                    onSuccess()
                } else {
                    // Handle the case where the username already exists
                    Log.e("LoginViewModel", "Username already exists: $username")

                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error during login", e) // Log the error
                onError(e)
            }
        }
    }
}
