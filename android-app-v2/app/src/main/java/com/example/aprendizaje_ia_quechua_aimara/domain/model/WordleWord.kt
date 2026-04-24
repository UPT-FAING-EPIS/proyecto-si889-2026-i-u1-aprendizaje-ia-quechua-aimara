package com.example.aprendizaje_ia_quechua_aimara.domain.model

data class WordleWord(
    val id: String = "",
    val quechua: String = "",
    val aimara: String = "",
    val espanol: String = "",
    val categoria: String = "",
    val descripcion: String = "",
    val intentos_max: Int = 6
)
