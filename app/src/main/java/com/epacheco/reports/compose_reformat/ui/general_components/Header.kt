package com.epacheco.reports.compose_reformat.ui.general_components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String? = null,
    titleColor: Color = MaterialTheme.colorScheme.surface,
    backgroundToolbar: Color = MaterialTheme.colorScheme.primary,
    rightImageVector: ImageVector = Icons.Filled.Clear,
    tintImageRight: Color = MaterialTheme.colorScheme.surface,
    leftImageVector: ImageVector = Icons.Filled.AccountCircle,
    tintImageLeft: Color = MaterialTheme.colorScheme.surface,
    modifier: Modifier = Modifier,
    onRightIconClicked: (() -> Unit)? = null,
    onLeftIconClicked: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = it,
                    modifier = modifier
                        .fillMaxWidth()
                        .offset(x = (-13).dp)
                        .padding(horizontal = 16.dp),
                    fontSize = 22.sp,
                    textAlign = if (onRightIconClicked != null) TextAlign.Start else TextAlign.Center
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundToolbar,
            titleContentColor = titleColor
        ),
        actions = {
            onRightIconClicked?.let { rightAction ->
                IconButton(onClick = rightAction) {
                    Icon(
                        imageVector = rightImageVector,
                        contentDescription = "close",
                        tint = tintImageRight
                    )
                }

            }

        },
        navigationIcon = {
            onLeftIconClicked?.let { actionLeft ->
                IconButton(onClick = { actionLeft() }) {
                    Icon(
                        imageVector = leftImageVector,
                        contentDescription = "back",
                        tint = tintImageLeft
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun showBasicHeader() {
    Header(title = "Toolbar")

}

@Preview
@Composable
private fun showRightActionHeader() {
    Header(title = "Toolbar", onRightIconClicked = {})

}

@Preview
@Composable
private fun showLeftActionHeader() {
    Header(title = "Toolbar", onLeftIconClicked = {})

}

@Preview
@Composable
private fun showBothActionHeader() {
    Header(title = "Toolbar", onLeftIconClicked = {}, onRightIconClicked = {})

}