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
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import com.hourimeche.mvpmatchmovieapp.business.domain.util.firstCap
import com.hourimeche.mvpmatchmovieapp.business.domain.util.getYear
import com.hourimeche.mvpmatchmovieapp.databinding.DialogMovieBinding


class MovieDialog : DialogFragment() {

    companion object {
        const val TAG = "MovieDialog"

        fun newInstance(property: Movie): MovieDialog {
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
        val movie: Movie = arguments?.getSerializable(TAG) as Movie

        if (movie.title != null)
            "${movie.title} ${movie.release_date.getYear()}".also { binding.movieTitle.text = it }

        if (movie.name != null)
            "${movie.name} ${movie.first_air_date.getYear()}".also { binding.movieTitle.text = it }

        if (movie.media_type != null)
            movie.media_type.let { binding.movieType.text = it.firstCap() }

        if (movie.genres != null)
            binding.movieDescription.append("- Genre: ${movie.genres}\n")

        if (movie.original_title != null)
            binding.movieDescription.append("- Original title: ${movie.original_title}\n")

        if (movie.release_date != null)
            binding.movieDescription.append("- Release date: ${movie.release_date}\n")

        if (movie.first_air_date != null)
            binding.movieDescription.append("- First air date: ${movie.first_air_date}\n")

        if (movie.original_language != null)
            binding.movieDescription.append("- Original language: ${movie.original_language}\n")

        if (movie.popularity != null)
            binding.movieDescription.append("- Popularity: ${movie.popularity}\n")

        if (movie.overview != null)
            binding.movieDescription.append("- Overview: ${movie.overview}")

        try {
            binding.movieRating.rating = movie.vote_average?.toFloat()?.div(2) ?: 0F
        } catch (ignored: Exception) {
            binding.movieRating.visibility = View.INVISIBLE
        }

        binding.movieRating.visibility =
            if (movie.vote_average != null) View.VISIBLE else View.INVISIBLE
        val posterUrl =
            if (movie.poster_path == null || movie.poster_path.isEmpty()) Constants.NOT_AVAILABLE_POSTER
            else Constants.IMAGES_URL + movie.poster_path
        Glide.with(requireActivity())
            .load(posterUrl)
            .into(binding.moviePoster)

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