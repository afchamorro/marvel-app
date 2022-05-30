package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val superHeroesState = SuperHeroesState()
    private val adapter: CompositeAdapter = CompositeAdapter.Builder().add(
        SuperHeroViewAdapter { superHeroesState.onSuperHeroClicked(it.toDomainModel()) }
    ).build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SuperheroesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerview.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.uiState) {
            showLoading(it.loading)
            showError(it.error != null)
            bindSuperHeroesList(it.dataList)
        }

        loadSuperheroes()
    }

    private fun loadSuperheroes() {
        viewModel.loadSuperheroes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(show: Boolean) = binding.apply { if (show) loadingPb.visible() else loadingPb.gone() }

    // TODO ( Mostrar mensaje de error )
    private fun showError(show: Boolean) = binding.apply { if (show) errorTv.visible() else errorTv.gone() }

    private fun bindSuperHeroesList(dataList: List<SuperheroView>) = adapter.submitList(dataList)


    companion object {
        fun newInstance() = SuperheroesFragment()
    }
}
