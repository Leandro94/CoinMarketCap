package com.leandro.coinmarketcap.domain.usecase

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.repository.RemoteRepository
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 11/11/2021.
 */

class GetRemoteListUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val saveLocalListUseCase: SaveLocalListUseCase
) : BaseUseCase.Empty<DataState<List<Cryptocurrency>>?> {
    override suspend fun invoke(): DataState<List<Cryptocurrency>>? {
        val result = repository.getCoins()
        if (result is DataState.OnSuccess) {
            result.data.let {
                saveLocalListUseCase.invoke(SaveLocalListUseCase.Params(listCryptocurrency = it))
            }
        }
        return result
    }
}