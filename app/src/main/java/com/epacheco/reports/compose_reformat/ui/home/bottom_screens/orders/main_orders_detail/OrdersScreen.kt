package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.CheckPermission
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.gotoApplicationContact
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import android.location.Address
import com.epacheco.reports.compose_reformat.utils.extensions.getFormatAddress
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel<OrdersViewModel>(),
    onBackPressed: (() -> Unit)? = null,
    onNavigateToCreateOrder: ((String, Season?) -> Unit)? = null,
    onNavigateToEditOrder: ((String, String) -> Unit)? = null,
    mainOrderId: String,
    orderSeason: Season?,
    nameOrderMain: String,
    clientId: String? = null
) {
    val uiState by ordersViewModel.uiState.collectAsState()
    var showLocationDialog by remember { mutableStateOf(false) }
    var selectionMode by remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        ordersViewModel.handleIntent(OrdersUiIntent.LoadOrders(mainOrderId))
    }

    LaunchedEffect(Unit) {
        clientId?.let {
            onNavigateToCreateOrder?.invoke(mainOrderId, orderSeason)
        }
    }

    LaunchedEffect(ordersViewModel) {
        ordersViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersUiEffect.NavigateToCreateOrder -> onNavigateToCreateOrder?.invoke(
                    mainOrderId,
                    orderSeason
                )

            }
        }
    }

    OrdersView(
        orderList = uiState.orders,
        showImgEmptyList = uiState.showImgEmptyList,
        nameOrderMain = nameOrderMain,
        mainOrderId = mainOrderId,
        onBackPressed = { onBackPressed?.invoke() },
        onCreateOrderClick = {
            onNavigateToCreateOrder?.invoke(mainOrderId, orderSeason)
        },
        onOrderClick = {
            if (it == null) {
                selectionMode = false
            }else{
                selectionMode = !selectionMode
            }

            //onNavigateToEditOrder?.invoke(it.orderListId, it.orderId)
        },
        isRefreshing = uiState.isLoading,
        onUpdateStatusOrderClick = {
            showLocationDialog = true
            ordersViewModel.handleIntent(OrdersUiIntent.SetOrderSelected(it))
        },
        onSelectedModeClick = {
            selectionMode = !selectionMode
        },
        isSelectedMode = selectionMode,
        progressList = uiState.progressOrders,
        onRefresh = {
            ordersViewModel.handleIntent(OrdersUiIntent.LoadOrders(mainOrderId))
        },
        moveSelectedItems = {itemsSelected ->
            itemsSelected.forEach { order ->
                Log.e("aqui","item: ${order.orderName}")
            }
            //ordersViewModel.handleIntent(OrdersUiIntent.MoveSelectedItems(itemsSelected))

        }
    )

    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                ordersViewModel.handleIntent(OrdersUiIntent.HideDialogs)
            })
    }

    uiState.successOperationMsg?.let { msgSuccessOperation ->
        Toast.makeText(context, stringResource(msgSuccessOperation), Toast.LENGTH_SHORT).show()
        ordersViewModel.handleIntent(OrdersUiIntent.HideDialogs)
    }

    if (showLocationDialog) {
        CheckPermission(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            iconPermission = R.drawable.ic_location,
            onGranted = {
                showLocationDialog = false
                getCurrentLocation(context) { lat, long ->
                    uiState.orderSelected?.let { orderSelected ->
                        ordersViewModel.handleIntent(
                            OrdersUiIntent.UpdateStatusOrder(
                                orderSelected.orderId,
                                mainOrderId,
                                orderBuy = !orderSelected.orderBuy,
                                locationLat = lat,
                                locationLong = long,
                                address = getStreetName(context, lat, long)?.getFormatAddress()
                            )
                        )
                    }
                }
            },
            permissionRationaleTitle = stringResource(R.string.permission_phone_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_phone_settings_title),
            onCancel = { showLocationDialog = false }
        )

    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getStreetName(context: Context, lat: Double, lng: Double): Address? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        var address: Address? = null
        // Fetches maximum of 1 address result

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // API 33+ (Non-blocking)
            geocoder.getFromLocation(lat, lng, 1) { addresses ->
                // Handle list of addresses here
                address = addresses?.firstOrNull()
            }
        } else {
            // Legacy (Blocking - should be run on a background thread)
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            address = addresses?.firstOrNull()
        }

        address
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            }
        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            exception.printStackTrace()
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdersScreenPreview() {
    ReportsGoTheme {
        OrdersView()
    }

}