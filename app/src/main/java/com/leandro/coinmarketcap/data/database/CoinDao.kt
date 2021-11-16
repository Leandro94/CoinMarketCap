package com.leandro.coinmarketcap.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leandro.coinmarketcap.data.database.entity.CoinEntity

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<CoinEntity>): List<Long>

    @Query("SELECT * FROM coin")
    suspend fun getAll(): List<CoinEntity>?

    @Query("SELECT * FROM coin WHERE _id = :idCoin")
    suspend fun getOne(idCoin: Int): CoinEntity?

    @Query("DELETE FROM coin")
    suspend fun deleteAll()
}