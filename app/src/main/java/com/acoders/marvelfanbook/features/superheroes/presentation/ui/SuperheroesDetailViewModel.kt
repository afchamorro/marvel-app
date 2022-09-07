package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.comics.domain.caseuse.GetSuperheroComicsUseCase
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSkeletonView
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
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

    private val heroId: Long =
        SuperheroesDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).heroId.toLong()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSuperheroComicsUseCase(heroId).fold({
                handleFailure(it)
            }, { comics ->
                _uiState.update {
                    val comicsList: List<DelegateAdapterItem> =
                        comics.map { comic -> comic.toPresentationModel() }
                    it.copy(comics = comicsList)
                }
            })
        }
    }

    fun loadSuperheroDetail() {
        viewModelScope.launch {
            getSuperheroDetailsUseCase(heroId).catch {
                handleFailure(it.toFailure())
            }.collect {
                handleSuperHeroSuccess(it)
            }
        }
    }

    private fun handleSuperHeroSuccess(superhero: Superhero) {
        _uiState.update {
            it.copy(
                superheroView = superhero.toPresentationModel()
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
