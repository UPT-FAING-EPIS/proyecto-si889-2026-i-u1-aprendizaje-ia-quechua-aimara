package com.nescore.aprendizaje_ia_quechua_aimara.data.repository

import android.content.Context
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Achievement
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Exam
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Question
import com.nescore.aprendizaje_ia_quechua_aimara.domain.repository.PracticeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class PracticeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PracticeRepository {

    override suspend fun getExamsByLevel(language: String, level: String): List<Exam> {
        val levelKey = mapLevelToKey(level)
        return loadExamsFromAssets(language.lowercase(), levelKey)
    }

    override suspend fun getExamByTitle(language: String, level: String, title: String): Exam? {
        val levelKey = mapLevelToKey(level)
        return loadExamsFromAssets(language.lowercase(), levelKey).find { it.examTitle == title }
    }

    private fun mapLevelToKey(level: String): String {
        return when (level.lowercase()) {
            "fácil", "easy" -> "easy"
            "normal", "intermedio", "intermediate" -> "intermediate"
            "difícil", "hard" -> "hard"
            else -> level.lowercase()
        }
    }

    private fun loadExamsFromAssets(language: String, level: String): List<Exam> {
        val exams = mutableListOf<Exam>()
        try {
            val fileName = "${language}_$level.json"
            val jsonString = context.assets.open("exams/$fileName").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                
                val questionsArray = jsonObject.getJSONArray("questions")
                val questions = mutableListOf<Question>()
                for (j in 0 until questionsArray.length()) {
                    val qObj = questionsArray.getJSONObject(j)
                    val optionsArray = qObj.getJSONArray("options")
                    val options = mutableListOf<String>()
                    for (k in 0 until optionsArray.length()) {
                        options.add(optionsArray.getString(k))
                    }
                    questions.add(
                        Question(
                            question = qObj.getString("question"),
                            options = options,
                            correctAnswer = qObj.getString("correctAnswer"),
                            explanation = qObj.optString("explanation", "")
                        )
                    )
                }
                
                val achObj = jsonObject.getJSONObject("achievement")
                val achievement = Achievement(
                    name = achObj.getString("name"),
                    description = achObj.getString("description"),
                    shareMessage = achObj.getString("shareMessage")
                )
                
                exams.add(
                    Exam(
                        language = jsonObject.getString("language"),
                        level = jsonObject.getString("level"),
                        examTitle = jsonObject.getString("examTitle"),
                        description = jsonObject.optString("description", ""),
                        questions = questions,
                        achievement = achievement
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return exams
    }
}
