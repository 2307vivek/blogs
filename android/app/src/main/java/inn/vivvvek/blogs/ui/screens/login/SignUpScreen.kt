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

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
fun SignUpScreen(
    viewModel: AuthViewModel,
    navigateToSignIn: () -> Unit,
    navigateToHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            navigateToHome()
        }
    }

    SignUpScreen(
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onLogin = {
            coroutineScope.launch {
                viewModel.signUp(email, password)
            }
        },
        onClickSignIn = navigateToSignIn
    )
}

@Composable
fun SignUpScreen(
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onClickSignIn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign up",
            style = Typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(48.dp))
        SignUpForm(
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onConfirmPasswordChange = onConfirmPasswordChange,
            onLogin = onLogin,
            onClickSignIn = onClickSignIn
        )
    }
}

@Composable
fun SignUpForm(
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onClickSignIn: () -> Unit
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isPasswordsEqual by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(confirmPassword) {
        isPasswordsEqual = password == confirmPassword
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

    PasswordField(
        value = confirmPassword,
        label = "Confirm Password",
        onValueChange = onConfirmPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        isPasswordVisible = isPasswordVisible,
        isError = !isPasswordsEqual,
        supportingText = {
            if (!isPasswordsEqual) {
                Text(text = "Passwords do not match")
            } else null
        },
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
        onClick = onLogin,
        enabled = isPasswordsEqual
    ) {
        Text(text = "Create a new account")
    }

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = buildAnnotatedString {
            append("Already have an account? Sign In")
            addStyle(SpanStyle(color = MaterialTheme.colorScheme.primary), 25, this.length)
        },
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.clickable(onClick = onClickSignIn)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpPreview() {
    BlogsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SignUpScreen(
                email = "",
                password = "",
                confirmPassword = "",
                onEmailChange = {},
                onPasswordChange = {},
                onConfirmPasswordChange = {},
                onLogin = { /*TODO*/ },
            ) {
            }
        }
    }
}
