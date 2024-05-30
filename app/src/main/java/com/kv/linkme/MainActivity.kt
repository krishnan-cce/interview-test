package com.kv.linkme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kv.linkme.presentation.screens.splashScreen.SplashScreen
import com.kv.linkme.presentation.screens.usersScreen.UsersScreen
import com.kv.linkme.presentation.ui.navigation.HomeScreen
import com.kv.linkme.presentation.ui.theme.LinkMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            MainAppContent(navHostController)
        }
    }
}


@Composable
fun MainAppContent(navHostController: NavHostController) {
    LinkMeTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navHostController, startDestination = HomeScreen.SplashScreen.route) {
                composable(HomeScreen.SplashScreen.route) {
                    SplashScreen(
                        onNavigate = {
                            navHostController.navigate(HomeScreen.UsersScreen.route) {
                                popUpTo(HomeScreen.SplashScreen.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(HomeScreen.UsersScreen.route) {
                    UsersScreen()
                }
            }
        }
    }
}

//@Composable
//fun MainAppContent() {
//    LinkMeTheme (
//        dynamicColor = false,
//        darkTheme = false
//    ){
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            UsersScreen()
//        }
//    }
//}