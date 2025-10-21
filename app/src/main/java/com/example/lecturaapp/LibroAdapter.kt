package com.example.lecturaapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class LibroAdapter(
    private val libros: List<Libro>,
    private val listener: OnItemClickListener,
    private val context: Context
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(libro: Libro)
    }

    class LibroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloTextView: TextView = view.findViewById(R.id.tituloTextView)
        val autorTextView: TextView = view.findViewById(R.id.autorTextView)
        val portadaImageView: ImageView = view.findViewById(R.id.portadaImageView)
        val favoriteButton: ImageView = view.findViewById(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.tituloTextView.text = libro.titulo
        holder.autorTextView.text = libro.autor
        holder.portadaImageView.setImageResource(libro.imagen)

        // Establecer el estado inicial del ícono de favoritos
        val favoriteIcon = if (libro.favorito) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        holder.favoriteButton.setImageResource(favoriteIcon)

        // Lógica para cambiar el ícono al hacer clic
        holder.favoriteButton.setOnClickListener {
            libro.favorito = !libro.favorito // Invertir el estado
            val newIcon = if (libro.favorito) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            holder.favoriteButton.setImageResource(newIcon)

            val message = if (libro.favorito) "Has agregado ${libro.titulo} a favoritos" else "Has quitado ${libro.titulo} de favoritos"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Lógica para el clic en la tarjeta del libro
        holder.itemView.setOnClickListener {
            listener.onItemClick(libro)
        }
    }

    override fun getItemCount() = libros.size
}