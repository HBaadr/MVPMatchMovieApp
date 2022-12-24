package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hourimeche.mvpmatchmovieapp.R
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
        const val MOVIE = "movie"
        const val REQUEST_KEY = "requestKey"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var snackbar: Snackbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // Whenever the view is resumed, subscribe to our viewmodel's view state StateFlow
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
        viewModel.getNetworkChecker().observe(this) { isConnected ->
            if (isConnected) snackbar.dismiss() else snackbar.show()
        }
    }

    private fun processViewState(viewState: MainState) {

        lifecycleScope.launch {

            viewState.searchResponse?.let {
                movieAdapter.setData(it)
                binding.btnSearch.text = getString(R.string.favorites)
            }
            viewState.cacheResponse?.let { movieAdapter.setData(it) }
            viewState.errorMessage?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }

            binding.progressCircular.visibility =
                if (viewState.showProgressBar) View.VISIBLE else View.INVISIBLE
            binding.noMovieFound.visibility =
                if (viewState.isEmptyList) View.VISIBLE else View.INVISIBLE
            binding.recyclerView.visibility =
                if (!viewState.isEmptyList) View.VISIBLE else View.INVISIBLE

            if (viewState.movieRemoved)
                viewModel.getMoviesFromCache()

            if (viewState.unwantedMovieAdded)
                viewModel.getUnwantedMoviesFromCache()

            if (viewState.unwantedMoviesReceived && viewState.query.isNotEmpty() && !viewState.isFavouriteScreen)
                viewModel.searchMovies(viewState.query)

            if (viewState.unwantedMoviesReceived && viewState.isFavouriteScreen)
                viewModel.getMoviesFromCache()

            (activity as AppCompatActivity).supportActionBar?.title =
                when (binding.textToSearch.text.toString().length) {
                    0 -> getString(R.string.fav_movies)
                    else -> getString(R.string.search_for) + binding.textToSearch.text
                }
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
            override fun addMovieToFav(movie: Movie) {
                viewModel.addMovieToCache(movie)
            }

            override fun removeMovieFromFav(movie: Movie) {
                viewModel.removeMovieFromCache(movie)
            }

            override fun openMovieDialog(movie: Movie) {
                val dialog = MovieDialog.newInstance(movie)
                dialog.show(childFragmentManager, MovieDialog.TAG)
            }

            override suspend fun isMovieInFavouriteList(movie: Movie): Boolean {
                return viewModel.isMovieInFavouriteList(movie)
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = movieAdapter
        binding.btnSearch.setOnClickListener {
            if (binding.btnSearch.text.equals(getString(R.string.favorites))) {
                viewModel.getMoviesFromCache()
                binding.textToSearch.setText("")
            } else
                viewModel.searchMovies(binding.textToSearch.text.toString())
        }

        binding.textToSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isEmpty())
                    binding.btnSearch.text = getString(R.string.favorites)
                else
                    binding.btnSearch.text = getString(R.string.search)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        viewModel.getMoviesFromCache()
        viewModel.getUnwantedMoviesFromCache()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, result ->
            val movie = result.getSerializable(MOVIE) as Movie
            viewModel.addMovieToUnwanted(movie)
        }

        snackbar =
            Snackbar.make(binding.main, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
        snackbar.view.setBackgroundColor(Color.RED)
        val snackbarTextview =
            snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarTextview.setTextColor(Color.WHITE)
        snackbarTextview.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbarTextview.gravity = Gravity.CENTER_HORIZONTAL
        snackbarTextview.textSize = 18f

    }
}