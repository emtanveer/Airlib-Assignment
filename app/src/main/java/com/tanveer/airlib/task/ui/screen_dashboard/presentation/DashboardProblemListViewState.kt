package com.tanveer.airlib.task.ui.screen_dashboard.presentation

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Problem

data class DashboardProblemListViewState(
    val isLoading: Boolean = false,
    val problems: List<Problem> = emptyList(),
    val error: String = "",
)