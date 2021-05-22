package com.rezaharis.movieku.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
class MovieDetailViewModel(private val movieKuRepository: MovieKuRepository): ViewModel() {

    private lateinit var obtainedMovies: LiveData<MovieEntities>

    fun getMovieId(id: Int): LiveData<MovieEntities>{
        obtainedMovies = movieKuRepository.getMovieId(id)
        return obtainedMovies
    }

    fun setFavoriteMovies(){
        val movieResource = obtainedMovies.value
        if (movieResource != null){
            val newState = !movieResource.setFavorite
            movieKuRepository.setFavoriteMovies(movieResource, newState)
        }
    }
}