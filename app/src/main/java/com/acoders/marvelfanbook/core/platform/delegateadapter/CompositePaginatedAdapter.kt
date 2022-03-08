package com.acoders.marvelfanbook.core.platform.delegateadapter

import android.util.SparseArray
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class CompositePaginatedAdapter(
    delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>>
) : CompositeAdapter(delegates) {

    private var _positionOfLastItem: Int = -1
    val positionOfLastItem: Int
        get() = _positionOfLastItem

    private var _lastPage: Int = 1
    val lastPage: Int
        get() = _lastPage

    var limitItemsPage: Int = DEFAULT_LIMIT

    var reachLimitsListener: ReachLimitsListener? = null

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (hasScrollPagination()) {
            handlePagination(position)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    private fun handlePagination(position: Int) {
        if (checkEndReaching(position)) {
            if (checkIsRemainItems()) {
                if (_positionOfLastItem != position) {
                    _positionOfLastItem = position
                    reachLimitsListener!!.onLastItem(this, position, ++_lastPage)
                }
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

    class Builder {

        private var count: Int = 0
        private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>> =
            SparseArray()

        fun add(delegateAdapter: DelegateAdapter<out DelegateAdapterItem, *>): Builder {
            delegates.put(
                count++,
                delegateAdapter as DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>
            )
            return this
        }

        fun build(): CompositePaginatedAdapter {
            require(count != 0) { "Register at least one adapter" }
            return CompositePaginatedAdapter(delegates)
        }
    }

    override fun addDataToList(data: List<DelegateAdapterItem>?) {
        val cleanCurrentList = currentList.filter {
            it !is LoadingNextPageView
        }
        submitList(cleanCurrentList + data.orEmpty())
    }

    fun addLoadingNextPageViewToList(commitCallback: Runnable? = null) {
        val dataList = currentList + LoadingNextPageView()
        submitList(dataList, commitCallback)
    }

    fun resetPage() {
        _lastPage = 1
        _positionOfLastItem = -1
    }

    fun clearData() {
        submitList(arrayListOf())
    }

    class LoadingNextPageView : DelegateAdapterItem {

        override fun id(): Any = LoadingNextPageView::class.toString()

        override fun content(): Any = Any()
    }

    companion object {
        private const val DEFAULT_LIMIT = 30
        private const val ELEMENTS_PAGE_MARGIN_BOTTOM = 1
    }
}
