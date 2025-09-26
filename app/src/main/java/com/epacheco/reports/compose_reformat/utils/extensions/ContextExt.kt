package com.epacheco.reports.compose_reformat.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> {
            baseContext.findActivity()
        }

        else -> null
    }
}

fun Context.gotoApplicationSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(intent)
}

fun Activity.shouldShowRationale(name: String): Boolean {
    return shouldShowRequestPermissionRationale(name)
}
