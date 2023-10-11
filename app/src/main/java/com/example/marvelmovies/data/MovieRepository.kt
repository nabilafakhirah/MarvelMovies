package com.example.marvelmovies.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRepository {
    @GET(".")
    fun getMovies(
        @Query("s") query: String = "Avengers",
        @Query("r") returnType: String = "json",
        @Query("page") page: Int = 1
    ): Observable<MovieResponse>

    @GET(".")
    fun getMovieDetails(
        @Query("i") query: String = "",
        @Query("r") returnType: String = "json"
    ): Observable<MovieResponse>
}