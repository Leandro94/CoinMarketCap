package com.leandro.coinmarketcap.domain.model

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
    val maxSupply: Double,
    val circulatingSupply: String,
    val quote: Quote,
    val imgUrl: String,
) : Parcelable
