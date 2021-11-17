package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.api.ApiService
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.api.parseResponse
import com.leandro.coinmarketcap.domain.model.Coin
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class RemoteRepository @Inject constructor(
    private val api: ApiService
) : Repository.RemoteData {
    override suspend fun getCoins(): DataState<List<Coin>>? {
        return try {
            when (val response = api.getCoins().parseResponse()) {
                is DataState.OnSuccess -> response.data?.data?.let { it ->
                    DataState.OnSuccess(it.responseToListCoin())
                }
                is DataState.OnError -> DataState.OnError(response.errorBody, response.code)
                is DataState.OnException -> DataState.OnException(response.e)
            }
        } catch (e: Exception) {
            DataState.OnException(e)
        }
    }
}