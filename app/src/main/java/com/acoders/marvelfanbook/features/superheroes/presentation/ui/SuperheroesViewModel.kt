package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.FetchHeroesList
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroes
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesViewModel @Inject constructor(
    getSuperheroes: GetSuperheroes,
    private val fetchHeroesList: FetchHeroesList,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSuperheroes()
                .catch { cause -> _uiState.update { it.copy(error = cause.toFailure()) } }
                .collect{  flowData -> _uiState.update { it.copy(dataList = flowData) }}
        }
    }

    fun fetchSuperHeroes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
           val failure = fetchHeroesList()
           _uiState.value = _uiState.value.copy(loading = false, error = failure)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val error: Failure? = null,
        val dataList: List<SuperheroView> = arrayListOf()
    )
}
