package com.rezaharis.movieku.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class ResponseTvShows(
        @field:SerializedName("results")
        val results: ArrayList<ResponseDataTvShows>? = null
)