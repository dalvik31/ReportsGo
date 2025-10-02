package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.CheckPermission
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.view.productsView.scanCode.ScannedBarcodeActivity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun PickerPictureDialog(
    onImageSelected: ((File?) -> Unit)? = null,
    onDismissRequest: ((() -> Unit))? = null,
) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        PickerPictureDialogScreen(
            onImageSelected = onImageSelected,
            onDismissRequest = onDismissRequest
        )
    }
}


@Composable
fun PickerPictureDialogScreen(
    permissionsViewModel: PermissionsViewModel = hiltViewModel<PermissionsViewModel>(),
    onImageSelected: ((File?) -> Unit)? = null,
    onDismissRequest: ((() -> Unit))? = null,
) {

    var showPermissionCameraDialog by remember { mutableStateOf(false) }
    var showPermissionGalleryDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    // Launchers for selecting an image from the gallery or camera
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                onImageSelected?.invoke(permissionsViewModel.getUriToFile(uri))
            }
        }
        onDismissRequest?.invoke()
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            coroutineScope.launch {
                onImageSelected?.invoke(permissionsViewModel.getBitmapToFile(bitmap))
                onDismissRequest?.invoke()
            }

        }
    }

    PermissionsPictureView(
        onGalleryClicked = {
            showPermissionGalleryDialog = true
        },
        onCameraClicked = {
            showPermissionCameraDialog = true
        })



    if (showPermissionCameraDialog) {
        CheckPermission(
            permission = Manifest.permission.CAMERA,
            onGranted = {
                cameraLauncher.launch(null)
                showPermissionCameraDialog = false

            },
            permissionRationaleTitle = stringResource(R.string.permission_camera_product_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_camera_settings_product_title),
            onCancel = { showPermissionCameraDialog = false }
        )

    }

    if (showPermissionGalleryDialog) {
        CheckPermission(
            permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            },
            onGranted = {
                galleryLauncher.launch("image/*")
                showPermissionGalleryDialog = false

            },
            permissionRationaleTitle = stringResource(R.string.permission_gallery_product_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_gallery_settings_product_title),
            onCancel = { showPermissionGalleryDialog = false }
        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PermissionsScreenPreview() {
    ReportsGoTheme {
        PickerPictureDialog()
    }

}