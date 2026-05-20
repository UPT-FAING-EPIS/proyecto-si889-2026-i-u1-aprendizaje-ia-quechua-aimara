package com.example.aprendizaje_ia_quechua_aimara.domain.model

data class Exam(
    val language: String,
    val level: String,
    val examTitle: String,
    val questions: List<Question>,
    val achievement: Achievement
)

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String
)

data class Achievement(
    val name: String,
    val description: String,
    val shareMessage: String
)
