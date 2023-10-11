package com.example.marvelmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import com.example.marvelmovies.R
import com.example.marvelmovies.model.Movie
import com.example.marvelmovies.ui.items.WideMovieAdapter
import com.example.marvelmovies.viewmodel.AvengerMoviesViewModel
import com.example.marvelmovies.viewmodel.AvengerMoviesViewModelFactory

class HomeFragment: Fragment(), LifecycleOwner {
    private lateinit var viewModel: AvengerMoviesViewModel
    private val allMovies = mutableListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            AvengerMoviesViewModelFactory()
        )[AvengerMoviesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val latestMoviesRv = view.findViewById<RecyclerView>(R.id.latestMoviesRv)
        val latestSeriesRv = view.findViewById<RecyclerView>(R.id.latestSeriesRv)
        val trendingTodayRv = view.findViewById<RecyclerView>(R.id.trendingTodayRv)

        val navigateToDetail = {
            findNavController().navigate(R.id.action_to_movie_detail)
        }

        val avengerObserver = Observer<List<Movie>> { movieList ->
            setUpWideRecyclerView(latestMoviesRv, movieList, navigateToDetail)
            allMovies.addAll(movieList)
        }
        val marvelObserver = Observer<List<Movie>> { movieList ->
            setUpWideRecyclerView(latestSeriesRv, movieList, navigateToDetail)
            allMovies.addAll(movieList)
            setUpWideRecyclerView(trendingTodayRv, allMovies, navigateToDetail)
        }
        viewModel.avengerList.observe(viewLifecycleOwner, avengerObserver)
        viewModel.marvelList.observe(viewLifecycleOwner, marvelObserver)
    }

    private fun setUpWideRecyclerView(recyclerView: RecyclerView, movieList: List<Movie>, navigate: () -> Unit) {
        recyclerView.layoutManager =
            this.context?.let { LoopingLayoutManager(it, LoopingLayoutManager.HORIZONTAL, false) }
        val adapter = WideMovieAdapter(movieList, navigate)
        recyclerView.adapter = adapter
    }
}