package com.binar.kos.utils

import java.text.NumberFormat
import java.util.*

fun Int.toRp() : String{
    val locale = Locale("in","ID")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    numberFormat.maximumFractionDigits = 0
    return  numberFormat.format(this)
}