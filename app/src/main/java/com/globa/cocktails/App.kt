package com.globa.cocktails

import android.app.Application
import com.globa.cocktails.di.ApplicationComponent
import com.globa.cocktails.di.DaggerApplicationComponent


class App : Application()
{
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
            .context(this)
            .build()
    }
}