package com.example.marvelmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import com.example.marvelmovies.R
import com.example.marvelmovies.model.Movie
import com.example.marvelmovies.ui.items.WideMovieAdapter
import com.example.marvelmovies.viewmodel.AvengerMoviesViewModel
import com.example.marvelmovies.viewmodel.AvengerMoviesViewModelFactory

class HomeFragment: Fragment(), LifecycleOwner {
    private lateinit var viewModel: AvengerMoviesViewModel
    private lateinit var wideMovieAdapter: WideMovieAdapter
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.latestMoviesRv)
        val movieObserver = Observer<List<Movie>> { movieList ->
            setupDataInRecyclerView(recyclerView, movieList)
        }
        viewModel.mutableLiveDataSearchList.observe(viewLifecycleOwner, movieObserver)
    }

    private fun setupDataInRecyclerView(recyclerView: RecyclerView, movieList: List<Movie>) {
        Log.v("MOVIE_TAG", "Size is ${movieList.size}")
        recyclerView.layoutManager =
            this.context?.let { LoopingLayoutManager(it, LoopingLayoutManager.HORIZONTAL, false) }
        wideMovieAdapter = WideMovieAdapter(movieList)
        recyclerView.adapter = wideMovieAdapter
    }
}