package com.rezaharis.movieku.viewmodel.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.vo.Resource

class TvShowsViewModel(private val movieKuRepository: MovieKuRepository): ViewModel() {
    fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntities>>> = movieKuRepository.getTvShows()
}