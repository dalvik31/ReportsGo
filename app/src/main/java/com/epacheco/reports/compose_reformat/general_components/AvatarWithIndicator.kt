package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R

@Composable
fun AvatarWithIndicator(
    avatarRes: String,
    indicatorRes: Int,
    indicatorSaleRes: Int? =null,
    avatarSize: Dp = 80.dp,
    indicatorSize: Dp = 30.dp,
    tintSaleIndicator: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(avatarSize)
            .padding(indicatorSize / 6) // Provides padding for the indicator
    ) {
        // Main circular image
        Image(
            painter = rememberAsyncImagePainter(avatarRes),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape) // Optional border
        )

        // Smaller indicator image in the bottom-end corner
        Image(
            painter = painterResource(id = indicatorRes),
            contentDescription = "Status indicator",
            modifier = Modifier
                .size(indicatorSize)
                .align(Alignment.BottomEnd) // Align to the bottom-right
                .clip(CircleShape)
                .background(Color.White)
                .padding(1.dp) // Add a small internal padding
        )

       /* indicatorSaleRes?.let {
            // Smaller indicator image in the bottom-end corner
            Image(
                painter = painterResource(id = it),
                contentDescription = "Status indicator",
                colorFilter = ColorFilter.tint(tintSaleIndicator),
                modifier = Modifier
                    .size(indicatorSize)
                    .align(Alignment.TopStart) // Align to the bottom-right
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(1.dp) // Add a small internal padding
            )
        }*/

    }
}


@Preview(showBackground = true)
@Composable
fun AvatarWithIndicatorPreview() {
    AvatarWithIndicator(
        avatarRes = "", // Replace with your avatar drawable
        indicatorRes = R.drawable.icon_person, // Replace with your badge drawable
        indicatorSaleRes = R.drawable.ic_sales, // Replace with your badge drawable
        avatarSize = 100.dp,
        indicatorSize = 30.dp,
        modifier = Modifier.padding(all = 8.dp)
    )
}