package com.leandro.coinmarketcap.domain.usecase

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.api.parseResponse
import com.leandro.coinmarketcap.data.model.Data
import com.leandro.coinmarketcap.data.repository.RemoteRepository
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class GetRemoteListCoins @Inject constructor(
    private val repository: RemoteRepository
) : BaseUseCase.Empty<DataState<Data>?> {
    override suspend fun invoke(): DataState<Data>? {
        return when (val response = repository.getCoins().parseResponse()) {
            is DataState.OnSuccess -> response.data?.let {
                DataState.OnSuccess(it)
            }

            is DataState.OnError -> DataState.OnError(response.errorBody, response.code)

            is DataState.OnException -> DataState.OnException(response.e)
        }
    }
}