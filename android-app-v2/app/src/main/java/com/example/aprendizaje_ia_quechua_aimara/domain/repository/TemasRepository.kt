package com.example.aprendizaje_ia_quechua_aimara.domain.repository

import com.example.aprendizaje_ia_quechua_aimara.data.model.Palabra
import com.example.aprendizaje_ia_quechua_aimara.domain.model.Tema

interface TemasRepository {
    suspend fun getTemas(): Result<List<Tema>>
    suspend fun getPalabrasPorTema(nombreTema: String): Result<List<Palabra>>
}
