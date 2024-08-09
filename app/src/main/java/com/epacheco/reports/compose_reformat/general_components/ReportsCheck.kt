package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ReportsCheckBox(
    isChecked: Boolean,
    textCheckBox: String? = null,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked, onCheckedChange = { onCheckedChange(!it) },
        )
        textCheckBox?.let {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = textCheckBox,
                modifier = Modifier.clickable {
                    onCheckedChange(!isChecked)
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun ShowCheckBox() {
    ReportsCheckBox(true, "ShowCheckBox") {

    }
}
