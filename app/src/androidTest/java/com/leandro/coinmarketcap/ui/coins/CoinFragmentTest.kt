package com.leandro.coinmarketcap.ui.coins

import android.content.Context
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.domain.model.Quote
import com.leandro.coinmarketcap.launchFragmentInHiltContainer
import com.leandro.coinmarketcap.utils.IMAGE_URL
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Leandro.Reis on 16/11/2021.
 */
@HiltAndroidTest
@SmallTest
class CoinFragmentTest {
    private lateinit var testContext: Context

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var fragment: CoinsFragment

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragment()
        initAdapter()
    }

    @Test
    fun recyclerViewMustBeInitializedAndShowList() {
        Espresso.onView(withId(R.id.rcv_coins))
            .perform()
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun recyclerViewMustBeInitializedAndPossibleScroll() {
        Espresso.onView(withId(R.id.rcv_coins))
            .perform(
                RecyclerViewActions.scrollToPosition<CoinsAdapter.CoinViewHolder>(
                    2
                )
            )
    }

    @Test
    fun itemActionSearchMustBeInitializedAndShow() {
        Espresso.onView(withId(R.id.action_search))
            .perform()
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun itemActionSearchClick() {
        Espresso.onView(withId(R.id.action_search))
            .perform(click())
    }

    private fun initAdapter() {
        fragment.coinAdapter
            .submitList(getListCoins())
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<CoinsFragment> {
            fragment = this as CoinsFragment
            testContext = this.requireContext()
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.CoinsFragment)
            this.viewLifecycleOwnerLiveData.observeForever { lifeCycle ->
                if (lifeCycle != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }

    private fun getListCoins(): MutableList<Coin> {
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
        list.add(coin)
        list.add(coin)
        list.add(coin)
        list.add(coin)
        return list
    }
}