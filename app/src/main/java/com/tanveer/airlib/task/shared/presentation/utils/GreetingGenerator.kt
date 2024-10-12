package com.tanveer.airlib.task.shared.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class GreetingGenerator @Inject constructor(private val clock: Clock) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateGreeting(): String {
        val hour = clock.currentTimeNow().hour
        return when (hour) {
            in 5..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            in 18..21 -> "Good Evening"
            else -> "Good Night"
        }
    }
}