package com.rezaharis.movieku.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rezaharis.movieku.data.source.local.entity.MovieEntities
import com.rezaharis.movieku.data.source.local.entity.TvShowsEntities
import com.rezaharis.movieku.data.source.local.sources.LocalDataSources
import com.rezaharis.movieku.data.source.remote.ApiResponse
import com.rezaharis.movieku.data.source.remote.responses.ResponseDataMovie
import com.rezaharis.movieku.data.source.remote.responses.ResponseDataTvShows
import com.rezaharis.movieku.data.source.remote.sources.RemoteDataSource
import com.rezaharis.movieku.utils.AppExecutors
import com.rezaharis.movieku.vo.Resource

class FakeMovieKuRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSources: LocalDataSources,
    private val appExecutors: AppExecutors): MovieKuDataSource {

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntities>>> {
        return object : NetworkBoundResource<PagedList<MovieEntities>, List<ResponseDataMovie>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<MovieEntities>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSources.getAllMovies(), config).build()
            }

            public override fun shouldFetch(data: PagedList<MovieEntities>?): Boolean {
                return data == null || data.isEmpty()
            }

            public override fun createCall(): LiveData<ApiResponse<List<ResponseDataMovie>>> {
                return remoteDataSource.getMovies()
            }

            public override fun saveCallResult(data: List<ResponseDataMovie>) {
                val movieList = ArrayList<MovieEntities>()
                for (response in data){
                    val movies = MovieEntities(
                        response.id,
                        response.poster,
                        response.movieName,
                        response.description,
                        response.releasedate,
                        response.rate,
                        response.votecount
                    )
                    movieList.add(movies)
                }
                localDataSources.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieId(id: Int): LiveData<MovieEntities> {
        return localDataSources.getIdMovies(id)
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntities>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSources.getFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovies(movieEntities: MovieEntities, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSources.setFavoriteMovies(movieEntities, state)
        }
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntities>>>{
        return object : NetworkBoundResource<PagedList<TvShowsEntities>, List<ResponseDataTvShows>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TvShowsEntities>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSources.getAlltTvShows(), config).build()
            }

            public override fun shouldFetch(data: PagedList<TvShowsEntities>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ResponseDataTvShows>>> {
                return remoteDataSource.getTvShows()
            }

            public override fun saveCallResult(data: List<ResponseDataTvShows>) {
                val tvShowsList = ArrayList<TvShowsEntities>()
                for (response in data){
                    val tvShows = TvShowsEntities(
                        response.id,
                        response.poster,
                        response.tvShowsName,
                        response.description,
                        response.releasedate,
                        response.rate,
                        response.votecount
                    )
                    tvShowsList.add(tvShows)
                }
                localDataSources.insertTvShows(tvShowsList)
            }
        }.asLiveData()
    }

    override fun getTvShowsId(id: Int): LiveData<TvShowsEntities>{
        return localDataSources.getIdTvShows(id)
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntities>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSources.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteTvShows(tvShowsEntities: TvShowsEntities, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSources.setFavoriteTvShows(tvShowsEntities, state)
        }
    }
}