package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.AvatarWithIndicator
import com.epacheco.reports.compose_reformat.general_components.ButtonItem
import com.epacheco.reports.compose_reformat.general_components.ModernProfileWarningBanner
import com.epacheco.reports.compose_reformat.general_components.OptionItem
import com.epacheco.reports.compose_reformat.general_components.TextButtonItem
import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile
import com.epacheco.reports.compose_reformat.ui.theme.PaypalColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.ui.theme.YellowColor
import com.epacheco.reports.compose_reformat.utils.extensions.initials

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProfileView(
    userInfoProfile: UserInfoProfile?,
    onLogoutClick: () -> Unit,
    onUpdateProfilePictureClicked: (() -> Unit)? = null,
    onVerifyEmailClick: (() -> Unit)? = null
) {

    Surface(color = Color.Transparent) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            userInfoProfile?.let { user ->

                AvatarWithIndicator(avatarUrl = user.avatarUrl, avatarLetters = user.fullName) {
                    onUpdateProfilePictureClicked?.invoke()
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = userInfoProfile.fullName ?: stringResource(R.string.user),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = userInfoProfile.email ?: stringResource(R.string.email),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Spacer(modifier = Modifier.height(24.dp))
                if (!userInfoProfile.isEmailVerified) {
                    ModernProfileWarningBanner(
                        title = stringResource(R.string.user_not_verified_title),
                        stringResource(R.string.lbl_send_email_to_verified),
                        icon = R.drawable.ic_mail
                    ) {
                        onVerifyEmailClick?.invoke()
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    colors =
                        CardColors(
                            contentColor = White,
                            containerColor = MaterialTheme.colorScheme.surface,
                            disabledContentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                                .background(Color.Transparent),
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .wrapContentHeight()
                                    .animateContentSize(
                                        animationSpec =
                                            spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow,
                                            ),
                                    ),
                        ) {
                            Column(
                                Modifier.wrapContentHeight(),
                                Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.End,
                            ) {

                                OptionItem(
                                    text = stringResource(R.string.lbl_email),
                                    textSecondary = userInfoProfile.email,
                                )

                                OptionItem(
                                    text = stringResource(R.string.login_method),
                                    textSecondary = userInfoProfile.signInMethod,
                                )

                                OptionItem(
                                    text = stringResource(R.string.last_connection),
                                    textSecondary = userInfoProfile.lastSignInAt,
                                )

                                OptionItem(
                                    text = stringResource(R.string.version_app),
                                    textSecondary = com.epacheco.reports.BuildConfig.VERSION_NAME,
                                )
                            }
                        }
                    }
                }

            } ?: run {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Image(
                        painter = painterResource(R.drawable.ic_empty_profile),
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = stringResource(R.string.msg_user_profile_not_found),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))
            Column {
                ButtonItem(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    textButton = stringResource(R.string.lbl_paypal),
                    colorBackground = PaypalColor,
                    iconBtn = R.drawable.ic_paypal,
                ) {
                    onLogoutClick.invoke()
                }

                ButtonItem(
                    modifier =
                        Modifier
                            .padding(horizontal = 24.dp),
                    textButton = stringResource(R.string.lbl_logout),
                ) {
                    onLogoutClick.invoke()
                }
            }

        }


    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewProfileViewPreview() {
    ReportsGoTheme {
        NewProfileView(UserInfoProfile("1", "correo@gmail.com", null, null, null, null), {}, {}, {})
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewProfileViewUserNullPreview() {
    ReportsGoTheme {
        NewProfileView(null, {}, {}, {})
    }
}
