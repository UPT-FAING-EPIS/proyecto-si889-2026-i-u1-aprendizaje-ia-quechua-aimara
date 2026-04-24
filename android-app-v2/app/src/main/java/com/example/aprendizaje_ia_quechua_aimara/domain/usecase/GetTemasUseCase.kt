package com.example.aprendizaje_ia_quechua_aimara.domain.usecase

import com.example.aprendizaje_ia_quechua_aimara.domain.model.Tema
import com.example.aprendizaje_ia_quechua_aimara.domain.repository.TemasRepository
import javax.inject.Inject

class GetTemasUseCase @Inject constructor(
    private val repository: TemasRepository
) {
    suspend operator fun invoke(): Result<List<Tema>> = repository.getTemas()
}
