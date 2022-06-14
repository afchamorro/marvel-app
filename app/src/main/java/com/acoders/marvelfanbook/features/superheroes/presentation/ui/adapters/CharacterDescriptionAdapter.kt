package com.acoders.marvelfanbook.features.superheroes.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapter
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.databinding.DescriptionViewBinding
import com.acoders.marvelfanbook.features.superheroes.presentation.model.DescriptionView

class CharacterDescriptionAdapter :
    DelegateAdapter<DescriptionView, CharacterDescriptionAdapter.ViewHolder>(
        DescriptionView::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DescriptionViewBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )

    override fun bindViewHolder(
        model: DescriptionView,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: DescriptionViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DescriptionView) {
            binding.apply {
                descriptionTv.text = item.description
            }
        }
    }
}