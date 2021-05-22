package com.rezaharis.movieku.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class ResponseMovie(
        @field:SerializedName("results")
        val results: ArrayList<ResponseDataMovie>? = null
)
