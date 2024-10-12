package com.tanveer.airlib.task.shared.presentation.utils

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun setupFullScreen(window: Window) {
    // Configure window to extend behind system bars
    WindowCompat.setDecorFitsSystemWindows(window, false)

    // Hide system UI (status bar and navigation bar)
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.hide(WindowInsetsCompat.Type.systemBars()) // Hide both status and nav bars
    controller.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE // Allow swipe to show bars
}

// Optional: Restore system bars if needed in specific cases
fun restoreSystemBars(window: Window) {
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.show(WindowInsetsCompat.Type.systemBars()) // Show system UI
}