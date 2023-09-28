package com.example.marvelmovies.data

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search") val Search: List<Search>,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String,
)

data class Search(
    @SerializedName("Title") val Title: String,
    @SerializedName("Year") val Year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val Type: String,
    @SerializedName("Poster") val Poster: String,
)
