package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.domain.model.Coin

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
interface Repository {
    interface RemoteData {
        suspend fun getCoins(): DataState<List<Coin>>?
    }

    interface LocalData {
        suspend fun insertAll(coins: List<Coin>): List<Long>

        suspend fun getAll(): List<Coin>?

    }
}