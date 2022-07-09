package com.globa.cocktails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.globa.cocktails.R
import com.globa.cocktails.fragments.CocktailListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var cocktailListFragment : CocktailListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!::cocktailListFragment.isInitialized){
            cocktailListFragment = CocktailListFragment()
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer,cocktailListFragment)
            .setReorderingAllowed(true)
            .commit()
    }
}