package com.tanveer.airlib.task.ui.screen_dashboard.presentation.extensions

import java.util.Locale

fun String.capitalizeFirstChar(): String {
    return if (isNotEmpty()) {
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } else { this }
}