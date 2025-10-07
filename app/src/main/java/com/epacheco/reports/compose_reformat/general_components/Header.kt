package com.epacheco.reports.compose_reformat.general_components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundToolbar: Color = MaterialTheme.colorScheme.background,
    leftImageVector: ImageVector = Icons.Filled.ArrowBackIosNew,
    onLeftIconClicked: (() -> Unit)? = null,
    tintImageLeft: Color = MaterialTheme.colorScheme.surface,
    onRightIconClicked: (() -> Unit)? = null,
    rightImageVector: ImageVector = Icons.Filled.Clear,
    tintImageRight: Color = MaterialTheme.colorScheme.surface,
    onProfileClicked: (() -> Unit)? = null,
    tintIconProfile: Color = MaterialTheme.colorScheme.surface,

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
                    textAlign = TextAlign.Start
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundToolbar,
            titleContentColor = titleColor
        ),
        actions = {
            onProfileClicked?.let { profileAction ->
                IconButton(onClick = profileAction) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "close",
                        tint = tintImageRight
                    )
                }
            }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BasicHeaderPreview() {
    ReportsGoTheme {
        Header(title = "Toolbar")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RightActionHeaderPreview() {
    ReportsGoTheme {
        Header(title = "Toolbar", onRightIconClicked = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LeftActionHeaderPreview() {
    ReportsGoTheme {
        Header(title = "showLeftActionHeader", onLeftIconClicked = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BothActionHeaderPreview() {
    ReportsGoTheme {
        Header(title = "Toolbar", onLeftIconClicked = {}, onRightIconClicked = {})
    }
}