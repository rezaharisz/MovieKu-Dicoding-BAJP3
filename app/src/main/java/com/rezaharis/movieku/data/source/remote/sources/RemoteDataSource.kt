package com.rezaharis.movieku.data.source.remote.sources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezaharis.movieku.api.ApiCall
import com.rezaharis.movieku.data.source.remote.responses.ResponseDataMovie
import com.rezaharis.movieku.data.source.remote.responses.ResponseDataTvShows
import com.rezaharis.movieku.data.source.remote.ApiResponse
import com.rezaharis.movieku.data.source.remote.responses.ResponseMovie
import com.rezaharis.movieku.data.source.remote.responses.ResponseTvShows
import com.rezaharis.movieku.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getMovies(): LiveData<ApiResponse<List<ResponseDataMovie>>>{
        EspressoIdlingResource.increment()
        val response = ApiCall.movieApiService.getMovie()
        val resultMovies = MutableLiveData<ApiResponse<List<ResponseDataMovie>>>()
        response.enqueue(object : Callback<ResponseMovie>{
            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {
                resultMovies.value = ApiResponse.success(response.body()?.results as List<ResponseDataMovie>)

                if (!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                Log.e("MovieViewModel", "Data is Not Available")

                if (!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            }

        })
        return resultMovies
    }

    fun getTvShows(): LiveData<ApiResponse<List<ResponseDataTvShows>>>{
        EspressoIdlingResource.increment()
        val response = ApiCall.movieApiService.getTvShows()
        val resultTvShows = MutableLiveData<ApiResponse<List<ResponseDataTvShows>>>()
        response.enqueue(object : Callback<ResponseTvShows>{
            override fun onResponse(call: Call<ResponseTvShows>, response: Response<ResponseTvShows>) {
                resultTvShows.value = ApiResponse.success(response.body()?.results as List<ResponseDataTvShows>)

                if (!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<ResponseTvShows>, t: Throwable) {
                Log.e("TvShowsViewModel", "Data is Not Available")

                if (!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            }
        })
        return resultTvShows
    }
}