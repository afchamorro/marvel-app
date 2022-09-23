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
    private val getSuperheroesUseCase: GetSuperheroesUseCase,
    private val getAttributionLinkUseCase: GetAttributionLinkUseCase,
    private val fetchHeroesListUseCase: FetchHeroesListUseCase,
    private val networkConnectivityManager: NetworkConnectivityManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    init {
        collectNetworkState()
        collectSuperHeroes()
    }

    private fun collectSuperHeroes() {
        viewModelScope.launch {
            getSuperheroesUseCase()
                .catch { cause -> _uiState.update { it.copy(error = cause.toFailure()) } }
                .collect { flowData -> _uiState.update { it.copy(dataList = flowData) } }
        }
    }

    fun fetchSuperHeroes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val failure = fetchHeroesListUseCase()
            _uiState.value = _uiState.value.copy(loading = false, error = failure)
        }
    }

    fun getAttributionLink() {
        viewModelScope.launch {
            getAttributionLinkUseCase().catch { cause -> _uiState.update { it.copy(error = cause.toFailure()) } }
                .collect { link -> _uiState.update { it.copy(attributionLink = link) } }
        }
    }

    private fun collectNetworkState() {
        viewModelScope.launch {
            networkConnectivityManager.hasConnection.collect { hasConnection ->
                _uiState.update { it.copy(networkAvailable = hasConnection) }
            }
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
