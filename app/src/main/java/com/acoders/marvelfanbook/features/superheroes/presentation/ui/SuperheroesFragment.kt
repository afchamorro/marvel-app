package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.acoders.marvelfanbook.core.extensions.diff
import com.acoders.marvelfanbook.core.extensions.gone
import com.acoders.marvelfanbook.core.extensions.visible
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import com.acoders.marvelfanbook.databinding.SuperheroesFragmentBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters.SuperHeroViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SuperheroesFragment : Fragment() {

    @Inject
    lateinit var adapter: RecycleViewDelegateAdapter

    private val viewModel: SuperheroesViewModel by viewModels()

    private var _binding: SuperheroesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var superHeroesState: SuperHeroesState

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
        setRecyclerViewAdapter()
        viewModel.fetchSuperHeroes()
        updateUI()
    }

    private fun setRecyclerViewAdapter() {
        binding.apply {
            recyclerview.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

            adapter.add(
                SuperHeroViewAdapter { superHeroesState.onSuperHeroClicked(it.toDomainModel()) }
            )
            recyclerview.adapter = adapter
        }
    }

    private fun updateUI() {
        with(viewModel.uiState) {

            diff(viewLifecycleOwner, { it.loading }) {
                showLoading(it)
            }

            diff(viewLifecycleOwner, { it.dataList }) {
                bindSuperHeroesList(it)
            }

            diff(viewLifecycleOwner, { it.error }) {
                showError(it != null)
            }
        }
    }

    private fun bindSuperHeroesList(dataList: List<SuperheroView>) =
        adapter.submitList(dataList) { binding.recyclerview.scheduleLayoutAnimation() }

    private fun showLoading(show: Boolean) {
        if (show) binding.loadingPb.visibility = View.VISIBLE else binding.loadingPb.visibility =
            View.GONE
    }

    private fun showError(show: Boolean) {
        if (show) binding.errorTv.visible() else binding.errorTv.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
