package com.epacheco.reports.compose_reformat.general_components.dialogs.color_picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.utils.extensions.toColor

@Composable
fun ColorPickerDialog(
    onDismiss: (() -> Unit),
    currentlySelected: Color,
    onColorSelected: ((Color, Int) -> Unit)
) {
    val gridState = rememberLazyGridState()
    val colorList = stringArrayResource(R.array.colors_code_array)

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState
            ) {
                itemsIndexed(colorList) { index, color ->

                    var currentColor = color.toColor()
                    var borderWidth = 0.dp
                    if (currentlySelected == currentColor) {
                        borderWidth = 2.dp
                    }

                    Canvas(modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            borderWidth,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                            RoundedCornerShape(20.dp)
                        )
//                        .background(color)
                        .requiredSize(70.dp)
                        .clickable {
                            onColorSelected(currentColor, index)
                            onDismiss()
                        }
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        drawPath(Path().apply {
                            moveTo(0f, 0f)
                            lineTo(canvasWidth, 0f)
                            lineTo(0f, canvasHeight)
                            close()
                        }, color = currentColor)

                        drawPath(Path().apply {
                            moveTo(canvasWidth, 0f)
                            lineTo(0f, canvasHeight)
                            lineTo(canvasWidth, canvasHeight)
                            close()
                        }, color = currentColor.copy(alpha = 0.6f))

                    }
                }
            }
        },
        confirmButton = {}
    )
}


@Preview
@Composable
fun ColorPickerDialogPreview() {
    ColorPickerDialog(onDismiss = {}, currentlySelected = Color.Red, onColorSelected = { _, _ -> })
}

