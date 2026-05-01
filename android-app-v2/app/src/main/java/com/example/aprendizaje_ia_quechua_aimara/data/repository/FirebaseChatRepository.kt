package com.example.aprendizaje_ia_quechua_aimara.data.repository

import com.example.aprendizaje_ia_quechua_aimara.domain.repository.ChatRepository
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseChatRepository @Inject constructor(
    private val functions: FirebaseFunctions
) : ChatRepository {

    override suspend fun getAIResponse(prompt: String): Result<String> {
        return try {
            val data = hashMapOf("prompt" to prompt)
            
            // Forzamos la llamada a la región específica donde desplegaste
            val result = functions
                .getHttpsCallable("getOpenAIResponse")
                .call(data)
                .await()

            val responseData = result.data as Map<*, *>
            val text = responseData["response"] as String
            Result.success(text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
