package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItem
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsView(
    clientsList: List<Client> = emptyList(),
    onItemSelected : (Client) -> Unit = {}) {



    Column {
        Header(
            title = pluralStringResource(
                R.plurals.title_clients,
                count = clientsList.size,
                clientsList.size
            ),
            backgroundToolbar = Color.Transparent,
            titleColor = MaterialTheme.colorScheme.primary,
            onRightIconClicked = {

            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            items(clientsList) { client ->
                ListAnimationItem(
                    title = client.phone,
                    body = client.name.plus(" ").plus(client.lastNanme),
                    content = client.detail
                ){
                    onItemSelected.invoke(client)
                }
            }
        }
    }
}

@Preview
@Composable
fun ClientsViewPreview() {
    ReportsGoTheme {
        ClientsView()
    }
}