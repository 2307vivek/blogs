package inn.vivvvek.blogs.ui.navigation

sealed class Screen(val route: String, val name: String) {
    data object SignIn : Screen("signin", name = "Sign In")
    data object SignUp : Screen("signup", name = "Sign Up")
    data object Home : Screen(route = "home", name = "Home")
}