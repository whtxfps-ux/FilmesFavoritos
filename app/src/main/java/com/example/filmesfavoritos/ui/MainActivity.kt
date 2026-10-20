package com.example.filmesfavoritos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmesfavoritos.R
import com.example.filmesfavoritos.data.Movie
import com.example.filmesfavoritos.databinding.ActivityMainBinding
import com.example.filmesfavoritos.databinding.DialogEditMovieBinding
import com.example.filmesfavoritos.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()

    private val adapter = MovieAdapter(
        onEdit = { showEditDialog(it) },
        onDelete = { viewModel.deleteMovie(it.id) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerMovies.layoutManager = LinearLayoutManager(this)
        binding.recyclerMovies.adapter = adapter
        binding.fabAdd.setOnClickListener { showEditDialog(null) }

        // Observa o estado exposto pelo ViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { list ->
                    adapter.submitList(list)
                    binding.textEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    /** Abre o diálogo para adicionar (movie == null) ou editar um filme. */
    private fun showEditDialog(movie: Movie?) {
        val dialogBinding = DialogEditMovieBinding.inflate(LayoutInflater.from(this))
        movie?.let {
            dialogBinding.editTitle.setText(it.title)
            dialogBinding.editImageUrl.setText(it.imageUrl)
            dialogBinding.ratingBarEdit.rating = it.rating
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle(if (movie == null) R.string.novo_filme else R.string.editar_filme)
            .setView(dialogBinding.root)
            .setNegativeButton(R.string.cancelar, null)
            .setPositiveButton(R.string.salvar, null) // sobrescrito abaixo p/ não fechar se inválido
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val ok = viewModel.saveMovie(
                    id = movie?.id,
                    title = dialogBinding.editTitle.text.toString(),
                    rating = dialogBinding.ratingBarEdit.rating,
                    imageUrl = dialogBinding.editImageUrl.text.toString()
                )
                if (ok) dialog.dismiss()
                else Toast.makeText(this, R.string.erro_nome, Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
}
