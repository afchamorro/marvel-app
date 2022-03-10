package com.acoders.marvelfanbook.core.platform.delegateadapter

import androidx.recyclerview.widget.RecyclerView

interface ReachLimitsListener {
    fun onLastItem(adapter: RecyclerView.Adapter<*>?, position: Int, page:Int)
}