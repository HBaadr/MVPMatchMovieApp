package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hourimeche.mvpmatchmovieapp.R
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import com.hourimeche.mvpmatchmovieapp.business.domain.util.firstCap
import com.hourimeche.mvpmatchmovieapp.business.domain.util.getYear
import com.hourimeche.mvpmatchmovieapp.databinding.CardMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieAdapter(private val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var data = ArrayList<Movie>()
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

            if (movie.title != null)
                "${movie.title} ${movie.release_date.getYear()}".also {
                    binding.movieTitle.text = it
                }

            if (movie.name != null)
                "${movie.name} ${movie.first_air_date.getYear()}".also {
                    binding.movieTitle.text = it
                }

            movie.media_type.let { binding.movieType.text = it?.firstCap() }
            movie.overview.let { binding.movieDescription.text = it }

            try {
                binding.movieRating.rating = movie.vote_average?.toFloat()?.div(2) ?: 0F
            } catch (ignored: Exception) {
                binding.movieRating.visibility = View.INVISIBLE
            }

            binding.movieRating.visibility =
                if (movie.vote_average != null) View.VISIBLE else View.INVISIBLE
            binding.movieDescription.visibility =
                if (movie.media_type != null) View.VISIBLE else View.INVISIBLE
            Glide.with(context).load(Constants.IMAGES_URL + movie.poster_path)
                .into(binding.moviePoster)

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

            binding.root.setOnClickListener {
                listener.openMovieDialog(movie)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardMovieBinding.bind(view)

        init {
            binding.movieTitle.movementMethod = ScrollingMovementMethod()
        }
    }

    fun setData(data: List<Movie>) {
        this.data.clear()
        this.data.addAll(data)
        this.listener = listener
        notifyDataSetChanged()
    }

    fun setListener(listener: MovieListener) {
        this.listener = listener
    }

    interface MovieListener {
        fun addMovieToFav(movie: Movie)
        fun removeMovieFromFav(movie: Movie)
        fun openMovieDialog(movie: Movie)
        suspend fun isMovieInFavouriteList(movie: Movie): Boolean
    }
}
