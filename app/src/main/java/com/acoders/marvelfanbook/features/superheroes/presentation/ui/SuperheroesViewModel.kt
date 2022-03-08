package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.interactor.UseCase
import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesViewModel @Inject constructor(private val getSuperheroes: GetSuperheroes) : ViewModel() {

    //TODO UI STATE

    fun loadSuperheroes(){
        viewModelScope.launch{
            getSuperheroes(UseCase.None()) { it.fold(::handleFailure, ::handleSuccess) }
        }
    }

    private fun handleFailure(failure: Failure) {
        //TODO
    }

    private fun handleSuccess(result: PaginatedWrapper<Superhero>){
        //TODO
    }
}
