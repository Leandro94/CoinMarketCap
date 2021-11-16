package com.leandro.coinmarketcap.di

import android.content.Context
import androidx.room.Room
import com.leandro.coinmarketcap.data.database.AppDatabase
import com.leandro.coinmarketcap.data.database.CryptocurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideCoinDao(
        db: AppDatabase
    ): CryptocurrencyDao {
        return db.coinDao()
    }
}