package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil3.compose.AsyncImage
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.theme.GreyLight
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE2
import com.epacheco.reports.compose_reformat.utils.extensions.nameProfile
import com.google.firebase.auth.FirebaseUser


@Composable
fun ProfileView(
    firebaseUser: FirebaseUser? = null,
    onLogoutClicked: (() -> Unit)? = null,
    loading: Boolean
) {

    Column(Modifier.fillMaxSize()) {
        HeaderProfile(firebaseUser)
        BodyProfile(firebaseUser, loading)
        FooterProfile(onLogoutClicked)
    }

}

@Composable
private fun HeaderProfile(firebaseUser: FirebaseUser?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BackgroundTopImg(firebaseUser?.photoUrl.toString())
            ProfileTopImg(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center),
                firebaseUser?.photoUrl.toString()
            )
        }
    }

}

@Composable
private fun BodyProfile(firebaseUser: FirebaseUser?, loading: Boolean) {


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
}

@Composable
private fun FooterProfile(onLogoutClicked: (() -> Unit)?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 48.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        PrimaryButton(textButton = stringResource(R.string.btn_logout)) {
            onLogoutClicked?.invoke()
        }

    }
}

@Composable
private fun BackgroundTopImg(imgProfile: String) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {
        if (URLUtil.isValidUrl(imgProfile)) {

            AsyncImage(
                model = imgProfile,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 20.dp,
                        radiusY = 20.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(bottomEnd = 100.dp))
                    ),
                contentScale = ContentScale.FillWidth
            )

        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GreyLight, shape = RoundedCornerShape(bottomEnd = 100.dp)),
            )
        }

    }

}

@Composable
private fun ProfileTopImg(modifier: Modifier, imgProfile: String) {

    Surface(
        color = Color.Transparent,
        modifier = modifier,

        ) {
        if (URLUtil.isValidUrl(imgProfile)) {
            AsyncImage(
                model = imgProfile,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.icon_person),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop,
            )

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