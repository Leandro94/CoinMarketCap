package com.leandro.coinmarketcap.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Parcelize
data class Brl(
    val price: Double,
    val percentChange1h: Double,
    val volume24h: Double,
    val percentChange24h: Double,
    val percentChange7d: Double,
    val marketCap: Double,
    val dilutedMarketCap: Double
) : Parcelable
