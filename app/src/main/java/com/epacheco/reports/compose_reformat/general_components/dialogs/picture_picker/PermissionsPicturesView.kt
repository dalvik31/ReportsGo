package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton


@Composable
fun PermissionsPictureView(
    onGalleryClicked: (() -> Unit)? = null,
    onCameraClicked: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
    ) {
        Column() {
            Spacer(modifier = Modifier.padding(16.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(48.dp),
                painter = painterResource(R.drawable.ic_vector_add_photo),
                contentDescription = null,
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(R.string.permission_title),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.padding(16.dp))

            PrimaryButton(
                textButton = stringResource(R.string.lbl_select_gallery).uppercase(),
                modifier = Modifier.padding(horizontal = 24.dp),
                onButtonClicked = {
                    onGalleryClicked?.invoke()

                })
            PrimaryButton(
                textButton = stringResource(R.string.lbl_take_photo).uppercase(),
                modifier = Modifier.padding(horizontal = 24.dp),
                onButtonClicked = {
                    onCameraClicked?.invoke()
                })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

        }
    }

}


@Preview
@Composable
fun PermissionsViewPreview() {
    PermissionsPictureView()
}