package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.ClientCompactItem
import com.epacheco.reports.compose_reformat.general_components.ClientItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.SearchBarElement
import com.epacheco.reports.compose_reformat.general_components.SecondaryItem
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.Initials
import com.epacheco.reports.compose_reformat.utils.extensions.getNameSeason

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsView(
    clientsList: List<Client> = emptyList(),
    onClientSelected: (Client) -> Unit = {},
    onInputNameChanged: ((String) -> Unit)? = null,
    inputName: String? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    onNavigateToCreateClient: ((String?) -> Unit)? = null,
) {


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
                onNavigateToCreateClient?.invoke(null)
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add),
            onProfileClicked = { onNavigateToProfile?.invoke() },
            tintIconProfile = MaterialTheme.colorScheme.primary,
        )

        /*LazyColumn(
             modifier = Modifier
                 .fillMaxWidth()
                 .background(color = Color.Transparent)
         ) {
             items(clientsList) { client ->
                 ClientItem(
                     client = client,
                     showActions = true,
                     onClickIcon = {

                     },
                     onClick = {
                         onItemSelected.invoke(client)
                     })
             }
         }*/

        SearchBarElement(
            modifier = Modifier.padding(horizontal = 16.dp),
            searchHintText = stringResource(id = R.string.lbl_search_client_hint),
            searchText = inputName ?: "") {
            onInputNameChanged?.invoke(it)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {


            items(clientsList) { client ->

                val clientName = client.name.plus(" ").plus(client.lastNanme)
                ClientCompactItem(
                    text = clientName,
                    contentText = client.detail.ifEmpty { null },
                    secondaryText = client.phone.ifEmpty { null },
                    onClick = {
                        onClientSelected.invoke(client)
                    },
                    onLongClick = {
                        onNavigateToCreateClient?.invoke(client.id)
                    }, avatarLetters = clientName.Initials(),
                    progressLimit = client.geProgressLimit()
                )
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