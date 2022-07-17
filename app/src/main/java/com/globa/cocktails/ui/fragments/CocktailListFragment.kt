package com.globa.cocktails.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globa.cocktails.R
import com.globa.cocktails.adapters.CocktailsAdapter
import com.globa.cocktails.databinding.CocktailListFragmentBinding
import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.database.getDatabase
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.CocktailFilter
import com.globa.cocktails.datalayer.network.CocktailNetworkDataSource
import com.globa.cocktails.datalayer.network.CocktailNetworkService
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CocktailListFragment : Fragment(), CocktailsAdapter.ItemClicked, AdapterView.OnItemSelectedListener {

    private lateinit var cocktailListRecyclerView : RecyclerView
    private var adapter : CocktailsAdapter? = null
    private lateinit var binding : CocktailListFragmentBinding
    var openFragment : OpenFragment? = null
    private val filter = CocktailFilter()

    companion object {
        fun newInstance() = CocktailListFragment()
    }

    private val viewModel by lazy {
        val cocktailNetworkDataSource = CocktailNetworkDataSource(Dispatchers.IO,CocktailNetworkService)
        val cocktailLocalDataSource = CocktailLocalDataSource(getDatabase(requireContext()),Dispatchers.IO)
        val repository = CocktailRepository(cocktailLocalDataSource,cocktailNetworkDataSource)
        val filterCocktailsUseCase = FilterCocktailsUseCase(repository,Dispatchers.IO)
        ViewModelProvider(this, CocktailListViewModel.Factory(filterCocktailsUseCase))[CocktailListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CocktailListFragmentBinding.inflate(inflater,container,false)
        binding.filterButton.setOnClickListener {
            if (binding.filtersLayout.visibility == GONE)
                binding.filtersLayout.visibility = VISIBLE
            else binding.filtersLayout.visibility = GONE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCocktails(CocktailFilter())
        adapter = CocktailsAdapter()
        adapter?.clickInterface = this
        cocktailListRecyclerView = view.findViewById(R.id.cocktailListRecyclerView)
        cocktailListRecyclerView.layoutManager = LinearLayoutManager(context)
        cocktailListRecyclerView.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                    binding.cocktailListLoadingImageView.isVisible = it.isLoading
                    adapter?.list = it.cocktailList
                }
            }
        }

        binding.searchEditText.addTextChangedListener {
            filter.name = it.toString()
            viewModel.loadCocktails(filter)
        }
        binding.typeOfCocktailSpinner.onItemSelectedListener = this
        binding.hasAlcoholSpinner.onItemSelectedListener = this
    }

    override fun clicked(cocktail: Cocktail) {
        openFragment?.open(cocktail)
    }

    interface OpenFragment{
        fun open(cocktail: Cocktail)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent?.id){
            R.id.typeOfCocktailSpinner -> filter.type = parent.selectedItem.toString()
            R.id.hasAlcoholSpinner -> filter.hasAlcohol = parent.selectedItem.toString()
        }
        viewModel.loadCocktails(filter)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}