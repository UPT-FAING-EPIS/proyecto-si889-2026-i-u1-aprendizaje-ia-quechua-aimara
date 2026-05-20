package com.example.aprendizaje_ia_quechua_aimara.domain.repository

import com.example.aprendizaje_ia_quechua_aimara.domain.model.Exam

interface PracticeRepository {
    suspend fun getExams(language: String): List<Exam>
    suspend fun getExamByLevel(language: String, level: String): Exam?
}
