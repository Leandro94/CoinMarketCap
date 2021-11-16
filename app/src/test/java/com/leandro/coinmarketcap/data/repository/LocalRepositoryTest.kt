package com.leandro.coinmarketcap.data.repository

import androidx.room.withTransaction
import com.leandro.coinmarketcap.data.database.AppDatabase
import com.leandro.coinmarketcap.data.database.entity.CoinEntity
import com.leandro.coinmarketcap.utils.IMAGE_URL
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

/**
 * Created by Leandro.Reis on 16/11/2021.
 */
@ExperimentalCoroutinesApi
class LocalRepositoryTest {
    @MockK
    private lateinit var appDatabase: AppDatabase

    private lateinit var repository: Repository.LocalData

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        initMocks()
        repository = LocalRepository(appDatabase.coinDao())
    }

    private fun initMocks() {
        MockKAnnotations.init(this)
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )
        val transactionLambda = slot<suspend () -> Unit>()
        coEvery { appDatabase.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
        coEvery {
            appDatabase.coinDao().insertAll(getListEntity())
        } returns getListLong()
        coEvery {
            appDatabase.coinDao().getAll()
        } returns getListEntity()
    }

    @Test
    fun `repository should fetch all database data and return success with a list equal to the one entered`() =
        runBlockingTest {
            val result = repository.getAll()

            assertEquals(getListEntity().toListCoin(), result)
        }

    @Test
    fun `after an insertion in the database the repository must return a valid id list`() =
        runBlockingTest {
            val result = repository.insertAll(getListEntity().toListCoin())

            assertEquals(getListLong(), result)
        }

    private fun getListLong(): List<Long> {
        val list: ArrayList<Long> = arrayListOf()
        list.add(1)
        return list
    }

    private fun getListEntity(): List<CoinEntity> {
        val entity = CoinEntity(
            _id = 1,
            id = "1",
            symbol = "BTC",
            name = "Bitcoin",
            price = 50.00,
            percentChange1h = 0.5,
            percentChange24h = 0.4,
            percentChange7d = 1.70,
            maxSupply = 55454454.55,
            volume24h = 1222.45,
            circulatingSupply = "4555.54",
            marketCap = 5455454.44,
            imgUrl = IMAGE_URL + 1 + ".png",
            dilutedMarketCap = 44745445.44
        )
        val list: ArrayList<CoinEntity> = arrayListOf()
        list.add(entity)
        return list
    }
}