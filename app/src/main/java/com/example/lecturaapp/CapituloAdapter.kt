package com.example.lecturaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CapituloAdapter(
    private val capitulos: List<Capitulo>,
    private val listener: OnCapituloClickListener
) : RecyclerView.Adapter<CapituloAdapter.CapituloViewHolder>() {

    interface OnCapituloClickListener {
        fun onCapituloClick(capitulo: Capitulo)
    }

    class CapituloViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapituloViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return CapituloViewHolder(view)
    }

    override fun onBindViewHolder(holder: CapituloViewHolder, position: Int) {
        val capitulo = capitulos[position]
        holder.nombreTextView.text = capitulo.nombre
        holder.itemView.setOnClickListener {
            listener.onCapituloClick(capitulo)
        }
    }

    override fun getItemCount() = capitulos.size
}