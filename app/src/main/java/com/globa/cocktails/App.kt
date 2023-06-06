package com.globa.cocktails

import android.app.Application
import android.preference.PreferenceManager
import com.globa.cocktails.datalayer.repository.CocktailRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    private val DATABASE_LOADED = "DATABASE_LOADED"
    @Inject
    lateinit var repository: CocktailRepository

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if (!getBoolean(DATABASE_LOADED, false)) {
                scope.launch {
                    repository.loadFromFile()
                    PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().apply {
                        putBoolean(DATABASE_LOADED, true)
                        apply()
                    }
                }
            }
        }
    }
}