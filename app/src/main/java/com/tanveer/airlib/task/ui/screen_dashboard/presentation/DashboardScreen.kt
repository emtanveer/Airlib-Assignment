package com.tanveer.airlib.task.ui.screen_dashboard.presentation

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.presentation.utils.MockExtractDrugsUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.presentation.utils.MockGreetingsGeneratorUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.presentation.utils.MockProblemsListingUseCase


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    username: String,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val state by dashboardViewModel.uiState.collectAsState()
    var greetingMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // Fetch the Problem and Medication List from Api when the composable is first composed
        dashboardViewModel.fetchProblemList()

        // Fetch the greetings message when the composable is first composed
        greetingMessage = dashboardViewModel.getGreetingMessage()
    }
    ShouldShowDashboardContent(navController, state, dashboardViewModel, username, greetingMessage)

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShouldShowDashboardContent(
    navController: NavHostController,
    state: DashboardProblemListViewState,
    dashboardViewModel: DashboardViewModel,
    username: String,
    greetingMessage: String
) {
    var drugs by remember { mutableStateOf(emptyList<Drug>()) }

    // Fetching updated problemList here
    LaunchedEffect(state.problems) {
        drugs = dashboardViewModel.extractDrugs(state.problems)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            // Greeting message
            Text(
                text = "$greetingMessage, $username!",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Loading indicator or error message
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (state.error.isNotEmpty()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                // Handle the list of problems
                if (state.problems.isEmpty()) {
                    Text(
                        text = "No medications available.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    DashboardCardItemsViewState(medicines = drugs, onItemClick = { drug ->
                        val drugJson = Uri.encode(Gson().toJson(drug))
                        Log.d("DashboardScreen", "Navigating with drugJson: $drugJson")

                        if (drugJson.isNotEmpty()) {
                            navController.navigate("medicine_detail/$drugJson")
                        } else {
                            Log.e("DashboardScreen", "Drug JSON is empty or null, cannot navigate.")
                        }
                    })
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewShouldShowDashboardContent() {
    val navController = rememberNavController()

    // Sample DashboardProblemListViewState with mock data
    val sampleState = DashboardProblemListViewState(
        isLoading = false,
        error = "",
        problems = listOf()
    )

    // Mock use cases
    val mockProblemsListingUseCase = MockProblemsListingUseCase()
    val mockExtractDrugsUseCase = MockExtractDrugsUseCase()
    val mockGreetingsGeneratorUseCase = MockGreetingsGeneratorUseCase()

    // DashboardViewModel with the mock use cases
    val dashboardViewModel = DashboardViewModel(
        getProblemsUseCase = mockProblemsListingUseCase,
        extractDrugsUseCase = mockExtractDrugsUseCase,
        greetingUseCase = mockGreetingsGeneratorUseCase
    )

    // Sample username and greeting message
    val username = "Test User"
    val greetingMessage = "Good Morning"

    ShouldShowDashboardContent(
        navController = navController,
        state = sampleState,
        dashboardViewModel = dashboardViewModel,
        username = username,
        greetingMessage = greetingMessage
    )
}
