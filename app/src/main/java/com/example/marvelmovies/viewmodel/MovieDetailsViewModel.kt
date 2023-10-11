package com.example.marvelmovies.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelmovies.data.MovieDetailResponse
import com.example.marvelmovies.data.MovieRepositoryProvider
import com.example.marvelmovies.model.Movie
import com.example.marvelmovies.model.Rating
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel : ViewModel(), DefaultLifecycleObserver {
    private val movieRepositoryProvider = MovieRepositoryProvider()
    val movieDetails: MutableLiveData<Movie> by lazy {
        MutableLiveData<Movie>()
    }

    fun getMovieData(imdbId: String) {
        movieRepositoryProvider.getMovieDetails(imdbId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it
            }
            .subscribe(
                ::populateMovieDetail,
                ::onError
            )
    }

    private fun populateMovieDetail(movie: MovieDetailResponse) {
        movieDetails.value = Movie(
            title = movie.Title,
            year = movie.Year,
            imdbId = movie.imdbID,
            type = movie.Type,
            poster = movie.Poster,
            ratings = movie.Ratings.map { rating ->
                Rating(
                    source = rating.Source,
                    value = rating.Value,
                )
            }
        )
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}

class MovieDetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel() as T
    }
}