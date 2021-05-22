package com.rezaharis.movieku.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rezaharis.movieku.data.source.remote.ApiResponse
import com.rezaharis.movieku.data.source.remote.StatusResponse
import com.rezaharis.movieku.utils.AppExecutors
import com.rezaharis.movieku.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource){ data ->
            result.removeSource(dbSource)

            if (shouldFetch(data)){
                fetchFromNetwork(dbSource)
            } else{
                result.addSource(dbSource){ newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected open fun onTechFailed(){}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val apiResponse = createCall()

        result.addSource(dbSource){ newData ->
            result.value = Resource.loading(newData)
        }

        result.addSource(apiResponse){ response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when(response.status){
                StatusResponse.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()){ newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }

                StatusResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()){ newData ->
                        result.value = Resource.success(newData)
                    }
                }

                StatusResponse.ERROR -> {
                    onTechFailed()
                    result.addSource(dbSource){ newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}