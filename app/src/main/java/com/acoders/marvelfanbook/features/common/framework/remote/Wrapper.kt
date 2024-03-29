package com.acoders.marvelfanbook.features.common.framework.remote

data class Wrapper<A>(
    val code: Int = 0,
    val attributionHTML: String = "",
    val data: A
)

data class Paginated<A>(
    val offset: Int = 0,
    val limit: Int = 0,
    val total: Int = 0,
    val count: Int = 0,
    val results: List<A>
)

typealias PaginatedWrapper<A> = Wrapper<Paginated<A>>
