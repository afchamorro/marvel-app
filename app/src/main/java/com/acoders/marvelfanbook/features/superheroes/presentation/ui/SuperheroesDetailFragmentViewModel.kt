package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.extensions.logI
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesDetailFragmentViewModel @Inject constructor(
    private val getSuperheroDetails: GetSuperheroDetails
) :
    ViewModel() {

    //TODO
    private var movieId: Long = 0
    init {
        movieId = 1009368
    }

    fun loadSuperheroDetail() {
        viewModelScope.launch {
            viewModelScope.launch {
                getSuperheroDetails(GetSuperheroDetails.Params(movieId)) {
                    it.fold(::handleFailure, ::handleFilesCountSuccess)
                }
            }
        }
    }

    private fun handleFilesCountSuccess(superhero: Superhero) {
        superhero.name.logI()
    }

    private fun handleFailure(failure: Failure) {

    }
}

