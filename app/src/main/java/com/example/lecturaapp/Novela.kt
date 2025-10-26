package com.example.lecturaapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Novela(
    val id: String = "",
    val titulo: String = "",
    val autor: String = "",
    val descripcion: String = "",
    val categoria: String = "", // Añadido de nuevo
    val portadaUrl: String = ""
) : Parcelable
