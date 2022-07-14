package com.globa.cocktails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.globa.cocktails.R
import com.globa.cocktails.fragments.CocktailFragment
import com.globa.cocktails.fragments.CocktailListFragment
import com.globa.cocktails.datalayer.models.Cocktail

class MainActivity : AppCompatActivity(), CocktailListFragment.OpenFragment {

    private lateinit var cocktailListFragment : CocktailListFragment
    private lateinit var cocktailFragment: CocktailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!::cocktailListFragment.isInitialized){
            cocktailListFragment = CocktailListFragment()
            cocktailListFragment.openFragment = this
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer,cocktailListFragment)
            .setReorderingAllowed(true)
            .commit()
    }

    override fun open(cocktail: Cocktail) {
        cocktailFragment = CocktailFragment(cocktail)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer,cocktailFragment)
            .addToBackStack("OPEN")
            .commit()
    }
}