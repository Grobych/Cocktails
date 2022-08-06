package com.globa.cocktails.di

import com.globa.cocktails.di.modules.DatabaseModule
import com.globa.cocktails.di.modules.NetworkModule
import com.globa.cocktails.di.modules.AppModule
import com.globa.cocktails.di.modules.DispatcherModule
import com.globa.cocktails.ui.fragments.CocktailListFragment
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
    fun inject(fragment : CocktailListFragment)
}