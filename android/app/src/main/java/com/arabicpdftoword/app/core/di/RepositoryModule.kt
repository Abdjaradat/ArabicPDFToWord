package com.arabicpdftoword.app.core.di

import com.arabicpdftoword.app.data.repository.AuthRepositoryImpl
import com.arabicpdftoword.app.data.repository.ConversionRepositoryImpl
import com.arabicpdftoword.app.data.repository.PreferencesRepositoryImpl
import com.arabicpdftoword.app.domain.repository.AuthRepository
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import com.arabicpdftoword.app.domain.repository.PreferencesRepository
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
    abstract fun bindConversionRepository(
        impl: ConversionRepositoryImpl
    ): ConversionRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        impl: PreferencesRepositoryImpl
    ): PreferencesRepository
}
