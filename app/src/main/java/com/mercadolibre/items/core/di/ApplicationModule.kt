package com.mercadolibre.items.core.di

import com.mercadolibre.items.data.MercadoLibreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideMercadoLibreRepository(dataSource: MercadoLibreRepository.Network): MercadoLibreRepository = dataSource
}