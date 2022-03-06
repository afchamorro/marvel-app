package com.acoders.marvelfanbook.data.remote.schemes.common

data class Wrapper<A>(val code: Int, val attributionText: String, val data: A)

data class Paginated<A>(val results: List<A>)

typealias PaginatedWrapper<A> = Wrapper<Paginated<A>>
