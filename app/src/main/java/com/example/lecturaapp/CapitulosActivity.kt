package com.example.lecturaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class CapitulosActivity : AppCompatActivity(), CapituloAdapter.OnCapituloClickListener {

    private lateinit var chaptersRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capitulos)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Capítulos"

        val novelaTitulo = intent.getStringExtra("novelaId") ?: "Novela Desconocida"

        chaptersRecyclerView = findViewById(R.id.chaptersRecyclerView)
        
        // Generamos los capítulos de prueba localmente
        val capitulosDePrueba = createDummyChapters(novelaTitulo)
        val capituloAdapter = CapituloAdapter(capitulosDePrueba, this)
        chaptersRecyclerView.adapter = capituloAdapter
    }

    private fun createDummyChapters(novelaId: String): List<Capitulo> {
        val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam. Proin sed quam. Sed vahicula, urna ut laoreet tempus, mauris erat consectetuer libero, dapibus desper. Sed vahicula, urna ut laoreet tempus, mauris erat consectetuer libero, dapibus desper."
        return listOf(
            Capitulo(id="cap1", nombre="Capítulo 1: El Inicio", novelaId=novelaId, contenido=loremIpsum),
            Capitulo(id="cap2", nombre="Capítulo 2: La Trama", novelaId=novelaId, contenido=loremIpsum.reversed())
        )
    }

    override fun onCapituloClick(capitulo: Capitulo) {
        val intent = Intent(this, LecturaActivity::class.java)
        intent.putExtra("capitulo", capitulo)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}