package com.acoders.marvelfanbook.features.comics.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapter
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.databinding.ComicCardSkeletonBinding
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSkeletonView

class ComicSkeletonViewAdapter :
    DelegateAdapter<ComicSkeletonView, ComicSkeletonViewAdapter.ViewHolder>(ComicSkeletonView::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            ComicCardSkeletonBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )

    override fun bindViewHolder(
        model: ComicSkeletonView,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: ComicCardSkeletonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ComicSkeletonView) {
            binding.apply {
                shimmerLayout.startShimmer()
            }
        }
    }
}