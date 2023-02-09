package com.binar.kos.utils

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

fun View.hideKeyboard() {
    val imm = context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun getHeaderMap(accessToken: String): Map<String, String> {
    val headerMap = mutableMapOf<String, String>()
    headerMap["Connection"] = "keep-alive"
    headerMap["Accept"] = "*/*"
    headerMap["Accept-Encoding"] = "gzip, deflate, br"
    headerMap["Authorization"] = "Bearer $accessToken"
    return headerMap
}

