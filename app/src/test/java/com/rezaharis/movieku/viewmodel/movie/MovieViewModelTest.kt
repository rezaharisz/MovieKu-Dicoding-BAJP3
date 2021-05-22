@file:Suppress("DEPRECATION")

package com.rezaharis.movieku.viewmodel.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.vo.Resource
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
class MovieViewModelTest{

    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieKuRepository: MovieKuRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntities>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntities>

    @Before
    fun setUp(){
        movieViewModel = MovieViewModel(movieKuRepository)
    }

    @Test
    fun getMovies(){
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(10)
        val dataMovie = MutableLiveData<Resource<PagedList<MovieEntities>>>()
        dataMovie.value = dummyMovies

        `when`(movieKuRepository.getMovies()).thenReturn(dataMovie)
        val movieEntities = movieViewModel.getMovies().value?.data
        verify(movieKuRepository).getMovies()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        movieViewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}