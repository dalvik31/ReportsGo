package com.epacheco.reports.compose_reformat.general_components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.utils.extensions.findActivity
import com.epacheco.reports.compose_reformat.utils.extensions.gotoApplicationSettings
import com.epacheco.reports.compose_reformat.utils.extensions.shouldShowRationale
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    permission: String,
    onGranted: (() -> Unit),
    onCancel: (() -> Unit),
    permissionRationaleTitle: String = stringResource(R.string.lbl_empty),
    permissionOpenSettingsTitle: String = stringResource(R.string.lbl_empty)
) {
    val context = LocalContext.current
    var showScanCodeRationaleDialog by remember { mutableStateOf(false) }
    var showScanCodeSettingsDialog by remember { mutableStateOf(false) }

    val readStoragePermissionState = rememberPermissionState(
        permission = permission
    ) { granted ->
        if (granted) {
            onGranted.invoke()
        } else {
            context.findActivity()?.apply {
                when {
                    shouldShowRationale(permission) -> {
                        showScanCodeRationaleDialog = true
                    }

                    else -> {
                        showScanCodeSettingsDialog = true
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        readStoragePermissionState.launchPermissionRequest()
    }


    if (showScanCodeRationaleDialog) {
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_vector_add_photo,
            dialogSubTitle = permissionRationaleTitle,
            confirmButtonText = stringResource(R.string.permission_btn_get_permission),
            cancelButtonText = stringResource(R.string.btn_close),
            onDismissRequest = {
                onCancel.invoke()
                showScanCodeRationaleDialog = false
            },
            onConfirmation = {
                showScanCodeRationaleDialog = false
                readStoragePermissionState.launchPermissionRequest()
            }
        )
    }

    if (showScanCodeSettingsDialog) {
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_vector_add_photo,
            dialogSubTitle = permissionOpenSettingsTitle,
            confirmButtonText = stringResource(R.string.permission_btn_open_settings),
            cancelButtonText = stringResource(R.string.btn_close),
            onDismissRequest = {
                onCancel.invoke()
                showScanCodeSettingsDialog = false
            },
            onConfirmation = {
                context.gotoApplicationSettings()
                showScanCodeSettingsDialog = false
                onCancel.invoke()
            }
        )
    }

}