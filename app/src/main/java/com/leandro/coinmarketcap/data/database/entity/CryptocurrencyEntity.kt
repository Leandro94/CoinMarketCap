package com.leandro.coinmarketcap.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Entity(tableName = "cryptocurrency")
data class CryptocurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    val _id: Long,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "percentChange1h")
    val percentChange1h: Double,

    @ColumnInfo(name = "percentChange24h")
    val percentChange24h: Double,

    @ColumnInfo(name = "percentChange7d")
    val percentChange7d: Double,

    @ColumnInfo(name = "maxSupply")
    val maxSupply: Double?,

    @ColumnInfo(name = "volume24h")
    val volume24h: Double,

    @ColumnInfo(name = "circulatingSupply")
    val circulatingSupply: String,

    @ColumnInfo(name = "marketCap")
    val marketCap: Double
)