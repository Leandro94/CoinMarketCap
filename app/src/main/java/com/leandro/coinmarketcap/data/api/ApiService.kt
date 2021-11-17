package com.leandro.coinmarketcap.data.api

import com.leandro.coinmarketcap.data.model.DataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
interface ApiService {
    @GET("/v1/cryptocurrency/listings/latest?limit=30&convert=BRL")
    suspend fun getCoins(): Response<DataResponse?>?
}