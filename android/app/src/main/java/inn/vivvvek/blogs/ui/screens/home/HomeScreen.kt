package inn.vivvvek.blogs.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun Home(
    viewModel: HomeViewModel,
    navigateToSignIn: () -> Unit
) {
    LaunchedEffect(viewModel.isLoggedIn) {
        if (!viewModel.isLoggedIn) {
            navigateToSignIn()
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = viewModel.currentUser?.email ?: "Not logged in")
    }
}