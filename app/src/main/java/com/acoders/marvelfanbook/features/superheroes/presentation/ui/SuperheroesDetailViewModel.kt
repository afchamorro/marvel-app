package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperHeroDetailAndRelatedUseCase
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
    private val getSuperHeroDetailAndRelatedUseCase: GetSuperHeroDetailAndRelatedUseCase
) : ViewModel() {

    private val heroId: Long =
        SuperheroesDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).heroId.toLong()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSuperHeroDetailAndRelatedUseCase(heroId)
                .catch { cause -> handleFailure(cause.toFailure()) }
                .collect { data -> handleSuccess(data) }
        }
    }

    fun loadSuperheroDetail() {
        viewModelScope.launch {
            viewModelScope.launch {
                getSuperheroDetailsUseCase(heroId).catch {
                    handleFailure(it.toFailure())
                }.collect {
                    handleSuperHeroSuccess(it)
                }
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

    private fun handleSuccess(dataList: List<DelegateAdapterItem>) =
        _uiState.update { it.copy(dataList = dataList) }

    data class UiState(
        val superheroView: SuperheroView? = null,
        val dataList: List<DelegateAdapterItem> = arrayListOf(),
        val loading: Boolean = false,
        val error: Failure? = null,
    )
}
