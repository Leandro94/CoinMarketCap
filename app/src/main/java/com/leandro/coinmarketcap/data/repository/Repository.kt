package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.domain.model.Cryptocurrency

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
interface Repository {
    interface RemoteData {
        suspend fun getCoins(): DataState<List<Cryptocurrency>>?
    }

    interface LocalData {
        suspend fun insertAll(cryptocurrencies: List<Cryptocurrency>): List<Long>

        suspend fun getAll(): List<Cryptocurrency>?

    }
}