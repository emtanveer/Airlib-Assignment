package com.tanveer.airlib.task.shared.data.di

import android.app.Application
import com.tanveer.airlib.task.shared.business.network_utils.WebConstants.BASE_URL
import com.tanveer.airlib.task.shared.business.repository_impl.DatabaseRepositoryImpl
import com.tanveer.airlib.task.shared.business.use_cases.GetUsernameUseCase
import com.tanveer.airlib.task.shared.business.use_cases.GetUsernameUseCaseImpl
import com.tanveer.airlib.task.shared.business.use_cases.SaveUsernameUseCase
import com.tanveer.airlib.task.shared.business.use_cases.SaveUsernameUseCaseImpl
import com.tanveer.airlib.task.shared.data.db.AppDatabase
import com.tanveer.airlib.task.shared.data.db.UserDao
import com.tanveer.airlib.task.shared.data.di.AppModule.provideApiService
import com.tanveer.airlib.task.shared.data.di.AppModule.provideProblemRepository
import com.tanveer.airlib.task.shared.data.repository.DatabaseRepository
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

    //region Application
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
    //endregion

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

    //region Room DB
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return AppDatabase.getDatabase(app)
    }

    @Provides
    fun provideLoginUserDao(db: AppDatabase): UserDao {
        return db.loginUserDao()
    }

    @Provides
    fun provideLoginRepository(dao: UserDao): DatabaseRepository {
        return DatabaseRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideSaveUsernameUseCase(
        repository: DatabaseRepository
    ): SaveUsernameUseCase {
        return SaveUsernameUseCaseImpl(repository)
    }

    // Provide the GetUsernameUseCase
    @Provides
    @Singleton
    fun provideGetUsernameUseCase(
        repository: DatabaseRepository
    ): GetUsernameUseCase {
        return GetUsernameUseCaseImpl(repository)
    }
    //endregion
}
