package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.database.CoinDao
import com.leandro.coinmarketcap.domain.model.Coin
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class LocalRepository @Inject constructor(
    private val dao: CoinDao
) : Repository.LocalData {
    override suspend fun insertAll(coins: List<Coin>): List<Long> {
        return dao.insertAll(coins = coins.toListEntities())
    }

    override suspend fun getAll(): List<Coin>? {
        return dao.getAll()?.toListCoin()
    }
}