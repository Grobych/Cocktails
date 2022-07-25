package com.globa.cocktails.di

import com.globa.cocktails.datalayer.database.CocktailDatabase
import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.database.DatabaseModule
import com.globa.cocktails.datalayer.network.CocktailNetworkDataSource
import com.globa.cocktails.datalayer.network.CocktailNetworkService
import com.globa.cocktails.datalayer.network.NetworkModule
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.domain.RandomCocktailUseCase
import com.globa.cocktails.ui.fragments.CocktailListFragment
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DispatcherModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        AppModule::class
    ]
)
interface ApplicationComponent{
    fun cocktailRepository() : CocktailRepository
    fun cocktailNetworkDataSource() : CocktailNetworkDataSource
    fun cocktailLocalDataSource() : CocktailLocalDataSource
    fun cocktailNetworkService() : CocktailNetworkService
    fun cocktailDatabase() : CocktailDatabase
    fun cocktailListViewModel() : CocktailListViewModel
    fun randomCocktailUseCase() : RandomCocktailUseCase
    fun filterCocktailsUseCase() : FilterCocktailsUseCase

    fun inject(fragment : CocktailListFragment)
}