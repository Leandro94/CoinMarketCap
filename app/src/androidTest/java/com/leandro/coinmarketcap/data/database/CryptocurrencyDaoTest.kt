package com.leandro.coinmarketcap.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.leandro.coinmarketcap.data.database.entity.CryptocurrencyEntity
import com.leandro.coinmarketcap.data.repository.toListEntities
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.launchFragmentInHiltContainer
import com.leandro.coinmarketcap.ui.cryptocurrencys.CryptocurrencyFragment
import com.leandro.coinmarketcap.utils.IMAGE_URL
import com.leandro.coinmarketcap.utils.formatDoubleToDecimalTwoPlaces
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro.Reis on 13/11/2021.
 */
@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CryptocurrencyDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(AppDatabase.DATABASE_NAME)
    lateinit var database: AppDatabase
    private lateinit var dao: CryptocurrencyDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.coinDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertListOnDataBase() = runBlockingTest {
        insertOnDataBase()
    }

    @Test
    fun getListOnDataBase() = runBlockingTest {
        insertOnDataBase()
        val list = dao.getAll()
        assert(list == getListCryptocurrency())
    }

    @Test
    fun insertAndDeleteOnDataBase() = runBlockingTest {
        insertOnDataBase()
        dao.deleteAll()
        val list = dao.getAll()
        assert(list!!.isEmpty())
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<CryptocurrencyFragment> { }
    }

    private suspend fun insertOnDataBase() {
        dao.insertAll(getListCryptocurrency())
    }

    private fun getListCryptocurrency(): List<CryptocurrencyEntity> {
        val brl = Brl(
            formatDoubleToDecimalTwoPlaces(349287.4418665296),
            formatDoubleToDecimalTwoPlaces(-0.23579847),
            formatDoubleToDecimalTwoPlaces(187062823611.19772),
            formatDoubleToDecimalTwoPlaces(0.26901555),
            formatDoubleToDecimalTwoPlaces(2.69530735),
            formatDoubleToDecimalTwoPlaces(6591595423556.306),
            7136608293421.985
        )
        val quote = Quote(brl)
        val cryptocurrency = Cryptocurrency(
            "1",
            "Bitcoin",
            "BTC",
            21000000.0,
            "18871550",
            quote,
            IMAGE_URL + 1 + ".png"
        )
        val list: ArrayList<Cryptocurrency> = arrayListOf()
        list.add(cryptocurrency)
        return list.toListEntities()
    }
}