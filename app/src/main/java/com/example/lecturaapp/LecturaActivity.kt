package com.example.lecturaapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class LecturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lectura)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val capitulo = intent.getParcelableExtra<Capitulo>("capitulo")
        val contentTextView: TextView = findViewById(R.id.contentTextView)

        if (capitulo != null) {
            supportActionBar?.title = capitulo.nombre
            contentTextView.text = capitulo.contenido
        } else {
            supportActionBar?.title = "Error"
            contentTextView.text = "No se pudo cargar el contenido del capítulo."
            Toast.makeText(this, "Error al cargar el capítulo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}