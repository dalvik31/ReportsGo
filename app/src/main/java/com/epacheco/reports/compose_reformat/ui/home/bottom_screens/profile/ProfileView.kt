package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.OptionItem
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE2
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE6
import com.epacheco.reports.compose_reformat.utils.extensions.nameProfile
import com.google.firebase.auth.FirebaseUser


@Composable
fun ProfileView(
    firebaseUser: FirebaseUser? = null,
    onUpdateProfilePictureClicked: (() -> Unit)? = null,
    onLogoutClicked: (() -> Unit)? = null,
    signInMethod: String? = null,
    onSendEmailVerificationClicked: (() -> Unit)? = null,
) {
    Column(Modifier.fillMaxSize()) {
        DividerProfile(firebaseUser)
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            firebaseUser?.photoUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .clickable {
                            onUpdateProfilePictureClicked?.invoke()
                        },
                    contentScale = ContentScale.Crop,
                )
            } ?: run {
                Image(
                    painter = painterResource(id = R.drawable.icon_person),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(40.dp))
                        .clickable {
                            firebaseUser?.let {
                                onUpdateProfilePictureClicked?.invoke()
                            }
                        },
                    contentScale = ContentScale.Crop,
                )
            }

        }

        firebaseUser?.metadata?.lastSignInTimestamp?.let { lastConnection ->
            Text(
                text = stringResource(
                    R.string.last_connection
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 10.sp

            )
            Text(
                text = DateUtils.dateFormat(lastConnection.toString(), FORMAT_DATE6),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodySmall,

                )
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Card(
            modifier = Modifier.padding(horizontal = 16.dp), colors = CardColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {


            Column(
                modifier = Modifier
                    .wrapContentWidth(),
            ) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    OptionItem(
                        text = stringResource(R.string.lbl_email),
                        textSecondary = firebaseUser?.email

                    ) {}

                    OptionItem(
                        text = stringResource(R.string.login_method),
                        textSecondary = signInMethod
                    ) {}


                }

                if (firebaseUser?.isEmailVerified == true) {
                    OptionItem(
                        text = stringResource(R.string.email_verified),
                        textSecondary = stringResource(R.string.lbl_yes),
                        tintTextSecondary = GreenColor
                    ) {}

                } else {

                    OptionItem(
                        text = stringResource(R.string.email_verified),
                        textSecondary = stringResource(R.string.lbl_no),
                        tintTextSecondary = RedDark
                    ) {}
                    SecondaryButton(textButton = stringResource(R.string.lbl_send_email_to_verified)) {
                        onSendEmailVerificationClicked?.invoke()
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 24.dp),
            textButton = stringResource(R.string.lbl_logout)
        ) {

            onLogoutClicked?.invoke()
        }
    }

}


@Composable
private fun DividerProfile(firebaseUser: FirebaseUser?) {
    val userName = firebaseUser?.displayName.nameProfile(firebaseUser?.email)
    val userNameLbl = stringResource(
        R.string.title_profile,
        userName
    )
    TextDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        text = if (userName.isEmpty()) stringResource(R.string.msg_user_profile_not_found) else userNameLbl,
        fontSize = 20.sp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileViewPreview() {
    ReportsGoTheme {

        ProfileView()
    }

}