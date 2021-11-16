package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.database.entity.CryptocurrencyEntity
import com.leandro.coinmarketcap.data.model.BrlResponse
import com.leandro.coinmarketcap.data.model.CryptocurrencyResponse
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.utils.formatDoubleToDecimalTwoPlaces

/**
 * Created by Leandro.Reis on 13/11/2021.
 */
fun List<CryptocurrencyResponse>.responseToListCryptocurrency(): List<Cryptocurrency> {
    return this.map { it.toCryptocurrency() }
}

fun CryptocurrencyResponse.toCryptocurrency(): Cryptocurrency {
    val quote = Quote(
        brl = this.quote.blr.toBrl()
    )
    return Cryptocurrency(
        id = id,
        symbol = symbol,
        name = name,
        maxSupply = maxSupply?.let { formatDoubleToDecimalTwoPlaces(it) },
        circulatingSupply = circulatingSupply,
        quote = quote
    )
}

fun BrlResponse.toBrl(): Brl {
    return Brl(
        price = formatDoubleToDecimalTwoPlaces(this.price),
        percentChange1h = formatDoubleToDecimalTwoPlaces(this.percentChange1h),
        volume24h = formatDoubleToDecimalTwoPlaces(this.volume24h),
        percentChange24h = formatDoubleToDecimalTwoPlaces(this.percentChange24h),
        percentChange7d = formatDoubleToDecimalTwoPlaces(this.percentChange7d),
        marketCap = formatDoubleToDecimalTwoPlaces(this.marketCap)
    )
}

fun List<Cryptocurrency>.toListEntities(): List<CryptocurrencyEntity> {
    return this.map { it.toEntity() }
}

fun Cryptocurrency.toEntity(): CryptocurrencyEntity {
    return CryptocurrencyEntity(
        _id = this.id.toLong(),
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        price = this.quote.brl.price,
        percentChange1h = this.quote.brl.percentChange1h,
        percentChange24h = this.quote.brl.percentChange24h,
        percentChange7d = this.quote.brl.percentChange7d,
        maxSupply = this.maxSupply,
        volume24h = this.quote.brl.volume24h,
        circulatingSupply = this.circulatingSupply,
        marketCap = this.quote.brl.marketCap
    )
}

fun List<CryptocurrencyEntity>.toListCryptocurrency(): List<Cryptocurrency> {
    val coins = arrayListOf<Cryptocurrency>()
    for (obj in this) {
        val brl = Brl(
            price = obj.price,
            percentChange1h = obj.percentChange1h,
            percentChange24h = obj.percentChange24h,
            percentChange7d = obj.percentChange7d,
            volume24h = obj.volume24h,
            marketCap = obj.marketCap
        )
        val quote = Quote(
            brl = brl
        )
        val coin = Cryptocurrency(
            id = obj.id,
            symbol = obj.symbol,
            name = obj.name,
            maxSupply = obj.maxSupply,
            circulatingSupply = obj.circulatingSupply,
            quote = quote
        )
        coins.add(coin)
    }
    return coins
}