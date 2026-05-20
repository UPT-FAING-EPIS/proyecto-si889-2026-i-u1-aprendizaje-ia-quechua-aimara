package com.example.aprendizaje_ia_quechua_aimara.ui.practice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aprendizaje_ia_quechua_aimara.ui.practice.components.PracticeCard

@Composable
fun PracticeCategoryScreen(
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Elige un idioma para practicar",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PracticeCard(
                    title = "Quechua",
                    subtitle = "Practica el idioma de los Incas",
                    onClick = { onCategorySelected("Quechua") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
            item {
                PracticeCard(
                    title = "Aimara",
                    subtitle = "Practica la lengua del Altiplano",
                    onClick = { onCategorySelected("Aimara") },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeLevelsScreen(
    language: String,
    onLevelSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Niveles - $language") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PracticeCard(
                title = "Fácil",
                subtitle = "Vocabulario básico, saludos y números",
                onClick = { onLevelSelected("Fácil") },
                containerColor = Color(0xFF1B5E20), // Verde Muy Oscuro
                contentColor = Color.White
            )
            PracticeCard(
                title = "Intermedio",
                subtitle = "Frases completas y conversaciones cortas",
                onClick = { onLevelSelected("Intermedio") },
                containerColor = Color(0xFFE65100), // Naranja Muy Oscuro
                contentColor = Color.White
            )
            PracticeCard(
                title = "Difícil",
                subtitle = "Comprensión avanzada y contexto cultural",
                onClick = { onLevelSelected("Difícil") },
                containerColor = Color(0xFFB71C1C), // Rojo Muy Oscuro
                contentColor = Color.White
            )
        }
    }
}
