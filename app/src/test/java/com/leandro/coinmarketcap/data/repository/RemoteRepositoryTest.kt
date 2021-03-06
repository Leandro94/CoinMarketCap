package com.leandro.coinmarketcap.data.repository

import com.leandro.coinmarketcap.data.api.ApiService
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.api.parseResponse
import com.leandro.coinmarketcap.data.model.BrlResponse
import com.leandro.coinmarketcap.data.model.CoinResponse
import com.leandro.coinmarketcap.data.model.DataResponse
import com.leandro.coinmarketcap.data.model.QuoteResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by Leandro.Reis on 13/11/2021.
 */
@ExperimentalCoroutinesApi
class RemoteRepositoryTest {
    @MockK
    private lateinit var api: ApiService

    private lateinit var repository: Repository.RemoteData

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repository = RemoteRepository(api)
    }

    @Test
    fun `repository should call the service and get a successful response`() = runBlockingTest {
        coEvery {
            api.getCoins()
        } returns Response.success(getData())

        val result = repository.getCoins()

        assert(result is DataState.OnSuccess)
    }

    @Test
    fun `repository should call the service and get a error response`() = runBlockingTest {
        coEvery {
            api.getCoins()
        } returns getResponseError()

        val result = repository.getCoins()

        assert(result is DataState.OnError)
    }

    @Test
    fun `repository should call the service and get a exception response`() = runBlockingTest {
        coEvery {
            api.getCoins().parseResponse()
        } throws Exception()

        val result = repository.getCoins()

        assert(result is DataState.OnException)
    }

    @Test
    fun `repository should call the service and get a exception response `() = runBlockingTest {
        coEvery {
            api.getCoins()
        } throws Exception()

        val result = repository.getCoins()

        assert(result is DataState.OnException)
    }

    private fun getResponseError(): Response<DataResponse?>? {
        return Response.error(401, "".toResponseBody("application/json".toMediaType()))
    }

    private fun getData(): DataResponse {
        val brl = BrlResponse(
            349287.4418665296,
            -0.23579847,
            187062823611.19772,
            0.26901555,
            2.69530735,
            6591595423556.306,
            7136608293421.985
        )
        val quote = QuoteResponse(brl)
        val coin =
            CoinResponse("1", "Bitcoin", "BTC", 21000000.00, "18871550", quote)
        val list: ArrayList<CoinResponse> = arrayListOf()
        list.add(coin)
        return DataResponse(list)
    }
}