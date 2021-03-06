package com.leandro.coinmarketcap.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leandro.coinmarketcap.data.database.entity.CoinEntity

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Database(entities = [CoinEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        const val DATABASE_NAME: String = "coins.db"
    }
}