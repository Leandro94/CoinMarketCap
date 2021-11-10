package com.leandro.coinmarketcap.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Parcelize
data class Brl(
    val price: Double,
    val percent_change_1h: Double,
    val volume_24h: String,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val market_cap: String,
) : Parcelable
