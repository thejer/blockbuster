package com.example.blockbuster.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blockbuster.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesListAdapter = MoviesListAdapter {

            // open detail
        }
        binding.moviesRecyclerview.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = moviesListAdapter
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            moviesListAdapter.submitList(it.movies)
        }
        binding.searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                val query = binding.searchView.text.toString()
                viewModel.searchMovie(query)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.searchView.doOnTextChanged { text, _, _, _ ->
            if ((text?.length ?: 0) >= 2) {
                viewModel.searchMovie(text.toString())
            }
        }
    }
}