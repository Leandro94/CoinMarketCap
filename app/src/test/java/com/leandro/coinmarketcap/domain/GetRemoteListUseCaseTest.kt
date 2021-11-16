package com.leandro.coinmarketcap.domain

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.repository.RemoteRepository
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.domain.model.Data
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.domain.usecase.GetRemoteListUseCase
import com.leandro.coinmarketcap.domain.usecase.SaveLocalListUseCase
import com.leandro.coinmarketcap.utils.IMAGE_URL
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
class GetRemoteListUseCaseTest {
    private lateinit var useCase: GetRemoteListUseCase

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var repository: RemoteRepository

    @MockK
    private lateinit var saveLocalListUseCase: SaveLocalListUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        useCase = GetRemoteListUseCase(repository, saveLocalListUseCase)
    }

    @Test
    fun `useCase should call the repository and get a successful response and also succeed in the save useCase`() =
        runBlockingTest {
            coEvery {
                repository.getCoins()
            } returns DataState.OnSuccess(getListCoins())
            coEvery {
                saveLocalListUseCase.invoke(SaveLocalListUseCase.Params(getListCoins()))
            } returns DataState.OnSuccess(getListLong())
            val result = useCase.invoke()
            assert(result is DataState.OnSuccess)
        }

    @Test
    fun `if useCase succeeds in the API call it should return success even if the insert returns an exception`() =
        runBlockingTest {
            coEvery {
                repository.getCoins()
            } returns DataState.OnSuccess(getListCoins())
            coEvery {
                saveLocalListUseCase.invoke(SaveLocalListUseCase.Params(getListCoins()))
            } returns DataState.OnException(Exception())
            val result = useCase.invoke()
            assert(result is DataState.OnSuccess)
        }

    @Test
    fun `useCase should call the repository and get a error answer`() = runBlockingTest {
        coEvery {
            repository.getCoins()
        } returns DataState.OnError(getResponseError().errorBody(), getResponseError().code())
        val result = useCase.invoke()
        assert(result is DataState.OnError)
    }

    @Test
    fun `useCase should call the repository and get a exception answer`() = runBlockingTest {
        coEvery {
            repository.getCoins()
        } returns DataState.OnException(Exception())
        val result = useCase.invoke()
        assert(result is DataState.OnException)
    }

    private fun getResponseError(): Response<Data> {
        return Response.error(401, "".toResponseBody("application/json".toMediaType()))
    }

    private fun getListLong(): List<Long> {
        val list: ArrayList<Long> = arrayListOf()
        list.add(1)
        return list
    }

    private fun getListCoins(): List<Coin> {
        val brl = Brl(
            349287.4418665296,
            -0.23579847,
            187062823611.19772,
            0.26901555,
            2.69530735,
            6591595423556.306,
            7136608293421.985
        )
        val quote = Quote(brl)
        val coin = Coin("1", "Bitcoin", "BTC", 21000000.00, "18871550", quote,
            IMAGE_URL + 1 + ".png")
        val list: ArrayList<Coin> = arrayListOf()
        list.add(coin)
        return list
    }
}