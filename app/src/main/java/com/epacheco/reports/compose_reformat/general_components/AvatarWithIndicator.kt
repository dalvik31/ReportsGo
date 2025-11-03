package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.GrayDark
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.utils.ColorUtils
import java.time.format.TextStyle

@Composable
fun AvatarWithIndicator(
    avatarUrl: String? = null,
    avatarRes: Int? = null,
    avatarLetters: String? = null,
    indicatorRes: Int,
    indicatorSaleRes: Int? = null,
    avatarSize: Dp = 80.dp,
    indicatorSize: Dp = 30.dp,
    tintSaleIndicator: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(avatarSize)
            .padding(indicatorSize / 6),

        ) {
        // Main circular image
        if (!avatarUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(avatarUrl),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape) // Optional border
            )
        }else if(avatarRes != null){
            Image(
                painter = painterResource(avatarRes),
                contentDescription = "Avatar",
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(GreenColor),
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape) // Optional border
            )
        } else if (avatarLetters != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = avatarLetters,
                    textAlign = TextAlign.Center,
                    style = androidx.compose.ui.text.TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    ),

                    )
            }

        }


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
            , colorFilter = ColorFilter.tint(tintSaleIndicator)
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
        avatarUrl = null, // Replace with your avatar drawable
        avatarLetters = "EP",
        avatarRes = R.drawable.ic_vector_sale,
        indicatorRes = R.drawable.icon_person, // Replace with your badge drawable
        indicatorSaleRes = R.drawable.ic_sales, // Replace with your badge drawable
        avatarSize = 60.dp,
        indicatorSize = 15.dp,
        modifier = Modifier.padding(all = 8.dp)
    )
}