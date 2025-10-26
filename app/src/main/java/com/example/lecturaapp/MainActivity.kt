package com.example.lecturaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(), CarruselAdapter.OnItemClickListener, CatalogoAdapter.OnFavoriteClickListener {

    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val favoriteIds = mutableSetOf<String>()
    private var allNovels = listOf<Novela>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mainRecyclerView = findViewById(R.id.mainRecyclerView)
        mainRecyclerView.layoutManager = LinearLayoutManager(this)

        loadUserFavorites()
    }

    private fun loadUserFavorites() {
        if (auth.currentUser != null) {
            db.collection("usuarios").document(auth.currentUser!!.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val ids = document.get("favoriteIds") as? List<String>
                        favoriteIds.clear()
                        ids?.let { favoriteIds.addAll(it) }
                    }
                    loadAndDisplayNovels(allNovels) // Carga inicial con favoritos
                }
                .addOnFailureListener { loadAndDisplayNovels(allNovels) } // Si falla, carga sin favoritos
        } else {
            loadAndDisplayNovels(allNovels)
        }
    }

    private fun loadAndDisplayNovels(novelsToDisplay: List<Novela>) {
        if (novelsToDisplay.isNotEmpty()) {
            setupRecyclerView(novelsToDisplay)
            return
        }
        
        db.collection("novelas").orderBy("titulo").get()
            .addOnSuccessListener { result ->
                allNovels = result.toObjects(Novela::class.java)
                if (allNovels.isEmpty()) return@addOnSuccessListener
                setupRecyclerView(allNovels)
            }
    }

    private fun setupRecyclerView(novelsToDisplay: List<Novela>) {
        val catalogoItems = mutableListOf<CatalogoItem>()
        val destacados = novelsToDisplay.shuffled().take(7)
        catalogoItems.add(CatalogoItem.Destacados(destacados))

        val groupedByCategory = novelsToDisplay.groupBy { it.categoria }.toSortedMap()
        groupedByCategory.forEach { (category, novelsInCategory) ->
            catalogoItems.add(CatalogoItem.CategoryRow(category, novelsInCategory))
        }
        // Pasamos todos los listeners y datos necesarios al CatalogoAdapter
        mainRecyclerView.adapter = CatalogoAdapter(catalogoItems, this, this, favoriteIds)
    }

    override fun onFavoriteClick(novela: Novela, isCurrentlyFavorited: Boolean) {
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        val userId = auth.currentUser!!.uid
        val userDocRef = db.collection("usuarios").document(userId)
        val fieldUpdate = if (isCurrentlyFavorited) FieldValue.arrayRemove(novela.id) else FieldValue.arrayUnion(novela.id)

        userDocRef.update("favoriteIds", fieldUpdate)
            .addOnSuccessListener {
                if (isCurrentlyFavorited) favoriteIds.remove(novela.id) else favoriteIds.add(novela.id)
                (mainRecyclerView.adapter as? CatalogoAdapter)?.notifyDataSetChanged()
            }
            .addOnFailureListener {
                if (!isCurrentlyFavorited) {
                    userDocRef.set(mapOf("favoriteIds" to listOf(novela.id)))
                        .addOnSuccessListener {
                            favoriteIds.add(novela.id)
                            (mainRecyclerView.adapter as? CatalogoAdapter)?.notifyDataSetChanged()
                        }
                }
            }
    }

    override fun onItemClick(novela: Novela) {
        val intent = Intent(this, DetallesLibroActivity::class.java)
        intent.putExtra("novela", novela)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Buscar en el catálogo..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = if (newText.isNullOrBlank()) {
                    allNovels
                } else {
                    allNovels.filter { it.titulo.contains(newText, ignoreCase = true) }
                }
                setupRecyclerView(filteredList)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_account -> {
                if (auth.currentUser != null) {
                    startActivity(Intent(this, CuentaActivity::class.java))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}