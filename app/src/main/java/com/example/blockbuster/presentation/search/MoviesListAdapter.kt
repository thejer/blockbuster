package com.example.blockbuster.presentation.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.blockbuster.R
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.databinding.ItemMovieLayoutBinding
import com.example.blockbuster.presentation.inflate

class MoviesListAdapter(
    val movieClickListener: (String) -> Unit
): ListAdapter<MovieItem, MoviesListAdapter.MoviesViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
            oldItem.imdbId == newItem.imdbId

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
            oldItem == newItem
    }

    inner class MoviesViewHolder(private val binding: ItemMovieLayoutBinding): ViewHolder(binding.root) {
        fun bind(item: MovieItem) {
            binding.apply {
                movieItemContainer.setOnClickListener { movieClickListener(item.imdbId) }
                movieYear.text = item.year
                movieTitle.text = item.title
                Glide.with(movieItemContainer)
                    .load(item.poster)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.gradient_poster_background)
                    .into(moviePoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(ItemMovieLayoutBinding.bind(parent.inflate(R.layout.item_movie_layout)))

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) = holder.bind(getItem(position))
}