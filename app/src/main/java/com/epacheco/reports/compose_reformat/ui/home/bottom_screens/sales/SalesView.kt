package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.ProductItem
import com.epacheco.reports.compose_reformat.general_components.SearchBarElement
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White


@Composable
fun SalesView(
    inputClient: String? = null,
    onInputClientChanged: (() -> Unit)? = null,
    onNavigateToFinances: (() -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null
) {

    Column {
        Header(
            title = stringResource(R.string.generate_sale),
            titleColor = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_activity),
            onRightIconClicked = {
                onNavigateToFinances?.invoke()
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            onProfileClicked = { onNavigateToProfile?.invoke() },
            tintIconProfile = MaterialTheme.colorScheme.primary
        )

        InputTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clickable {

                },
            textHint = stringResource(R.string.select_client),
            textValue = inputClient ?: "",
            enable = false
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        InputTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clickable {

                },
            textHint = stringResource(R.string.select_product),
            textValue = inputClient ?: "",
            enable = false
        )


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable()
fun SalesViewPreview() {
    ReportsGoTheme {
        SalesView()
    }
}
