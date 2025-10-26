package com.example.lecturaapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Modelos de datos para la respuesta de la API de Google Books
data class BookResponse(val items: List<BookItem>?)
data class BookItem(val id: String, val volumeInfo: VolumeInfo)
data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val categories: List<String>?, // Añadido para la categoría
    val imageLinks: ImageLinks?
)
data class ImageLinks(val thumbnail: String?)

interface GoogleBooksApiService {
    @GET("volumes")
    fun searchBooks(
        @Query("q") query: String,
        @Query("langRestrict") lang: String,
        @Query("maxResults") maxResults: Int = 15
    ): Call<BookResponse>
}
