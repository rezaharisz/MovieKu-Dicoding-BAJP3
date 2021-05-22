package com.rezaharis.movieku.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities

@Dao
interface MovieKuFavoritesDao {

    //Movies
    @Query("SELECT * FROM movies_entities")
    fun getMovies(): DataSource.Factory<Int, MovieEntities>

    @Query("SELECT * FROM movies_entities WHERE movie_id = :id")
    fun getIdMovies(id: Int): LiveData<MovieEntities>

    @Query("SELECT * FROM movies_entities WHERE movie_setfavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieEntities: List<MovieEntities>)

    @Update
    fun updateMovies(movieEntities: MovieEntities)

    //TvShows
    @Query("SELECT * FROM tvshows_entities")
    fun getTvShows(): DataSource.Factory<Int, TvShowsEntities>

    @Query("SELECT * FROM tvshows_entities WHERE tvshows_id = :id")
    fun getIdTvShows(id: Int): LiveData<TvShowsEntities>

    @Query("SELECT * FROM tvshows_entities WHERE tvshows_setfavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowsEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShowsEntities: List<TvShowsEntities>)

    @Update
    fun updateTvShows(tvShowsEntities: TvShowsEntities)
}