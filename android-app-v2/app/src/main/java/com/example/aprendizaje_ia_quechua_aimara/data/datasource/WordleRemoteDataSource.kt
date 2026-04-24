package com.example.aprendizaje_ia_quechua_aimara.data.datasource

import com.example.aprendizaje_ia_quechua_aimara.domain.model.WordleWord
import com.example.aprendizaje_ia_quechua_aimara.domain.model.WordleLanguage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordleRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getRandomWord(language: WordleLanguage, category: String): Result<WordleWord> {
        return try {
            val snapshot = firestore.collection("wordle_words")
                .whereEqualTo("categoria", category.lowercase())
                .get()
                .await()
            
            val words = snapshot.toObjects(WordleWord::class.java).filter {
                when (language) {
                    WordleLanguage.QUECHUA -> it.quechua.isNotBlank()
                    WordleLanguage.AIMARA -> it.aimara.isNotBlank()
                    WordleLanguage.ESPANOL -> it.espanol.isNotBlank()
                }
            }

            if (words.isNotEmpty()) {
                Result.success(words.random())
            } else {
                Result.failure(Exception("No se encontraron palabras para $category en $language"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
