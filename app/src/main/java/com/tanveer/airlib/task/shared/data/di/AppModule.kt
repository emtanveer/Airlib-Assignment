package com.tanveer.airlib.task.shared.data.di

import com.tanveer.airlib.task.shared.business.network_utils.WebConstants.BASE_URL
import com.tanveer.airlib.task.ui.screen_dashboard.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The [AppModule] class
 * It's a Dagger module responsible for providing dependencies in the application scope.
 * It provides instances of the ApiService i.e.[provideApiService] and [provideTGRRepository] will be required for network communication and data access.
 * This module is annotated with @InstallIn(SingletonComponent::class) to ensure that the provided dependencies
 * have a singleton scope throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // OkHttpClient instance used for network communication.
    private val client = OkHttpClient()

    /**
     * Provides the ApiService instance for network communication.
     *
     * @return The ApiService instance.
     */
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
}