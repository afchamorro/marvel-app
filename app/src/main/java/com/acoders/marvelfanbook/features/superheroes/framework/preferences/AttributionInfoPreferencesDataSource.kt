package com.acoders.marvelfanbook.features.superheroes.framework.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.acoders.marvelfanbook.features.superheroes.data.datasource.AttributionInfoLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ATTRIBUTION_PREFERENCES = "ATTRIBUTION_PREFERENCES"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ATTRIBUTION_PREFERENCES)


class AttributionInfoPreferencesDataSource @Inject constructor(@ApplicationContext val context: Context) :
    AttributionInfoLocalDataSource {

    override fun getAttributionLink(): Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ATTRIBUTION_LINK].orEmpty()
    }

    override suspend fun saveAttributionLink(link: String) {
        context.dataStore.edit { preferences ->
            preferences[ATTRIBUTION_LINK] = link
        }
    }

    companion object {
        private const val ATTRIBUTION_LINK_LABEL = "ATTRIBUTION_LINK_LABEL"
        private val ATTRIBUTION_LINK = stringPreferencesKey(ATTRIBUTION_LINK_LABEL)
    }

}