package com.occiandiaali.diidi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.occiandiaali.diidi.navigation.CourseScreens
import com.occiandiaali.diidi.ui.screens.homeScreen.DetailScreen
import com.occiandiaali.diidi.ui.screens.homeScreen.HomeScreen
import com.occiandiaali.diidi.ui.theme.DiidiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            DiidiTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
        setContent {
            DiidiTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = CourseScreens.HomeScreen.name) {
        composable(CourseScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(CourseScreens.DetailScreen.name+"/{course}",
            arguments = listOf(navArgument(name = "course") {type = NavType.StringType})
            ) {
            backStackEntry ->
            DetailScreen(navController = navController, backStackEntry.arguments?.getString("course"))
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiidiTheme {
        Greeting("Android")
    }
}