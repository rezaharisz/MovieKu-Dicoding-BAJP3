@file:Suppress("DEPRECATION")

package com.rezaharis.movieku.viewmodel.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
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
class FavoritesTvShowsViewModelTest {

    private lateinit var favoriteTvShowsViewModel: FavoritesTvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieKuRepository: MovieKuRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowsEntities>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowsEntities>

    @Before
    fun setUp(){
        favoriteTvShowsViewModel = FavoritesTvShowsViewModel(movieKuRepository)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(10)
        val dataTvShows = MutableLiveData<PagedList<TvShowsEntities>>()
        dataTvShows.value = dummyTvShows

        `when`(movieKuRepository.getFavoriteTvShows()).thenReturn(dataTvShows)
        val tvShowsEntities = favoriteTvShowsViewModel.getFavoriteTvShows().value
        verify(movieKuRepository).getFavoriteTvShows()
        assertNotNull(tvShowsEntities)
        assertEquals(10, tvShowsEntities?.size)

        favoriteTvShowsViewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}