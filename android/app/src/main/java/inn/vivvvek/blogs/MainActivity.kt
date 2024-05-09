package inn.vivvvek.blogs

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import inn.vivvvek.blogs.auth.GoogleAuthClient
import inn.vivvvek.blogs.ui.theme.BlogsTheme
import kotlinx.coroutines.launch

class MainActivity: ComponentActivity() {

    private val googleAuthClient by lazy {
        GoogleAuthClient(applicationContext, Identity.getSignInClient(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    lifecycleScope.launch {
                        val signInResult =
                            googleAuthClient.signInWithIntent(result.data ?: return@launch)

                        Log.d("debuggg", signInResult.toString())
                    }
                }
            }

            BlogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LaunchedEffect(Unit) {
                            Log.d("debuggg issignedin", googleAuthClient.getSignedInUser().toString())
                        }

                        Button(onClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest
                                        .Builder(signInIntentSender ?: return@launch)
                                        .build()
                                )
                            }
                        }) {
                            Text(text = "Sign in")
                        }
                    }
                }
            }
        }
    }
}