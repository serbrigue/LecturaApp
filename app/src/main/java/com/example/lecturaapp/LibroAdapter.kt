package com.example.lecturaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LibroAdapter(
    private val novelas: List<Novela>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(novela: Novela)
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
        val novela = novelas[position]
        holder.tituloTextView.text = novela.titulo
        holder.autorTextView.text = novela.autor

        // Corregido: Usar Glide con la URL de la portada
        Glide.with(holder.itemView.context)
            .load(novela.portadaUrl)
            .placeholder(R.drawable.ic_launcher_background) // Opcional: una imagen mientras carga
            .into(holder.portadaImageView)

        holder.favoriteButton.visibility = View.GONE

        holder.itemView.setOnClickListener {
            listener.onItemClick(novela)
        }
    }

    override fun getItemCount() = novelas.size
}