package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.utils.UriUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(private val app: ReportsApp) : ViewModel() {
    private val manifestGalleryPermission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

    private val manifestCameraPermission = Manifest.permission.CAMERA

    private val manifestArrayPermission =
        arrayOf(manifestGalleryPermission, manifestCameraPermission)

    fun galleryPermissionIsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            app,
            manifestGalleryPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun cameraPermissionIsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            app,
            manifestCameraPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun saveBitmapToUri(bitmap: Bitmap): Uri? {
        return UriUtils.saveBitmapToUri(app, bitmap)
    }

    fun getGalleryPermission() = manifestGalleryPermission

    fun getArrayPermissions() = manifestArrayPermission
}