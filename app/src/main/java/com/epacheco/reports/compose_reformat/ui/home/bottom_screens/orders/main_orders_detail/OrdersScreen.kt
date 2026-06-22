package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
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
import android.net.Uri
import androidx.compose.runtime.rememberCoroutineScope
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.utils.extensions.getFormatAddress
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import androidx.core.net.toUri

@SuppressLint("NewApi")
@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel<OrdersViewModel>(),
    onBackPressed: (() -> Unit)? = null,
    onNavigateToCreateOrder: ((String, Season?) -> Unit)? = null,
    onNavigateToCreateMainOrder: ((List<Order>, String) -> Unit)? = null,
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
    val scope = rememberCoroutineScope()
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
            } else {
                selectionMode = !selectionMode
            }
        },
        isRefreshing = uiState.isLoading,
        onUpdateStatusOrderClick = {
            showLocationDialog = true
            ordersViewModel.handleIntent(OrdersUiIntent.SetOrderSelected(it))
        },
        isSelectedMode = selectionMode,
        progressList = uiState.progressOrders,
        onRefresh = {
            ordersViewModel.handleIntent(OrdersUiIntent.LoadOrders(mainOrderId))
        },
        moveSelectedItems = { itemsSelected ->
            onNavigateToCreateMainOrder?.invoke(itemsSelected, mainOrderId)
            //ordersViewModel.handleIntent(OrdersUiIntent.MoveSelectedItems(itemsSelected))

        },
        onEditOrderClick = {
            onNavigateToEditOrder?.invoke(it.orderListId, it.orderId)
        },
        onOrderLocationClick = { latitude, longitude ->
            openGoogleMaps(context, latitude, longitude)
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
                    scope.launch {
                        val address = getStreetName(context, lat, long)
                        uiState.orderSelected?.let { orderSelected ->
                            ordersViewModel.handleIntent(
                                OrdersUiIntent.UpdateStatusOrder(
                                    orderSelected.orderId,
                                    mainOrderId,
                                    orderBuy = !orderSelected.orderBuy,
                                    locationLat = lat,
                                    locationLong = long,
                                    address = address?.getFormatAddress()
                                )
                            )
                        }
                    }
                }
            },
            permissionRationaleTitle = stringResource(R.string.permission_phone_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_phone_settings_title),
            onCancel = { showLocationDialog = false }
        )
    }
}

fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
    val mapUri = "https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude&travelmode=driving".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, mapUri).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        context.startActivity(mapIntent)
    } catch (e: ActivityNotFoundException) {
        val browserUri = "https://google.com".toUri()
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}

suspend fun getStreetName(context: Context, lat: Double, lng: Double): Address? =
    withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context, Locale.getDefault())
        return@withContext try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // API 33+ (Non-blocking)
                suspendCancellableCoroutine { continuation ->
                    try {
                        geocoder.getFromLocation(lat, lng, 1, object : Geocoder.GeocodeListener {
                            override fun onGeocode(addresses: MutableList<Address>) {
                                continuation.resume(addresses.firstOrNull())
                            }

                            override fun onError(errorMessage: String?) {
                                Log.e(
                                    "LocationError",
                                    "Error getting location (API 33+): $errorMessage"
                                )
                                continuation.resume(null)
                            }
                        })
                    } catch (e: Exception) {
                        Log.e("LocationError", "Error initiating geocoding: ${e.message}")
                        continuation.resume(null)
                    }
                }
            } else {
                // Legacy (Blocking - run on IO thread)
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                addresses?.firstOrNull()
            }
        } catch (e: Exception) {
            Log.e("LocationError", "Error getting location: ${e.message}")
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