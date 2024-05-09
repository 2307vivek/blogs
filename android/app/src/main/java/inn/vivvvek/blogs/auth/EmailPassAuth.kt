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

import com.google.firebase.auth.FirebaseAuth
import inn.vivvvek.blogs.models.AuthenticatedUser
import inn.vivvvek.blogs.models.SignInResult
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailPassAuth @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signInWithEmail(email: String, pass: String): SignInResult {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            val user = authResult.user
            SignInResult(
                user = user?.run {
                    AuthenticatedUser(
                        uid = uid,
                        name = displayName ?: "",
                        profilePic = photoUrl?.toString() ?: "",
                        phoneNumber = phoneNumber ?: "",
                        email = email ?: ""
                    )
                },
                error = null
            )
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            SignInResult(user = null, error = e.message)
        }
    }

    suspend fun createUserWithEmail(email: String, pass: String): SignInResult {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
            val user = authResult.user
            SignInResult(
                user = user?.run {
                    AuthenticatedUser(
                        uid = uid,
                        name = displayName ?: "",
                        profilePic = photoUrl?.toString() ?: "",
                        phoneNumber = phoneNumber ?: "",
                        email = email ?: ""
                    )
                },
                error = null
            )
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            SignInResult(user = null, error = e.message)
        }
    }
}
