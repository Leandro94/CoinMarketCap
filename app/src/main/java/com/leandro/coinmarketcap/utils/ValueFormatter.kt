package com.leandro.coinmarketcap.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToLong

fun formatDoubleToStringCurrency(value: Double): String {
    val format = DecimalFormat(
        "##,###,###,##0.00",
        DecimalFormatSymbols(
            Locale(
                "pt",
                "BR"
            )
        )
    )

    val minimumFractionDigits = getMinimumFractionDigits(value)

    format.minimumFractionDigits = minimumFractionDigits
    format.isParseBigDecimal = true
    return format.format(value) + "%"
}

fun formatDoubleToStringCurrencyWithSymbol(value: Double): String {
    val format = DecimalFormat(
        "##,###,###,##0.00",
        DecimalFormatSymbols(
            Locale(
                "pt",
                "BR"
            )
        )
    )
    val locale = Locale(
        "pt",
        "BR"
    )
    val symbol = Currency.getInstance(locale).getSymbol(locale)

    val minimumFractionDigits = getMinimumFractionDigits(value)

    format.minimumFractionDigits = minimumFractionDigits
    format.isParseBigDecimal = true
    return symbol + " " + format.format(value)
}

fun formatDoubleToDecimalTwoPlaces(value: Double, places: Int = 2): Double {
    val price: Double
    val factor = 10.0.pow(places.toDouble()).toLong()
    price = value * factor
    val tmp = price.roundToLong()
    return tmp.toDouble() / factor
}

fun getMinimumFractionDigits(value: Double): Int {
    return if (value.toString().contains(".00") || value.toString().contains(".0")) {
        0
    } else {
        2
    }
}