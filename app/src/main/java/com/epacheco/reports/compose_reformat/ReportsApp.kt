package com.epacheco.reports.compose_reformat

import android.app.Application
import android.widget.Toast
import com.epacheco.reports.R
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ReportsApp : Application() {

    fun showMsgToast(msg: Int?) {
        val toastMsg = Toast.makeText(this, msg ?: R.string.general_error, Toast.LENGTH_LONG)
        toastMsg.cancel()
        toastMsg.show()
    }
}