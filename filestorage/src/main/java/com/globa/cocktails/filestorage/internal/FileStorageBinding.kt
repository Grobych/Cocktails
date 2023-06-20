package com.globa.cocktails.filestorage.internal

import com.globa.cocktails.filestorage.api.FileStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface FileStorageBinding {
    @Binds
    fun bindFileStorage(fileStorageImpl: FileStorageImpl): FileStorage
}