package com.acoders.marvelfanbook.features.comics.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acoders.marvelfanbook.R
import com.acoders.marvelfanbook.core.extensions.prepare
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapter
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.databinding.ComicCardBinding
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicView

class ComicViewAdapter :
    DelegateAdapter<ComicView, ComicViewAdapter.ComicViewHolder>(ComicView::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ComicViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.comic_card,
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        model: ComicView,
        viewHolder: ComicViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    class ComicViewHolder constructor(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ComicCardBinding.bind(view)

        fun bind(item: ComicView) {
            binding.apply {
                comicIv.prepare().placeholder(R.drawable.no_photo).load(item.thumbnail.getUri())
                comicTitleTv.text = item.title
            }
        }
    }
}