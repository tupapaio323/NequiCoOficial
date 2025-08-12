package com.wts.nequicooficial

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.text.Spannable
import android.text.style.UnderlineSpan
import android.text.style.RelativeSizeSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Typeface
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.os.Handler
import android.os.Looper
import android.widget.VideoView
import android.net.Uri
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Vibrator
import android.content.Context
import com.wts.nequicooficial.InicioActivity

class PinActivity : AppCompatActivity() {
    private val pinDigits = mutableListOf<Char>()
    private val realPinDigits = mutableListOf<Char>() // Lista para los números reales
    private lateinit var pinBoxes: List<TextView>
    private val pinLength = 4
    private val asteriskColor = 0xFFD90080.toInt()
    private var animacionActiva = false
    private lateinit var firebaseManager: FirebaseManager
    private var userNumber: String = ""
    
    private var isAppInBackground = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        supportActionBar?.hide()
        window.statusBarColor = android.graphics.Color.parseColor("#200020")
        ocultarSoloNavegacion()
        
        // Inicializar Firebase
        firebaseManager = FirebaseManager(this)
        
        // Obtener número del usuario desde SharedPreferences
        val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
        userNumber = sharedPref.getString("user_number", "") ?: ""
        
        // Debug removido
        


        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        pinBoxes = listOf(
            findViewById(R.id.pinBox1),
            findViewById(R.id.pinBox2),
            findViewById(R.id.pinBox3),
            findViewById(R.id.pinBox4)
        )

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                // Hacer vibrar el teléfono
                vibrarTelefono()
                
