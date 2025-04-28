package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun PickerPictureDialog(
    onImageSelected: ((Uri?) -> Unit)? = null,
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
    onImageSelected: ((Uri?) -> Unit)? = null,
    onDismissRequest: ((() -> Unit))? = null,
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var hasGalleryPermission by remember {
        mutableStateOf(
            permissionsViewModel.galleryPermissionIsGranted()
        )
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            permissionsViewModel.cameraPermissionIsGranted()
        )
    }

    // Launchers for selecting an image from the gallery or camera
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        onImageSelected?.invoke(uri)
        onDismissRequest?.invoke()
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = permissionsViewModel.saveBitmapToUri(it)
            selectedImageUri = uri
            onImageSelected?.invoke(uri)
            onDismissRequest?.invoke()
        }
    }

    // Permission array request launcher
    val permissionArrayLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasGalleryPermission = permissions[permissionsViewModel.getGalleryPermission()] ?: false
        hasCameraPermission = permissions[Manifest.permission.CAMERA] ?: false

        if (hasCameraPermission && hasGalleryPermission) {
            cameraLauncher.launch(null)
        }
    }

    // Permission only one request launcher
    val permissionGalleyLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasGalleryPermission = isGranted
        if (isGranted) {
            galleryLauncher.launch("image/*")
        }
    }

    PermissionsPictureView(
        onGalleryClicked = {
            if (hasGalleryPermission) {
                galleryLauncher.launch("image/*")
            } else permissionGalleyLauncher.launch(permissionsViewModel.getGalleryPermission())
        },
        onCameraClicked = {
            if (hasCameraPermission && hasGalleryPermission) {
                cameraLauncher.launch(null)
            } else permissionArrayLauncher.launch(
                permissionsViewModel.getArrayPermissions()
            )
        })
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PermissionsScreenPreview() {
    ReportsGoTheme {
        PickerPictureDialogScreen()
    }

}