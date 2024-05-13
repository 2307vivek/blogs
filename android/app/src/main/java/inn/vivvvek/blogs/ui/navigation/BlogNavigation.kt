package inn.vivvvek.blogs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import inn.vivvvek.blogs.ui.screens.home.HomeScreen
import inn.vivvvek.blogs.ui.screens.login.LogInScreen
import inn.vivvvek.blogs.ui.screens.login.SignUpScreen

@Composable
fun BlogNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.SignIn.route) {
            LogInScreen(
                viewModel = hiltViewModel(),
                navigateToSignUP = { navController.navigate(Screen.SignUp.route) },
                navigateToHome = { navController.navigate(Screen.Home.route) }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                viewModel = hiltViewModel(),
                navigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                navigateToHome = { navController.navigate(Screen.Home.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigateToSignIn = { navController.navigate(Screen.SignIn.route) }
            )
        }
    }
}