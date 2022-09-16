package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.acoders.marvelfanbook.core.extensions.diff
import com.acoders.marvelfanbook.core.extensions.invisible
import com.acoders.marvelfanbook.core.extensions.load
import com.acoders.marvelfanbook.core.extensions.visible
import com.acoders.marvelfanbook.core.platform.AppBarStateChangeListener
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import com.acoders.marvelfanbook.databinding.FragmentSuperheroesDetailBinding
import com.acoders.marvelfanbook.features.comics.presentation.ui.adapter.ComicSkeletonViewAdapter
import com.acoders.marvelfanbook.features.comics.presentation.ui.adapter.ComicViewAdapter
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuperheroesDetailFragment : Fragment() {

    @Inject
    lateinit var recyclerAdapter: RecycleViewDelegateAdapter

    private val viewModel: SuperheroesDetailViewModel by viewModels()

    private var _binding: FragmentSuperheroesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindSharedElementsTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperheroesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeroTransitionName()
        setupNavigationUp()
        setRecycleViewAdapter()
        updateUI()
        viewModel.apply {
            loadSuperheroDetail()
        }
    }

    private fun setHeroTransitionName() {
        ViewCompat.setTransitionName(binding.heroIv, "hero_image_${viewModel.heroId}")
        ViewCompat.setTransitionName(binding.tvHeroName, "hero_title_${viewModel.heroId}")
        ViewCompat.setTransitionName(binding.ivShadow, "hero_shadow_${viewModel.heroId}")
    }

    private fun bindSharedElementsTransition() {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    private fun setupNavigationUp() {
        binding.heroToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecycleViewAdapter() {
        recyclerAdapter.apply {
            add(ComicViewAdapter())
            add(ComicSkeletonViewAdapter())
        }

        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
                bindDescription(it)
            }

            diff(viewLifecycleOwner, { it.comics }) {
                bindComics(it)
            }

            diff(viewLifecycleOwner, { it.error }) {
                showError(it != null)
            }
        }
    }

    private fun bindComics(comics: List<DelegateAdapterItem>) {
        binding.comicSectionTv.visible()
        recyclerAdapter.submitList(comics)
    }

    private fun showLoading(show: Boolean) {
        if (show) binding.loadingPb.visibility = View.VISIBLE else binding.loadingPb.visibility =
            View.GONE
    }

    private fun bindToolbar(superheroView: SuperheroView?) {
        binding.apply {
//            collapsingToolbar.title = superheroView?.name.orEmpty() // TODO problemas con la animación de transición
            heroIv.load(superheroView?.thumbnail?.getUri().orEmpty())
            tvHeroName.text = superheroView?.name.orEmpty()
            appbarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    when (state) {
                        State.COLLAPSED -> {
                            collapsingToolbar.title = superheroView?.name.orEmpty()
                        }
                        State.EXPANDED -> {
                            collapsingToolbar.title = ""
                        }
                        State.IDLE -> {
                            collapsingToolbar.title = ""
                        }
                    }
                }
            }
            )
        }
    }

    private fun bindDescription(superheroView: SuperheroView?) {
        binding.apply {
            descriptionTv.text = superheroView?.description.orEmpty()
        }
    }

    private fun showError(show: Boolean) {
        // TODO SNACKA BAR if (show) binding.errorTv.visible() else binding.errorTv.gone()
    }
}
