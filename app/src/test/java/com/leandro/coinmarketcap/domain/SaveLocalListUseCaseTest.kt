package com.leandro.coinmarketcap.domain

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.repository.LocalRepository
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.domain.model.Quote
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
import org.junit.Before
import org.junit.Test

/**
 * Created by Leandro.Reis on 13/11/2021.
 */
@ExperimentalCoroutinesApi
class SaveLocalListUseCaseTest {
    private lateinit var useCase: SaveLocalListUseCase

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var repository: LocalRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        useCase = SaveLocalListUseCase(repository)
    }

    @Test
    fun `useCase should call the repository and get a successful answer`() = runBlockingTest {
        coEvery {
            repository.insertAll(getListCoins())
        } returns getListLong()
        val result = useCase.invoke(SaveLocalListUseCase.Params(getListCoins()))
        assert(result is DataState.OnSuccess)
    }

    @Test
    fun `useCase should call the repository and get a exception answer`() =
        runBlockingTest {
            coEvery {
                repository.insertAll(getListCoins())
            } returns getListLong()

            val result = useCase.invoke(SaveLocalListUseCase.Params(getListCoinsIsEmpty()))
            assert(result is DataState.OnException)
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
        val coin = Coin(
            "1",
            "Bitcoin",
            "BTC",
            21000000.00,
            "18871550",
            quote,
            IMAGE_URL + 1 + ".png"
        )
        val list: ArrayList<Coin> = arrayListOf()
        list.add(coin)
        return list
    }

    private fun getListCoinsIsEmpty(): List<Coin> {
        return arrayListOf()
    }

    private fun getListLong(): List<Long> {
        val list: ArrayList<Long> = arrayListOf()
        list.add(1)
        return list
    }
}