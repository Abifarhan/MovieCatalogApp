package com.ourproject.moviecatalogapp.core.data.source.remote

import com.ourproject.moviecatalogapp.core.data.source.remote.network.ApiResponse
import com.ourproject.moviecatalogapp.core.data.source.remote.network.MovieService
import com.ourproject.moviecatalogapp.core.data.source.remote.response.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class RemoteDataSource(private val movieService: MovieService) {

    suspend fun getMovie(): Flow<ApiResponse<List<Results>?>> {
        return flow {
            try {
                val response = movieService.getMovies()
                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun Exception.getErrorMessage(): String {
        return when (this) {
            is HttpException -> {
                "Data Invalid"
            }

            is IOException -> {
                "Have no connection"
            }

            else -> {
                this.message.toString()
            }
        }
    }

}