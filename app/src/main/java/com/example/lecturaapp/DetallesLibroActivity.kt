package com.example.lecturaapp
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class DetallesLibroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalles_libro_activity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val titulo = intent.getStringExtra("titulo")
        val autor = intent.getStringExtra("autor")
        val descripcion = intent.getStringExtra("descripcion")
        val imagenResId = intent.getIntExtra("imagenResId", 0)

        val tituloTextView: TextView = findViewById(R.id.tituloDetalleTextView)
        val autorTextView: TextView = findViewById(R.id.autorDetalleTextView)
        val descripcionTextView: TextView = findViewById(R.id.descripcionDetalleTextView)
        val portadaDetalleImageView: ImageView = findViewById(R.id.portadaDetalleImageView)

        // Cambiamos a ExtendedFloatingActionButton
        val verCapitulosButton: ExtendedFloatingActionButton = findViewById(R.id.verCapitulosButton)

        tituloTextView.text = titulo
        autorTextView.text = autor
        descripcionTextView.text = descripcion
        if (imagenResId != 0) {
            portadaDetalleImageView.setImageResource(imagenResId)
        }

        verCapitulosButton.setOnClickListener {
            val intent = Intent(this, CapitulosActivity::class.java)
            intent.putExtra("titulo", titulo)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}