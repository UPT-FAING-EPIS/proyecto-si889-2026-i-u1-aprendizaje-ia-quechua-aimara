package com.example.aprendizaje_ia_quechua_aimara.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aprendizaje_ia_quechua_aimara.domain.model.Exam
import com.example.aprendizaje_ia_quechua_aimara.domain.usecase.GetExamByLevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExamUiState(
    val exam: Exam? = null,
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val selectedOption: String? = null,
    val isAnswerChecked: Boolean = false,
    val isExamCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val getExamByLevelUseCase: GetExamByLevelUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExamUiState())
    val uiState: StateFlow<ExamUiState> = _uiState.asStateFlow()

    fun loadExam(language: String, level: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val exam = getExamByLevelUseCase(language, level)
            if (exam != null) {
                _uiState.update { it.copy(exam = exam, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "No se pudo cargar el examen") }
            }
        }
    }

    fun onOptionSelected(option: String) {
        if (_uiState.value.isAnswerChecked) return
        _uiState.update { it.copy(selectedOption = option) }
    }

    fun checkAnswer() {
        val state = _uiState.value
        val currentQuestion = state.exam?.questions?.getOrNull(state.currentQuestionIndex) ?: return
        val isCorrect = state.selectedOption == currentQuestion.correctAnswer

        _uiState.update {
            it.copy(
                isAnswerChecked = true,
                score = if (isCorrect) it.score + 1 else it.score
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentQuestionIndex + 1
        val totalQuestions = state.exam?.questions?.size ?: 0

        if (nextIndex < totalQuestions) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    selectedOption = null,
                    isAnswerChecked = false
                )
            }
        } else {
            _uiState.update { it.copy(isExamCompleted = true) }
        }
    }

    fun resetExam() {
        _uiState.update {
            it.copy(
                currentQuestionIndex = 0,
                score = 0,
                selectedOption = null,
                isAnswerChecked = false,
                isExamCompleted = false
            )
        }
    }
}
