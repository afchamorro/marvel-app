package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetails
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesDetailFragmentViewModel @Inject constructor(
    private val getSuperheroDetails: GetSuperheroDetails
) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    //TODO
    private var movieId: Long = 0
    init {
        movieId = 1009368
    }

    fun loadSuperheroDetail() {
        viewModelScope.launch {
            viewModelScope.launch {
                getSuperheroDetails(GetSuperheroDetails.Params(movieId)) {
                    it.fold(::handleFailure, ::handleSuccess)
                }
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        _state.value = state.value.copy(failure = failure)
    }

    private fun handleSuccess(superhero: Superhero){
        _state.value = state.value.copy(superheroView = superhero.toPresentationModel())
    }

    data class UiState(
        val superheroView: SuperheroView? = null,
        val failure: Failure? = null
    )

}

