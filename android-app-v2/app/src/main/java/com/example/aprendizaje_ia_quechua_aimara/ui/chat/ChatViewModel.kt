package com.example.aprendizaje_ia_quechua_aimara.ui.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aprendizaje_ia_quechua_aimara.domain.model.ChatMessage
import com.example.aprendizaje_ia_quechua_aimara.domain.model.MessageRole
import com.example.aprendizaje_ia_quechua_aimara.domain.usecase.GetAIResponseUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getAIResponseUseCase: GetAIResponseUseCase,
    private val ttsManager: TTSManager
) : ViewModel() {

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> = _messages

    var inputText by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun onInputChange(newValue: String) {
        inputText = newValue
    }

    fun speak(text: String, lang: String) {
        ttsManager.speak(text, lang)
    }

    fun sendMessage() {
        val text = inputText.trim()
        if (text.isEmpty() || isLoading) return

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user == null) {
            _messages.add(ChatMessage(content = "Error: No has iniciado sesión.", role = MessageRole.ASSISTANT))
            return
        }

        _messages.add(ChatMessage(content = text, role = MessageRole.USER))
        val currentInput = text
        inputText = ""
        isLoading = true

        viewModelScope.launch {
            try {
                // FORZAR REFRESCO DE TOKEN: Esto soluciona el 90% de los errores UNAUTHENTICATED
                user.getIdToken(true).await()
                Log.d("ChatDebug", "Token refrescado con éxito para UID: ${user.uid}")

                getAIResponseUseCase(currentInput)
                    .onSuccess { response ->
                        _messages.add(ChatMessage(content = response, role = MessageRole.ASSISTANT))
                    }
                    .onFailure { error ->
                        Log.e("ChatDebug", "Error en la función:", error)
                        _messages.add(ChatMessage(content = "Error del servidor: ${error.message}", role = MessageRole.ASSISTANT))
                    }
            } catch (e: Exception) {
                Log.e("ChatDebug", "Error al refrescar token:", e)
                _messages.add(ChatMessage(content = "Error de autenticación local.", role = MessageRole.ASSISTANT))
            } finally {
                isLoading = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }
}
