package com.acoders.marvelfanbook.features.superheroes.data.datasource

import kotlinx.coroutines.flow.Flow

interface AttributionInfoLocalDataSource {

    fun getAttributionLink(): Flow<String>

    suspend fun saveAttributionLink(link: String)
}