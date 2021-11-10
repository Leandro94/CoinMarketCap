package com.leandro.coinmarketcap.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.data.repository.LocalRepository
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class SaveListCoins @Inject constructor(private val repository: LocalRepository) :
    BaseUseCase.Params<DataState<List<Coin>>, SaveListCoins.Params> {
    override suspend fun invoke(params: Params): DataState<List<Coin>> {
        TODO("Not yet implemented")
    }

    data class Params(
        val context: Context,
        val bitmap: Bitmap?
    )
}