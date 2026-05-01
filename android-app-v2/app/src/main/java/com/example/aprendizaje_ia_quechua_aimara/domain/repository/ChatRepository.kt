package com.example.aprendizaje_ia_quechua_aimara.domain.repository

interface ChatRepository {
    suspend fun getAIResponse(prompt: String): Result<String>
}
