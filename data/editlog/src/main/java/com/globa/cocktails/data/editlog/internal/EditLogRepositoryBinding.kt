package com.globa.cocktails.data.editlog.internal

import com.globa.cocktails.data.editlog.api.EditLogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface EditLogRepositoryBinding {
    @Binds
    fun bindEditLogRepository(editLogRepositoryImpl: EditLogRepositoryImpl): EditLogRepository
}