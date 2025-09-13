package com.epacheco.reports.compose_reformat.general_components.header_image


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.GrayLight
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderImage(
    urlImage: Uri? = null,
    imageSize: HeaderImageSize = HeaderImageSize.MEDIUM,
    onUpdateProfilePictureClicked: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BackgroundTopImg(urlImage)
            ProfileTopImg(
                modifier = Modifier
                    .size(size = imageSize.height)
                    .align(Alignment.Center),
                urlImage,
                onUpdateProfilePictureClicked = onUpdateProfilePictureClicked
            )
        }
    }
}

@Composable
private fun BackgroundTopImg(imgProfile: Uri?) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {

        imgProfile?.let {
            Image(
                painter = rememberAsyncImagePainter(imgProfile),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 20.dp,
                        radiusY = 20.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(bottomEnd = 30.dp))
                    ),
                contentScale = ContentScale.FillWidth
            )
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayLight, shape = RoundedCornerShape(bottomEnd = 30.dp)),
            )
        }


    }

}

@Composable
private fun ProfileTopImg(
    modifier: Modifier,
    imgProfile: Uri?,
    onUpdateProfilePictureClicked: (() -> Unit)? = null,
) {

    Surface(
        color = Color.Transparent,
        modifier = modifier,

        ) {
        imgProfile?.let {
            Image(
                painter = rememberAsyncImagePainter(imgProfile),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
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
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(40.dp))
                    .clickable {
                        onUpdateProfilePictureClicked?.invoke()
                    },
                contentScale = ContentScale.Crop,
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BasicHeaderImagePreview() {
    ReportsGoTheme {
        HeaderImage(urlImage = null, imageSize = HeaderImageSize.LARGE)
    }
}

