package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.util.firstCap
import com.hourimeche.mvpmatchmovieapp.databinding.DialogMovieBinding


class MovieDialog : DialogFragment() {

    companion object {
        const val TAG = "MovieDialog"

        fun newInstance(property: MovieResponse): MovieDialog {
            val f = MovieDialog()
            val args = Bundle()
            args.putSerializable(TAG, property)
            f.arguments = args
            return f
        }
    }

    init {
        isCancelable = true
    }


    private var _binding: DialogMovieBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = DialogMovieBinding.inflate(inflater)
        onViewCreated()
        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    private fun onViewCreated() {
        val movie: MovieResponse = arguments?.getSerializable(TAG) as MovieResponse

        "${movie.Title} (${movie.Year})".also { binding.movieTitle.text = it }
        movie.Type.let { binding.movieType.text = it?.firstCap() }
        ("- Genre: ${movie.Genre}\n" +
                "- Rated: ${movie.Rated}\n" +
                "- Released: ${movie.Released}\n" +
                "- Runtime: ${movie.Runtime}\n" +
                "- Director: ${movie.Director}\n" +
                "- Writer: ${movie.Writer}\n" +
                "- Actors: ${movie.Actors}\n" +
                "- Language: ${movie.Language}\n" +
                "- Country: ${movie.Country}\n" +
                "- Awards: ${movie.Awards}\n" +
                "- Plot: ${movie.Plot}\n").let { binding.movieDescription.text = it }

        try {
            binding.movieRating.rating = movie.imdbRating?.toFloat()?.div(2) ?: 0F
        } catch (ignored: Exception) {
            binding.movieRating.visibility = View.INVISIBLE
        }

        binding.movieRating.visibility =
            if (movie.imdbRating != null) View.VISIBLE else View.INVISIBLE
        Glide.with(requireActivity()).load(movie.Poster).into(binding.moviePoster)

        binding.btnHide.setOnClickListener {
            requireActivity().supportFragmentManager.setFragmentResult(
                MainFragment.REQUEST_KEY,
                bundleOf(MainFragment.MOVIE to movie)
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}