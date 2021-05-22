package com.rezaharis.movieku.vo

import com.rezaharis.movieku.vo.Status.*

data class Resource<T>(val status: Status, val data: T?, val message: String?){
    companion object{
        fun <T> success(data: T?): Resource<T> = Resource(SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): Resource<T> = Resource(ERROR, data, msg)

        fun <T> loading(data: T?): Resource<T> = Resource(LOADING, data, null)
    }
}
