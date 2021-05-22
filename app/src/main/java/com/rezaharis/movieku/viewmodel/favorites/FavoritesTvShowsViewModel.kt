package com.rezaharis.movieku.viewmodel.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities

class FavoritesTvShowsViewModel(private val movieKuRepository: MovieKuRepository): ViewModel() {
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntities>> = movieKuRepository.getFavoriteTvShows()
}