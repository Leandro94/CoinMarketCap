package com.leandro.coinmarketcap.domain

import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.repository.LocalRepository
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.domain.usecase.GetLocalListUseCase
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
 * Created by Leandro.Reis on 15/11/2021.
 */
@ExperimentalCoroutinesApi
class GetLocalListUseCaseTest {
    private lateinit var useCase: GetLocalListUseCase

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var repository: LocalRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        useCase = GetLocalListUseCase(repository)
    }

    @Test
    fun `useCase should call the repository and get a successful response when returning a list of items`() = runBlockingTest {
        coEvery {
            repository.getAll()
        } returns getListCryptocurrency()
        val result = useCase.invoke()
        assert(result is DataState.OnSuccess)
    }

    @Test
    fun `useCase should call the repository and get a successful response when returning an empty list`() = runBlockingTest {
        coEvery {
            repository.getAll()
        } returns getListCryptocurrencyEmpty()
        val result = useCase.invoke()
        assert(result is DataState.OnSuccess)
    }

    @Test
    fun `useCase should call the repository and get a successful response when returning a null list`() = runBlockingTest {
        coEvery {
            repository.getAll()
        } returns null
        val result = useCase.invoke()
        assert(result is DataState.OnSuccess)
    }

    @Test
    fun `useCase should call the repository and get a exception answer`() = runBlockingTest {
        coEvery {
            repository.getAll()
        } throws Exception()
        val result = useCase.invoke()
        assert(result is DataState.OnException)
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

    private fun getListCryptocurrencyEmpty(): List<Cryptocurrency> {
        return arrayListOf()
    }
}