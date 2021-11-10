package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.data.model.Data
import com.leandro.coinmarketcap.data.model.entity.CoinEntity
import retrofit2.Response

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
interface Repository {
    interface RemoteData {
        suspend fun getCoins(): Response<Data?>?
    }

    interface LocalData {
        suspend fun insertAll(coin: Coin): List<Long?>

        suspend fun getForId(id: Int): CoinEntity?

        suspend fun getAll(): List<CoinEntity>

        suspend fun deleteAll()
    }
}