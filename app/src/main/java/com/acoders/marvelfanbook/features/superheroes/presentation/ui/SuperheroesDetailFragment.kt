package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import com.acoders.marvelfanbook.databinding.SuperheroesFragmentBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters.CharacterDescriptionAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuperheroesDetailFragment : Fragment() {

    @Inject
    lateinit var recyclerAdapter: RecycleViewDelegateAdapter

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

    private fun setRecycleViewAdapter() {
        recyclerAdapter.add(
            CharacterDescriptionAdapter()
        )
    }
}