package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.acoders.marvelfanbook.databinding.SuperheroesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperheroesDetailFragment : Fragment() {

    private val viewModel: SuperheroesDetailFragmentViewModel by viewModels()

    private var _binding: SuperheroesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SuperheroesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSuperheroDetail()
    }

    companion object {

        private const val MOVIE_ID = "movieId"

        @JvmStatic
        fun newInstance(movieId: Int) = SuperheroesDetailFragment().apply {
            arguments = bundleOf(
                MOVIE_ID to movieId
            )
        }
    }
}