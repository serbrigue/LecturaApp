package com.example.lecturaapp

data class Libro(
    val titulo: String,
    val autor: String,
    val descripcion: String,
    val imagen: Int,
    var favorito: Boolean = false // Nuevo campo
)