package com.leandro.coinmarketcap.domain.usecase

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.model.Brl
import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.data.model.Quote
import com.leandro.coinmarketcap.data.model.entity.CoinEntity
import com.leandro.coinmarketcap.data.repository.LocalRepository
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class GetLocalListCoins @Inject constructor(
    private val repository: LocalRepository
) : BaseUseCase.Empty<DataState<List<Coin>>> {
    override suspend fun invoke(): DataState<List<Coin>> {
        return try {
            DataState.OnSuccess(repository.getAll().toCoin())
        } catch (e: Exception) {
            DataState.OnException(e)
        }
    }

    private fun List<CoinEntity>.toCoin(): List<Coin> {
        val coins = arrayListOf<Coin>()
        for (obj in this) {
            val brl = Brl(
                price = obj.price,
                percent_change_1h = obj.percent_change_1h,
                percent_change_24h = obj.percent_change_24h,
                percent_change_7d = obj.percent_change_7d,
                volume_24h = obj.volume_24h,
                market_cap = obj.market_cap
            )
            val quote = Quote(
                brl = brl
            )
            val coin = Coin(
                id = obj.id,
                symbol = obj.symbol,
                name = obj.name,
                max_supply = obj.max_supply,
                circulating_supply = obj.circulating_supply,
                quote = quote
            )
            coins.add(coin)
        }
        return coins
    }
}