package com.ourproject.moviecatalogapp.core.data.source.remote

import com.ourproject.moviecatalogapp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

abstract class RepositoryLoader<RequestType> {
    private var result: Flow<Resource<RequestType>> = flow {

        emit(Resource.Loading())

        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("Empty Data"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
                Timber.e(apiResponse.errorMessage)
            }
        }
    }



    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<RequestType>> = result
}