package com.leandro.coinmarketcap.data.api

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
sealed class DataState<out R> {
    data class OnSuccess<out T>(val data: T) : DataState<T>()
    data class OnError(val errorBody: ResponseBody?, var code: Int) : DataState<Nothing>()
    data class OnException(val e: Exception) : DataState<Nothing>()
}

fun <T : Any> Response<T?>?.parseResponse(): DataState<T?> {
    return try {
        when (this) {
            null -> throw NullPointerException("Response is null!")
            else -> when (isSuccessful) {
                true -> DataState.OnSuccess(body())
                else -> DataState.OnError(errorBody(), code())
            }
        }
    } catch (e: Exception) {
        DataState.OnException(e)
    }
}
