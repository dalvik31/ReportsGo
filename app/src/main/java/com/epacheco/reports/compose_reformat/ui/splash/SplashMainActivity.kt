package com.epacheco.reports.compose_reformat.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.navigation.AppNavHost
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_HOME
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_LOGIN
import com.epacheco.reports.compose_reformat.ui.register.RegisterScreen
import com.epacheco.reports.compose_reformat.ui.register.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isSplashShow.value
        }
        setContent {
            ReportsGoTheme {
                AppNavHost(
                    registerViewModel,
                    startDestination = getStartDestination()
                )
            }
        }
    }

    private fun getStartDestination(): String {
        return when (registerViewModel.loginFlow.value) {
            is Resource.Success -> ROUTE_HOME
            else -> ROUTE_LOGIN
        }
    }

}

