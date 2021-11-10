package com.leandro.coinmarketcap.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Parcelize
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val max_supply: String,
    val circulating_supply: String,
    val quote: Quote
) : Parcelable
