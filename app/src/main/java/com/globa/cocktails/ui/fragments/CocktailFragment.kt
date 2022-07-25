package com.globa.cocktails.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globa.cocktails.R
import com.globa.cocktails.adapters.IngredientsAdapter
import com.globa.cocktails.databinding.CocktailFragmentBinding
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.viewmodels.CocktailViewModel

class CocktailFragment(val cocktail: Cocktail) : Fragment() {

    companion object;
    lateinit var binding: CocktailFragmentBinding
    private var adapter : IngredientsAdapter? = null
    private lateinit var recyclerView : RecyclerView

    private val viewModel by lazy {
        ViewModelProvider(this, CocktailViewModel.Factory(cocktail))[CocktailViewModel::class.java]
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
        viewModel.cocktail.observe(viewLifecycleOwner) { cocktail ->
            binding.cocktail = cocktail
        }

        adapter = IngredientsAdapter()
        recyclerView = view.findViewById(R.id.cocktailIngredientsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        val map = cocktail.ingredients.zip(cocktail.measures).toMap()
        adapter!!.map = map
    }

}