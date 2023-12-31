package com.ourproject.moviecatalogapp.di

import com.ourproject.moviecatalogapp.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{MovieViewModel(get())}
}