package com.binar.kos.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
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