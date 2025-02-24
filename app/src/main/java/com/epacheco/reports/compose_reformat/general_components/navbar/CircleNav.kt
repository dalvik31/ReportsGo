package com.epacheco.reports.compose_reformat.general_components.navbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomNavigationItem

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    radius: Dp,
    button: BottomNavigationItem,
    iconColor: Color,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(radius * 2)
            .clip(CircleShape)
            .background(color),
    ) {
        AnimatedContent(
            targetState = button.icon, label = "Bottom bar circle icon",
        ) { targetIcon ->
            Icon(
                ImageVector.vectorResource(targetIcon),
                stringResource(button.label),
                tint = iconColor
            )
        }
    }
}