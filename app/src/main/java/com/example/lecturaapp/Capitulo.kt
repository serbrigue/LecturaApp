package com.example.lecturaapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Capitulo(
    val id: String = "",
    val nombre: String = "",
    val novelaId: String = "",
    val contenido: String = ""
) : Parcelable
