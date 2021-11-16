package com.leandro.coinmarketcap.domain.usecase

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.repository.LocalRepository
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class GetLocalWalletListUseCase @Inject constructor(
    private val repository: LocalRepository
) : BaseUseCase.Empty<DataState<List<Cryptocurrency>?>?> {
    override suspend fun invoke(): DataState<List<Cryptocurrency>?>? {
        return try {
            DataState.OnSuccess(repository.getAll())
        } catch (e: Exception) {
            DataState.OnException(e)
        }
    }
}