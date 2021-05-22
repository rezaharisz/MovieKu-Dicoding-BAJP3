package com.rezaharis.movieku.api

import com.rezaharis.movieku.BuildConfig
import com.rezaharis.movieku.data.source.remote.responses.ResponseDataMovie
import com.rezaharis.movieku.data.source.remote.responses.ResponseDataTvShows
import com.rezaharis.movieku.data.source.remote.responses.ResponseMovie
import com.rezaharis.movieku.data.source.remote.responses.ResponseTvShows
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    fun getMovie(): Call<ResponseMovie>

    @GET("movie/{movieID}?api_key=${BuildConfig.API_KEY}")
    fun getDetailMovie(@Path("movieID") id: Int): Call<ResponseDataMovie>

    @GET("tv/popular?api_key=${BuildConfig.API_KEY}")
    fun getTvShows(): Call<ResponseTvShows>

    @GET("tv/{tvshowsID}?api_key=${BuildConfig.API_KEY}")
    fun getDetailTvShows(@Path("tvshowsID") id: Int): Call<ResponseDataTvShows>
}
