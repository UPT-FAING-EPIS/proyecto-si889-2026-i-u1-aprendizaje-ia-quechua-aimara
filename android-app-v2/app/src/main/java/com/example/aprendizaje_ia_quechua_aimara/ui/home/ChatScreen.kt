package com.example.aprendizaje_ia_quechua_aimara.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aprendizaje_ia_quechua_aimara.domain.model.ChatMessage
import com.example.aprendizaje_ia_quechua_aimara.domain.model.MessageRole
import com.example.aprendizaje_ia_quechua_aimara.ui.chat.ChatViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.isNotEmpty()) {
            listState.animateScrollToItem(viewModel.messages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize().imePadding()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (viewModel.messages.isEmpty()) {
                item {
                    AssistantTriBubble(
                        spanish = "¡Hola! Soy tu tutor de lenguas andinas.",
                        quechua = "¡Imaynalla! Qichwa simita yachaq tumpam kani.",
                        aymara = "¡Kamisaraki! Nayax aymar yatiqir yanapiriwa.",
                        onSpeak = { text, lang -> viewModel.speak(text, lang) }
                    )
                }
            }

            items(viewModel.messages) { message ->
                val triResponse = message.parseTriResponse()
                if (message.role == MessageRole.USER) {
                    UserBubble(message.content)
                } else if (triResponse != null) {
                    AssistantTriBubble(
                        spanish = triResponse.first,
                        quechua = triResponse.second,
                        aymara = triResponse.third,
                        onSpeak = { text, lang -> viewModel.speak(text, lang) }
                    )
                } else {
                    // Fallback para mensajes de error o simples
                    SimpleAssistantBubble(message.content)
                }
            }

            if (viewModel.isLoading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp).padding(start = 8.dp))
                }
            }
        }

        ChatInputArea(
            text = viewModel.inputText,
            onValueChange = { viewModel.onInputChange(it) },
            onSend = { viewModel.sendMessage() },
            enabled = !viewModel.isLoading
        )
    }
}

@Composable
fun UserBubble(text: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(text, color = Color.White, modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun AssistantTriBubble(
    spanish: String,
    quechua: String,
    aymara: String,
    onSpeak: (String, String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp),
        modifier = Modifier.widthIn(max = 320.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            LanguageRow("Español", spanish, "es", onSpeak)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
            LanguageRow("Quechua", quechua, "quechua", onSpeak)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
            LanguageRow("Aimara", aymara, "aimara", onSpeak)
        }
    }
}

@Composable
fun LanguageRow(label: String, text: String, langCode: String, onSpeak: (String, String) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onSpeak(text, langCode) }, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Escuchar", modifier = Modifier.size(18.dp))
            }
        }
        Text(text, fontSize = 15.sp)
    }
}

@Composable
fun SimpleAssistantBubble(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp),
        modifier = Modifier.widthIn(max = 280.dp)
    ) {
        Text(text, modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun ChatInputArea(text: String, onValueChange: (String) -> Unit, onSend: () -> Unit, enabled: Boolean) {
    Surface(tonalElevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                placeholder = { Text("Escribe aquí...") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                enabled = enabled
            )
            IconButton(onClick = onSend, enabled = enabled && text.isNotBlank()) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
