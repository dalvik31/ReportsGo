package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun NewProductView(
    productToEdit: String? = null,
) {
    Column {
        Header(
            title = "Mis productos ${productToEdit}",
            backgroundToolbar = Color.Transparent,
            titleColor = MaterialTheme.colorScheme.primary,
            onRightIconClicked = {
                //onCreateOrderMainClick?.invoke()
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add)
        )


    }
}

@Preview
@Composable
fun ProductsViewPreview() {
    ReportsGoTheme {
        NewProductView()
    }
}
