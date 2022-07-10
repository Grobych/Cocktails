package com.globa.cocktails.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globa.cocktails.R
import com.globa.cocktails.adapters.CocktailsAdapter
import com.globa.cocktails.models.Cocktail

class CocktailListFragment : Fragment(), CocktailsAdapter.ItemClicked {

    private lateinit var cocktailListRecyclerView : RecyclerView
    private var adapter : CocktailsAdapter? = null
    var openFragment : OpenFragment? = null

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
        adapter?.clickInterface = this
        cocktailListRecyclerView = view.findViewById(R.id.cocktailListRecyclerView)
        cocktailListRecyclerView.layoutManager = LinearLayoutManager(context)
        cocktailListRecyclerView.adapter = adapter
        viewModel.cocktails.observe(viewLifecycleOwner) { cocktails ->
            cocktails?.apply { adapter?.list = cocktails }
        }
        view.findViewById<EditText>(R.id.searchEditText).addTextChangedListener {
            viewModel.setFilter(it.toString())
        }
    }

    override fun clicked(cocktail: Cocktail) {
        openFragment?.open(cocktail)
    }

    interface OpenFragment{
        fun open(cocktail: Cocktail)
    }


}