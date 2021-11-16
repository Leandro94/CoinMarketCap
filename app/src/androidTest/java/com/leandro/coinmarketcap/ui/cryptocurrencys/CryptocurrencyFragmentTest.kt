package com.leandro.coinmarketcap.ui.cryptocurrencys

import android.content.Context
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.domain.model.Brl
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
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
class CryptocurrencyFragmentTest {
    private lateinit var testContext: Context

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var fragment: CryptocurrencyFragment

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragment()
        initAdapter()
    }

    @Test
    fun recyclerViewMustBeInitializedAndShowList() {
        Espresso.onView(withId(R.id.rcv_crypto_fragment))
            .perform()
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun recyclerViewMustBeInitializedAndPossibleScroll() {
        Espresso.onView(withId(R.id.rcv_crypto_fragment))
            .perform(
                RecyclerViewActions.scrollToPosition<LocalAdapter.LocalViewHolder>(
                    2
                )
            )
    }

    @Test
    fun recyclerViewMustBeInitializedAndPossibleSeeDetailScreenOnClickItem() {
        Espresso.onView(withId(R.id.rcv_crypto_fragment))
            .perform(actionOnItemAtPosition<LocalAdapter.LocalViewHolder>(0, ViewActions.click()))
        Espresso.onView(withId(R.id.cv_item))
            .perform()
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun initAdapter() {
        fragment.localAdapter.clearList()
        fragment.localAdapter
            .submitList(getListCryptocurrency())
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<CryptocurrencyFragment> {
            fragment = this as CryptocurrencyFragment
            testContext = this.requireContext()
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.CryptocurrencyFragment)
            this.viewLifecycleOwnerLiveData.observeForever { lifeCycle ->
                if (lifeCycle != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }

    private fun getListCryptocurrency(): MutableList<Cryptocurrency> {
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
        val cryptocurrency = Cryptocurrency(
            "1",
            "Bitcoin",
            "BTC",
            21000000.00,
            "18871550",
            quote,
            IMAGE_URL + 1 + ".png"
        )
        val list: ArrayList<Cryptocurrency> = arrayListOf()
        list.add(cryptocurrency)
        list.add(cryptocurrency)
        list.add(cryptocurrency)
        list.add(cryptocurrency)
        list.add(cryptocurrency)
        return list
    }
}