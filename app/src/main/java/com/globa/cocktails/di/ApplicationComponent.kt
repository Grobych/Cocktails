package com.globa.cocktails.di

import android.content.Context
import com.globa.cocktails.di.modules.DatabaseModule
import com.globa.cocktails.di.modules.NetworkModule
import com.globa.cocktails.di.modules.DispatcherModule
import com.globa.cocktails.ui.fragments.CocktailFragment
import com.globa.cocktails.ui.fragments.CocktailListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DispatcherModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent{
    fun inject(fragment: CocktailListFragment)
    fun inject(fragment: CocktailFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }
}