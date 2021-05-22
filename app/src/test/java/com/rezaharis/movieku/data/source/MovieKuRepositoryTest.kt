@file:Suppress("DEPRECATION")

package com.rezaharis.movieku.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.data.source.local.sources.LocalDataSources
import com.rezaharis.movieku.data.source.remote.sources.RemoteDataSource
import com.rezaharis.movieku.utils.*
import com.rezaharis.movieku.vo.Resource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieKuRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSources::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieKuRepository = FakeMovieKuRepository(remote, local, appExecutors)
    private val testExecutors = AppExecutors(TestExecutors(), TestExecutors(), TestExecutors())

    private val movieResponses = DataDummy.getMoviesList()
    private val tvShowsResponses = DataDummy.getTvShowsList()

    @Test
    fun getMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntities>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieKuRepository.getMovies()

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMoviesList()))
        verify(local).getAllMovies()
        assertNotNull(moviesEntities.data)
        assertEquals(movieResponses.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieId(){
        val movieEntities = MutableLiveData<MovieEntities>()
        val dummyMovie = movieResponses[0]
        movieEntities.value = dummyMovie
        `when`(dummyMovie.id?.let { local.getIdMovies(it) }).thenReturn(movieEntities)

        val movies = LiveDataTestUtils.getValue(movieKuRepository.getMovieId(dummyMovie.id!!))
        verify(local).getIdMovies(dummyMovie.id!!)
        assertNotNull(movies)
        assertEquals(dummyMovie.id, movies.id)
    }


    @Test
    fun getFavoriteMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntities>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieKuRepository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMoviesList()))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun setFavoriteMovies(){
        val dummyMovies = movieResponses[0]
        val state = !dummyMovies.setFavorite

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteMovies(dummyMovies, state)

        movieKuRepository.setFavoriteMovies(dummyMovies, state)
        verify(local, times(1)).setFavoriteMovies(dummyMovies, state)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun getTvShows(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntities>
        `when`(local.getAlltTvShows()).thenReturn(dataSourceFactory)
        movieKuRepository.getTvShows()

        val tvShowsEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShowsList()))
        verify(local).getAlltTvShows()
        assertNotNull(tvShowsEntities.data)
        assertEquals(tvShowsResponses.size.toLong(), tvShowsEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowsId(){
        val tvShowsEntities = MutableLiveData<TvShowsEntities>()
        val dummyTvShows = tvShowsResponses[0]
        tvShowsEntities.value = dummyTvShows
        `when`(dummyTvShows.id?.let { local.getIdTvShows(it) }).thenReturn(tvShowsEntities)

        val tvShows = LiveDataTestUtils.getValue(movieKuRepository.getTvShowsId(dummyTvShows.id!!))
        verify(local).getIdTvShows(dummyTvShows.id!!)
        assertNotNull(tvShows)
        assertEquals(dummyTvShows.id, tvShows.id)
    }

    @Test
    fun getFavoriteTvShows(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntities>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        movieKuRepository.getFavoriteTvShows()

        val tvShowsEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShowsList()))
        verify(local).getFavoriteTvShows()
        assertNotNull(tvShowsEntities.data)
        assertEquals(tvShowsResponses.size.toLong(), tvShowsEntities.data?.size?.toLong())
    }

    @Test
    fun setFavoriteTvShows(){
        val dummyTvShows = tvShowsResponses[0]
        val state = !dummyTvShows.setFavorite

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteTvShows(dummyTvShows, state)

        movieKuRepository.setFavoriteTvShows(dummyTvShows, state)
        verify(local, times(1)).setFavoriteTvShows(dummyTvShows, state)
        verifyNoMoreInteractions(local)
    }
}