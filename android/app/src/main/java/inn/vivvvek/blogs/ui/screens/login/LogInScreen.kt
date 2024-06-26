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
package inn.vivvvek.blogs.ui.screens.login

import android.app.Activity.RESULT_OK
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import inn.vivvvek.blogs.ui.screens.components.PasswordField
import inn.vivvvek.blogs.ui.theme.BlogsTheme
import inn.vivvvek.blogs.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    viewModel: AuthViewModel,
    navigateToSignUP: () -> Unit,
    navigateToHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            navigateToHome()
        }
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            coroutineScope.launch {
                val signInResult =
                    viewModel.signInWithIntent(result.data ?: return@launch)
                Log.d("debuggg", signInResult.toString())
            }
        }
    }

    LogInScreen(
        email = email,
        password = password,
        isLoading = state.loading,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLogin = {
            coroutineScope.launch {
                viewModel.signIn(email, password)
            }
        },
        onGoogleSignIn = {
            coroutineScope.launch {
                val intentSender = viewModel.signInWithGoogle()
                launcher.launch(
                    IntentSenderRequest
                        .Builder(intentSender ?: return@launch)
                        .build()
                )
            }
        },
        onClickSignup = navigateToSignUP
    )
}

@Composable
fun LogInScreen(
    email: String,
    password: String,
    isLoading: Boolean = false,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onGoogleSignIn: () -> Unit,
    onClickSignup: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign in",
            style = Typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(48.dp))
        LoginForm(
            email = email,
            password = password,
            isLoading = isLoading,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onLogin = onLogin,
            onClickSignup = onClickSignup
        )

        Text(text = "or", modifier = Modifier.padding(24.dp))

        Button(
            onClick = onGoogleSignIn,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = "Sign in with Google")
            }
        }
    }
}

@Composable
fun LoginForm(
    email: String,
    password: String,
    isLoading: Boolean = false,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onClickSignup: () -> Unit
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = email,
        label = { Text(text = "Email") },
        onValueChange = onEmailChange,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = VisualTransformation.None
    )

    PasswordField(
        value = password,
        label = "Password",
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        isPasswordVisible = isPasswordVisible,
        onClickTrailingIcon = { isPasswordVisible = !isPasswordVisible },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )

    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onLogin
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text = "Sign in")
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = buildAnnotatedString {
            append("Don't have an account? Sign Up")
            addStyle(SpanStyle(color = MaterialTheme.colorScheme.primary), 23, this.length)
        },
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.clickable(onClick = onClickSignup)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignInPreview() {
    BlogsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LogInScreen(
                email = "",
                password = "",
                onEmailChange = {},
                onPasswordChange = {},
                onLogin = { /*TODO*/ },
                onGoogleSignIn = { /*TODO*/ }
            ) {
            }
        }
    }
}
