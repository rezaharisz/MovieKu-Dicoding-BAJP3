@file:Suppress("DEPRECATION")

package com.rezaharis.movieku.viewmodel.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
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
class TvShowsViewModelTest{

    private lateinit var tvShowsViewModel: TvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieKuRepository: MovieKuRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowsEntities>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowsEntities>

    @Before
    fun setUp(){
        tvShowsViewModel = TvShowsViewModel(movieKuRepository)
    }

    @Test
    fun getTvShows(){
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(10)
        val dataTvShows = MutableLiveData<Resource<PagedList<TvShowsEntities>>>()
        dataTvShows.value = dummyTvShows

        `when`(movieKuRepository.getTvShows()).thenReturn(dataTvShows)
        val tvShowsEntities = tvShowsViewModel.getTvShows().value?.data
        verify(movieKuRepository).getTvShows()
        assertNotNull(tvShowsEntities)
        assertEquals(10, tvShowsEntities?.size)

        tvShowsViewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }

}