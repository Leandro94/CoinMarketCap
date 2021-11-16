package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.database.CryptocurrencyDao
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class LocalRepository @Inject constructor(
    private val dao: CryptocurrencyDao
) : Repository.LocalData {
    override suspend fun insertAll(cryptocurrencies: List<Cryptocurrency>): List<Long> {
        return dao.insertAll(cryptocurrencies = cryptocurrencies.toListEntities())
    }

    override suspend fun getAll(): List<Cryptocurrency>? {
        return dao.getAll()?.toListCryptocurrency()
    }
}