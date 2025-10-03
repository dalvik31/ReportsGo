package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.util.Log
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
import com.epacheco.reports.view.productsView.productAddView.ProductAddViewClass
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException


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


            //Bitmap obtenido en la toma de la foto.
            /*var takenImage =
                BitmapFactory.decodeFile(permissionsViewModel.getBitmapToFile(bitmap).absolutePath)
            try {
                takenImage =
                    rotateImageIfRequired(
                        takenImage,
                        permissionsViewModel.getBitmapToFile(bitmap).absolutePath
                    )
            } catch (e: IOException) {
                Log.e("Error", "Ocurrio un error al girar la imagen")
                e.printStackTrace()
            }*/
            coroutineScope.launch {
                onImageSelected?.invoke(
                    permissionsViewModel.getBitmapToFile(bitmap)
                )
                // onImageSelected?.invoke(permissionsViewModel.getBitmapToFile(rotateImage(bitmap, 90)))
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

/**
 * En dispositivos actuales al tomar la foto, la toma con una orientacion
 * diferente y parace que la toma horizontal.
 * Este metodo parece que soluciona ese issue pero se tendria que probar en diferentes dispositivos.
 */
/*Throws(IOException::class)
private fun rotateImageIfRequired(img: Bitmap, selectedImage: String): Bitmap {
    val ei = ExifInterface(selectedImage)
    val orientation =
        ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
        else -> img
    }
}

/**
 * Metodo que rota la imagen
 */
private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true)
    img.recycle()
    return rotatedImg
}*/

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PermissionsScreenPreview() {
    ReportsGoTheme {
        PickerPictureDialog()
    }

}