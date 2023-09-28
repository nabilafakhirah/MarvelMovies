package com.example.marvelmovies.data

import com.example.marvelmovies.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepositoryProvider {
    fun getAvengerMovies(): Observable<MovieResponse> {
        return createService().getMovies()
    }

    fun getMarvelMovies(): Observable<MovieResponse> {
        return createService().getMovies(query = "Marvel")
    }

    private fun createService(): MovieRepository {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
        val interceptor = Interceptor { chain ->
            val headers = chain.request()
                .headers
                .newBuilder()
                .add("X-RapidAPI-Key", KEY)
                .add("X-RapidAPI-Host", HOST)
                .build()

            val request = chain.request()
                .newBuilder()
                .headers(headers)
                .build()

            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieRepository::class.java)
    }

    companion object {
        const val BASE_URL = "https://movie-database-alternative.p.rapidapi.com"
        const val KEY = "5c47b240damsh465cf0b2fee10cbp134f1ajsn3fbe4eec4f79"
        const val HOST = "movie-database-alternative.p.rapidapi.com"
    }
}