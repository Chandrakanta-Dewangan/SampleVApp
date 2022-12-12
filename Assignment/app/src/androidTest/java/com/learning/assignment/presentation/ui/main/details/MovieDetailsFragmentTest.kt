package com.learning.assignment.presentation.ui.main.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.learning.assignment.R
import com.learning.assignment.launchFragmentInHiltContainer
import com.learning.assignment.ui.main.details.MovieDetailsFragment
import com.learning.assignment.utils.FakeDataGenerator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.hamcrest.Matcher

@HiltAndroidTest
class MovieDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_title_and_releaseDate_in_detail_view() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<MovieDetailsFragment>(Bundle().apply {
            putSerializable("movies", FakeDataGenerator.getFakeMovie()[0])
        }) {
            Navigation.setViewNavController(requireView(), navController)
        }
        Thread.sleep(10000L)

        onView(withId(R.id.titleTv)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTv)).check(matches(withText(containsString("Batman"))))
        onView(withId(R.id.releaseTv)).check(matches(withText(containsString("2005"))))
    }

}