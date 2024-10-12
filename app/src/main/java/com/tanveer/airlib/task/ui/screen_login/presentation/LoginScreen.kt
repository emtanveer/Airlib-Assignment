package com.tanveer.airlib.task.ui.screen_login.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()

) {
    val state by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.username, // Correctly accessing the username
            onValueChange = { loginViewModel.onUsernameChange(it) },
            label = { Text(text = "Username or Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {

                    loginViewModel.onLogin(
                        onSuccess = {
                            coroutineScope.launch {
                                delay(1000L)  // 3-second delay
                                // Navigate to the dashboard after saving the username
                                navController.navigate(route = "dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        },
                        onError = { error ->
                            Log.e("LoginScreen", "Error: ${error.message}") // Log error messages
                            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        }
                    )

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.username.isNotBlank()
        ) {
            Text("Login")
        }
    }
}