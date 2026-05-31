package com.nescore.aprendizaje_ia_quechua_aimara.ui.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.ChatMessage
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.MessageRole
import com.nescore.aprendizaje_ia_quechua_aimara.domain.usecase.GetAIResponseUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getAIResponseUseCase: GetAIResponseUseCase,
    private val ttsManager: TTSManager,
    private val sttManager: SpeechToTextManager
) : ViewModel() {

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> = _messages

    var inputText by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isRecording by mutableStateOf(false)
        private set

    var sttError by mutableStateOf<String?>(null)
        private set

    private var sttSessionActive = false
    private var stopRecordingTimeout: Job? = null

    fun onInputChange(newValue: String) {
        inputText = newValue
    }

    fun speak(text: String, lang: String) {
        ttsManager.speak(text, lang)
    }

    fun startRecording() {
        Log.d("ChatFlow", "--- INICIO GRABACIÓN ---")
        sttError = null
        inputText = ""
        isRecording = true
        sttSessionActive = true
        
        sttManager.startListening(
            onPartialResult = { result ->
                if (sttSessionActive) {
                    Log.d("ChatFlow", "Resultado Parcial: '$result'")
                    inputText = result
                }
            },
            onFinalResult = { result ->
                if (sttSessionActive) {
                    Log.d("ChatFlow", "Resultado Final Recibido: '$result' (Buffer actual: '$inputText')")
                    stopRecordingTimeout?.cancel()
                    sttSessionActive = false
                    isRecording = false
                    
                    val textToSend = if (result.isNotBlank()) result else inputText
                    if (textToSend.isNotBlank()) {
                        Log.d("ChatFlow", "Procesando envío desde onFinalResult")
                        sendMessage(textToSend)
                    } else {
                        Log.d("ChatFlow", "Resultado vacío, nada que enviar.")
                    }
                }
            },
            onError = { error ->
                if (sttSessionActive) {
                    Log.e("ChatFlow", "Error STT: $error")
                    stopRecordingTimeout?.cancel()
                    sttSessionActive = false
                    isRecording = false
                    sttError = error
                    
                    if (inputText.isNotBlank()) {
                        Log.d("ChatFlow", "Error detectado, pero enviando buffer parcial: '$inputText'")
                        sendMessage(inputText)
                    }
                }
            },
            onReady = { 
                Log.d("ChatFlow", "Micrófono listo y escuchando...")
            }
        )
    }

    fun stopRecording() {
        if (isRecording) {
            Log.d("ChatFlow", "--- DETENCIÓN MANUAL SOLICITADA ---")
            isRecording = false // UI feedback inmediato
            sttManager.stopListening()
            
            // Timeout de seguridad: Si el sistema no responde en 2.5s, forzamos envío de lo que tengamos
            stopRecordingTimeout?.cancel()
            stopRecordingTimeout = viewModelScope.launch {
                delay(2500)
                if (sttSessionActive) {
                    Log.w("ChatFlow", "Timeout de seguridad disparado. Forzando cierre de sesión STT.")
                    sttSessionActive = false
                    if (inputText.isNotBlank()) {
                        Log.d("ChatFlow", "Enviando buffer capturado por timeout: '$inputText'")
                        sendMessage(inputText)
                    } else {
                        Log.d("ChatFlow", "Timeout disparado pero no hay texto capturado.")
                    }
                }
            }
        }
    }

    fun sendMessage(textOverride: String? = null) {
        val text = (textOverride ?: inputText).trim()
        Log.d("ChatFlow", "Intentando sendMessage. Texto: '$text', isLoading: $isLoading")

        if (text.isEmpty()) {
            Log.w("ChatFlow", "Cancelando envío: Texto vacío.")
            return
        }
        
        if (isLoading) {
            Log.w("ChatFlow", "Cancelando envío: Ya hay una petición en curso.")
            return
        }

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user == null) {
            Log.e("ChatFlow", "Error: Usuario no autenticado.")
            _messages.add(ChatMessage(content = "Error: No has iniciado sesión.", role = MessageRole.ASSISTANT))
            return
        }

        _messages.add(ChatMessage(content = text, role = MessageRole.USER))
        val currentInput = text
        inputText = ""
        isLoading = true

        viewModelScope.launch {
            try {
                Log.d("ChatFlow", "Solicitando Token y enviando a UseCase...")
                user.getIdToken(true).await()
                
                getAIResponseUseCase(currentInput)
                    .onSuccess { response ->
                        Log.d("ChatFlow", "IA respondió con éxito.")
                        _messages.add(ChatMessage(content = response, role = MessageRole.ASSISTANT))
                    }
                    .onFailure { error ->
                        Log.e("ChatFlow", "Error en UseCase/API: ${error.message}")
                        _messages.add(ChatMessage(content = "Error del servidor: ${error.message}", role = MessageRole.ASSISTANT))
                    }
            } catch (e: Exception) {
                Log.e("ChatFlow", "Excepción en flujo de envío: ${e.message}")
                _messages.add(ChatMessage(content = "Error de conexión.", role = MessageRole.ASSISTANT))
            } finally {
                isLoading = false
                Log.d("ChatFlow", "--- FIN DEL FLUJO DE MENSAJE ---")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopRecordingTimeout?.cancel()
        ttsManager.stop()
        sttManager.destroy()
    }
}
