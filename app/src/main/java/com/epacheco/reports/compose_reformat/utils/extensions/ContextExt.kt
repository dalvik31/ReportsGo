package com.epacheco.reports.compose_reformat.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import androidx.core.net.toUri

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


fun Context.getContactDetails(contactUri: Uri): Triple<String?, String?, String?> {
    var name: String? = null
    var lastName: String? = null
    var phoneNo: String? = null

    val cursor: Cursor? = this.contentResolver.query(contactUri, null, null, null, null)

    try {
        if (cursor != null && cursor.moveToFirst()) {
            val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nameIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

            if (phoneIndex != -1) {
                phoneNo = cursor.getString(phoneIndex)
            }
            if (nameIndex != -1) {
                name = cursor.getString(nameIndex).substringBefore(" ")
                lastName = cursor.getString(nameIndex).substringAfterLast(" ")
                if (name == lastName) {
                    lastName = ""
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        cursor?.close()
    }
    return Triple(name, lastName, phoneNo)
}


fun Context.gotoApplicationContact(phone: String?) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.setData(("tel:$phone").toUri())
    this.startActivity(intent)
}

