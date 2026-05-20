package com.example.aprendizaje_ia_quechua_aimara.domain.usecase

import com.example.aprendizaje_ia_quechua_aimara.domain.model.Exam
import com.example.aprendizaje_ia_quechua_aimara.domain.repository.PracticeRepository
import javax.inject.Inject

class GetExamByLevelUseCase @Inject constructor(
    private val repository: PracticeRepository
) {
    suspend operator fun invoke(language: String, level: String): Exam? {
        return repository.getExamByLevel(language, level)
    }
}
