package com.leandro.coinmarketcap.ui.cryptocurrencys

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import com.leandro.coinmarketcap.domain.model.Data
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.domain.usecase.GetLocalListUseCase
import com.leandro.coinmarketcap.domain.usecase.GetRemoteListUseCase
import io.mockk.MockKAnnotations.init
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

/**
 * Created by Leandro.Reis on 12/11/2021.
 */
@ExperimentalCoroutinesApi
class CryptocurrencyViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CryptocurrencyViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var getRemoteListUseCase: GetRemoteListUseCase

    @MockK
    lateinit var getLocalListUseCase: GetLocalListUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        init(this)
        viewModel = CryptocurrencyViewModel(getRemoteListUseCase, getLocalListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model should call the getRemoteListUseCase and get a successful response`() =
        runBlockingTest {
            coEvery {
                getRemoteListUseCase.invoke()
            } returns DataState.OnSuccess(getListCryptocurrency())

            viewModel.getRemoteList()

            viewModel.getRemoteListEvent.observeForever { result ->
                assertEquals(DataState.OnSuccess(getListCryptocurrency()), result)
            }
        }

    @Test
    fun `view model should call the getRemoteListUseCase and get a error response`() =
        runBlockingTest {
            coEvery {
                getRemoteListUseCase.invoke()
            } returns DataState.OnError(getResponseError().errorBody(), getResponseError().code())

            viewModel.getRemoteList()

            viewModel.getRemoteListEvent.observeForever { result ->
                assert(result is DataState.OnError)
            }
        }

    @Test
    fun `view model should call the getRemoteListUseCase and get a exception response`() =
        runBlockingTest {
            coEvery {
                getRemoteListUseCase.invoke()
            } returns DataState.OnException(Exception())

            viewModel.getRemoteList()

            viewModel.getRemoteListEvent.observeForever { result ->
                assert(result is DataState.OnException)
            }
        }

    @Test
    fun `view model should call the getLocalListUseCase and get a successful response`() =
        runBlockingTest {
            coEvery {
                getLocalListUseCase.invoke()
            } returns DataState.OnSuccess(getListCryptocurrency())

            viewModel.getLocalList()

            viewModel.getLocalListEvent.observeForever { result ->
                assertEquals(DataState.OnSuccess(getListCryptocurrency()), result)
            }
        }

    @Test
    fun `view model should call the getLocalListUseCase and get a exception response`() =
        runBlockingTest {
            coEvery {
                getLocalListUseCase.invoke()
            } returns DataState.OnException(Exception())

            viewModel.getLocalList()

            viewModel.getLocalListEvent.observeForever { result ->
                assert(result is DataState.OnException)
            }
        }

    private fun getResponseError(): Response<Data> {
        return Response.error(401, "".toResponseBody("application/json".toMediaType()))
    }

    private fun getListCryptocurrency(): List<Cryptocurrency> {
        val brl = Brl(
            349287.4418665296,
            -0.23579847,
            "187062823611.19772",
            0.26901555,
            2.69530735,
            "6591595423556.306"
        )
        val quote = Quote(brl)
        val cryptocurrency = Cryptocurrency("1", "Bitcoin", "BTC", "21000000", "18871550", quote)
        val list: ArrayList<Cryptocurrency> = arrayListOf()
        list.add(cryptocurrency)
        return list
    }
}

