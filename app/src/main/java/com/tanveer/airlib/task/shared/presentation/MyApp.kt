package com.tanveer.airlib.task.shared.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * [MyApp] is the custom [Application] class for the Android app.
 * It is annotated with @[HiltAndroidApp], which indicates that this class is the entry point for Hilt Dependency Injection.
 * Hilt will generate the necessary components and injectors to provide dependencies throughout the app.
 */
@HiltAndroidApp
class MyApp : Application() {
}