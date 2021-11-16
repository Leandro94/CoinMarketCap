package com.leandro.coinmarketcap.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.leandro.coinmarketcap.data.database.entity.CryptocurrencyEntity
import com.leandro.coinmarketcap.data.repository.toListEntities
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import com.leandro.coinmarketcap.domain.model.Quote
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
class CoinDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(AppDatabase.DATABASE_NAME)
    lateinit var database: AppDatabase
    private lateinit var dao: CoinDao

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

    private suspend fun insertOnDataBase() {
        dao.insertAll(getListCryptocurrency())
    }

    private fun getListCryptocurrency(): List<CryptocurrencyEntity> {
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
        return list.toListEntities()
    }
}