package com.acoders.marvelfanbook.features.superheroes.presentation.ui
import androidx.navigation.NavController
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero


class SuperHeroesState(private val navigation: NavController) {

    fun onSuperHeroClicked(hero: Superhero){
        navigation.navigate(SuperheroesFragmentDirections.actionHeroesToDetail(hero.id.toInt()))
    }
}