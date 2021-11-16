package com.leandro.coinmarketcap.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
data class QuoteResponse(
    @SerializedName("BRL")
    val blr: BrlResponse?
)
