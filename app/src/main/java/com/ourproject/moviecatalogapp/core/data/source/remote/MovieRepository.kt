package com.ourproject.moviecatalogapp.core.data.source.remote

import com.ourproject.moviecatalogapp.core.data.source.remote.response.Results
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(): Flow<Resource<List<Results>?>>
}