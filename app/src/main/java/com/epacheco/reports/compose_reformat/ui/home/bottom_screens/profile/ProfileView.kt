package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.general_components.header_image.HeaderImage
import com.epacheco.reports.compose_reformat.general_components.header_image.HeaderImageSize
import com.epacheco.reports.compose_reformat.ui.theme.GrayLight
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE2
import com.epacheco.reports.compose_reformat.utils.extensions.nameProfile
import com.google.firebase.auth.FirebaseUser


@Composable
fun ProfileView(
    firebaseUser: FirebaseUser? = null,
    imageProfile: Uri? = null,
    onUpdateProfilePictureClicked: (() -> Unit)? = null,
    onLogoutClicked: (() -> Unit)? = null,
    loading: Boolean
) {

    Column(Modifier.fillMaxSize()) {
        HeaderImage(
            imageProfile,
            onUpdateProfilePictureClicked = onUpdateProfilePictureClicked
        )
        firebaseUser?.let {
            DividerProfile(firebaseUser)
            firebaseUser.email?.let {
                Text(
                    it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = if (firebaseUser.email.isNullOrEmpty()) 24.dp else 0.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        } ?: run {
            if (!loading) {

                Column(modifier = Modifier.padding(vertical = 24.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_user_resource_not_found),
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally),
                        contentDescription = stringResource(R.string.msg_user_profile_not_found)
                    )
                    Text(
                        "Usuario no encontrado",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }

            }
        }

        firebaseUser?.metadata?.lastSignInTimestamp?.let { lastConnection ->
            Text(
                text = stringResource(
                    R.string.last_connection,
                    DateUtils.format(lastConnection, FORMAT_DATE2)
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 10.sp

            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            PrimaryButton(textButton = stringResource(R.string.title_close_session)) {
                onLogoutClicked?.invoke()
            }

        }
    }

}


@Composable
private fun DividerProfile(firebaseUser: FirebaseUser) {
    TextDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        textDivider = stringResource(
            R.string.title_profile,
            firebaseUser.displayName.nameProfile(firebaseUser.email)
        ),
        fontSize = 18.sp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileViewPreview() {
    ReportsGoTheme {
        ProfileView(null, loading = false)
    }

}