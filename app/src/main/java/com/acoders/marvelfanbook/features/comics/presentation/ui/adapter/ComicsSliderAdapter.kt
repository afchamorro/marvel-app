package com.acoders.marvelfanbook.features.comics.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapter
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import com.acoders.marvelfanbook.databinding.ComicsSliderBinding
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSetView

class ComicsSliderAdapter : DelegateAdapter<ComicSetView, ComicsSliderAdapter.ViewHolder>(
    ComicSetView::class.java
) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            ComicsSliderBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )

    override fun bindViewHolder(
        model: ComicSetView,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: ComicsSliderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ComicSetView) {

            val recycleViewAdapter =
                RecycleViewDelegateAdapter().apply {
                    add(ComicViewAdapter())
                    add(ComicSkeletonViewAdapter())
                }

            val layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            binding.apply {
                recycleViewAdapter.let { compositeAdapter ->
                    dataListRv.layoutManager = layoutManager
                    dataListRv.adapter = compositeAdapter
                    dataListRv.itemAnimator = null

                    val dataList =
                        mutableListOf<DelegateAdapterItem>().apply { addAll(item.comics) }
                    compositeAdapter.submitList(dataList)
                }
            }
        }
    }
}