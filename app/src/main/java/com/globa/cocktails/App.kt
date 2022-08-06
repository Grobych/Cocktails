package com.globa.cocktails

import android.app.Application
import com.globa.cocktails.di.modules.AppModule
import com.globa.cocktails.di.ApplicationComponent
import com.globa.cocktails.di.DaggerApplicationComponent


class App : Application()
{
    val appComponent: ApplicationComponent = DaggerApplicationComponent.builder()
        .appModule(AppModule(this))
        .build()
}