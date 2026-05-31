package com.nescore.aprendizaje_ia_quechua_aimara.data.repository

import android.content.Context
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Achievement
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Exam
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Question
import com.nescore.aprendizaje_ia_quechua_aimara.domain.repository.PracticeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

class PracticeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PracticeRepository {

    override suspend fun getExams(language: String): List<Exam> {
        val levels = listOf("easy", "intermediate", "hard")
        return levels.mapNotNull { level ->
            loadExamFromAssets(language.lowercase(), level)
        }
    }

    override suspend fun getExamByLevel(language: String, level: String): Exam? {
        val levelKey = when (level.lowercase()) {
            "fácil" -> "easy"
            "normal", "intermedio" -> "intermediate"
            "difícil" -> "hard"
            else -> level.lowercase()
        }
        return loadExamFromAssets(language.lowercase(), levelKey)
    }

    private fun loadExamFromAssets(language: String, level: String): Exam? {
        return try {
            val fileName = "${language}_$level.json"
            val jsonString = context.assets.open("exams/$fileName").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            
            val questionsArray = jsonObject.getJSONArray("questions")
            val questions = mutableListOf<Question>()
            for (i in 0 until questionsArray.length()) {
                val qObj = questionsArray.getJSONObject(i)
                val optionsArray = qObj.getJSONArray("options")
                val options = mutableListOf<String>()
                for (j in 0 until optionsArray.length()) {
                    options.add(optionsArray.getString(j))
                }
                questions.add(
                    Question(
                        question = qObj.getString("question"),
                        options = options,
                        correctAnswer = qObj.getString("correctAnswer"),
                        explanation = qObj.getString("explanation")
                    )
                )
            }
            
            val achObj = jsonObject.getJSONObject("achievement")
            val achievement = Achievement(
                name = achObj.getString("name"),
                description = achObj.getString("description"),
                shareMessage = achObj.getString("shareMessage")
            )
            
            Exam(
                language = jsonObject.getString("language"),
                level = jsonObject.getString("level"),
                examTitle = jsonObject.getString("examTitle"),
                description = jsonObject.optString("description", "Practica tus habilidades en $language"),
                questions = questions,
                achievement = achievement
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
