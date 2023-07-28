package com.ourproject.moviecatalogapp.core.di

import com.ourproject.moviecatalogapp.BuildConfig
import com.ourproject.moviecatalogapp.core.data.source.remote.ImplMovieRepository
import com.ourproject.moviecatalogapp.core.data.source.remote.MovieRepository
import com.ourproject.moviecatalogapp.core.data.source.remote.RemoteDataSource
import com.ourproject.moviecatalogapp.core.data.source.remote.network.MovieService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY}

        val httpClient = Interceptor { chain ->
            val original = chain.request()
            val method = original.method
            val httpBuilder = original.url.newBuilder()
            val token = BuildConfig.API_KEY

            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type","application/json")
                .method(method, original.body)
                .header("Authorization", "Bearer $token")

            val request = requestBuilder.url(httpBuilder.build()).build()
            chain.proceed(request)
        }

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpClient)
            .addInterceptor(interceptor)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(MovieService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    factory<MovieRepository> { ImplMovieRepository(get()) }
}