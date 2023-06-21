package com.globa.cocktails

import android.app.Application
import com.globa.cocktails.domain.LoadCocktailsUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var loadCocktailsUseCase: LoadCocktailsUseCase

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        scope.launch { loadCocktailsUseCase() }
    }
}