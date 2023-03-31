package com.globa.cocktails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.globa.cocktails.App
import com.globa.cocktails.R
import com.globa.cocktails.ui.fragments.CocktailFragment
import com.globa.cocktails.ui.fragments.CocktailListFragment
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.CocktailListScreen
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CocktailListFragment.OpenFragment {

    private lateinit var cocktailListFragment : CocktailListFragment
    private lateinit var cocktailFragment: CocktailFragment

    @Inject
    lateinit var cocktailListViewModel: CocktailListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                CocktailListScreen(viewModel = cocktailListViewModel)
            }
        }


//        setContentView(R.layout.activity_main)

//        if (!::cocktailListFragment.isInitialized){
//            cocktailListFragment = CocktailListFragment()
//            cocktailListFragment.openFragment = this
//        }
//        supportFragmentManager.beginTransaction()
//            .add(R.id.mainFragmentContainer,cocktailListFragment)
//            .setReorderingAllowed(true)
//            .commit()
    }

    override fun open(cocktail: Cocktail) {
        cocktailFragment = CocktailFragment(cocktail)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer,cocktailFragment)
            .addToBackStack("OPEN")
            .commit()
    }
}