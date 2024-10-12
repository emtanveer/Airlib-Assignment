package com.tanveer.airlib.task.shared.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.tanveer.airlib.task.shared.presentation.navigation.NavGraph
import com.tanveer.airlib.task.shared.presentation.theme.AirlibTaskTheme
import com.tanveer.airlib.task.shared.presentation.utils.setupFullScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirlibTaskTheme {
                setupFullScreen(window)
                NavGraph()
            }
        }
    }
}
