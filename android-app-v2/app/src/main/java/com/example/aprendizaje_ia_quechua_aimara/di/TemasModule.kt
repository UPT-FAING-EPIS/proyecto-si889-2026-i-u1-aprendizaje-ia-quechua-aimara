package com.example.aprendizaje_ia_quechua_aimara.di

import com.example.aprendizaje_ia_quechua_aimara.data.repository.TemasRepositoryImpl
import com.example.aprendizaje_ia_quechua_aimara.domain.repository.TemasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TemasModule {

    @Provides
    @Singleton
    fun provideTemasRepository(
        repositoryImpl: TemasRepositoryImpl
    ): TemasRepository = repositoryImpl
}
