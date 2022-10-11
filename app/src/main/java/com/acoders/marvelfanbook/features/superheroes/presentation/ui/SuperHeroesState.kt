package com.acoders.marvelfanbook.features.superheroes.presentation.ui


import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero

class SuperHeroesState(private val navigation: NavController) {

    fun onSuperHeroClicked(titleView: View, imageView: View, shadowView: View, hero: Superhero) {
        navigation.navigate(
            SuperheroesFragmentDirections.actionHeroesToDetail(hero.id.toInt()),
            FragmentNavigatorExtras(
                titleView to "hero_title_${hero.id}",
                shadowView to "hero_shadow_${hero.id}",
                imageView to "hero_image_${hero.id}"
            )

        )
    }
}
