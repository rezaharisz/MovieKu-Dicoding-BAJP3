package com.rezaharis.movieku.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.rezaharis.movieku.R
import com.rezaharis.movieku.utils.DataDummy
import com.rezaharis.movieku.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityTest{
    private val movie = DataDummy.getMoviesList()
    private val tvShows = DataDummy.getTvShowsList()

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testMovies(){
        onView(withId(R.id.navigation_movies))
            .perform(ViewActions.click())
        onView(withId(R.id.cr_rview))
            .check(ViewAssertions.matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(movie.size-1))
    }

    @Test
    fun testClickDetailMovies(){
        onView(withId(R.id.navigation_movies))
            .perform(ViewActions.click())
        onView(withId(R.id.cr_rview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_movie))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_release))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_rate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_votecount))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_des))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun tesTAddMovieFavorites(){
        onView(withId(R.id.navigation_movies))
            .perform(ViewActions.click())
        onView(withId(R.id.cr_rview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.set_favorite))
            .perform(ViewActions.click())
    }

    @Test
    fun testTvShows(){
        onView(withId(R.id.navigation_tvshows))
            .perform(ViewActions.click())
        onView(withId(R.id.cr_tvshows))
            .check(ViewAssertions.matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(tvShows.size-1))
    }

    @Test
    fun testClickDetailTvShows(){
        onView(withId(R.id.navigation_tvshows))
            .perform(ViewActions.click())
        onView(withId(R.id.cr_tvshows))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_tvshows))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvshows_release))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvshows_rate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvshows_votecount))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_des_tvshows))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testAddTvShowsFavorite(){
        onView(withId(R.id.navigation_tvshows))
            .perform(ViewActions.click())
        onView(withId(R.id.cr_tvshows))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.set_favorite))
            .perform(ViewActions.click())
    }

    @Test
    fun testFavoriteMovies(){
        onView(withId(R.id.navigation_favorite))
            .perform(ViewActions.click())
        onView(withId(R.id.rv_favorites_movies))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(movie.size-1))
    }

    @Test
    fun testDetailFavoriteMovies(){
        onView(withId(R.id.navigation_favorite))
            .perform(ViewActions.click())
        onView(withId(R.id.rv_favorites_movies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
    }

    @Test
    fun testFavoriteTvShows(){
        onView(withId(R.id.navigation_favorite))
            .perform(ViewActions.click())
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
        onView(withId(R.id.rv_favorites_tvshows))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(tvShows.size - 1))
    }

    @Test
    fun testDetailFavoriteTvShows(){
        onView(withId(R.id.navigation_favorite))
            .perform(ViewActions.click())
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
        onView(withId(R.id.rv_favorites_tvshows))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
    }
}