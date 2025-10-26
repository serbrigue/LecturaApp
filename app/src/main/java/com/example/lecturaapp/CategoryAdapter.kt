package com.example.lecturaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Category(val title: String, val novels: List<Novela>)

class CategoryAdapter(
    private val categories: List<Category>,
    private val novelClickListener: CarruselAdapter.OnItemClickListener,
    private val favoriteClickListener: CatalogoAdapter.OnFavoriteClickListener, // Se añade el listener de favoritos
    private val favoriteIds: Set<String> // Se añade el set de IDs
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryTitle: TextView = view.findViewById(R.id.categoryTitleTextView)
        val booksRecyclerView: RecyclerView = view.findViewById(R.id.booksRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_row, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryTitle.text = category.title

        // Configurar el RecyclerView horizontal para los libros
        holder.booksRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        
        // Corregido: Pasamos todos los parámetros necesarios al CarruselAdapter
        val novelAdapter = CarruselAdapter(category.novels, novelClickListener, favoriteClickListener, favoriteIds)
        holder.booksRecyclerView.adapter = novelAdapter
    }

    override fun getItemCount() = categories.size
}
