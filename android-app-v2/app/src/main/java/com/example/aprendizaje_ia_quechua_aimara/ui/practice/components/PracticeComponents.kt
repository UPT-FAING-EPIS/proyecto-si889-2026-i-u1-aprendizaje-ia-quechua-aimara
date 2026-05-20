package com.example.aprendizaje_ia_quechua_aimara.ui.practice.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PracticeCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = contentColorFor(containerColor)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun OptionTile(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit,
    enabled: Boolean
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            isCorrect == true -> Color.Green
            isCorrect == false -> Color.Red
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline
        },
        label = "borderColor"
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            isCorrect == true -> Color.Green.copy(alpha = 0.1f)
            isCorrect == false -> Color.Red.copy(alpha = 0.1f)
            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else -> MaterialTheme.colorScheme.surface
        },
        label = "containerColor"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, borderColor),
        color = containerColor
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            if (isCorrect == true) {
                Text("✓", color = Color.Green, fontWeight = FontWeight.Bold)
            } else if (isCorrect == false) {
                Text("✗", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ExamProgressHeader(
    currentQuestion: Int,
    totalQuestions: Int,
    progress: Float
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pregunta ${currentQuestion + 1} de $totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}
