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
package inn.vivvvek.blogs.auth

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.FirebaseAuth
import inn.vivvvek.blogs.models.AuthenticatedUser
import inn.vivvvek.blogs.models.SignInResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlogAuth @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleAuth: GoogleAuthClient,
    private val emailAuth: EmailPassAuth
) {
    val isLoggedIn = auth.currentUser != null
    val loggedInUser: AuthenticatedUser?
        get() = auth.currentUser?.run {
            AuthenticatedUser(
                uid = uid,
                name = displayName ?: "",
                profilePic = photoUrl?.toString() ?: "",
                phoneNumber = phoneNumber ?: "",
                email = email ?: ""
            )
        }

    suspend fun getIdToken(): String? {
        if (!isLoggedIn) throw IllegalArgumentException("User not logged in.")
        return auth.currentUser?.getIdToken(false)?.await()?.token
    }

    suspend fun signInWIthGoogle(): IntentSender? {
        return googleAuth.signIn()
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        return googleAuth.signInWithIntent(intent)
    }

    suspend fun signInWithEmail(email: String, pass: String): SignInResult {
        return emailAuth.signInWithEmail(email, pass)
    }

    suspend fun createUserWithEmail(email: String, pass: String): SignInResult {
        return emailAuth.createUserWithEmail(email, pass)
    }
}
