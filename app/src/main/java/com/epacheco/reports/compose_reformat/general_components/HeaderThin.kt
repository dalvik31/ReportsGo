package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun HeaderThin(
    textHeader: String? = null,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    onLeftIconClicked: (() -> Unit)? = null,
    leftImageVector: ImageVector = Icons.Filled.AccountCircle,
    tintImageLeft: Color = MaterialTheme.colorScheme.surface,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                Modifier.weight(1f),
                thickness = 1.dp,
                color = primaryColor
            )
            Text(
                text = textHeader ?: "",
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
            )

            HorizontalDivider(
                Modifier.weight(1f),
                thickness = 1.dp,
                color = primaryColor
            )
        }

    }

}

@Preview
@Composable
private fun showBasicHeaderThin() {
    ReportsGoTheme {
        HeaderThin(textHeader = "DIVIDER")
    }
}

@Preview
@Composable
private fun showProfileImgHeaderThin() {
    ReportsGoTheme {
        HeaderThin(textHeader = "DIVIDER", onLeftIconClicked = {})
    }
}

