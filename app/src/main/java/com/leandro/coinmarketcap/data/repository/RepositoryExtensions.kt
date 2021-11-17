package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.database.entity.CoinEntity
import com.leandro.coinmarketcap.data.model.BrlResponse
import com.leandro.coinmarketcap.data.model.CoinResponse
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.utils.IMAGE_EXTENSION
import com.leandro.coinmarketcap.utils.IMAGE_URL
import com.leandro.coinmarketcap.utils.formatDoubleToDecimalTwoPlaces

/**
 * Created by Leandro.Reis on 13/11/2021.
 */
fun List<CoinResponse>.responseToListCoin(): List<Coin> {
    return this.map { it.toCoin() }
}

fun CoinResponse.toCoin(): Coin {
    val quote = Quote(
        brl = this.quote?.blr?.toBrl() ?: Brl(
            0.00,
            0.00,
            0.00,
            0.00,
            0.00,
            0.00,
            0.00
        )
    )
    return Coin(
        id = id ?: "",
        symbol = symbol ?: "",
        name = name ?: "",
        maxSupply = maxSupply?.let { formatDoubleToDecimalTwoPlaces(it) } ?: 0.00,
        circulatingSupply = circulatingSupply ?: "",
        quote = quote,
        imgUrl = IMAGE_URL + id + IMAGE_EXTENSION
    )
}

fun BrlResponse.toBrl(): Brl {
    return Brl(
        price = formatDoubleToDecimalTwoPlaces(this.price),
        percentChange1h = formatDoubleToDecimalTwoPlaces(this.percentChange1h),
        volume24h = formatDoubleToDecimalTwoPlaces(this.volume24h),
        percentChange24h = formatDoubleToDecimalTwoPlaces(this.percentChange24h),
        percentChange7d = formatDoubleToDecimalTwoPlaces(this.percentChange7d),
        marketCap = formatDoubleToDecimalTwoPlaces(this.marketCap),
        dilutedMarketCap = formatDoubleToDecimalTwoPlaces(this.dilutedMarketCap)
    )
}

fun List<Coin>.toListEntities(): List<CoinEntity> {
    return this.map { it.toEntity() }
}

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
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
        marketCap = this.quote.brl.marketCap,
        imgUrl = this.imgUrl ?: "",
        dilutedMarketCap = this.quote.brl.dilutedMarketCap
    )
}

fun List<CoinEntity>.toListCoin(): List<Coin> {
    val coins = arrayListOf<Coin>()
    for (obj in this) {
        val brl = Brl(
            price = obj.price,
            percentChange1h = obj.percentChange1h,
            percentChange24h = obj.percentChange24h,
            percentChange7d = obj.percentChange7d,
            volume24h = obj.volume24h,
            marketCap = obj.marketCap,
            dilutedMarketCap = obj.dilutedMarketCap
        )
        val quote = Quote(
            brl = brl
        )
        val coin = Coin(
            id = obj.id,
            symbol = obj.symbol,
            name = obj.name,
            maxSupply = obj.maxSupply ?: 0.00,
            circulatingSupply = obj.circulatingSupply,
            quote = quote,
            imgUrl = IMAGE_URL + obj.id + IMAGE_EXTENSION
        )
        coins.add(coin)
    }
    return coins
}