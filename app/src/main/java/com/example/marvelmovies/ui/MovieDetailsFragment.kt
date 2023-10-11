package com.example.marvelmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marvelmovies.R
import com.example.marvelmovies.model.Movie
import com.example.marvelmovies.viewmodel.MovieDetailsViewModel
import com.example.marvelmovies.viewmodel.MovieDetailsViewModelFactory

class MovieDetailsFragment : Fragment(), LifecycleOwner {
    private lateinit var viewModel: MovieDetailsViewModel
    private val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory()
        )[MovieDetailsViewModel::class.java]
        viewModel.getMovieData(args.imdbId)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imgMovie = view.findViewById<ImageView>(R.id.img_movie)
        val tvMovieTitle = view.findViewById<TextView>(R.id.tv_movie_title)
        val tvMovieYear = view.findViewById<TextView>(R.id.tv_movie_year)
        val tvMovieType = view.findViewById<TextView>(R.id.tv_movie_type)
        val tvMovieRatingFirst = view.findViewById<TextView>(R.id.tv_movie_rating_1)
        val tvMovieRatingSecond = view.findViewById<TextView>(R.id.tv_movie_rating_2)
        val tvMovieRatingThird = view.findViewById<TextView>(R.id.tv_movie_rating_3)

        val movieObserver = Observer<Movie> { movie ->
            Glide.with(imgMovie.context)
                .load(movie.poster)
                .apply(RequestOptions.overrideOf(200, 300).centerCrop())
                .into(imgMovie)
            tvMovieTitle.text = movie.title
            tvMovieYear.text = movie.year
            tvMovieType.text = movie.type

            //this section would ideally use recyclerview / set to be more aware of how much ratings are present exactly
            tvMovieRatingFirst.text = "${movie.ratings[0].source} ${movie.ratings[0].value}"
            tvMovieRatingSecond.text = "${movie.ratings[1].source} ${movie.ratings[1].value}"
            tvMovieRatingThird.text = "${movie.ratings[2].source} ${movie.ratings[2].value}"
        }
        viewModel.movieDetails.observe(viewLifecycleOwner, movieObserver)
        super.onViewCreated(view, savedInstanceState)
    }
}