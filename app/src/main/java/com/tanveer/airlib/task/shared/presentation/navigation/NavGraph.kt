package com.tanveer.airlib.task.shared.presentation.navigation

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.presentation.DashboardScreen
import com.tanveer.airlib.task.ui.screen_dashboard.presentation.DashboardViewModel
import com.tanveer.airlib.task.ui.screen_login.presentation.LoginScreen
import com.tanveer.airlib.task.ui.screen_login.presentation.LoginViewModel
import com.tanveer.airlib.task.ui.screen_medicine_detail.presentation.MedicineDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                loginViewModel = loginViewModel,
                navController = navController
            )
        }

        composable(route = "dashboard") {
            val dashboardViewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(
                dashboardViewModel,
                navController = navController
            )
        }

        composable(
            route = "medicine_detail/{drugJson}",
            arguments = listOf(navArgument("drugJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val drugJson = backStackEntry.arguments?.getString("drugJson") ?: ""
            Log.d("MedicineDetailScreen", "Received drugJson: $drugJson")

            val context = LocalContext.current
            if (drugJson.isNotEmpty()) {
                val medicine = Gson().fromJson(drugJson, Drug::class.java)
                if (medicine != null) {
                    MedicineDetailScreen(drug = medicine)
                } else {
                    Log.e("MedicineDetailScreen", "Failed to parse drugJson into Drug object.")
                    Toast.makeText(
                        context,
                        "Error: Unable to parse medicine data.",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                }
            } else {
                Log.e("MedicineDetailScreen", "drugJson is empty.")
                Toast.makeText(context, "Error: No medicine data found.", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }
}
