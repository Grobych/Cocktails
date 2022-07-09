package com.globa.cocktails.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globa.cocktails.R
import com.globa.cocktails.adapters.CocktailsAdapter
import com.globa.cocktails.models.Cocktail

class CocktailListFragment : Fragment() {

    private lateinit var cocktailListRecyclerView : RecyclerView
    private var adapter : CocktailsAdapter? = null

    companion object {
        fun newInstance() = CocktailListFragment()
    }

    private val viewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, CocktailListViewModel.Factory(activity.application))[CocktailListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cocktail_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CocktailsAdapter()
        cocktailListRecyclerView = view.findViewById(R.id.cocktailListRecyclerView)
        cocktailListRecyclerView.layoutManager = LinearLayoutManager(context)
        cocktailListRecyclerView.adapter = adapter
        viewModel.cocktails.observe(viewLifecycleOwner) { cocktails ->
            cocktails?.apply { adapter?.list = cocktails }
        }
    }


}