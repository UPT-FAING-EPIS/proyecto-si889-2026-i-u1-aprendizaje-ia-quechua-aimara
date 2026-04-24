package com.example.aprendizaje_ia_quechua_aimara.data.datasource

import com.example.aprendizaje_ia_quechua_aimara.data.model.Palabra
import com.example.aprendizaje_ia_quechua_aimara.domain.model.Tema
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.text.Normalizer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemasRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getTemas(): Result<List<Tema>> {
        return try {
            // En una app real, esto vendría de una colección "temas_config"
            val temasPredeterminados = listOf(
                Tema("saludos", "Saludos", "Aprende a saludar"),
                Tema("numeros", "Números", "Cuenta en Quechua y Aimara"),
                Tema("familia", "Familia", "Miembros de la familia"),
                Tema("colores", "Colores", "Los colores de la naturaleza"),
                Tema("cuerpohumano", "Cuerpo Humano", "Partes del cuerpo"),
                Tema("animales", "Animales", "Animales de la región")
            )
            Result.success(temasPredeterminados)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPalabrasPorTema(nombreTema: String): Result<List<Palabra>> {
        return try {
            val idDocumento = normalizarId(nombreTema)
            val snapshot = firestore.collection("temas")
                .document(idDocumento)
                .collection("items")
                .orderBy("orden", Query.Direction.ASCENDING)
                .get()
                .await()
            
            Result.success(snapshot.toObjects(Palabra::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun normalizarId(texto: String): String {
        val temp = Normalizer.normalize(texto.lowercase(), Normalizer.Form.NFD)
        return temp.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .replace(" ", "")
    }
}
