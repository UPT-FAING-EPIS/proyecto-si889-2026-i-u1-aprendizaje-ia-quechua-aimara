package com.nescore.aprendizaje_ia_quechua_aimara.ui.practice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nescore.aprendizaje_ia_quechua_aimara.ui.practice.components.PracticeCard

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, top = 16.dp),
            textAlign = TextAlign.Center
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                PracticeCard(
                    title = "Quechua",
                    subtitle = "Practica el idioma de los Incas",
                    icon = { Icon(Icons.Default.Language, contentDescription = null, modifier = Modifier.fillMaxSize()) },
                    onClick = { onCategorySelected("Quechua") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            item {
                PracticeCard(
                    title = "Aimara",
                    subtitle = "Practica la lengua del Altiplano",
                    icon = { Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.fillMaxSize()) },
                    onClick = { onCategorySelected("Aimara") },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PracticeCard(
                title = "Fácil",
                subtitle = "Vocabulario básico, saludos y números",
                icon = { Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.fillMaxSize()) },
                onClick = { onLevelSelected("Fácil") },
                containerColor = Color(0xFF1B5E20), // Verde Muy Oscuro
                contentColor = Color.White
            )
            PracticeCard(
                title = "Intermedio",
                subtitle = "Frases completas y conversaciones cortas",
                icon = { 
                    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.Star, null, tint = Color.Yellow)
                        Icon(Icons.Default.Star, null, tint = Color.Yellow)
                    }
                },
                onClick = { onLevelSelected("Intermedio") },
                containerColor = Color(0xFFE65100), // Naranja Muy Oscuro
                contentColor = Color.White
            )
            PracticeCard(
                title = "Difícil",
                subtitle = "Comprensión avanzada y contexto cultural",
                icon = {
                    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.Star, null, tint = Color.Yellow)
                        Icon(Icons.Default.Star, null, tint = Color.Yellow)
                        Icon(Icons.Default.Star, null, tint = Color.Yellow)
                    }
                },
                onClick = { onLevelSelected("Difícil") },
                containerColor = Color(0xFFB71C1C), // Rojo Muy Oscuro
                contentColor = Color.White
            )
        }
    }
}
