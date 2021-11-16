package com.leandro.coinmarketcap.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Leandro.Reis on 09/11/2021.
 */

data class BrlResponse(
    @SerializedName("price")
    val price: Double,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double,
    @SerializedName("volume_24h")
    val volume24h: Double,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("fully_diluted_market_cap")
    val dilutedMarketCap: Double
)
