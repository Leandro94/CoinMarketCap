package com.leandro.coinmarketcap.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
data class DataResponse(
    @SerializedName("data")
    val data: List<CoinResponse>? = null
)