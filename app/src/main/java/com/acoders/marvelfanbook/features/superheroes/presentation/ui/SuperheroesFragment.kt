package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.acoders.marvelfanbook.core.extensions.gone
import com.acoders.marvelfanbook.core.extensions.launchAndCollect
import com.acoders.marvelfanbook.core.extensions.visible
import com.acoders.marvelfanbook.core.platform.delegateadapter.CompositeAdapter
import com.acoders.marvelfanbook.databinding.SuperheroesFragmentBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters.SuperHeroViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuperheroesFragment : Fragment() {

    private val viewModel: SuperheroesViewModel by viewModels()

    private var _binding: SuperheroesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var superHeroesState: SuperHeroesState
    private val adapter: CompositeAdapter = CompositeAdapter.Builder()
        .add(SuperHeroViewAdapter { superHeroesState.onSuperHeroClicked(it.toDomainModel()) })
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SuperheroesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        superHeroesState = SuperHeroesState(findNavController())

        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            recyclerview.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.uiState) {
            showLoading(it.loading)
            showError(it.error != null)
            bindSuperHeroesList(it.dataList)
        }

        viewModel.fetchSuperHeroes()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(show: Boolean) {
        if (show) binding.loadingPb.visible()  else binding.loadingPb.gone()
    }


    private fun showError(show: Boolean) {
        if (show) binding.errorTv.visible() else binding.errorTv.gone()
    }

    private fun bindSuperHeroesList(dataList: List<SuperheroView>) =
        adapter.submitList(dataList) { binding.recyclerview.scheduleLayoutAnimation() }
}
