package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
            binding.noMovieFound.visibility =
                if (viewState.isEmptyList) View.VISIBLE else View.INVISIBLE
            binding.recyclerView.visibility =
                if (!viewState.isEmptyList) View.VISIBLE else View.INVISIBLE
            if (binding.progressCircular.visibility != View.VISIBLE) {
                if (viewState.cacheResponse != null)
                    movieAdapter.setData(viewState.cacheResponse)
                if (viewState.searchResponse != null)
                    movieAdapter.setData(viewState.searchResponse)
            }
            viewState.errorMessage?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        initiateView()
        return binding.root
    }

    private fun initiateView() {
        movieAdapter = MovieAdapter(requireContext())
        movieAdapter.setListener(object : MovieAdapter.MovieListener {
            override fun addMovieToFav(movie: MovieResponse) {
                viewModel.addMovieToCache(movie)
            }

            override fun removeMovieFromFav(movie: MovieResponse) {
                viewModel.removeMovieFromCache(movie)
            }

            override suspend fun isMovieInFavouriteList(movie: MovieResponse): Boolean {
                return viewModel.isMovieInFavouriteList(movie)
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = movieAdapter

        binding.textToSearch.doOnTextChanged { text, _, _, _ ->
            if (text == null || text.isEmpty())
                viewModel.getMoviesFromCache()
            else
                viewModel.searchMovies(text.toString())
        }
        viewModel.getMoviesFromCache()
    }

}