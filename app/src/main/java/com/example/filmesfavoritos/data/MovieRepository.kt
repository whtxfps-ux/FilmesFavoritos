package com.example.filmesfavoritos.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Fonte de dados em memória (pode ser trocada por Room/API sem afetar a UI). */
class MovieRepository {

    private var nextId = 4L

    private val _movies = MutableStateFlow(
        listOf(
            Movie(1, "Interestelar", 5f, "https://picsum.photos/seed/interestelar/300/450"),
            Movie(2, "A Origem", 4.5f, "https://picsum.photos/seed/origem/300/450"),
            Movie(3, "Cidade de Deus", 5f, "https://picsum.photos/seed/cidadedeus/300/450")
        )
    )
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    fun add(title: String, rating: Float, imageUrl: String) {
        _movies.update { it + Movie(nextId++, title, rating, imageUrl) }
    }

    fun update(id: Long, title: String, rating: Float, imageUrl: String) {
        _movies.update { list ->
            list.map { if (it.id == id) it.copy(title = title, rating = rating, imageUrl = imageUrl) else it }
        }
    }

    fun delete(id: Long) {
        _movies.update { list -> list.filterNot { it.id == id } }
    }
}
