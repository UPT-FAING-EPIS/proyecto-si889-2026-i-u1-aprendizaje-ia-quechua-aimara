package com.nescore.aprendizaje_ia_quechua_aimara.data.repository

import com.nescore.aprendizaje_ia_quechua_aimara.data.datasource.TemasRemoteDataSource
import com.nescore.aprendizaje_ia_quechua_aimara.data.local.dao.TemaDao
import com.nescore.aprendizaje_ia_quechua_aimara.data.local.entity.toDomain
import com.nescore.aprendizaje_ia_quechua_aimara.data.local.entity.toEntity
import com.nescore.aprendizaje_ia_quechua_aimara.data.model.Palabra
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Tema
import com.nescore.aprendizaje_ia_quechua_aimara.domain.repository.TemasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemasRepositoryImpl @Inject constructor(
    private val remoteDataSource: TemasRemoteDataSource,
    private val localDataSource: TemaDao
) : TemasRepository {

    override suspend fun getTemas(): Result<List<Tema>> {
        val result = remoteDataSource.getTemas()
        result.onSuccess { temas ->
            saveTemasLocal(temas)
        }
        return result
    }

    override suspend fun getPalabrasPorTema(nombreTema: String): Result<List<Palabra>> =
        remoteDataSource.getPalabrasPorTema(nombreTema)

    override fun getAllTemasLocal(): Flow<List<Tema>> {
        return localDataSource.getAllTemas().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveTemasLocal(temas: List<Tema>) {
        localDataSource.insertTemas(temas.map { it.toEntity() })
    }
}
