package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.acoders.marvelfanbook.MainActivity
import com.acoders.marvelfanbook.R
import com.acoders.marvelfanbook.data.server.MockWebServerRule
import com.acoders.marvelfanbook.data.server.OkHttp3IdlingResource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SuperheroesFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var superHeroesRemoteDataSource: SuperHeroesRemoteDataSource

    @Before
    fun setUp() {

        hiltRule.inject()

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun check_superheroes_mock_server_is_working() = runTest {
        val wrapperScheme = superHeroesRemoteDataSource.superheroes()

        wrapperScheme.data.results.let {
            assertEquals(SuperheroDto::class.java, it[0].javaClass)
            assertEquals(1011334, it[0].id)
            assertEquals("3-D Man", it[0].name)
        }

    }

    @Test
    fun click_a_superhero_navigates_to_detail() = runTest {
        Espresso.onView(withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3, ViewActions.click()
                )
            )

        Espresso.onView(withId(R.id.tvHeroName)).check(ViewAssertions.matches(withText("Abyss")))

    }


}