package com.rezaharis.movieku.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.vo.Resource

interface MovieKuDataSource {
    //MOVIES
    fun getMovies(): LiveData<Resource<PagedList<MovieEntities>>>
    fun getMovieId(id: Int): LiveData<MovieEntities>
    fun getFavoriteMovies(): LiveData<PagedList<MovieEntities>>
    fun setFavoriteMovies(movieEntities: MovieEntities, state: Boolean)

    //TV SHOWS
    fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntities>>>
    fun getTvShowsId(id: Int): LiveData<TvShowsEntities>
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntities>>
    fun setFavoriteTvShows(tvShowsEntities: TvShowsEntities, state: Boolean)
}