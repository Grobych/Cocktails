package com.globa.cocktails.ui.fragments

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import com.globa.cocktails.datalayer.network.CocktailNetworkDataSource
import com.globa.cocktails.datalayer.network.CocktailNetworkService
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CocktailListFragment : Fragment(), CocktailsAdapter.ItemClicked {

    private lateinit var cocktailListRecyclerView : RecyclerView
    private var adapter : CocktailsAdapter? = null
    private lateinit var binding : CocktailListFragmentBinding
    var openFragment : OpenFragment? = null

    companion object {
        fun newInstance() = CocktailListFragment()
    }

    private val viewModel by lazy {
        val cocktailNetworkDataSource = CocktailNetworkDataSource(Dispatchers.IO,CocktailNetworkService)
        val cocktailLocalDataSource = CocktailLocalDataSource(getDatabase(requireContext()),Dispatchers.IO)
        val repository = CocktailRepository(cocktailLocalDataSource,cocktailNetworkDataSource)
        ViewModelProvider(this, CocktailListViewModel.Factory(repository))[CocktailListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CocktailListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCocktails()
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
    }

    override fun clicked(cocktail: Cocktail) {
        openFragment?.open(cocktail)
    }

    interface OpenFragment{
        fun open(cocktail: Cocktail)
    }


}