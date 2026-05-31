package com.nescore.aprendizaje_ia_quechua_aimara.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nescore.aprendizaje_ia_quechua_aimara.domain.model.Exam
import com.nescore.aprendizaje_ia_quechua_aimara.domain.repository.PracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PracticeListUiState(
    val language: String = "",
    val selectedLevel: String = "Fácil",
    val practices: List<Exam> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val repository: PracticeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeListUiState())
    val uiState: StateFlow<PracticeListUiState> = _uiState.asStateFlow()

    fun init(language: String) {
        if (_uiState.value.language != language) {
            _uiState.update { it.copy(language = language) }
            loadPractices()
        }
    }

    fun setLevel(level: String) {
        if (_uiState.value.selectedLevel != level) {
            _uiState.update { it.copy(selectedLevel = level) }
            loadPractices()
        }
    }

    private fun loadPractices() {
        val currentLanguage = _uiState.value.language
        val currentLevel = _uiState.value.selectedLevel
        
        if (currentLanguage.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Obtenemos la lista de exámenes para el nivel seleccionado
            val exams = repository.getExamsByLevel(currentLanguage, currentLevel)
            
            _uiState.update { 
                it.copy(
                    practices = exams,
                    isLoading = false
                )
            }
        }
    }
}
