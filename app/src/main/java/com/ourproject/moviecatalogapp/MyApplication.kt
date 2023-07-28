package com.ourproject.moviecatalogapp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.moviecatalogapp.core.data.source.remote.MovieRepository
import com.ourproject.moviecatalogapp.core.data.source.remote.Resource
import com.ourproject.moviecatalogapp.core.data.source.remote.response.Results
import com.ourproject.moviecatalogapp.core.di.networkModule
import com.ourproject.moviecatalogapp.core.di.repositoryModule
import com.ourproject.moviecatalogapp.di.viewModelModule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication() : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}