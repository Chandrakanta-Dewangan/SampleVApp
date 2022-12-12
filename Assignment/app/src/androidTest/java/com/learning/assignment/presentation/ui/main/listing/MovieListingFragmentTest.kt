package com.learning.assignment.presentation.ui.main.listing

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.learning.assignment.R
import com.learning.assignment.databinding.ListingFragmentBinding
import com.learning.assignment.launchFragmentInHiltContainer
import com.learning.assignment.ui.main.details.MovieDetailsFragment
import com.learning.assignment.ui.main.listing.MovieListingFragment
import com.learning.assignment.ui.main.listing.MovieListingFragmentDirections
import com.learning.assignment.ui.main.listing.MovieAdapter
import com.learning.assignment.utils.FakeDataGenerator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class MovieListingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `scroll_the_movie_list`() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<MovieListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(10000L)

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    10
                )
            )

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    0
                )
            )
    }

    @Test
    fun check_title_in_list_view(): Unit = runBlocking {
        val navController = Mockito.mock(NavController::class.java)

        lateinit var viewBindingAd: ListingFragmentBinding

        launchFragmentInHiltContainer<MovieListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewBindingAd = viewBinding
        }

        delay(10000L)

        val adapter = (viewBindingAd.list.adapter as MovieAdapter)
        if ((viewBindingAd.list.adapter as MovieAdapter).itemCount > 0) {
            var movie = adapter.getMovieAtPosition(0)
            movie.title = "Batman"

            Espresso.onView(withId(R.id.list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
                .check(ViewAssertions.matches(hasDescendant(withText(CoreMatchers.containsString("Batman")))))

        } else {
            Espresso.onView(withId(R.id.main)).check(ViewAssertions.matches(isDisplayed()))
        }
    }

    @Test
    fun `click_on_first_item`(): Unit = runBlocking {
        val navController = Mockito.mock(NavController::class.java)

        lateinit var viewBindingAd: ListingFragmentBinding

        launchFragmentInHiltContainer<MovieListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewBindingAd = viewBinding
        }

        delay(10000L)
        if ((viewBindingAd.list.adapter as MovieAdapter).itemCount > 0) {
            Espresso.onView(withId(R.id.list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        ClickOnButtonView()
                    )
                )

            val movie = (viewBindingAd.list.adapter as MovieAdapter).getMovieAtPosition(0)

            verify(navController).navigate(
                MovieListingFragmentDirections.actionMovieListingFragmentToMovieDetailsFragment(
                    movie
                ),
            )
        } else {
            Espresso.onView(withId(R.id.main)).check(ViewAssertions.matches(isDisplayed()))
        }
    }

    @Test
    fun `click_on_last_item`(): Unit = runBlocking {
        val navController = Mockito.mock(NavController::class.java)

        lateinit var viewBindingAd: ListingFragmentBinding

        launchFragmentInHiltContainer<MovieListingFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewBindingAd = viewBinding
        }

        delay(10000L)
        val adapter = (viewBindingAd.list.adapter as MovieAdapter)
        val lastItemIndex = adapter.itemCount - 1
        if (lastItemIndex > 0) {
            Espresso.onView(withId(R.id.list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        lastItemIndex,
                        ClickOnButtonView()
                    )
                )

            val movie = adapter.getMovieAtPosition(lastItemIndex)

            verify(navController).navigate(
                MovieListingFragmentDirections.actionMovieListingFragmentToMovieDetailsFragment(
                    movie
                ),
            )
        } else {
            Espresso.onView(withId(R.id.main)).check(ViewAssertions.matches(isDisplayed()))
        }
    }

    inner class ClickOnButtonView : ViewAction {
        private var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.cardView))
        }
    }
}