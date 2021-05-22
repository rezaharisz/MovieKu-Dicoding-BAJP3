@file:Suppress("DEPRECATION")

package com.rezaharis.movieku.viewmodel.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoritesMovieViewModelTest {

    private lateinit var favoriteMovieViewModel: FavoritesMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieKuRepository: MovieKuRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntities>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntities>

    @Before
    fun setUp(){
        favoriteMovieViewModel = FavoritesMovieViewModel(movieKuRepository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(10)
        val dataMovie = MutableLiveData<PagedList<MovieEntities>>()
        dataMovie.value = dummyMovies

        `when`(movieKuRepository.getFavoriteMovies()).thenReturn(dataMovie)
        val movieEntities = favoriteMovieViewModel.getFavoriteMovies().value
        verify(movieKuRepository).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        favoriteMovieViewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}