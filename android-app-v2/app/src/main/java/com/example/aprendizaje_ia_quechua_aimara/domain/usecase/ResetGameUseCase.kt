package com.example.aprendizaje_ia_quechua_aimara.domain.usecase

import com.example.aprendizaje_ia_quechua_aimara.domain.model.WordleWord
import com.example.aprendizaje_ia_quechua_aimara.domain.model.WordleLanguage
import javax.inject.Inject

class ResetGameUseCase @Inject constructor(
    private val getWordUseCase: GetWordUseCase
) {
    suspend operator fun invoke(language: WordleLanguage, category: String): Result<WordleWord> {
        return getWordUseCase(language, category)
    }
}
