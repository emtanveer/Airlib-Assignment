package com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases

import android.os.Build
import androidx.annotation.RequiresApi
import com.tanveer.airlib.task.shared.presentation.utils.GreetingGenerator
import javax.inject.Inject

interface GreetingsGeneratorUseCase {
    suspend operator fun invoke(): String
}

class GreetingsGeneratorUseCaseImpl @Inject constructor(
    private val greetingGenerator: GreetingGenerator // Assuming you have a GreetingGenerator class
) : GreetingsGeneratorUseCase {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun invoke(): String {
        return greetingGenerator.generateGreeting() // Return the greeting message
    }
}