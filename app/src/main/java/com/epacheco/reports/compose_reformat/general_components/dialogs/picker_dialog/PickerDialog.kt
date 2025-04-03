package com.epacheco.reports.compose_reformat.general_components.dialogs.picker_dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.Black

@Composable
fun PickerDialog(
    pickerDialogOption: PickerDialogOption,
    onDismiss: (() -> Unit),
    onValueSelected: ((String) -> Unit)
) {
    val elements = stringArrayResource(getArrayValues(pickerDialogOption))

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
            LazyColumn {
                items(elements) { value ->

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            modifier = Modifier.width(16.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.new_ic_vector_comdin),
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = value,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    onValueSelected.invoke(value)
                                    onDismiss()
                                },
                            color = Black,
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }


                }
            }
        },
        confirmButton = {}
    )
}

private fun getArrayValues(pickerDialogOption: PickerDialogOption): Int {
    return when (pickerDialogOption) {
        PickerDialogOption.SIZES -> R.array.sizes_array
        PickerDialogOption.GENDERS -> R.array.gender_array
        PickerDialogOption.NUMBER_SIZES -> R.array.number_sizes_array
    }
}


@Preview
@Composable
fun ColorPickerDialogPreview() {
    PickerDialog(
        onDismiss = {},
        onValueSelected = {},
        pickerDialogOption = PickerDialogOption.SIZES
    )
}

