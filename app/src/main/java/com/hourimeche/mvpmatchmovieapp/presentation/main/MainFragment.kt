package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hourimeche.mvpmatchmovieapp.R
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // Whenever the view is resumed, subscribe to our viewmodel's view state StateFlow
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
    }

    private fun processViewState(viewState: MainState) {

        lifecycleScope.launch {
            binding.progressCircular.visibility =
                if (viewState.showProgressBar) View.VISIBLE else View.INVISIBLE
            binding.recyclerView.visibility =
                if (viewState.searchResponse != null) View.VISIBLE else View.INVISIBLE
            binding.search.visibility =
                if (viewState.searchResponse == null) View.VISIBLE else View.INVISIBLE
            viewState.searchResponse?.let { movieAdapter.setData(it.Search) }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        binding.search.setOnClickListener {
            viewModel.searchMovies("Titanic")
        }
        movieAdapter = MovieAdapter(requireContext())
        movieAdapter.setListener(object : MovieAdapter.MovieListener {
            override fun addMovieToFav(movie: MovieResponse) {
                Toast.makeText(requireContext(), movie.Title + " Added", Toast.LENGTH_SHORT).show()
            }

            override fun removeMovieFromFav(movie: MovieResponse) {
                Toast.makeText(requireContext(), movie.Title + " Removed", Toast.LENGTH_SHORT)
                    .show()
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = movieAdapter
        return binding.root
    }

}