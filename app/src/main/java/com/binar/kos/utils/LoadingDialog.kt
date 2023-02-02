package com.binar.kos.utils

import android.app.Activity
import android.app.AlertDialog
import com.binar.kos.R

private lateinit var isdialog: AlertDialog

fun showLoading(mActivity: Activity) {
    /**set View*/
    val inflater = mActivity.layoutInflater
    val dialogView = inflater.inflate(R.layout.loading_dialog, null)

    /**set Dialog*/
    val builder = AlertDialog.Builder(mActivity)
    builder.setView(dialogView)
    builder.setCancelable(false)
    isdialog = builder.create()
    isdialog.show()
}

fun hideLoading() {
    isdialog.dismiss()
}