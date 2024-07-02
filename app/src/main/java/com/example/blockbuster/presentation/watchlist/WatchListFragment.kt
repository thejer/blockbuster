package com.example.blockbuster.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blockbuster.R
import com.example.blockbuster.databinding.FragmentWatchListBinding
import com.example.blockbuster.presentation.search.MoviesListAdapter
import com.example.blockbuster.presentation.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : Fragment() {

    private val viewModel: WatchListViewModel by viewModels()
    private lateinit var binding: FragmentWatchListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesListAdapter = MoviesListAdapter { imdbId ->
            findNavController().navigate(SearchFragmentDirections.actionSearchToMovieDetails(imdbId))
        }
        binding.watchlistRecyclerview.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = moviesListAdapter
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            moviesListAdapter.submitList(it.movies)
            binding.watchlistTitle.text = if (it.searchQuery.isNotEmpty()) {
                getString(R.string.showing_results_for, it.searchQuery)
            } else {
                getString(R.string.browse)
            }
        }

        binding.watchlistSearchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                val query = binding.watchlistSearchView.text.toString()
                viewModel.searchMovie(query)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.watchlistSearchView.doOnTextChanged { text, _, _, _ ->
            viewModel.updateQuery(text?.toString() ?: "")
            if ((text?.length ?: 0) >= 2) {
                viewModel.searchMovie(text.toString())
            } else {
                viewModel.getAllSavedMovies()
            }
        }
    }
}