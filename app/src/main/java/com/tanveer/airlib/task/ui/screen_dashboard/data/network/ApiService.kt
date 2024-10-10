package com.tanveer.airlib.task.ui.screen_dashboard.data.network

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel
import retrofit2.http.GET

/**
 * [ApiService] is a Retrofit interface that defines the endpoints for accessing the GitHub API.
 *
 * This interface provides methods to fetch listings from the API.
 */
interface ApiService {
    @GET("73fbd17f-b3f9-4ba8-868f-1a582fd4519b") // Replace with your actual endpoint
    suspend fun getProblemListing(): ProblemsResponseModel
}