package com.tanveer.airlib.task.shared.business.network_utils

/**
 * The WebConstants object contains constant values related to web services and API endpoints for the application.
 * It provides the base URL for accessing the APIs.
 * The BASE_URL constant will act as a global variable and can be used throughout the application to access the API.
 */
object WebConstants {
    // Base URL for the MOCKY.IO API in development environment.
    //https://run.mocky.io/v3/73fbd17f-b3f9-4ba8-868f-1a582fd4519b  --created mock api from 'mocky.io'.
    private const val BASE_URL_DEV = "https://run.mocky.io/v3/"

    /**
     * [BASE_URL]   This constant will be used as the base URL for API requests and can be accessed globally.
     */
    const val BASE_URL = BASE_URL_DEV
}