package com.rezaharis.movieku.viewmodel.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities

class TvShowsDetailViewModel(private val movieKuRepository: MovieKuRepository): ViewModel() {

    private lateinit var obtainedTvShows: LiveData<TvShowsEntities>

    fun getTvShowsId(id: Int): LiveData<TvShowsEntities> {
        obtainedTvShows = movieKuRepository.getTvShowsId(id)
        return obtainedTvShows
    }

    fun setFavoriteTvShows(){
        val movieResource = obtainedTvShows.value
        if (movieResource != null){
            val newState = !movieResource.setFavorite
            movieKuRepository.setFavoriteTvShows(movieResource, newState)
        }
    }
}