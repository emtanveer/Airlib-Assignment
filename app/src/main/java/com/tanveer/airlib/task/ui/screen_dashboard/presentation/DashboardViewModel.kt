package com.tanveer.airlib.task.ui.screen_dashboard.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanveer.airlib.task.shared.business.use_cases.GetUsernameUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ExtractDrugsUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.GreetingsGeneratorUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ProblemsListingUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Problem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getProblemsUseCase: ProblemsListingUseCase,
    private val extractDrugsUseCase: ExtractDrugsUseCase,
    private val greetingUseCase: GreetingsGeneratorUseCase,
    private val getUsernameUseCase: GetUsernameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardProblemListViewState())
    val uiState: StateFlow<DashboardProblemListViewState> = _uiState.asStateFlow()

    // StateFlow to hold the fetched username state
    private val _usernameState = MutableStateFlow(DashboardFetchUserViewState())
    val usernameState: StateFlow<DashboardFetchUserViewState> = _usernameState.asStateFlow()


    //region Fetch the list of problems and medications
    fun fetchProblemList() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = getProblemsUseCase()
                _uiState.value = _uiState.value.copy(problems = response.problems, isLoading = false)

                val drugs = extractDrugsUseCase(response.problems)

            } catch (e: Exception) {
                // Handle any exceptions and update the UI state with the error message
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }

    //region extractDrugs use-case
    suspend fun extractDrugs(problems: List<Problem>): List<Drug> {
        return extractDrugsUseCase.invoke(problems)
    }
    //endregion

    //region Greetings use-case
    suspend fun getGreetingMessage(): String {
        return greetingUseCase.invoke()
    }
    //endregion

    //region Fetch username and update StateFlow
    suspend fun fetchAndUpdateUsername() {
            try {
                val user = getUsernameUseCase.invoke()
                _usernameState.value = _usernameState.value.copy(fetchUser = user)
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Error fetching username", e)
            }
    }
    //endregion
}