package com.example.lecturaapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class CarruselAdapter(
    private val libros: List<Libro>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<CarruselAdapter.CarruselViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(libro: Libro)
    }

    class CarruselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val portadaImageView: ImageView = view.findViewById(R.id.portadaCarruselImageView)
        val tituloTextView: TextView = view.findViewById(R.id.tituloCarruselTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarruselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro_carrusel, parent, false)
        return CarruselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarruselViewHolder, position: Int) {
        val libro = libros[position]
        holder.portadaImageView.setImageResource(libro.imagen)
        holder.tituloTextView.text = libro.titulo

        holder.itemView.setOnClickListener {
            listener.onItemClick(libro)
        }
    }

    override fun getItemCount() = libros.size
}