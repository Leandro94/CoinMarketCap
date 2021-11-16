package com.leandro.coinmarketcap.di

import android.content.Context
import androidx.room.Room
import com.leandro.coinmarketcap.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

/**
 * Created by Leandro.Reis on 13/11/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModuleTest {
    @Provides
    @Named(AppDatabase.DATABASE_NAME)
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}