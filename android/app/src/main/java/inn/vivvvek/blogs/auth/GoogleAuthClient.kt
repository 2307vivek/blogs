package inn.vivvvek.blogs.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import inn.vivvvek.blogs.R
import inn.vivvvek.blogs.models.AuthenticatedUser
import inn.vivvvek.blogs.models.SignInResult
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
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
        } catch (e : Exception) {
            if (e is CancellationException) {
                throw e
            }
            SignInResult(
                user = null,
                error = e.message
            )
        }
    }

    fun getSignedInUser(): AuthenticatedUser? =
        auth.currentUser?.run {
            AuthenticatedUser(
                uid = uid,
                name = displayName ?: "",
                profilePic = photoUrl?.toString() ?: "",
                phoneNumber = phoneNumber ?: "",
                email = email ?: ""
            )
        }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}