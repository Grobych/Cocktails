package com.globa.cocktails.di.modules

import android.app.Application
import android.content.Context
import com.globa.cocktails.App
import dagger.Module
import dagger.Provides


@Module
internal class AppModule(app: App) {
  var app: Application
  @Provides
  fun provideContext(): Context {
    return app
  }

  init {
    this.app = app
  }
}