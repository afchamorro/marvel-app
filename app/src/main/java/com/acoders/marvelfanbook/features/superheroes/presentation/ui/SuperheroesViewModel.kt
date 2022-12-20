package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import com.acoders.marvelfanbook.core.platform.NetworkConnectivityManager
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.FetchHeroesListUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetAttributionLinkUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroesUseCase
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesViewModel @Inject constructor(
    private val fetchHeroesListUseCase: FetchHeroesListUseCase,
    private val getSuperheroesUseCase: GetSuperheroesUseCase,
    private val getAttributionLinkUseCase: GetAttributionLinkUseCase,
    private val networkConnectivityManager: NetworkConnectivityManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        collectNetworkState()
        collectSuperHeroes()
        fetchSuperheroes()
    }

    private fun collectNetworkState() {
        viewModelScope.launch {
            networkConnectivityManager.hasConnection.collect { hasConnection ->
                _uiState.update { it.copy(networkAvailable = hasConnection) }
            }
        }
    }

    private fun collectSuperHeroes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            getSuperheroesUseCase()
                .catch { cause ->
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = cause.toFailure()
                        )
                    }
                }
                .collect { flowData ->
                    _uiState.update {
                        it.copy(
                            loading = flowData.isEmpty(),
                            dataList = flowData
                        )
                    }
                }
        }
    }

    private fun fetchSuperheroes() {
        viewModelScope.launch {
            fetchHeroesListUseCase()
        }
    }

    fun getAttributionLink() {
        viewModelScope.launch {
            getAttributionLinkUseCase().catch { cause -> _uiState.update { it.copy(error = cause.toFailure()) } }
                .collect { link -> _uiState.update { it.copy(attributionLink = link) } }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val error: Failure? = null,
        val dataList: List<SuperheroView> = arrayListOf(),
        val attributionLink: String = "",
        val networkAvailable: Boolean = true
    )
}
