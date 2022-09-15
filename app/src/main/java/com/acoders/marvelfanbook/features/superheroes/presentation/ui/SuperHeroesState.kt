package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.content.res.Resources
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero

class SuperHeroesState(private val navigation: NavController, private val resources: Resources) {

    fun onSuperHeroClicked(titleView: View, imageView: View, hero: Superhero) {
        navigation.navigate(
            SuperheroesFragmentDirections.actionHeroesToDetail(hero.id.toInt()),
            FragmentNavigatorExtras(
                imageView to "hero_image_${hero.id}",
                titleView to "hero_title_${hero.id}"
            )

        )
    }
}
