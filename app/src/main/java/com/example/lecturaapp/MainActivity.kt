package com.example.lecturaapp
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity(), LibroAdapter.OnItemClickListener, CarruselAdapter.OnItemClickListener {

    private lateinit var destacadosRecyclerView: RecyclerView
    private lateinit var recientesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        destacadosRecyclerView = findViewById(R.id.destacadosRecyclerView)
        recientesRecyclerView = findViewById(R.id.recyclerView)

        val listaLibros = listOf(
            Libro(
                "Sobreviviendo al juego siendo un bárbaro",
                "Hansu Lee",
                "Después de pasar nueve años en 'Dungeon and Stone', un juego de rol que nadie ha logrado vencer, Hansu Lee finalmente llega a la mazmorra del jefe final. Sin embargo, al abrir la puerta, recibe un mensaje que indica que su tutorial ha sido completado y se encuentra en el cuerpo de Bjorn Yandel, un verdadero bárbaro dentro del juego. Una emocionante aventura le espera mientras se esfuerza por seguir las leyes de este mundo de ficción.",
                R.drawable.portada_barbaro,
                false
            ),
            Libro(
                "Punto de vista del lector omnisciente",
                "Sing-shong",
                "Dokja, un oficinista promedio, descubre que su novela web favorita, 'Tres formas de sobrevivir al Apocalipsis', se está volviendo realidad. Con un conocimiento único del desastre inminente, se esfuerza por alterar el destino de la historia y remodelar su mundo.",
                R.drawable.portada_lector,
                false
            ),
            Libro(
                "La vida después de la muerte",
                "Wook-Goh",
                "Bajo el glamuroso exterior de un poderoso rey se esconde la cáscara de un hombre, carente de propósito y voluntad. Reencarnado en un nuevo mundo lleno de magia y monstruos, el rey tiene una segunda oportunidad de revivir su vida.",
                R.drawable.portada_vida,
                false
            ),
            Libro(
                "Pick me up gacha infinito",
                "Heuk-Joo-Seung",
                "An Seo-jin, un jugador de alto rango en 'Pick Me Up' con el nombre de usuario 'Loki', es transportado al juego como un héroe... un héroe de nivel 1 y una estrella. Loki, una vez llamado el 'master of masters', ahora debe seguir las órdenes de su nuevo amo.",
                R.drawable.portada_gacha,
                false
            )
        )

        // Configurar el carrusel
        val carruselAdapter = CarruselAdapter(listaLibros, this)
        destacadosRecyclerView.adapter = carruselAdapter
        destacadosRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configurar la lista de actualizaciones recientes
        val recientesAdapter = LibroAdapter(listaLibros, this, this)
        recientesRecyclerView.adapter = recientesAdapter
        recientesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Buscar novelas..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, "Buscando: $query", Toast.LENGTH_SHORT).show()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // El evento ya es manejado por el SearchView, pero lo consumimos
                true
            }
            R.id.action_favorites -> {
                Toast.makeText(this, "Ver favoritos", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_account -> {
                // Lanza la actividad de cuenta/usuario
                val intent = Intent(this, CuentaActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(libro: Libro) {
        val intent = Intent(this, DetallesLibroActivity::class.java)
        intent.putExtra("titulo", libro.titulo)
        intent.putExtra("autor", libro.autor)
        intent.putExtra("descripcion", libro.descripcion)
        intent.putExtra("imagenResId", libro.imagen)
        startActivity(intent)
    }
}