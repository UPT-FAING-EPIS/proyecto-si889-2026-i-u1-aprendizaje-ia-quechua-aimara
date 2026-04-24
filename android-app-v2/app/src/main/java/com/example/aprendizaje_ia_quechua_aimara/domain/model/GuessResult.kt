package com.example.aprendizaje_ia_quechua_aimara.domain.model

data class GuessResult(
    val word: String,
    val statuses: List<LetterStatus>,
    val isCorrect: Boolean
)
