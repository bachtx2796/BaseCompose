package com.example.basecompose.di

import com.example.basecompose.data.repository.HDSDRepository
import com.example.basecompose.data.repository.HDSDRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHDSDRepository(
        impl: HDSDRepositoryImpl,
    ): HDSDRepository
}
