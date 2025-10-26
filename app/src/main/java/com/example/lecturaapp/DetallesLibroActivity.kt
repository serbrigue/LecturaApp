package com.example.lecturaapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class DetallesLibroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalles_libro_activity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val novela = intent.getParcelableExtra<Novela>("novela")

        val tituloTextView: TextView = findViewById(R.id.tituloDetalleTextView)
        val autorTextView: TextView = findViewById(R.id.autorDetalleTextView)
        val descripcionTextView: TextView = findViewById(R.id.descripcionDetalleTextView)
        val portadaDetalleImageView: ImageView = findViewById(R.id.portadaDetalleImageView)
        val verCapitulosButton: ExtendedFloatingActionButton = findViewById(R.id.verCapitulosButton)

        if (novela != null) {
            tituloTextView.text = novela.titulo
            autorTextView.text = novela.autor
            descripcionTextView.text = novela.descripcion

            Glide.with(this)
                .load(novela.portadaUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(portadaDetalleImageView)

            verCapitulosButton.setOnClickListener {
                val intent = Intent(this, CapitulosActivity::class.java)
                // Le pasamos el título de la novela para que sepa qué capítulos generar
                intent.putExtra("novelaId", novela.titulo) 
                startActivity(intent)
            }
        } else {
            verCapitulosButton.hide()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}