package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.GrayBackground
import com.epacheco.reports.compose_reformat.ui.theme.White

@Composable
fun OrderMainBanner() {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 4.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(100.dp),

        ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(GrayBackground)
        ) {
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd),
                painter = painterResource(R.drawable.img_banner_invierno),
                contentDescription = null,

                )

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Text(
                    stringResource(R.string.title_season),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Text(
                    stringResource(R.string.season_fall_winter),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    color = White,
                    text = stringResource(R.string.season_warning),
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}

@Preview
@Composable
fun OrderMainBannerPreview() {
    OrderMainBanner()
}