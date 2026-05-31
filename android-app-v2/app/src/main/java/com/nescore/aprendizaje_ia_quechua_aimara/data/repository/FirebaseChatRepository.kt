package com.nescore.aprendizaje_ia_quechua_aimara.data.repository

import com.nescore.aprendizaje_ia_quechua_aimara.domain.repository.ChatRepository
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseChatRepository @Inject constructor(
    private val functions: FirebaseFunctions
) : ChatRepository {

    override suspend fun getAIResponse(prompt: String): Result<String> {
        return try {
            val data = hashMapOf("prompt" to prompt)
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

    override suspend fun getAIAudioResponse(audioPath: String): Result<Map<String, String>> {
        return try {
            // Enviamos el path directo para evitar errores de Object not found
            val data = hashMapOf("audioPath" to audioPath)

            val result = functions
                .getHttpsCallable("processAudioMessage")
                .call(data)
                .await()

            val responseData = result.data as Map<*, *>
            val resultMap = mutableMapOf<String, String>()
            responseData.forEach { (key, value) ->
                if (key is String && value is String) {
                    resultMap[key] = value
                }
            }
            Result.success(resultMap)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
