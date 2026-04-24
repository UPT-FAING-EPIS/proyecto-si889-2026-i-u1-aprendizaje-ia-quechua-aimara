package com.example.aprendizaje_ia_quechua_aimara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aprendizaje_ia_quechua_aimara.ui.home.DetalleTemaScreen
import com.example.aprendizaje_ia_quechua_aimara.ui.home.HomeScreen
import com.example.aprendizaje_ia_quechua_aimara.ui.home.WordleScreen
import com.example.aprendizaje_ia_quechua_aimara.ui.login.LoginScreen
import com.example.aprendizaje_ia_quechua_aimara.ui.theme.Aprendizaje_IA_Qechua_AimaraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            Aprendizaje_IA_Qechua_AimaraTheme {
                val navController = rememberNavController()
                var selectedLanguage by remember { mutableStateOf("Quechua") }

                NavHost(navController = navController, startDestination = "login") {
                    
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    
                    composable("home") {
                        HomeScreen(
                            selectedLanguage = selectedLanguage,
                            onSignOut = {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onTemaClick = { tema ->
                                if (tema == "Wordle") {
                                    navController.navigate("wordle")
                                } else {
                                    navController.navigate("detalle_tema/$tema")
                                }
                            }
                        )
                    }

                    composable("wordle") {
                        WordleScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = "detalle_tema/{temaNombre}",
                        arguments = listOf(navArgument("temaNombre") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val temaNombre = backStackEntry.arguments?.getString("temaNombre") ?: ""
                        DetalleTemaScreen(
                            temaNombre = temaNombre,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
