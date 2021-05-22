package com.rezaharis.movieku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.di.Injection
import com.rezaharis.movieku.viewmodel.favorites.FavoritesMovieViewModel
import com.rezaharis.movieku.viewmodel.favorites.FavoritesTvShowsViewModel
import com.rezaharis.movieku.viewmodel.movie.MovieDetailViewModel
import com.rezaharis.movieku.viewmodel.movie.MovieViewModel
import com.rezaharis.movieku.viewmodel.tvshows.TvShowsDetailViewModel
import com.rezaharis.movieku.viewmodel.tvshows.TvShowsViewModel

class ViewModelFactory(private val movieKuRepository: MovieKuRepository): ViewModelProvider.NewInstanceFactory() {

    private var id: Int = 0

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance?: synchronized(this){
                    instance?: ViewModelFactory(Injection.getRepositories(context))
                }

        fun getInstance(id: Int, context: Context): ViewModelFactory =
                instance?: synchronized(this){
                    instance?: ViewModelFactory(Injection.getRepositories(context), id)
                }
    }

    private constructor(movieKuRepository: MovieKuRepository, id: Int):
            this(movieKuRepository){
        this.id = id
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieKuRepository) as T
            }

            modelClass.isAssignableFrom(TvShowsViewModel::class.java) -> {
                TvShowsViewModel(movieKuRepository) as T
            }

            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(movieKuRepository) as T
            }

            modelClass.isAssignableFrom(TvShowsDetailViewModel::class.java) -> {
                TvShowsDetailViewModel(movieKuRepository) as T
            }

            modelClass.isAssignableFrom(FavoritesMovieViewModel::class.java) -> {
                FavoritesMovieViewModel(movieKuRepository) as T
            }

            modelClass.isAssignableFrom(FavoritesTvShowsViewModel::class.java) -> {
                FavoritesTvShowsViewModel(movieKuRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}