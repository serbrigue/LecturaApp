package com.example.lecturaapp

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class CapitulosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capitulos)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val tituloLibro = intent.getStringExtra("titulo")

        val tituloCapitulosTextView: TextView = findViewById(R.id.tituloCapitulosTextView)
        if (tituloLibro != null) {
            tituloCapitulosTextView.text = "Capítulos de\n$tituloLibro"
        } else {
            tituloCapitulosTextView.text = "Capítulos"
        }

        val capitulosContainer: LinearLayout = findViewById(R.id.capitulosContainer)

        val capitulos = listOf(
            "Capítulo 1: El inicio de la aventura",
            "Capítulo 2: El primer encuentro",
            "Capítulo 3: Un misterio por resolver",
            "Capítulo 4: La revelación",
            "Capítulo 5: El plan final"
        )

        for (capitulo in capitulos) {
            val textView = TextView(this)
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 16
            }
            textView.text = capitulo
            textView.textSize = 18f
            textView.setPadding(16, 16, 16, 16)
            textView.setBackgroundResource(R.drawable.rounded_textview)
            capitulosContainer.addView(textView)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}