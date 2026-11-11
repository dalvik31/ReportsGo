package com.epacheco.reports.compose_reformat.ui.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.MainActivity
import com.epacheco.reports.compose_reformat.ui.deeplink.state.RedirectDestination
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.handleDeeplinks
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class DeepLinkHandlerActivity : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private val viewModel: DeepLinkHandlerViewModel by viewModels()

    private var callback: ((String, String) -> Unit)? = null

    @OptIn(ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supabaseClient.handleDeeplinks(intent = intent,
            onSessionSuccess = { userSession ->
                Log.d("LOGIN", "Log in successfully with user info: ${userSession.user}")
                userSession.user?.apply {
                    callback?.invoke(email ?: "", createdAt.toString())
                }
            })
        setContent {
            val navController = rememberNavController()
            val emailState = remember { mutableStateOf("") }
            val createdAtState = remember { mutableStateOf("") }
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(Unit) {
                handleDeepLink(intent)
            }
            LaunchedEffect(state.redirectDestination) {
                when (state.redirectDestination) {
                    is RedirectDestination.EmailConfirmation -> {
                        // Success
                        navigateToMainApp()
                    }

                    else -> Unit
                }
            }
            LaunchedEffect(Unit) {
                callback = { email, created ->
                    emailState.value = email
                    createdAtState.value = created
                }
            }
            navigateToMainApp()
        }
    }

    private fun navigateToMainApp() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW) {
            val uri: Uri? = intent.data
            uri?.let {
                val tokenHash = it.getQueryParameter("token_hash")
                val code = it.getQueryParameter("code") ?: ""
                val actionPath = it.pathSegments.lastOrNull()
                if (tokenHash != null) {
                    when (actionPath) {
                        "confirm", null -> {
                            viewModel.verifyEmailConfirmation(tokenHash)
                        }
                    }
                } else if (code.isNotEmpty()) {
                    when (actionPath) {
                        "oauth", null -> {
                            viewModel.verifyGoogleAuth(code = code)
                        }
                    }
                } else {
                    println("Invalid deep link parameters")
                }
            }
        }
    }
}

