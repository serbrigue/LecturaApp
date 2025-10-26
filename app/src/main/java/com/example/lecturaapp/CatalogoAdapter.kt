package com.example.lecturaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// El item del catálogo ahora puede ser un carrusel de Destacados o una Fila de Categoría (que tiene su propio carrusel)
sealed class CatalogoItem {
    data class Destacados(val novelas: List<Novela>) : CatalogoItem()
    data class CategoryRow(val title: String, val novelas: List<Novela>) : CatalogoItem()
}

class CatalogoAdapter(
    private val items: List<CatalogoItem>,
    private val novelClickListener: CarruselAdapter.OnItemClickListener,
    private val favoriteClickListener: OnFavoriteClickListener,
    private val favoriteIds: Set<String> // Conjunto de IDs de novelas favoritas
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Listener para clics en el ícono de favorito
    interface OnFavoriteClickListener {
        fun onFavoriteClick(novela: Novela, isCurrentlyFavorited: Boolean)
    }

    companion object {
        private const val VIEW_TYPE_DESTACADOS = 0
        private const val VIEW_TYPE_CATEGORY_ROW = 1
    }

    // ViewHolder para el carrusel de Destacados
    class DestacadosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.findViewById(R.id.destacadosRecyclerView)
    }

    // ViewHolder para una fila de Categoría
    class CategoryRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.categoryTitleTextView)
        val recyclerView: RecyclerView = view.findViewById(R.id.booksRecyclerView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CatalogoItem.Destacados -> VIEW_TYPE_DESTACADOS
            is CatalogoItem.CategoryRow -> VIEW_TYPE_CATEGORY_ROW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DESTACADOS -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_destacados_carrusel, parent, false)
                DestacadosViewHolder(view)
            }
            VIEW_TYPE_CATEGORY_ROW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_row, parent, false)
                CategoryRowViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            // Configura el carrusel de Destacados
            is CatalogoItem.Destacados -> {
                val destacadosHolder = holder as DestacadosViewHolder
                destacadosHolder.recyclerView.layoutManager = LinearLayoutManager(destacadosHolder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
                // Pasamos los IDs y los listeners al adapter del carrusel
                destacadosHolder.recyclerView.adapter = CarruselAdapter(item.novelas, novelClickListener, favoriteClickListener, favoriteIds)
            }
            // Configura una fila de Categoría con su propio carrusel
            is CatalogoItem.CategoryRow -> {
                val categoryHolder = holder as CategoryRowViewHolder
                categoryHolder.title.text = item.title
                categoryHolder.recyclerView.layoutManager = LinearLayoutManager(categoryHolder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
                // Pasamos los IDs y los listeners al adapter del carrusel
                categoryHolder.recyclerView.adapter = CarruselAdapter(item.novelas, novelClickListener, favoriteClickListener, favoriteIds)
            }
        }
    }

    override fun getItemCount() = items.size
}
