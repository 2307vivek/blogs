package inn.vivvvek.blogs.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToSignIn: () -> Unit
) {
    LaunchedEffect(viewModel.isLoggedIn) {
        if (!viewModel.isLoggedIn) {
            navigateToSignIn()
        }
    }

    HomeScreen(email = viewModel.currentUser?.email ?: "Not logged in.")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    email: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Menu"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(16.dp),
        ) {
            Text(text = email)
        }
    }
}


@Composable
@Preview
fun HomePreview() {
    HomeScreen(email = "hello@gmail.com")
}