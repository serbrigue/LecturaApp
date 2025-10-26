package com.example.lecturaapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects

class CuentaActivity : AppCompatActivity(), CarruselAdapter.OnItemClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var rvFavorites: RecyclerView
    private lateinit var rvRecommendations: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvUserEmail: TextView = findViewById(R.id.tvUserEmail)
        rvFavorites = findViewById(R.id.rvFavorites)
        rvRecommendations = findViewById(R.id.rvRecommendations)

        val user = auth.currentUser
        if (user == null) {
            finish()
            return
        }

        tvUserName.text = user.displayName ?: "Usuario"
        tvUserEmail.text = user.email

        rvFavorites.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvRecommendations.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        loadFavoritesAndRecommendations()
    }

    private fun loadFavoritesAndRecommendations() {
        val userId = auth.currentUser!!.uid
        db.collection("usuarios").document(userId).get()
            .addOnSuccessListener { document ->
                val favoriteIds = document.get("favoriteIds") as? List<String>
                if (favoriteIds.isNullOrEmpty()) {
                    Toast.makeText(this, "Aún no tienes favoritos", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // 1. Cargar las novelas favoritas
                db.collection("novelas").whereIn("id", favoriteIds).get()
                    .addOnSuccessListener { favoriteNovelsSnapshot ->
                        val favoriteNovels = favoriteNovelsSnapshot.toObjects<Novela>()
                        rvFavorites.adapter = CarruselAdapter(favoriteNovels, this, object : CatalogoAdapter.OnFavoriteClickListener { // Dummy listener
                            override fun onFavoriteClick(novela: Novela, isCurrentlyFavorited: Boolean) { /* No hacer nada aquí */ }
                        }, favoriteIds.toSet())

                        // 2. Generar y cargar recomendaciones
                        loadRecommendations(favoriteNovels, favoriteIds)
                    }
            }
    }

    private fun loadRecommendations(favoriteNovels: List<Novela>, favoriteIds: List<String>) {
        val favoriteCategories = favoriteNovels.map { it.categoria }.distinct()
        if (favoriteCategories.isEmpty()) return

        // Buscar otras novelas de las categorías favoritas, excluyendo los ya favoritos
        db.collection("novelas")
            .whereIn("categoria", favoriteCategories)
            .get()
            .addOnSuccessListener { recommendationSnapshot ->
                val recommendations = recommendationSnapshot.toObjects<Novela>()
                    .filterNot { favoriteIds.contains(it.id) } // Excluir los que ya son favoritos
                    .shuffled()
                    .take(10) // Tomar hasta 10 recomendaciones
                
                rvRecommendations.adapter = CarruselAdapter(recommendations, this, object : CatalogoAdapter.OnFavoriteClickListener {
                    override fun onFavoriteClick(novela: Novela, isCurrentlyFavorited: Boolean) { /* No hacer nada aquí */ }
                }, favoriteIds.toSet())
            }
    }

    override fun onItemClick(novela: Novela) {
        val intent = Intent(this, DetallesLibroActivity::class.java)
        intent.putExtra("novela", novela)
        startActivity(intent)
    }
}