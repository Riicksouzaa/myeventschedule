package com.rsdev.myeventschedule.utils.highorder

import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

fun Number.formatToDate(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    val netDate = Date(this.toLong())
    return sdf.format(netDate)
}

fun Number.formatNumberIntoFiatPattern(): String {
    val symbols = DecimalFormatSymbols()
    symbols.decimalSeparator = ','
    symbols.groupingSeparator = '.'

    val format = DecimalFormat("#,###.00")
    format.decimalFormatSymbols = symbols
    format.minimumIntegerDigits = 1
    format.isDecimalSeparatorAlwaysShown = true
    return format.format(this)
}

fun String.transformToFiatBRL(): String = "R$ ${this.fromFiatBRL().formatNumberIntoFiatPattern()}"

fun Number.transformToFiatBRL(): String = "R$ ${this.formatNumberIntoFiatPattern()}"

fun String.fromFiatBRL(): Float = this.replaceStringFiatBRL().toFloat() / 100

fun String.replaceStringFiatBRL() = this
    .replace("[R$]".toRegex(), "")
    .replace("[.]".toRegex(), "")
    .replace("[,]".toRegex(), "")
    .replace("[ ]".toRegex(), "")
    .replace("[\n]".toRegex(), "")

fun TextInputEditText.setupError(message: String) {
    this.error = message
    this.requestFocus()
}
