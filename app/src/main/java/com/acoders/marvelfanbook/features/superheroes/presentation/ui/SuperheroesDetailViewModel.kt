package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.comics.domain.usecase.GetSuperheroComicsUseCase
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSkeletonView
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetailsUseCase
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSuperheroDetailsUseCase: GetSuperheroDetailsUseCase,
    private val getSuperheroComicsUseCase: GetSuperheroComicsUseCase
) : ViewModel() {

    val heroId: Long =
        SuperheroesDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).heroId.toLong()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadSuperheroComics()
    }

    private fun loadSuperheroComics() {
        viewModelScope.launch {
            getSuperheroComicsUseCase(heroId).fold(
                {
                    handleFailure(it)
                }, { comics ->
                    _uiState.update { it.copy(comics = comics) }
                }
            )
        }
    }

    fun loadSuperheroDetail() {

        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            getSuperheroDetailsUseCase(heroId).catch {
                handleFailure(it.toFailure())
            }.collect {
                handleSuperHeroSuccess(it)
            }
        }
    }

    private fun handleSuperHeroSuccess(superhero: SuperheroView) {
        _uiState.update {
            it.copy(
                superheroView = superhero, loading = false
            )
        }
    }

    private fun handleFailure(failure: Failure) = _uiState.update { it.copy(error = failure) }

    data class UiState(
        val superheroView: SuperheroView? = null,
        val comics: List<DelegateAdapterItem> = ComicSkeletonView.emptySkeleton,
        val loading: Boolean = false,
        val error: Failure? = null,
    )
}
