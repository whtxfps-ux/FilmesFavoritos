package com.example.filmesfavoritos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmesfavoritos.data.Movie
import com.example.filmesfavoritos.data.MovieRepository
import kotlinx.coroutines.flow.StateFlow

class MovieViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    val movies: StateFlow<List<Movie>> = repository.movies

    /**
     * Cria (id == null) ou atualiza um filme.
     * Retorna false se a validação falhar.
     */
    fun saveMovie(id: Long?, title: String, rating: Float, imageUrl: String): Boolean {
        val cleanTitle = title.trim()
        if (cleanTitle.isEmpty()) return false

        if (id == null) repository.add(cleanTitle, rating, imageUrl.trim())
        else repository.update(id, cleanTitle, rating, imageUrl.trim())
        return true
    }

    fun deleteMovie(id: Long) = repository.delete(id)
}
