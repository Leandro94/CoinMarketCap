package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.database.CoinDao
import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.data.model.entity.CoinEntity
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class LocalRepository @Inject constructor(
    private val dao: CoinDao
) : Repository.LocalData {
    override suspend fun insertAll(coin: Coin): List<Long?> {
        return dao.insertAll(coin = coin.toEntity())
    }

    override suspend fun getForId(id: Int): CoinEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<CoinEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    private fun Coin.toEntity(): Array<out CoinEntity> {
        val coinEntity = CoinEntity(
            _id = this.id.toLong(),
            id = this.id,
            symbol = this.symbol,
            name = this.name,
            price = this.quote.brl.price,
            percent_change_1h = this.quote.brl.percent_change_1h,
            percent_change_24h = this.quote.brl.percent_change_24h,
            percent_change_7d = this.quote.brl.percent_change_7d,
            max_supply = this.max_supply,
            volume_24h = this.quote.brl.volume_24h,
            circulating_supply = this.circulating_supply,
            market_cap = this.quote.brl.market_cap
        )
        return arrayOf(coinEntity)
    }
}