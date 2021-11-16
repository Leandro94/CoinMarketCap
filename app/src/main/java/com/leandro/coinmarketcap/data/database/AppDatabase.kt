package com.leandro.coinmarketcap.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leandro.coinmarketcap.data.database.entity.CryptocurrencyEntity

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Database(entities = [CryptocurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CryptocurrencyDao

    companion object {
        const val DATABASE_NAME: String = "cryptocurrency.db"
    }
}