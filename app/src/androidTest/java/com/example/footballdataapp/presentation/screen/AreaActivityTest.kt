package com.example.footballdataapp.presentation.screen

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.gomoneyapp.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AreaActivityTest{

    @Test
    fun testAreaActivityScreen(){
        val activityScenario = ActivityScenario.launch(AreaActivity::class.java)
        onView(withId(R.id.area_progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.area_error_box)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.success_box)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
    @Test
    fun testCompetitionActivityScreen(){
        val activityScenario = ActivityScenario.launch(AreaLeagueActivity::class.java)
        onView(withId(R.id.league_error)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.league_recycler)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.league_progress)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}