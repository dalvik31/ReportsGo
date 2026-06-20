@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.epacheco.reports.compose_reformat.utils.extensions

import android.location.Address
import java.io.File

fun Address.getFormatAddress(): String {
    val fullAddress: StringBuilder = StringBuilder()

    val countryName = this.countryName
    val adminArea = this.adminArea
    val subLocality = this.subLocality
    val locality = this.locality
    val thoroughfare = this.thoroughfare
    val subThoroughfare = this.subThoroughfare

  /*  thoroughfare?.let {
        fullAddress.append(it)
        fullAddress.append(", ")
    }

    subThoroughfare?.let {
        fullAddress.append(it)
        fullAddress.append(", ")
    }

    subLocality?.let {
        fullAddress.append(it)
        fullAddress.append(", ")
    }*/

    locality?.let {
        fullAddress.append(it)
        //fullAddress.append(", ")
    }
  /*  adminArea?.let {
        fullAddress.append(it)
        fullAddress.append(", ")
    }
    countryName?.let {
        fullAddress.append(it)

    }*/

    return fullAddress.toString()
}


