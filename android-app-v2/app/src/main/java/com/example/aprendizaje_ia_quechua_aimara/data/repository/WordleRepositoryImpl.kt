package com.example.aprendizaje_ia_quechua_aimara.data.repository

import com.example.aprendizaje_ia_quechua_aimara.data.datasource.WordleRemoteDataSource
import com.example.aprendizaje_ia_quechua_aimara.domain.model.WordleWord
import com.example.aprendizaje_ia_quechua_aimara.domain.model.WordleLanguage
import com.example.aprendizaje_ia_quechua_aimara.domain.repository.WordleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordleRepositoryImpl @Inject constructor(
    private val remoteDataSource: WordleRemoteDataSource
) : WordleRepository {

    override suspend fun getRandomWord(language: WordleLanguage, category: String): Result<WordleWord> {
        return remoteDataSource.getRandomWord(language, category)
    }
}
