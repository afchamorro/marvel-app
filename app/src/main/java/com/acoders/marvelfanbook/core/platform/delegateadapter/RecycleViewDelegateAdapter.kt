package com.acoders.marvelfanbook.core.platform.delegateadapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class RecycleViewDelegateAdapter :
    ListAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>(DelegateAdapterItemDiffCallback()) {

    var reachLimitsListener: ReachLimitsListener? = null

    var limitItemsPage: Int = DEFAULT_LIMIT

    private var _positionOfLastItem: Int = -1

    private var _lastPage: Int = 1
    val lastPage: Int
        get() = _lastPage

    private lateinit var delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>>

    fun add(delegateAdapter: DelegateAdapter<out DelegateAdapterItem, *>) {
        delegates.put(
            delegates.size(),
            delegateAdapter as DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>
        )
    }

    override fun getItemViewType(position: Int): Int {
        for (i in 0 until delegates.size()) {
            if (delegates[i].modelClass == getItem(position).javaClass && delegates[i].isFoViewType(
                    getItem(position)
                )
            ) {
                return delegates.keyAt(i)
            }
        }
        throw NullPointerException("Can not get viewType for position $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].createViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        onBindViewHolder(holder, position, mutableListOf())

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val delegateAdapter = delegates[getItemViewType(position)]

        if (delegateAdapter != null) {
            val delegatePayloads = payloads.map { it as DelegateAdapterItem.Payloadable }
            delegateAdapter.bindViewHolder(getItem(position), holder, delegatePayloads)
        } else {
            throw NullPointerException("can not find adapter for position $position")
        }

        if (hasScrollPagination()) {
            handlePagination(position)
        }
    }

    private fun handlePagination(position: Int) {
        if (checkEndReaching(position) && checkIsRemainItems()) {
            if (_positionOfLastItem != position) {
                _positionOfLastItem = position
                reachLimitsListener!!.onLastItem(this, position, ++_lastPage)
            }
        }
    }

    private fun hasScrollPagination(): Boolean {
        return reachLimitsListener != null
    }

    private fun checkEndReaching(position: Int): Boolean {
        return itemCount - ELEMENTS_PAGE_MARGIN_BOTTOM == position
    }

    private fun checkIsRemainItems(): Boolean {
        return limitItemsPage > 1
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewAttachedToWindow(holder)
        super.onViewAttachedToWindow(holder)
    }

    companion object {
        private const val DEFAULT_LIMIT = 30
        private const val ELEMENTS_PAGE_MARGIN_BOTTOM = 1
    }
}