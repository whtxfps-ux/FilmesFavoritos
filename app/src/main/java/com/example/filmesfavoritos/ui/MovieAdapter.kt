package com.example.filmesfavoritos.ui

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmesfavoritos.data.Movie
import com.example.filmesfavoritos.databinding.ItemMovieBinding

class MovieAdapter(
    private val onEdit: (Movie) -> Unit,
    private val onDelete: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(Diff) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) = with(binding) {
            textTitle.text = movie.title
            ratingBar.rating = movie.rating
            textRating.text = String.format("%.1f / 5", movie.rating)

            // Coil: carrega a foto da URL
            imagePoster.load(movie.imageUrl) {
                crossfade(true)
                placeholder(ColorDrawable(0xFFCCCCCC.toInt()))
                error(android.R.drawable.ic_menu_gallery)
            }

            buttonEdit.setOnClickListener { onEdit(movie) }
            buttonDelete.setOnClickListener { onDelete(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))

    private object Diff : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(a: Movie, b: Movie) = a.id == b.id
        override fun areContentsTheSame(a: Movie, b: Movie) = a == b
    }
}
