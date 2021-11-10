package com.leandro.coinmarketcap.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    val _id: Long,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    val symbol: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "percent_change_1h")
    val percent_change_1h: Double,

    @ColumnInfo(name = "percent_change_24h")
    val percent_change_24h: Double,

    @ColumnInfo(name = "percent_change_7d")
    val percent_change_7d: Double,

    @ColumnInfo(name = "max_supply")
    @SerializedName("max_supply")
    val max_supply: String,

    @ColumnInfo(name = "volume_24h")
    val volume_24h: String,

    @ColumnInfo(name = "circulating_supply")
    @SerializedName("circulating_supply")
    val circulating_supply: String,

    @ColumnInfo(name = "market_cap")
    @SerializedName("market_cap")
    val market_cap: String,
)