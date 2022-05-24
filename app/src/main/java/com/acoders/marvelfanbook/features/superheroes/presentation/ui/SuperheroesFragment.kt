package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.acoders.marvelfanbook.databinding.SuperheroesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperheroesFragment : Fragment() {

    private val viewModel: SuperheroesViewModel by viewModels()

    private var _binding: SuperheroesFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SuperheroesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadSuperheroes() {
        viewModel.loadSuperheroes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SuperheroesFragment()
    }
}
