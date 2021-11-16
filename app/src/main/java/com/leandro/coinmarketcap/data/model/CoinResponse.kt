package com.leandro.coinmarketcap.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
data class CoinResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("max_supply")
    val maxSupply: Double?,
    @SerializedName("circulating_supply")
    val circulatingSupply: String?,
    @SerializedName("quote")
    val quote: QuoteResponse?,
)
