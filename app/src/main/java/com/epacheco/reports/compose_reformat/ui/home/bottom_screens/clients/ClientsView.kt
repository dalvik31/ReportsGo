package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.ClientCompactItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.SearchBarElement
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.initials
import com.epacheco.reports.compose_reformat.utils.extensions.toCurrencyFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsView(
    clientsList: List<Client> = emptyList(),
    onClientSelected: (Client) -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onInputNameChanged: ((String) -> Unit)? = null,
    inputName: String? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    onNavigateToCreateClient: ((String?) -> Unit)? = null,
    onPhoneClick: ((String) -> Unit)? = null
) {
    val state = rememberPullToRefreshState()

    Column {
        Header(
            text = pluralStringResource(
                R.plurals.title_clients,
                count = clientsList.size,
                clientsList.size
            ),
            backgroundToolbar = Color.Transparent,
            textColor = MaterialTheme.colorScheme.primary,
            onRightIconClicked = {
                onNavigateToCreateClient?.invoke(null)
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add),
            onProfileClicked = { onNavigateToProfile?.invoke() },
            tintIconProfile = MaterialTheme.colorScheme.primary,
        )

        SearchBarElement(
            modifier = Modifier.padding(horizontal = 16.dp),
            searchHintText = stringResource(id = R.string.lbl_search_client_hint),
            searchText = inputName ?: ""
        ) {
            onInputNameChanged?.invoke(it)
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = state,
            onRefresh = { onRefresh?.invoke() },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    color = MaterialTheme.colorScheme.primary,
                    state = state
                )
            }
        ) {

            if (clientsList.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_vector_clients_empty),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onNavigateToCreateClient?.invoke(null)
                        }
                    )
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.msg_clients_not_found),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            } else {
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
                            contentText = client.debt.toCurrencyFormat(),
                            secondaryText = client.phone.ifEmpty { null },
                            onClick = {
                                onClientSelected.invoke(client)
                            },
                            onLongClick = {
                                onNavigateToCreateClient?.invoke(client.id)
                            }, avatarLetters = clientName.initials(),
                            progressLimit = client.geProgressLimit(),
                            phoneClick = {
                                onPhoneClick?.invoke(client.phone)
                            }
                        )
                    }
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