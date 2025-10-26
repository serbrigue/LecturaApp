package com.example.lecturaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarruselAdapter(
    private val novelas: List<Novela>,
    private val novelClickListener: OnItemClickListener,
    private val favoriteClickListener: CatalogoAdapter.OnFavoriteClickListener, // Se añade el listener
    private val favoriteIds: Set<String> // Se añaden los IDs de favoritos
) : RecyclerView.Adapter<CarruselAdapter.CarruselViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(novela: Novela)
    }

    class CarruselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val portadaImageView: ImageView = view.findViewById(R.id.portadaCarruselImageView)
        val tituloTextView: TextView = view.findViewById(R.id.tituloCarruselTextView)
        val favoriteButton: ImageView = view.findViewById(R.id.favoriteButton) // Referencia al botón
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarruselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro_carrusel, parent, false)
        return CarruselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarruselViewHolder, position: Int) {
        val novela = novelas[position]
        holder.tituloTextView.text = novela.titulo

        Glide.with(holder.itemView.context)
            .load(novela.portadaUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.portadaImageView)

        // Lógica para el botón de favoritos
        val isFavorited = favoriteIds.contains(novela.id)
        holder.favoriteButton.setImageResource(if (isFavorited) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
        holder.favoriteButton.visibility = View.VISIBLE

        holder.favoriteButton.setOnClickListener {
            favoriteClickListener.onFavoriteClick(novela, isFavorited)
        }

        holder.itemView.setOnClickListener {
            novelClickListener.onItemClick(novela)
        }
    }

    override fun getItemCount() = novelas.size
}