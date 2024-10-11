package com.tanveer.airlib.task.ui.screen_dashboard.data.repository

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel

interface ProblemRepository {
    suspend fun getProblems(): ProblemsResponseModel
}