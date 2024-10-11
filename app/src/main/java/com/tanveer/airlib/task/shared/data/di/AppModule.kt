package com.tanveer.airlib.task.shared.data.di

import com.tanveer.airlib.task.shared.business.network_utils.WebConstants.BASE_URL
import com.tanveer.airlib.task.shared.data.di.AppModule.provideApiService
import com.tanveer.airlib.task.shared.data.di.AppModule.provideProblemRepository
import com.tanveer.airlib.task.shared.presentation.utils.Clock
import com.tanveer.airlib.task.shared.presentation.utils.GreetingGenerator
import com.tanveer.airlib.task.shared.presentation.utils.SystemClock
import com.tanveer.airlib.task.ui.screen_dashboard.business.repository_impl.ProblemRepositoryImpl
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ExtractDrugsUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ExtractDrugsUseCaseImpl
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.GetProblemsUseCaseImpl
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.GreetingsGeneratorUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.GreetingsGeneratorUseCaseImpl
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ProblemsListingUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.data.network.ApiService
import com.tanveer.airlib.task.ui.screen_dashboard.data.repository.ProblemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * The [AppModule] class
 * It's a Dagger module responsible for providing dependencies in the application scope.
 * It provides instances of the ApiService i.e.[provideApiService] and [provideProblemRepository] will be required for network communication and data access.
 * This module is annotated with @InstallIn(SingletonComponent::class) to ensure that the provided dependencies
 * have a singleton scope throughout the application.
 */
@InstallIn(SingletonComponent::class)
@Module

object AppModule {
    // OkHttpClient instance used for network communication.
    private val client = OkHttpClient()

    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)

    // Provides the ProblemRepository implementation
    @Provides
    fun provideProblemRepository(
        apiService: ApiService
    ): ProblemRepository = ProblemRepositoryImpl(apiService)

    // Provide ExtractDrugsUseCase
    @Provides
    @Singleton
    fun provideExtractDrugsUseCase(
        extractDrugsUseCaseImpl: ExtractDrugsUseCaseImpl
    ): ExtractDrugsUseCase {
        return extractDrugsUseCaseImpl
    }

    // Provide ProblemsListingUseCase
    @Provides
    fun provideProblemsListingUseCase(
        repository: ProblemRepository
    ): ProblemsListingUseCase = GetProblemsUseCaseImpl(repository)


    @Provides
    fun provideGreetingUseCase(
        greetingGenerator: GreetingGenerator
    ): GreetingsGeneratorUseCase {
        return GreetingsGeneratorUseCaseImpl(greetingGenerator)
    }

    //region Used for Unit Testing Greetings according to Time.
    @Provides
    @Singleton
    fun provideClock(): Clock {
        return SystemClock()
    }

    @Provides
    fun provideGreetingGenerator(clock: Clock): GreetingGenerator {
        return GreetingGenerator(clock)
    }
    //endregion
}
