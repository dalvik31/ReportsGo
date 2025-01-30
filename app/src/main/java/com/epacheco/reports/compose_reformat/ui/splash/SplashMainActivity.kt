package com.epacheco.reports.compose_reformat.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.base.ReportsNavHost
import com.epacheco.reports.compose_reformat.ui.login.RegisterScreen
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isSplashShow.value
        }
        setContent {
            ReportsNavHost(registerViewModel = viewModel)
        }
    }

}


@Preview()
@Composable
fun ShowRegisterScreenPreview() {
    ReportsGoTheme {
        RegisterScreen(null, rememberNavController())
    }
}

