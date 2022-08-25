package com.acoders.marvelfanbook.features.comics.presentation.model

import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem

data class ComicSetView(
    val comics: List<DelegateAdapterItem> = arrayListOf()
) : DelegateAdapterItem {

    override fun id() = comics.size

    override fun content() = ComicSetViewViewContent(comics)

    inner class ComicSetViewViewContent(
        val comics: List<DelegateAdapterItem>
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is ComicSetViewViewContent) {
                var equals = true
                for (i in comics.indices) {
                    equals = equals && comics[i].content() == other.comics[i].content()
                }
            }
            return false
        }

        override fun hashCode(): Int {
            return comics.hashCode()
        }
    }


    companion object {
        val emptySkeleton = ComicSetView(
            comics = arrayListOf(
                ComicSkeletonView(),
                ComicSkeletonView(),
                ComicSkeletonView()
            )
        )
    }
}