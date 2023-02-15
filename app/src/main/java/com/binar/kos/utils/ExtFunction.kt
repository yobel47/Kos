package com.binar.kos.utils

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.text.NumberFormat
import java.util.*

fun Int.toRp() : String{
    val locale = Locale("in","ID")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    numberFormat.maximumFractionDigits = 0
    return  numberFormat.format(this)
}

fun String.toCapital() : String{
    return split(" ").joinToString(separator = " ", transform = String::capitalize)
}

fun getHeaderMap(accessToken: String): Map<String, String> {
    val headerMap = mutableMapOf<String, String>()
    headerMap["Connection"] = "keep-alive"
    headerMap["Accept"] = "*/*"
    headerMap["Accept-Encoding"] = "gzip, deflate, br"
    headerMap["Authorization"] = "Bearer $accessToken"
    return headerMap
}

fun getFirebaseToken() : String{
    var firebaseToken = ""
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        firebaseToken = task.result

        // Log and toast
        Log.d("Firebase token", firebaseToken)
    })
    return firebaseToken
}