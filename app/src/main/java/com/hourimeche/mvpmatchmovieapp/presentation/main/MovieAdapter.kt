package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hourimeche.mvpmatchmovieapp.R
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.databinding.CardMovieBinding

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var data = ArrayList<MovieResponse>()
    private lateinit var listener: MovieListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_movie, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = data[position]
        with(holder) {
            "${movie.Title} (${movie.Year})".also { binding.movieTitle.text = it }
            ("Released: ${movie.Released}\n" +
                    "Genre: ${movie.Genre}\n" +
                    "Actors: ${movie.Actors}\n" +
                    "Language: ${movie.Language}\n" +
                    "Country: ${movie.Country}\n" +
                    "Awards: ${movie.Awards}\n" +
                    "Plot: ${movie.Plot}").also { binding.movieDescription.text = it }
            "${movie.Type} (${movie.Year})".also { binding.movieType.text = it }
            binding.movieRating.rating = movie.imdbRating.toFloat() / 2
            Glide.with(context).load(movie.Poster).into(binding.moviePoster)
            binding.movieAddToFavorite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) listener.addMovieToFav(movie) else listener.removeMovieFromFav(movie)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardMovieBinding.bind(view)

        init {
            binding.movieTitle.movementMethod = ScrollingMovementMethod()
        }
    }

    fun setData(data: ArrayList<MovieResponse>, listener: MovieListener) {
        this.data = data
        this.listener = listener
        notifyDataSetChanged()
    }

    interface MovieListener {
        fun addMovieToFav(movie: MovieResponse)
        fun removeMovieFromFav(movie: MovieResponse)
    }
}
