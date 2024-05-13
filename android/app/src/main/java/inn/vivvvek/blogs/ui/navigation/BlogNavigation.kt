/*
 * Copyright 2024 Vivek Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
