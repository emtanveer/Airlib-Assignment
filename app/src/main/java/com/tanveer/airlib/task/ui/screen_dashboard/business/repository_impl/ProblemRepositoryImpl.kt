package com.tanveer.airlib.task.ui.screen_dashboard.business.repository_impl

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel
import com.tanveer.airlib.task.ui.screen_dashboard.data.network.ApiService
import com.tanveer.airlib.task.ui.screen_dashboard.data.repository.ProblemRepository
import javax.inject.Inject

/**
 * [ProblemRepositoryImpl] is a domain layer repository implementation of the Problem Repository interface, which provides data access
 * and business logic for interacting with the Repositories feature.
 * It uses the ApiService instance to fetch data from the API and converts the response to the domain model.
 *
 * @param apiService The ApiService instance used to interact with the API.
 */
class ProblemRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProblemRepository {

    override suspend fun getProblems(): ProblemsResponseModel {
        return apiService.getProblemListing()
    }
}