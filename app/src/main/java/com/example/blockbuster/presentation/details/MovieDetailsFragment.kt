package com.example.blockbuster.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.blockbuster.R
import com.example.blockbuster.databinding.FragmentMovieDetailsBinding
import com.example.blockbuster.presentation.setResizableText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icToggleDetails.setOnClickListener {
            viewModel.toggleViewMoreDetails()
        }
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.apply {
                uiState.movieDetails?.let { movieDetails ->
                    Glide.with(view)
                        .load(movieDetails.poster)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.gradient_poster_background)
                        .into(moviePosterBanner)
                    movieDetailsTitle.text = movieDetails.title
                    maturityRating.text = movieDetails.rated
                    movieDetailsRuntime.text = movieDetails.runtime
                    movieDetailsGenre.text = movieDetails.genre
                    movieDetailsYear.text = movieDetails.year
                    movieDirectorName.text = movieDetails.director
                    movieWriters.text = movieDetails.writer
                    movieCast.text = movieDetails.actors
                    movieDetailsPlot.setResizableText(movieDetails.plot, 5, true)
                }
                uiState.isViewMoreDetails.let { isViewMoreDetails ->
                    val moreDetailsCaret = if (isViewMoreDetails) {
                        moreMovieDetails.visibility = View.VISIBLE
                        R.drawable.ic_caret_up
                    } else {
                        moreMovieDetails.visibility = View.GONE
                        R.drawable.ic_caret_down
                    }
                    icToggleDetails.setImageResource(moreDetailsCaret)
                }

            }
        }
        val args: MovieDetailsFragmentArgs by navArgs()
        viewModel.getMovieDetails(args.imdbId)
    }
}