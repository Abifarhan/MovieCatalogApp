package com.ourproject.moviecatalogapp.core.data.source.remote

import com.ourproject.moviecatalogapp.core.data.source.remote.network.ApiResponse
import com.ourproject.moviecatalogapp.core.data.source.remote.response.Results
import kotlinx.coroutines.flow.Flow

class ImplMovieRepository(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override fun getMovie(): Flow<Resource<List<Results>?>> {
        return object : RepositoryLoader<List<Results>?>() {
            override suspend fun createCall(): Flow<ApiResponse<List<Results>?>> {
                return remoteDataSource.getMovie()
            }
        }.asFlow()
    }


}