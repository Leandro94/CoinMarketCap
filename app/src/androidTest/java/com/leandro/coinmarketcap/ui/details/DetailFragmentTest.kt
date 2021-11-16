package com.leandro.coinmarketcap.ui.details

import android.content.Context
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.SmallTest
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Leandro.Reis on 16/11/2021.
 */
@HiltAndroidTest
@SmallTest
class DetailFragmentTest {
    private lateinit var testContext: Context

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var fragment: DetailFragment

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        hiltRule.inject()
        launchFragment()
    }

    @ExperimentalCoroutinesApi
    private fun launchFragment() {
        launchFragmentInHiltContainer<DetailFragment> {
            fragment = this as DetailFragment
            testContext = this.requireContext()
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.DetailFragment)
            this.viewLifecycleOwnerLiveData.observeForever { lifeCycle ->
                if (lifeCycle != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }
}