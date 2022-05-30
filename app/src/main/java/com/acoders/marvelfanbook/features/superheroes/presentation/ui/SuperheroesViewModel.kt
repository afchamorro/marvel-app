package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroes
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.PrepareSuperHeroList
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesViewModel @Inject constructor(
    private val getSuperheroes: GetSuperheroes,
    private val prepareSuperHeroList: PrepareSuperHeroList
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun loadSuperheroes() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            getSuperheroes().collect {
                it.fold(
                    {
                        _uiState.update { state -> state.copy(loading = false, error = it) }
                    },
                    {
                        _uiState.update { state -> state.copy(loading = false, dataList = prepareSuperHeroList(it)) }
                    }
                )
            }
        }
    }


    data class UiState(
        val loading: Boolean = false,
        val error: Failure? = null,
        val dataList: List<SuperheroView> = arrayListOf()
    )
}
