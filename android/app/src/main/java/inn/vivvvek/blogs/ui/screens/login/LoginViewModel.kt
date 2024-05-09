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

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import inn.vivvvek.blogs.auth.BlogAuth
import inn.vivvvek.blogs.models.AuthenticatedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: BlogAuth
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    init {
        _state.value = LoginState(
            isLoggedIn = auth.isLoggedIn,
            user = auth.loggedInUser
        )
    }

    suspend fun signInWithGoogle(): IntentSender? {
        return auth.signInWIthGoogle()
    }

    suspend fun signInWithIntent(intent: Intent) {
        val signInResult = auth.signInWithIntent(intent)
        _state.value = LoginState(
            isLoggedIn = auth.isLoggedIn,
            user = auth.loggedInUser
        )
    }

    suspend fun signIn(email: String, pass: String) {
        val signInResult = auth.signInWithEmail(email, pass)
        _state.value = LoginState(
            isLoggedIn = auth.isLoggedIn,
            user = auth.loggedInUser
        )
    }
}

data class LoginState(
    val isLoggedIn: Boolean = false,
    val user: AuthenticatedUser? = null
)
