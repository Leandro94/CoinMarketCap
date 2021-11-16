package com.leandro.coinmarketcap.di

import com.leandro.coinmarketcap.BuildConfig
import com.leandro.coinmarketcap.data.api.ApiService
import com.leandro.coinmarketcap.utils.BASE_URL
import com.leandro.coinmarketcap.utils.KEY
import com.leandro.coinmarketcap.utils.KEY_VALUE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideLevel(
    ): HttpLoggingInterceptor.Level {
        return HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideHttpLogging(
        level: HttpLoggingInterceptor.Level
    ): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = level
        return logging
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header(KEY, KEY_VALUE)
                chain.proceed(builder.build())
            }
        if (BuildConfig.DEBUG) {
            client.addInterceptor(logging)
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
    }

    @Singleton
    @Provides
    fun provideCoinsService(
        retrofit: Retrofit.Builder,
    ): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
}