package com.tanveer.airlib.task.shared.presentation.utils

import java.time.LocalTime

interface Clock {
    fun currentTimeNow(): LocalTime
}