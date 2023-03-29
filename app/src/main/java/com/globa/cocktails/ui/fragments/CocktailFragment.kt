package com.globa.cocktails.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.globa.cocktails.App
import com.globa.cocktails.adapters.IngredientsAdapter
import com.globa.cocktails.databinding.CocktailFragmentBinding
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.viewmodels.CocktailViewModel
import com.globa.cocktails.ui.viewmodels.CocktailViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CocktailFragment(val cocktail: Cocktail) : Fragment() {

    private lateinit var binding: CocktailFragmentBinding

    @Inject
    lateinit var factory: CocktailViewModelFactory.Factory

    private val viewModel: CocktailViewModel by lazy {
        factory.create(cocktail).create(CocktailViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CocktailFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cocktail
            .onEach {
            binding.cocktail = it
            binding.cocktailIngredientsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = IngredientsAdapter()
                (adapter as IngredientsAdapter).map = it.ingredients.zip(it.measures).toMap()
            }
        }.launchIn(lifecycleScope)
    }

}