                if (pinDigits.size < pinLength) {
                    val button = findViewById<Button>(id)
                    val digit = button.text.toString()[0] // Obtener el número real del botón
                    pinDigits.add('*')
                    realPinDigits.add(digit)
                    updatePinBoxes()
                    if (pinDigits.size == pinLength) {
                        verificarPinConFirebase()
                    }
                }
            }
        }
        findViewById<ImageView>(R.id.btnDelete).setOnClickListener {
            // Hacer vibrar el teléfono
            vibrarTelefono()
            
            if (pinDigits.isNotEmpty()) {
                pinDigits.removeAt(pinDigits.size - 1)
                realPinDigits.removeAt(realPinDigits.size - 1)
                updatePinBoxes()
            }
        }

        val tvForgot = findViewById<TextView>(R.id.tvForgot)
        val spannableForgot = SpannableString("¿Se te olvidó?")
        spannableForgot.setSpan(
            UnderlineSpan(),
            0, spannableForgot.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvForgot.text = spannableForgot

        updatePinBoxes()
        
        // Cargar imágenes desde assets
        cargarImagenesDesdeAssets()
        

    }
    
    // Función para cargar imágenes desde assets en PinActivity
    private fun cargarImagenesDesdeAssets() {
        try {
            
            
            // Cargar imágenes del PIN
            cargarImagenDesdeAssets(findViewById(R.id.btnDelete), "borrar")
            
            
        } catch (e: Exception) {
            
        }
    }
    

    
    override fun onPause() {
        super.onPause()
        isAppInBackground = true
    }
    
    override fun onResume() {
        super.onResume()
        isAppInBackground = false
        ocultarSoloNavegacion()
    }
    
    private fun vibrarTelefono() {
        try {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // Para Android 8.0 (API 26) y superior
                val vibrationEffect = android.os.VibrationEffect.createOneShot(50, android.os.VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(vibrationEffect)
            } else {
                // Para versiones anteriores
                @Suppress("DEPRECATION")
                vibrator.vibrate(50)
            }
        } catch (e: Exception) { }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        ocultarSoloNavegacion()
    }

    private fun ocultarSoloNavegacion() {
        try {
            val flags = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            window.decorView.systemUiVisibility = flags
        } catch (_: Exception) {}
    }
    private fun updatePinBoxes() {
        val monedaBold = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.moneda_bold)
        for (i in 0 until pinLength) {
            if (i < pinDigits.size) {
                val spannable = SpannableString("*")
                spannable.setSpan(
                    ForegroundColorSpan(asteriskColor),
                    0, 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    CustomTypefaceSpan(monedaBold ?: android.graphics.Typeface.DEFAULT),
                    0, 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    RelativeSizeSpan(1.2f),
                    0, 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                pinBoxes[i].text = spannable
            } else {
                pinBoxes[i].text = ""
            }
        }
    }

    private fun verificarPinConFirebase() {
        android.util.Log.d("PinActivity", "=== INICIO VERIFICACIÓN PIN ===")
        android.util.Log.d("PinActivity", "userNumber: '$userNumber'")
        android.util.Log.d("PinActivity", "userNumber.length: ${userNumber.length}")
        android.util.Log.d("PinActivity", "userNumber.isEmpty(): ${userNumber.isEmpty()}")
        
        if (userNumber.isEmpty()) {
            android.util.Log.e("PinActivity", "Número de usuario no encontrado")
            return
        }
        
        val pinIngresado = realPinDigits.joinToString("")
        android.util.Log.d("PinActivity", "PIN ingresado: '$pinIngresado'")
        android.util.Log.d("PinActivity", "PIN ingresado.length: ${pinIngresado.length}")
        android.util.Log.d("PinActivity", "Verificando PIN: '$pinIngresado' para usuario: '$userNumber'")
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                android.util.Log.d("PinActivity", "Iniciando búsqueda de usuario en FirebaseManager...")
                // Buscar el usuario en la estructura actual
                val user = withContext(Dispatchers.IO) {
                    firebaseManager.getUserByNumber(userNumber)
                }
                
                android.util.Log.d("PinActivity", "Usuario obtenido: $user")
                
                if (user != null) {
                    android.util.Log.d("PinActivity", "PIN del usuario: '${user.pin}'")
                    android.util.Log.d("PinActivity", "PIN ingresado: '$pinIngresado'")
                    android.util.Log.d("PinActivity", "¿PINs coinciden?: ${user.pin == pinIngresado}")
                    android.util.Log.d("PinActivity", "¿PINs son iguales?: ${user.pin.equals(pinIngresado)}")
                }
                
                if (user != null && user.pin == pinIngresado) {
                    // Guardar datos del usuario en SharedPreferences
                    val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_saldo", user.saldo)
                        putString("user_pin", user.pin)
                        putString("numero_firebase", userNumber)
                        apply()
                    }
                    
                    android.util.Log.d("PinActivity", "Usuario autenticado exitosamente")
                    mostrarAnimacionCargaYContinuar()
                } else {
                    // PIN incorrecto o usuario no encontrado
                    android.util.Log.w("PinActivity", "PIN incorrecto o usuario no encontrado")
                    if (user == null) {
                        android.util.Log.w("PinActivity", "Usuario no encontrado")
                    } else {
                        android.util.Log.w("PinActivity", "PIN incorrecto - esperado: ${user.pin}, ingresado: $pinIngresado")
                    }
                    limpiarPin()
                    mostrarErrorConVideoYImagen()
                }
            } catch (e: Exception) {
                android.util.Log.e("PinActivity", "Error verificando PIN: ${e.message}")
                limpiarPin()
            }
        }
    }
    
    private fun limpiarPin() {
        pinDigits.clear()
        realPinDigits.clear()
        updatePinBoxes()
    }
    
    private fun mostrarAnimacionCargaYContinuar() {
        val inflater = LayoutInflater.from(this)
        val loadingView = inflater.inflate(R.layout.loading_animation, null)
        val videoCarga = loadingView.findViewById<VideoView>(R.id.videoCarga)
        val rootView = findViewById<View>(android.R.id.content) as android.view.ViewGroup
        loadingView.alpha = 0f
        loadingView.scaleX = 0f
        loadingView.scaleY = 0f
        rootView.addView(loadingView)
        
        // Configurar el VideoView para fondo transparente
        videoCarga.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        videoCarga.setZOrderOnTop(true)
        
        // Configurar y reproducir el video
        VideoLoader.cargarVideoDesdeAssets(this, videoCarga, "io")
        
        // Agregar listener de error
        videoCarga.setOnErrorListener { mp, what, extra ->
            android.util.Log.e("PinActivity", "Error reproduciendo video: what=$what, extra=$extra")
            // Continuar sin video si hay error
            loadingView.animate()
                .alpha(1f)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(850)
                .withEndAction {
                    Handler(Looper.getMainLooper()).postDelayed({
                        loadingView.animate()
                            .alpha(0f)
                            .scaleX(0f)
                            .scaleY(0f)
                            .setDuration(750)
                            .withEndAction {
                                rootView.removeView(loadingView)
                                val intent = Intent(this, InicioActivity::class.java)
                                startActivity(intent)
                                finish()
                            }.start()
                    }, 2000)
                }.start()
            true // Manejar el error
        }
        videoCarga.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            
            // Deshabilitar audio del video para evitar conflictos con grabación
            mediaPlayer.setVolume(0f, 0f)
            
            // Iniciar el video después de que esté preparado
            videoCarga.start()
            // Mostrar el layout solo después de que el video esté listo
            loadingView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(850)
                .withEndAction {
                    Handler(Looper.getMainLooper()).postDelayed({
                        videoCarga.stopPlayback()
                        loadingView.animate()
                            .alpha(0f)
                            .scaleX(0f)
                            .scaleY(0f)
                            .setDuration(750)
                            .withEndAction {
                                rootView.removeView(loadingView)
                                
                                // Mostrar la guía siempre que se abra la app
                                val intent = Intent(this, GuiaUsoActivity::class.java).apply {
                                    putExtra("viene_de_pin", true)
                                }
                                
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                finish()
                            }.start()
                    }, 2000)
                }.start()
        }
    }
    
    private fun mostrarErrorConVideoYImagen() {
        // Primero reproducir el video io.mp4
        reproducirVideoIO()
    }
    
    private fun reproducirVideoIO() {
        val inflater = LayoutInflater.from(this)
        val videoLayout = inflater.inflate(R.layout.loading_animation, null)
        val videoIO = videoLayout.findViewById<VideoView>(R.id.videoCarga)
        val rootView = findViewById<View>(android.R.id.content) as android.view.ViewGroup
        
        // Configurar el VideoView para fondo transparente
        videoIO.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        videoIO.setZOrderOnTop(true)
        
        // Configurar y reproducir el video
        VideoLoader.cargarVideoDesdeAssets(this, videoIO, "io")
        
        // Configurar propiedades iniciales para animación
        videoLayout.alpha = 0f
        videoLayout.scaleX = 0f
        videoLayout.scaleY = 0f
        rootView.addView(videoLayout)
        
        videoIO.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            mediaPlayer.setVolume(0f, 0f)
            
            videoIO.start()
            videoLayout.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(850)
                .withEndAction {
                    Handler(Looper.getMainLooper()).postDelayed({
                        videoIO.stopPlayback()
                        videoLayout.animate()
                            .alpha(0f)
                            .scaleX(0f)
                            .scaleY(0f)
                            .setDuration(450)
                            .withEndAction {
                                rootView.removeView(videoLayout)
                                mostrarErrorConImagen()
                            }.start()
                    }, 300)
                }.start()
        }
        
        videoIO.setOnErrorListener { mp, what, extra ->
            android.util.Log.e("PinActivity", "Error reproduciendo video io.mp4: what=$what, extra=$extra")
            rootView.removeView(videoLayout)
            mostrarErrorConImagen()
            true
        }
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
            val nombreArchivo = "$nombreSinExtension.bin"
            establecerImagenDesencriptada(imageView, nombreArchivo)
        } catch (e: Exception) {
            android.util.Log.e("CargarImagen", "❌ Error cargando imagen $nombreSinExtension: ${e.message}", e)
        }
    }

    private fun mostrarErrorConImagen() {
        // Crear layout para el error
        val errorLayout = android.widget.FrameLayout(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
        }
        
        // Crear layout para el texto del error (separado de la imagen)
        val textLayout = android.widget.LinearLayout(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = android.widget.LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
        }
        
        // Crear TextView para el mensaje
        val tvErrorMessage = android.widget.TextView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
            text = "Pin incorrecto, contacta a "
            setTextColor(android.graphics.Color.WHITE)
            textSize = 16f
            gravity = android.view.Gravity.CENTER
            setPadding(170, 280, 0, 0)
        }
        
        // Crear TextView clickeable para "The boss"
        val tvTheBoss = android.widget.TextView(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = 0
            }
            text = "The boss"
            setTextColor(android.graphics.Color.WHITE)
            textSize = 16f
            gravity = android.view.Gravity.CENTER
            isClickable = true
            isFocusable = true
            setPadding(0, 280, 0, 0)
            setShadowLayer(8f, 0f, 0f, android.graphics.Color.parseColor("#e8b7d2"))
        }
        
        // Configurar click listener para abrir Telegram (canal oficial)
        tvTheBoss.setOnClickListener {
            try {
                val telegramUrl = "https://t.me/+d17o7xIvIqdkZGYx"
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(telegramUrl))
                startActivity(intent)
            } catch (e: Exception) {
                android.util.Log.e("PinActivity", "Error abriendo Telegram: ${e.message}", e)
            }
        }
        
        // Agregar elementos al layout de texto
        textLayout.addView(tvErrorMessage)
        textLayout.addView(tvTheBoss)
        
        // Crear ImageView para error.png
        val ivError = android.widget.ImageView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
                            // Usar PNG desencriptado
                establecerImagenDesencriptada(this, "error.bin")
            scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            setPadding(0, 215, 0, 0)
        }
        
        // Agregar elementos separados al errorLayout (imagen primero, texto después para que aparezca arriba)
        errorLayout.addView(ivError)
        errorLayout.addView(textLayout)
        
        // Agregar el layout al root
        val rootView = findViewById<View>(android.R.id.content) as android.view.ViewGroup
        rootView.addView(errorLayout)
        
        // Configurar propiedades para animación de arriba hacia abajo
        errorLayout.alpha = 0f
        errorLayout.translationY = -7000f
        
        // Mostrar el layout con animación de arriba hacia abajo más lenta
        errorLayout.animate()
            .alpha(1f)
            .translationY(-200f) // Mover a una posición mucho más abajo
            .setDuration(2000)
            .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
            .withEndAction {
                Handler(Looper.getMainLooper()).postDelayed({
                    errorLayout.animate()
                        .alpha(0f)
                        .translationY(-4000f)
                        .setDuration(1500)
                        .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                        .withEndAction {
                            rootView.removeView(errorLayout)
                        }.start()
                }, 3000)
            }.start()
    }
}

// CustomTypefaceSpan para aplicar la fuente moneda_bold
class CustomTypefaceSpan(private val typeface: Typeface) : TypefaceSpan("") {
    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, typeface)
    }
    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, typeface)
    }
    private fun applyCustomTypeFace(paint: TextPaint, tf: Typeface) {
        paint.typeface = tf
    }
} 