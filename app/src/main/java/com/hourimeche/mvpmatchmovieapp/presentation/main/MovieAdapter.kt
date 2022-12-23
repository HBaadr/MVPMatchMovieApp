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
import com.hourimeche.mvpmatchmovieapp.business.domain.util.firstCap
import com.hourimeche.mvpmatchmovieapp.databinding.CardMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieAdapter(private val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

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
            movie.Type.let { binding.movieType.text = it?.firstCap() }
            movie.Plot.let { binding.movieDescription.text = it }
            movie.imdbRating.let {
                binding.movieRating.rating = movie.imdbRating?.toFloat()?.div(2) ?: 0F
            }
            binding.movieRating.visibility =
                if (movie.imdbRating != null) View.VISIBLE else View.INVISIBLE
            binding.movieDescription.visibility =
                if (movie.Genre != null) View.VISIBLE else View.INVISIBLE
            Glide.with(context).load(movie.Poster).into(binding.moviePoster)

            CoroutineScope(Dispatchers.Main).launch {
                binding.movieAddToFavorite.setOnCheckedChangeListener(null)
                val isMovieInFavouriteList = listener.isMovieInFavouriteList(movie)
                if (binding.movieAddToFavorite.isChecked != isMovieInFavouriteList)
                    binding.movieAddToFavorite.isChecked = isMovieInFavouriteList
                binding.movieAddToFavorite.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) listener.addMovieToFav(movie) else listener.removeMovieFromFav(
                        movie
                    )
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardMovieBinding.bind(view)

        init {
            binding.movieTitle.movementMethod = ScrollingMovementMethod()
        }
    }

    fun setData(data: List<MovieResponse>) {
        this.data.clear()
        this.data.addAll(data)
        this.listener = listener
        notifyDataSetChanged()
    }

    fun setListener(listener: MovieListener) {
        this.listener = listener
    }

    interface MovieListener {
        fun addMovieToFav(movie: MovieResponse)
        fun removeMovieFromFav(movie: MovieResponse)
        suspend fun isMovieInFavouriteList(movie: MovieResponse): Boolean
    }
}
