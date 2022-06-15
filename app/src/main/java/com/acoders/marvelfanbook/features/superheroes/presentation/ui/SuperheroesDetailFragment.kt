package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.acoders.marvelfanbook.R
import com.acoders.marvelfanbook.core.extensions.diff
import com.acoders.marvelfanbook.core.extensions.load
import com.acoders.marvelfanbook.core.extensions.setupStatusBarColor
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import com.acoders.marvelfanbook.databinding.FragmentSuperheroesDetailBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters.CharacterDescriptionAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuperheroesDetailFragment : Fragment() {

    @Inject
    lateinit var recyclerAdapter: RecycleViewDelegateAdapter

    private val viewModel: SuperheroesDetailViewModel by viewModels()

    private var _binding: FragmentSuperheroesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperheroesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar()
        setRecycleViewAdapter()
        updateUI()
        viewModel.loadSuperheroDetail()
    }

    private fun setStatusBar() {
        requireActivity().setupStatusBarColor(R.color.black_trans_40)
    }

    private fun setRecycleViewAdapter() {
        recyclerAdapter.add(
            CharacterDescriptionAdapter()
        )

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = recyclerAdapter
        }
    }

    private fun updateUI() {
        with(viewModel.uiState) {

            diff(viewLifecycleOwner, { it.loading }) {
                showLoading(it)
            }

            diff(viewLifecycleOwner, { it.superheroView }) {
                bindToolbar(it)
            }

            diff(viewLifecycleOwner, { it.dataList }) {
                recyclerAdapter.submitList(it)
            }

            diff(viewLifecycleOwner, { it.error }) {
                showError(it != null)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) binding.loadingPb.visibility = View.VISIBLE else binding.loadingPb.visibility =
            View.GONE
    }

    private fun bindToolbar(superheroView: SuperheroView?) {
        binding.apply {
            collapsingToolbar.title = superheroView?.name.orEmpty()
            heroIv.load(superheroView?.thumbnail?.getUri().orEmpty())
        }
    }

    private fun showError(show: Boolean) {
        //TODO SNACKA BAR if (show) binding.errorTv.visible() else binding.errorTv.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}