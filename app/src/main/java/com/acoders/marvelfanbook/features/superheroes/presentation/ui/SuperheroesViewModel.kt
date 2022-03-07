package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoders.marvelfanbook.features.superheroes.domain.SuperheroesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperheroesViewModel @Inject constructor(private val superheroesRepository: SuperheroesRepository) : ViewModel() {

    fun testAPIService(){
        viewModelScope.launch{
            superheroesRepository.superHeroesList()
        }
    }
}