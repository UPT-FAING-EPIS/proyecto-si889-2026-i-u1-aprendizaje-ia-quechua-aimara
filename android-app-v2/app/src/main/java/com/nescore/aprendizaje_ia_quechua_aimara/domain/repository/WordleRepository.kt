package com.nescore.aprendizaje_ia_quechua_aimara.domain.repository

import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.WordleWord
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.WordleLanguage

interface WordleRepository {
    suspend fun getRandomWord(language: WordleLanguage, category: String): Result<WordleWord>
}
