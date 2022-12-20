package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.acoders.marvelfanbook.R
import com.acoders.marvelfanbook.core.extensions.diff
import com.acoders.marvelfanbook.core.extensions.gone
import com.acoders.marvelfanbook.core.extensions.htmlSpan
import com.acoders.marvelfanbook.core.extensions.visible
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import com.acoders.marvelfanbook.databinding.FragmentSuperherosBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters.SuperHeroViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperheroesFragment : Fragment() {

    private val viewModel: SuperheroesViewModel by viewModels()

    private val adapter: RecycleViewDelegateAdapter = RecycleViewDelegateAdapter()
    private var _binding: FragmentSuperherosBinding? = null
    private val binding get() = _binding!!

    private lateinit var superHeroesState: SuperHeroesState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        superHeroesState = SuperHeroesState(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuperherosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        updateUI()
        viewModel.apply {
            getAttributionLink()
        }
    }

    private fun setRecyclerViewAdapter() {
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter.add(SuperHeroViewAdapter { titleView, imageView,shadow, hero ->
                superHeroesState.onSuperHeroClicked(
                    titleView,
                    imageView,
                    shadow,
                    hero.toDomainModel()
                )
            })
            recyclerview.adapter = adapter
        }
        binding.recyclerview.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
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

            diff(viewLifecycleOwner, { it.attributionLink }) {
                bindAttributionLink(it)
            }

            diff(viewLifecycleOwner, { it.error }) {
                showError(it != null)
            }
            diff(viewLifecycleOwner, { it.networkAvailable }) { available ->
                if (available) hideNetworkBanner() else showNetworkBanner()
            }
        }
    }

    private fun bindSuperHeroesList(dataList: List<SuperheroView>) =
        adapter.submitList(dataList) { binding.recyclerview.scheduleLayoutAnimation() }

    private fun showLoading(show: Boolean) {
        if (show) binding.loadingPb.visibility = View.VISIBLE else binding.loadingPb.visibility =
            View.GONE
    }

    private fun bindAttributionLink(link: String) {
        binding.attributionTv.apply {
            text = link.htmlSpan()
            movementMethod = LinkMovementMethod()
        }
    }

    private fun showError(show: Boolean) {
        if (show) binding.errorTv.visible() else binding.errorTv.gone()
    }

    private fun showNetworkBanner() {
        binding.bannerLayout.rootBanner.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.slide_bottom_down
            )
        )
        binding.bannerLayout.rootBanner.visible()
    }

    private fun hideNetworkBanner() {
        binding.bannerLayout.rootBanner.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.slide_bottom_up
            )
        )
        binding.bannerLayout.rootBanner.gone()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
