package com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.acoders.marvelfanbook.R
import com.acoders.marvelfanbook.core.extensions.load
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapter
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.databinding.SuperheroCardItemBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView

class SuperHeroViewAdapter(private val superHeroCallback: (View,View,SuperheroView) -> Unit) :
    DelegateAdapter<SuperheroView, SuperHeroViewAdapter.SuperHeroViewHolder>(SuperheroView::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SuperHeroViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.superhero_card_item,
                parent,
                false
            ),
            superHeroCallback
        )
    }

    override fun bindViewHolder(
        model: SuperheroView,
        viewHolder: SuperHeroViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    class SuperHeroViewHolder constructor(
        view: View,
        private val superHeroCallback: (View,View,SuperheroView) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding = SuperheroCardItemBinding.bind(view)

        fun bind(item: SuperheroView) {
            ViewCompat.setTransitionName(binding.heroePictureIv, "hero_image_${item.id}")
            ViewCompat.setTransitionName(binding.heroeNameTv, "hero_title_${item.id}")
            binding.apply {
                heroePictureIv.load(item.thumbnail.getUri())
                heroeNameTv.text = item.name
                card.setOnClickListener { superHeroCallback(heroeNameTv,heroePictureIv,item) }
            }
        }
    }
}