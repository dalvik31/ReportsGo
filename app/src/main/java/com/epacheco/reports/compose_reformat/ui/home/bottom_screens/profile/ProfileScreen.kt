package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.navigation.NavHostScreens
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.nameProfile


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    navController: NavHostController
) {
    val orderResponse = profileViewModel.userFlow.collectAsState()

    orderResponse.value?.let {
        when (it) {
            is Resource.Failure -> {
                it.exception?.let {
                    Log.e("aqui", "ProfileScreen ${it.message}")
                }
            }

            Resource.Loading -> Loader(false, stringResource(R.string.search_profile))
            is Resource.Success -> {
                Column {
                    TextDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        textDivider = stringResource(
                            R.string.title_profile,
                            it.result.displayName.nameProfile(it.result.email)
                        ),
                        fontSize = 14.sp
                    )
                    PrimaryButton(textButton = "Logout") {
                        profileViewModel.logout()
                        navController.navigate(NavHostScreens.REGISTER.route) {
                            popUpTo(NavHostScreens.HOME.route) { inclusive = true }
                        }
                    }
                }

            }

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ReportsGoTheme {
        ProfileScreen(navController = rememberNavController())
    }

}