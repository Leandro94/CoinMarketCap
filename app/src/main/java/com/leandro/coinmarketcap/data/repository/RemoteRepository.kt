package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.api.ApiService
import com.leandro.coinmarketcap.data.model.Data
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class RemoteRepository @Inject constructor(
    private val api: ApiService
) : Repository.RemoteData {
    override suspend fun getCoins(): Response<Data?>? {
        return api.getCoins()
    }
}