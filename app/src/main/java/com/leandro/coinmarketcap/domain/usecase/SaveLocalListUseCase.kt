package com.leandro.coinmarketcap.domain.usecase

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.repository.LocalRepository
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class SaveLocalListUseCase @Inject constructor(
    private val repository: LocalRepository
) :
    BaseUseCase.Params<DataState<List<Long>?>, SaveLocalListUseCase.Params> {
    override suspend fun invoke(params: Params): DataState<List<Long>?> {
        return try {
            DataState.OnSuccess(params.listCryptocurrency?.let { repository.insertAll(it) })
        } catch (e: Exception) {
            DataState.OnException(e)
        }
    }

    data class Params(
        val listCryptocurrency: List<Cryptocurrency>?
    )
}