package com.epacheco.reports.compose_reformat.utils

import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


/**
 * PermissionToCheck -> Manifest.permission.READ_EXTERNAL_STORAGE or  Manifest.permission.CAMERA
 *
 * */
@Composable
fun CheckPermissions(
    onDenied: (requester: () -> Unit) -> Unit,
    onGranted: () -> Unit, permissionToCheck: String
) {
    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permissionToCheck
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    if (hasPermission) {
        onGranted.invoke()
    } else {
        val launcher: ManagedActivityResultLauncher<String, Boolean> =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
                hasPermission = it
            }

        onDenied { launcher.launch(permissionToCheck) }
    }

}
