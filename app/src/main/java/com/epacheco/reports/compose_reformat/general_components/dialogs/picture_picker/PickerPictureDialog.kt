package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
    val coroutineScope = rememberCoroutineScope()

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onImageSelected?.invoke(permissionsViewModel.getUriToFile(uri))
            onDismissRequest?.invoke()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {

            coroutineScope.launch {
                onImageSelected?.invoke(
                    permissionsViewModel.getBitmapToFile(bitmap)
                )

                onDismissRequest?.invoke()
            }

        }
    }

    PermissionsPictureView(
        onGalleryClicked = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onCameraClicked = {
            showPermissionCameraDialog = true
        })



    if (showPermissionCameraDialog) {
        CheckPermission(
            permission = Manifest.permission.CAMERA,
            iconPermission = R.drawable.ic_vector_add_photo,
            onGranted = {
                cameraLauncher.launch(null)
                showPermissionCameraDialog = false

            },
            permissionRationaleTitle = stringResource(R.string.permission_camera_product_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_camera_settings_product_title),
            onCancel = { showPermissionCameraDialog = false }
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