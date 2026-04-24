package com.example.aprendizaje_ia_quechua_aimara.domain.model

data class User(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?,
    val isAnonymous: Boolean
)
