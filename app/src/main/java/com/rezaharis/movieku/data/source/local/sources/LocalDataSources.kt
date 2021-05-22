package com.rezaharis.movieku.data.source.local.sources

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.data.source.local.room.MovieKuFavoritesDao

class LocalDataSources private constructor(private val movieKuFavoritesDao: MovieKuFavoritesDao){
    companion object{
        private var INSTANCE: LocalDataSources? = null

        fun getInstance(movieKuFavoritesDao: MovieKuFavoritesDao): LocalDataSources =
            INSTANCE ?: LocalDataSources(movieKuFavoritesDao)
    }

    //Movies
    fun getAllMovies(): DataSource.Factory<Int, MovieEntities> = movieKuFavoritesDao.getMovies()

    fun getIdMovies(id: Int): LiveData<MovieEntities> = movieKuFavoritesDao.getIdMovies(id)

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntities> = movieKuFavoritesDao.getFavoriteMovies()

    fun insertMovies(movieEntities: List<MovieEntities>) = movieKuFavoritesDao.insertMovies(movieEntities)

    fun setFavoriteMovies(movieEntities: MovieEntities, newState: Boolean){
        movieEntities.setFavorite = newState
        movieKuFavoritesDao.updateMovies(movieEntities)
    }

    //TvShows
    fun getAlltTvShows(): DataSource.Factory<Int, TvShowsEntities> = movieKuFavoritesDao.getTvShows()

    fun getIdTvShows(id: Int): LiveData<TvShowsEntities> = movieKuFavoritesDao.getIdTvShows(id)

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowsEntities> = movieKuFavoritesDao.getFavoriteTvShows()

    fun insertTvShows(tvShowsEntities: ArrayList<TvShowsEntities>) = movieKuFavoritesDao.insertTvShows(tvShowsEntities)

    fun setFavoriteTvShows(tvShowsEntities: TvShowsEntities, newState: Boolean){
        tvShowsEntities.setFavorite = newState
        movieKuFavoritesDao.updateTvShows(tvShowsEntities)
    }
}