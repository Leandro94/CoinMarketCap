package com.leandro.coinmarketcap.di

import com.leandro.coinmarketcap.data.api.ApiService
import com.leandro.coinmarketcap.data.database.CoinDao
import com.leandro.coinmarketcap.data.repository.LocalRepository
import com.leandro.coinmarketcap.data.repository.RemoteRepository
import com.leandro.coinmarketcap.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: ApiService,
    ): Repository.RemoteData {
        return RemoteRepository(api)
    }


    @Singleton
    @Provides
    fun provideLocalRepository(dao: CoinDao): Repository.LocalData {
        return LocalRepository(dao)
    }
}