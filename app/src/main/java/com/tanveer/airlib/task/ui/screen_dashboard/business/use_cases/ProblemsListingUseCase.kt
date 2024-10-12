package com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel
import com.tanveer.airlib.task.ui.screen_dashboard.data.repository.ProblemRepository
import javax.inject.Inject

interface ProblemsListingUseCase {
    suspend operator fun invoke(): ProblemsResponseModel
}

class GetProblemsUseCaseImpl @Inject constructor(
    private val repository: ProblemRepository
) : ProblemsListingUseCase {

    override suspend fun invoke(): ProblemsResponseModel {
        return repository.getProblems() // Return the entire ProblemsResponseModel
    }
}