package com.wts.nequicooficial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuiaUsoActivity : AppCompatActivity() {

    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var ivFlechaCerrarGuia: ImageView
    private lateinit var btnAceptarGuia: TextView
    private lateinit var btnNoMostrarGuia: TextView
    private lateinit var indicador1: View
    private lateinit var indicador2: View
    private lateinit var indicador3: View
    private lateinit var indicador4: View
    private lateinit var indicador5: View
    private lateinit var indicador6: View
    private lateinit var indicador7: View
    private lateinit var indicador8: View

    private val indicadores = mutableListOf<View>()
    private var currentPage = 0
    private val totalPages = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guia_uso)
        
        // Configurar barra de estado
        window.statusBarColor = android.graphics.Color.parseColor("#200020")
        supportActionBar?.hide()

        initViews()
        setupClickListeners()
        setupScrollListener()
        updateIndicators(0)
    }

    private fun initViews() {
        horizontalScrollView = findViewById(R.id.horizontalScrollView)
        ivFlechaCerrarGuia = findViewById(R.id.ivFlechaCerrarGuia)
        btnAceptarGuia = findViewById(R.id.btnAceptarGuia)
        btnNoMostrarGuia = findViewById(R.id.btnNoMostrarGuia)
        
        indicador1 = findViewById(R.id.indicador1)
        indicador2 = findViewById(R.id.indicador2)
        indicador3 = findViewById(R.id.indicador3)
        indicador4 = findViewById(R.id.indicador4)
        indicador5 = findViewById(R.id.indicador5)
        indicador6 = findViewById(R.id.indicador6)
        indicador7 = findViewById(R.id.indicador7)
        indicador8 = findViewById(R.id.indicador8)

        indicadores.addAll(listOf(indicador1, indicador2, indicador3, indicador4, indicador5, indicador6, indicador7, indicador8))
    }

    private fun setupClickListeners() {
        ivFlechaCerrarGuia.setOnClickListener {
            cerrarGuia()
        }

        btnAceptarGuia.setOnClickListener { cerrarGuia() }
        btnNoMostrarGuia.setOnClickListener {
            // Guardar preferencia para no volver a mostrar
            try {
                val sp = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                sp.edit().putBoolean("show_guide", false).apply()
            } catch (_: Exception) { }
            cerrarGuia()
        }
    }

    private fun setupScrollListener() {
        horizontalScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollX = horizontalScrollView.scrollX
            val slideWidth = horizontalScrollView.width + 40 // 300dp + 20dp margin
            
            currentPage = (scrollX / slideWidth).coerceIn(0, totalPages - 1)
            updateIndicators(currentPage)
        }
    }

    private fun updateIndicators(activePage: Int) {
        for (i in indicadores.indices) {
            if (i == activePage) {
                indicadores[i].setBackgroundResource(R.drawable.indicador_activo)
            } else {
                indicadores[i].setBackgroundResource(R.drawable.indicador_inactivo)
            }
        }
    }

    private fun cerrarGuia() {
        // No guardar el flag para que la guía aparezca siempre
        val vieneDePin = intent.getBooleanExtra("viene_de_pin", false)
        
        if (vieneDePin) {
            android.util.Log.d("GuiaUso", "Guía cerrada (viene de PinActivity) - ir a InicioActivity")
            
            // Si viene de PinActivity, ir a InicioActivity
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            android.util.Log.d("GuiaUso", "Guía cerrada (viene del perfil) - volver a InicioActivity")
            
            // Si viene del perfil, simplemente cerrar y volver a InicioActivity
            finish()
        }
    }

    override fun onBackPressed() {
        cerrarGuia()
    }
} 