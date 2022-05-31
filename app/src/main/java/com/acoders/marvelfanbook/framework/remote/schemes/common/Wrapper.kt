package com.acoders.marvelfanbook.framework.remote.schemes.common

data class Wrapper<A>(
    val code: Int = 0,
    val attributionText: String = "",
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
