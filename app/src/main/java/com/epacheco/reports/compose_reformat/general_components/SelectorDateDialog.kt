package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorDateDialog(
    modifier: Modifier = Modifier,
    initialDate: Long? = System.currentTimeMillis(),
    finalDate: Long? = System.currentTimeMillis(),
    onDateSelected: ((Long, Long) -> Unit)? = null,
) {
    val state =
        rememberDateRangePickerState(
            initialSelectedStartDateMillis = initialDate,
            initialSelectedEndDateMillis = finalDate
        )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column {
            /*Header(
                title = stringResource(
                    R.string.msg_select_date,
                ),
                titleColor = MaterialTheme.colorScheme.primary,
                onRightIconClicked = {
                    val startDate = state.selectedStartDateMillis ?: System.currentTimeMillis()
                    val endDate = state.selectedEndDateMillis ?: startDate
                    onDateSelected?.invoke(
                        startDate,
                        endDate
                    )
                },
                tintImageRight = MaterialTheme.colorScheme.primary,
                rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_ok),
            )*/

            DateRangePicker(
                state = state,
                modifier = modifier
                    .weight(1f)
            )
        }

        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .padding(bottom = 24.dp),
            textButton = stringResource(
                R.string.msg_date_selected,
            )
        ) {
            val startDate = state.selectedStartDateMillis ?: System.currentTimeMillis()
            val endDate = state.selectedEndDateMillis ?: startDate
            onDateSelected?.invoke(
                startDate,
                endDate
            )
        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SelectorDateDialogPreview() {
    ReportsGoTheme {
        SelectorDateDialog()
    }
}