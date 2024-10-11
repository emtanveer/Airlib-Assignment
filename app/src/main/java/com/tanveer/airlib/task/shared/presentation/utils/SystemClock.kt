package com.tanveer.airlib.task.shared.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import javax.inject.Inject

class SystemClock @Inject constructor() : Clock {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun currentTimeNow(): LocalTime = LocalTime.now()
}