package com.ourproject.moviecatalogapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.moviecatalogapp.core.data.source.remote.MovieRepository
import com.ourproject.moviecatalogapp.core.data.source.remote.Resource
import com.ourproject.moviecatalogapp.core.data.source.remote.response.Results
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private var _movie: MutableLiveData<Resource<List<Results>?>> = MutableLiveData()
    val movie = _movie

    fun getMovie() {
        viewModelScope.launch {
            repository.getMovie().collect{
                Log.d("TAG", "getMovie: data you get is ${it.message}")
                _movie.value = it
            }
        }
    }
}