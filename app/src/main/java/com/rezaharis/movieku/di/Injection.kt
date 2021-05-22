package com.rezaharis.movieku.di

import android.content.Context
import com.rezaharis.movieku.data.source.remote.sources.RemoteDataSource
import com.rezaharis.movieku.data.source.MovieKuRepository
import com.rezaharis.movieku.data.source.local.room.MovieKuRoomDatabase
import com.rezaharis.movieku.data.source.local.sources.LocalDataSources
import com.rezaharis.movieku.utils.AppExecutors

object Injection {
    fun getRepositories(context: Context): MovieKuRepository{

        val database = MovieKuRoomDatabase.getInstance(context)
        val movieKuDataSource = RemoteDataSource()
        val localDataSources = LocalDataSources.getInstance(database.movieKuFavoritesDao())
        val appExecutors = AppExecutors()

        return MovieKuRepository.getInstance(movieKuDataSource, localDataSources, appExecutors)
    }
}