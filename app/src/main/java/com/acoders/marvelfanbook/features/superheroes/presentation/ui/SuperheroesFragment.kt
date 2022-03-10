package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.acoders.marvelfanbook.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperheroesFragment : Fragment() {

    private val viewModel: SuperheroesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.superheroes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testAPI()
    }

    private fun testAPI() {
        viewModel.loadSuperheroes()
    }

    companion object {
        fun newInstance() = SuperheroesFragment()
    }
}
