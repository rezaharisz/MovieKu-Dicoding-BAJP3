@file:Suppress("DEPRECATION")

package com.rezaharis.movieku.viewmodel.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.utils.DataDummy
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest{

    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private val dummyMovie = DataDummy.getMoviesList()[0]
    private val movieId = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieKuRepository: MovieKuRepository

    @Mock
    private lateinit var moviesObserver: Observer<MovieEntities>

    @Before
    fun setUp(){
        movieDetailViewModel = MovieDetailViewModel(movieKuRepository)
    }

    @Test
    fun getMovieId(){
        val dataMovie = MutableLiveData<MovieEntities>()
        dataMovie.value = dummyMovie

        `when`(movieId?.let { movieKuRepository.getMovieId(it) }).thenReturn(dataMovie)
        val moviesEntities = movieId?.let { movieDetailViewModel.getMovieId(it).value }
        assertNotNull(moviesEntities)
        assertEquals(dummyMovie.id, moviesEntities?.id)

        movieId?.let { movieDetailViewModel.getMovieId(it).observeForever(moviesObserver) }
        verify(moviesObserver).onChanged(dummyMovie)
    }

    @Test
    fun setFavoriteMovies(){
        val movies = MutableLiveData<MovieEntities>()
        movies.value = dummyMovie
        val state = !dummyMovie.setFavorite
        `when`(movieId?.let { movieKuRepository.getMovieId(it) }).thenReturn(movies)

        movieId?.let { movieDetailViewModel.getMovieId(it) }
        movieDetailViewModel.setFavoriteMovies()

        verify(movieKuRepository, times(1)).setFavoriteMovies(movies.value!!, state)
        verifyNoMoreInteractions(moviesObserver)
    }
}