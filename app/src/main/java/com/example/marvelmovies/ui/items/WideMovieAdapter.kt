package com.example.marvelmovies.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marvelmovies.R
import com.example.marvelmovies.model.Movie

class WideMovieAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<WideMovieAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wideMovieImage = itemView.findViewById<ImageView>(R.id.wideMovieImg)
        fun bind(movie: Movie) {
            Glide.with(wideMovieImage.context)
                .load(movie.poster)
                .apply(RequestOptions.overrideOf(300, 220).centerCrop())
                .into(wideMovieImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_wide, parent,false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(movies[position])
}
