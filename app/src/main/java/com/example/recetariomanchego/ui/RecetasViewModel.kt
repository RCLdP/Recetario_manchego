package com.example.recetariomanchego.ui

import androidx.lifecycle.ViewModel
import com.example.recetariomanchego.data.Receta
import com.example.recetariomanchego.data.recetasIniciales
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecetasViewModel : ViewModel() {

    private val _recetas = MutableStateFlow(recetasIniciales())
    val recetas: StateFlow<List<Receta>> = _recetas.asStateFlow()

    private val _expandidaId = MutableStateFlow<Int?>(null)
    val expandidaId: StateFlow<Int?> = _expandidaId.asStateFlow()

    fun alternarExpandida(id: Int) {
        _expandidaId.value = if (_expandidaId.value == id) null else id
    }
    fun alternarFavorita(id: Int) {
        _recetas.update { lista ->
            lista.map { receta ->
                if (receta.id == id) receta.copy(favorita = !receta.favorita) else receta
            }
        }
    }

    fun recetaConId(id: Int): Receta? =
        _recetas.value.firstOrNull { it.id == id }
}