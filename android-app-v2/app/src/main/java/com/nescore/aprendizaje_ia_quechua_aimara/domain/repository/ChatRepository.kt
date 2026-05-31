package com.nescore.aprendizaje_ia_quechua_aimara.domain.repository

interface ChatRepository {
    suspend fun getAIResponse(prompt: String): Result<String>
    suspend fun getAIAudioResponse(audioPath: String): Result<Map<String, String>>
}
