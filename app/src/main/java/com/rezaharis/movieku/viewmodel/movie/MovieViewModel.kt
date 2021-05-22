package com.rezaharis.movieku.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.vo.Resource

class MovieViewModel(private val movieKuRepository: MovieKuRepository): ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntities>>> = movieKuRepository.getMovies()
}