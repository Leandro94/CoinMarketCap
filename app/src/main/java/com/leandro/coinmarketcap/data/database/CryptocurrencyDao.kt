package com.leandro.coinmarketcap.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leandro.coinmarketcap.data.database.entity.CryptocurrencyEntity

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Dao
interface CryptocurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptocurrencies: List<CryptocurrencyEntity>): List<Long>

    @Query("SELECT * FROM cryptocurrency")
    suspend fun getAll(): List<CryptocurrencyEntity>?

    @Query("SELECT * FROM cryptocurrency WHERE _id = :idCoin")
    suspend fun getOne(idCoin: Int): CryptocurrencyEntity?

    @Query("DELETE FROM cryptocurrency")
    suspend fun deleteAll()
}