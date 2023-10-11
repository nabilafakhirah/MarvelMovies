package com.example.marvelmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.marvelmovies.R

class MovieDetailsFragment : Fragment() {
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
        val tvMovieRating = view.findViewById<TextView>(R.id.tv_movie_rating)

        tvMovieTitle.text = "Dummy Movie"
        tvMovieYear.text = "Dummy Year"
        tvMovieRating.text = "Dummy Rating"
        super.onViewCreated(view, savedInstanceState)
    }
}