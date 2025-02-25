package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.Green
import com.epacheco.reports.compose_reformat.ui.theme.Yellow

@Composable
fun ListAnimationItem(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    content: String? = null,

    ) {
    var expandedContent by rememberSaveable { mutableStateOf(false) }

    Surface(color = Color.Transparent) {

        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
        ) {


            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

            ) {


                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(bottom = 12.dp),
                ) {
                    Text(
                        text = title
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = body.uppercase(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )

                    content?.let {
                        if (expandedContent) {
                            Text(
                                text = content,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                        }
                    }

                }

                content?.let {
                    IconButton(onClick = {
                        expandedContent = !expandedContent
                    }) {
                        Icon(
                            imageVector = if (expandedContent) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription =
                            if (expandedContent) stringResource(R.string.show_less) else stringResource(
                                R.string.show_more
                            )
                        )
                    }
                }

            }
        }

    }

}

@Preview
@Composable
fun ListAnimationItemPreview() {
    ListAnimationItem(Modifier, "12/03/2025", "ListAnimationItem", "ListAnimationItem")
}