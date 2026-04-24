package com.example.aprendizaje_ia_quechua_aimara.data.repository

import com.example.aprendizaje_ia_quechua_aimara.data.datasource.TemasRemoteDataSource
import com.example.aprendizaje_ia_quechua_aimara.data.model.Palabra
import com.example.aprendizaje_ia_quechua_aimara.domain.model.Tema
import com.example.aprendizaje_ia_quechua_aimara.domain.repository.TemasRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemasRepositoryImpl @Inject constructor(
    private val remoteDataSource: TemasRemoteDataSource
) : TemasRepository {

    override suspend fun getTemas(): Result<List<Tema>> = remoteDataSource.getTemas()

    override suspend fun getPalabrasPorTema(nombreTema: String): Result<List<Palabra>> =
        remoteDataSource.getPalabrasPorTema(nombreTema)
}
