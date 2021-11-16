package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.database.CoinDao
import com.leandro.coinmarketcap.data.database.entity.CryptocurrencyEntity
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class LocalRepository @Inject constructor(
    private val dao: CoinDao
) : Repository.LocalData {
    override suspend fun insertAll(cryptocurrencies: List<Cryptocurrency>): List<Long> {
        return dao.insertAll(cryptocurrencies = cryptocurrencies.toListEntities())
    }

    override suspend fun getForId(id: Int): CryptocurrencyEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Cryptocurrency>? {
        return dao.getAll()?.toListCryptocurrency()
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}