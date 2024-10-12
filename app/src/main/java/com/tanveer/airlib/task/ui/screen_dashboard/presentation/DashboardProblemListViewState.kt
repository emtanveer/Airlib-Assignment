package com.tanveer.airlib.task.ui.screen_dashboard.presentation

import com.tanveer.airlib.task.shared.data.UserEntity
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Problem

data class DashboardProblemListViewState(
    val isLoading: Boolean = false,
    val problems: List<Problem> = emptyList(),
    val error: String = "",
)

data class DashboardFetchUserViewState(
    val fetchUser: UserEntity? = null
)
