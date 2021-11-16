package com.leandro.coinmarketcap.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Parcelize
data class Data(
    val data: List<Cryptocurrency>? = null
) : Parcelable