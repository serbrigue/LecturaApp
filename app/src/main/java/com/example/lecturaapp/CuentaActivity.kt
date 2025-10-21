package com.example.lecturaapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class CuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 1. Configurar Opciones y Eventos (Simulados)
        configurarOpcion(
            R.id.opcionConfiguracion,
            "Configuración",
            R.drawable.ic_settings,
            "Abriendo configuración..."
        )

        configurarOpcion(
            R.id.opcionModoOscuro,
            "Modo Oscuro",
            R.drawable.ic_dark_mode,
            "Cambiando a Modo Oscuro... (Simulación)"
        )

        configurarOpcion(
            R.id.opcionAcercaDe,
            "Acerca de Lectura App",
            R.drawable.ic_info,
            "Versión 1.0.0"
        )
    }

    private fun configurarOpcion(layoutId: Int, titulo: String, iconoResId: Int, toastMessage: String) {
        val opcionView = findViewById<View>(layoutId)

        // Configurar el contenido del layout incluido
        val icono = opcionView.findViewById<ImageView>(R.id.opcionIcono)
        val tituloTextView = opcionView.findViewById<TextView>(R.id.opcionTitulo)

        tituloTextView.text = titulo
        icono.setImageResource(iconoResId)

        // Manejar el evento de clic con un Toast simple
        opcionView.setOnClickListener {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}