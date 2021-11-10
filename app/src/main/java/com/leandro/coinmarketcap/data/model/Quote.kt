package com.leandro.coinmarketcap.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Parcelize
data class Quote(
    @SerializedName("BRL")
    val brl: Brl
): Parcelable
