package com.acoders.marvelfanbook.features.superheroes.presentation.model

import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem

data class DescriptionView(
    val description: String
) : DelegateAdapterItem {
    override fun id() = 1

    override fun content() = description
}
