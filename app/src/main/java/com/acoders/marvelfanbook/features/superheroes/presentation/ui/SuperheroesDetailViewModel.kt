package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.comics.domain.caseuse.GetSuperheroComics
import com.acoders.marvelfanbook.features.comics.domain.model.Comic
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSetView
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSetView.Companion.emptySkeleton
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetailsUseCase
import com.acoders.marvelfanbook.features.superheroes.presentation.model.DescriptionView
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSuperheroDetailsUseCase: GetSuperheroDetailsUseCase,
    private val getSuperheroComics: GetSuperheroComics
) : ViewModel() {

    private val heroId: Long =
        SuperheroesDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).heroId.toLong()

    private var descriptionView: DescriptionView = DescriptionView("")
    private var comicSetView: ComicSetView = emptySkeleton

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadSuperheroDetail() {
        viewModelScope.launch {
            viewModelScope.launch {
                getSuperheroDetailsUseCase(heroId).catch {
                    handleFailure(it.toFailure())
                }.collect {
                    handleSuccess(it)
                }
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        _uiState.update { it.copy(error = failure) }
    }

    private fun handleSuccess(superhero: Superhero) {
        descriptionView = superhero.toDescriptionView()
        _uiState.update {
            it.copy(
                superheroView = superhero.toPresentationModel(),
                dataList = getDataForList()
            )
        }
    }

    fun loadSuperheroComics() {
        viewModelScope.launch {
            viewModelScope.launch {
                getSuperheroComics(heroId).fold({ handleFailure(it) }, { handleComicsSuccess(it) })
            }
        }
    }

    private fun handleComicsSuccess(comics: List<Comic>) {
        comicSetView = ComicSetView(comics.map { it.toPresentationModel() })
        _uiState.update {
            it.copy(
                dataList = getDataForList()
            )
        }
    }

    private fun getDataForList() = arrayListOf(descriptionView, comicSetView)

    data class UiState(
        val superheroView: SuperheroView? = null,
        val dataList: List<DelegateAdapterItem> = arrayListOf(),
        val loading: Boolean = false,
        val error: Failure? = null,
    )
}

