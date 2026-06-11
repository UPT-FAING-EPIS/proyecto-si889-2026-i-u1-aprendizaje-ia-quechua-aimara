package com.nescore.aprendizaje_ia_quechua_aimara.data.repository

import com.nescore.aprendizaje_ia_quechua_aimara.data.datasource.WordleRemoteDataSource
import com.nescore.aprendizaje_ia_quechua_aimara.data.local.dao.WordleDao
import com.nescore.aprendizaje_ia_quechua_aimara.data.local.entity.toDomain
import com.nescore.aprendizaje_ia_quechua_aimara.data.local.entity.toEntity
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.WordleWord
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.WordleLanguage
import com.nescore.aprendizaje_ia_quechua_aimara.domain.repository.WordleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordleRepositoryImpl @Inject constructor(
    private val remoteDataSource: WordleRemoteDataSource,
    private val localDataSource: WordleDao
) : WordleRepository {

    override suspend fun getRandomWord(language: WordleLanguage, category: String): Result<WordleWord> {
        return remoteDataSource.getRandomWord(language, category)
    }

    override fun getAllWords(): Flow<List<WordleWord>> {
        return localDataSource.getAllWords().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveWords(words: List<WordleWord>) {
        localDataSource.insertWords(words.map { it.toEntity() })
    }

    override suspend fun clearWords() {
        localDataSource.deleteAllWords()
    }
}
