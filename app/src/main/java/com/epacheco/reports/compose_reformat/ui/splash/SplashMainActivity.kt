package com.epacheco.reports.compose_reformat.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.login.RegisterScreenContent
import com.epacheco.reports.compose_reformat.ui.navigation.ReportsNavHost
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isSplashShow.value
        }
        setContent {
            ReportsGoTheme {
                Scaffold { paddingValues ->
                    ReportsNavHost(
                        modifier = Modifier.padding(paddingValues = paddingValues)
                    )
                }
            }

        }
    }

}


@Preview
@Composable
fun ShowRegisterScreenPreview() {
    ReportsGoTheme {
        RegisterScreenContent(navController = rememberNavController())
    }
}

