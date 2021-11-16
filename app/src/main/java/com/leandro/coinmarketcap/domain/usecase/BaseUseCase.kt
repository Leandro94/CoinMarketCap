package com.leandro.coinmarketcap.domain.usecase

import com.leandro.coinmarketcap.domain.model.Data
import retrofit2.Response

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
interface BaseUseCase {
    interface Params<T, Params> {
        suspend operator fun invoke(params: Params): T
    }

    interface Empty<T> {
        suspend operator fun invoke(): T
    }

    interface UseCase {
        suspend operator fun invoke(): Response<Data?>?
    }
}