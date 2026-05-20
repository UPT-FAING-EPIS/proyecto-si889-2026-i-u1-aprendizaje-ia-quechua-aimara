package com.example.aprendizaje_ia_quechua_aimara.ui.practice

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor() : ViewModel() {
    // Para categorías y niveles, la navegación de Compose suele ser suficiente.
    // Podríamos manejar estados globales aquí si fuera necesario.
}
