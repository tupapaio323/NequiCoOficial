package com.wts.nequicooficial

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.HorizontalScrollView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import android.widget.VideoView
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import android.view.LayoutInflater
import android.view.Gravity
import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.InputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.content.res.ResourcesCompat
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.PendingIntent


class InicioActivity : AppCompatActivity() {
    
    private lateinit var etNombre: EditText
    private lateinit var tvHola: TextView
    private lateinit var tvMonto: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvMontoComa: TextView
    private lateinit var tvTotalComa: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6Abajo: Button
    private lateinit var ivMovements: ImageView
    private lateinit var ivServicios: ImageView
    private lateinit var ivEnvia: ImageView
    private lateinit var layoutBlanco: View
    
    // Utilidad para convertir dp a px
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }


    
    // Propiedades del modal
    private lateinit var modalOverlay: RelativeLayout
    private lateinit var modalScrollView: ScrollView
    private lateinit var modalScrollViewQR: ScrollView
    private lateinit var tvModalClose: TextView
    private lateinit var btnModalEnviar: Button

    // Layout enviar plata
    private lateinit var layoutEnviarPlata: RelativeLayout
    private lateinit var layoutPago: RelativeLayout
    private lateinit var ivFlechaRegresoEnviar: ImageView
    private lateinit var ivNovalidado: ImageView
    private lateinit var btnListoNovalidado: Button
    
    // Layout botón 3
    private lateinit var layoutBotón3: RelativeLayout

    
    // Layout botón 7
    private lateinit var layoutBotón7: RelativeLayout
    private lateinit var ivFlechaRegresoBotón7: ImageView
    private lateinit var ivEstrellaBotón7: ImageView
    private lateinit var tvTítuloBotón7: TextView
    private lateinit var ivCostoBotón7: ImageView
    private lateinit var etCampo1Botón7: TextView
    private lateinit var etCampo2Botón7: EditText
    private lateinit var etCampo3Botón7: EditText
    private lateinit var ivRecordarBotón7: ImageView
    private lateinit var tvEscogePlataBotón7: TextView
    private lateinit var ivDisponibleBotón7: ImageView
    private lateinit var btnConfirmarBotón7: TextView
    
    // Modal tipo de cuenta
    private lateinit var modalTipoCuenta: RelativeLayout
    private lateinit var contenedorModalTipoCuenta: RelativeLayout
    private lateinit var tvCorriente: TextView
    private lateinit var tvAhorros: TextView
    
    // Variables para el modal del botón 8
    private lateinit var modalBotón8: RelativeLayout
    private lateinit var contenedorModalBotón8: RelativeLayout
    private lateinit var ivFlechaRegresoModal8: ImageView
    private lateinit var btnListoModal8: TextView
    private lateinit var tvMasInfoModal8: TextView
    
    // Variables para el layout Transfiya
    private lateinit var layoutTransfiya: RelativeLayout
    private lateinit var ivFlechaRegresoTransfiya: ImageView
    private lateinit var tvTransfiya: TextView
    private lateinit var etCelTransfiya: EditText
    private lateinit var etCuantoTransfiya: EditText
    private lateinit var etMensajeTransfiya: EditText
    private lateinit var btnSigueTransfiya: TextView
    private lateinit var progressBarSigueTransfiya: ProgressBar
    
    // Modal de confirmación Transfiya
    private lateinit var modalScrollViewConfirmacionTransfiya: ScrollView
    private lateinit var modalBackgroundOverlayTransfiya: View
    private lateinit var tvNumeroCelularConfirmacionTransfiya: TextView
    private lateinit var tvCantidadConfirmacionTransfiya: TextView
    private lateinit var btnConfirmaTransfiya: TextView

    private lateinit var tvModalCloseConfirmacionTransfiya: TextView

    // Layout contactos
    private lateinit var layoutContactos: RelativeLayout
    
    // Layout servicios
    private lateinit var layoutServicios: RelativeLayout
    private lateinit var ivFlechaRegresoServicios: ImageView
    private lateinit var ivBorrarContactos: ImageView
    private lateinit var etNombreContacto: EditText
    private lateinit var etNumeroContacto: EditText
    private lateinit var btnGuardarContactoServicios: TextView
    private lateinit var llListaContactos: LinearLayout
    
    // Layout movimientos
    private lateinit var layoutMovimientos: RelativeLayout
    private lateinit var ivHomeMovimientos: ImageView
    private lateinit var ivMovementsMovimientos: ImageView
    private lateinit var ivServiciosMovimientos: ImageView
    private lateinit var btnHoyMovimientos: Button
    private lateinit var btnMasMovimientos: Button
    private lateinit var ivHoyMovimientos: ImageView
    private lateinit var ivMasMovimientos: ImageView
    private lateinit var tvHoyTextoMovimientos: TextView
    private lateinit var scrollEnviosMovimientos: ScrollView
    private lateinit var llEnviosMovimientos: FrameLayout
    private lateinit var btnCargarMas: TextView
    private lateinit var progressBarCargarMas: ProgressBar
    private lateinit var ivMinMovimientos: ImageView
    private lateinit var layoutVerificacionBancolombia: RelativeLayout
    
    // Layout para cambiar saldo
    private lateinit var layoutCambiarSaldo: RelativeLayout
    private lateinit var cajaCambiarSaldo: RelativeLayout
    private lateinit var etNuevoSaldo: EditText
    private lateinit var btnAplicarSaldo: Button
    private lateinit var btnCancelarSaldo: Button
    private lateinit var btnCambiarSaldo: Button
    private lateinit var ivFlechaRegresoVerificacion: ImageView
    private lateinit var tvVerificaInfo: TextView
    private lateinit var ivLVerificacion: ImageView
    private lateinit var tvVasAEnviarA: TextView
    private lateinit var tvCuantoVerificacion: TextView
    private lateinit var tvBancoVerificacion: TextView
    private lateinit var tvBancoValorVerificacion: TextView
    private lateinit var tvTipoCuentaVerificacion: TextView
    private lateinit var tvTipoCuentaValorVerificacion: TextView
    private lateinit var tvNumeroCuentaVerificacion: TextView
    private lateinit var tvNumeroCuentaValorVerificacion: TextView
    private lateinit var tvDeDondeSaldraVerificacion: TextView
    private lateinit var tvDeDondeSaldraValorVerificacion: TextView
    private lateinit var tvCantidadVerificacion: TextView
    private lateinit var btnEnviarVerificacion: TextView
    private lateinit var tvCorrigeInfo: TextView
    private lateinit var tvNombreBancolombia: TextView
    
    // Variables para paginación de envíos
    private var enviosPorPagina = 8
    private var enviosActualmenteVisibles = 8
    private lateinit var btn1Movimientos: Button
    private lateinit var btn2Movimientos: Button
    private lateinit var btn3Movimientos: Button
    private lateinit var btn4Movimientos: Button
    
    // Variables para el estado de movimientos
    private var estadoMovimientosActual = "hoy" // "hoy" o "ayer"
    private var vieneDeTransfiya = false // Para rastrear si viene de Transfiya
    // Último pago QR (para agregar envio5 luego de mostrar movimientos)
    private var lastQrNombre: String = ""
    private var lastQrCantidad: String = ""
    private var lastQrReferencia: String = ""
    private var lastQrFecha: String = ""
    private var lastQrValor: Long = 0L

    // Parsear monto en COP escrito como "$ 50.000,00" o "50.000" a valor en pesos (Long)
    private fun parsePesos(valorTexto: String): Long {
        return try {
            var s = valorTexto
                .replace("\u00A0", "")
                .replace(Regex("\\s"), "")
                .replace("$", "")
                .trim()

            val hasComma = s.contains(',')
            val hasDot = s.contains('.')

            fun dropDecimals(t: String, sepIndex: Int): String {
                return t.substring(0, sepIndex)
            }

            if (hasComma && hasDot) {
                val lastComma = s.lastIndexOf(',')
                val lastDot = s.lastIndexOf('.')
                val lastSep = maxOf(lastComma, lastDot)
                val digitsAfter = s.length - lastSep - 1
                if (digitsAfter == 2) {
                    s = dropDecimals(s, lastSep)
                }
                s = s.replace(",", "").replace(".", "")
            } else if (hasComma) {
                val lastComma = s.lastIndexOf(',')
                val digitsAfter = s.length - lastComma - 1
                if (digitsAfter == 2) {
                    s = dropDecimals(s, lastComma)
                }
                s = s.replace(",", "")
            } else if (hasDot) {
                val lastDot = s.lastIndexOf('.')
                val digitsAfter = s.length - lastDot - 1
                if (digitsAfter == 2) {
                    s = dropDecimals(s, lastDot)
                }
                s = s.replace(".", "")
            }

            s.filter { it.isDigit() }.toLongOrNull() ?: 0L
        } catch (_: Exception) { 0L }
    }
    
    // Constante para selección de imagen
    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE = 1001
        private const val QR_SCANNER_REQUEST_CODE = 1002
    }
    
    // Variables para la capa de Transfiya
    private lateinit var capaTransfiyaLayout: FrameLayout

    // Lanzador para permiso POST_NOTIFICATIONS (Android 13+)
    private val requestPostNotifications = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        Log.d("Permisos", "POST_NOTIFICATIONS concedido: $granted")
    }
    
    // Variables para el layout de agregar envío
    private lateinit var layoutAgregarEnvio: RelativeLayout
    private lateinit var ivFlechaRegresoAgregarEnvio: ImageView
    private lateinit var etNombreEnvio: EditText
    private lateinit var etCantidadEnvio: EditText
    private lateinit var btnAgregarEnvio: Button
    
    // Funciones para manejar floating labels
    private fun actualizarFloatingLabelCel(hasFocus: Boolean, hasContent: Boolean) {
        val etCel = findViewById<EditText>(R.id.etCel)
        val tvAdvertenciaCel = findViewById<TextView>(R.id.tvAdvertenciaCel)
        val etCuanto = findViewById<EditText>(R.id.etCuanto)
        val etMensaje = findViewById<EditText>(R.id.etMensaje)
        
        if (hasFocus || hasContent) {
            // Mostrar floating label rosa
            etCel.hint = ""
            etCel.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            
            // Crear o actualizar el floating label
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCel)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCel
                    text = "Cel"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                
                // Agregar el floating label al layout padre del EditText
                val parentLayout = etCel.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                
                // Posicionar el floating label
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCel.left + 45
                params.topMargin = etCel.top + 25
                floatingLabel.layoutParams = params
            }
            
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
            
            // MANTENER el texto de advertencia visible (NO ocultarlo)
            // tvAdvertenciaCel.animate() - COMENTADO: No ocultar el texto de advertencia
            
            // NO mover el campo Mensaje hacia arriba (solo el campo Cuanto se mueve con animarCampoCuanto)
            // etMensaje.animate() - COMENTADO: No mover el campo Mensaje
            
            // Mover solo el campo Cuanto hacia arriba (esto se maneja con animarCampoCuanto)
            // etCuanto.animate() - COMENTADO: Se maneja con animarCampoCuanto
            
            // COMENTADO: No mover el floating label de Cuanto para que permanezca en su posición original
            // val floatingLabelCuanto = findViewById<TextView>(R.id.tvFloatingLabelCuanto)
            // floatingLabelCuanto?.animate()
            //     ?.translationY(-(tvAdvertenciaCel.height.toFloat() + 20f))
            //     ?.setDuration(350)
            //     ?.start()
        } else {
            // Ocultar floating label
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCel)
            floatingLabel?.animate()
                ?.alpha(0f)
                ?.setDuration(200)
                ?.withEndAction {
                    floatingLabel?.visibility = View.INVISIBLE
                }
                ?.start()
            
            // Restaurar hint normal
            etCel.hint = "Cel"
            
            // MANTENER el texto de advertencia visible
            // tvAdvertenciaCel.visibility = View.VISIBLE - COMENTADO: No cambiar visibilidad
            // tvAdvertenciaCel.animate() - COMENTADO: No cambiar animación
            
            // NO restaurar posición del campo Mensaje (no se movió)
            // etMensaje.animate() - COMENTADO: No restaurar posición
            
            // Restaurar posición del campo Cuanto (se maneja con animarCampoCuanto)
            // etCuanto.animate() - COMENTADO: Se maneja con animarCampoCuanto
            
            // COMENTADO: No restaurar el floating label de Cuanto para que permanezca en su posición original
            // val floatingLabelCuanto = findViewById<TextView>(R.id.tvFloatingLabelCuanto)
            // floatingLabelCuanto?.animate()
            //     ?.translationY(0f)
            //     ?.setDuration(350)
            //     ?.start()
        }
    }
    
    private fun actualizarFloatingLabelCelTransfiya(hasFocus: Boolean, hasContent: Boolean) {
        val etCelTransfiya = findViewById<EditText>(R.id.etCelTransfiya)
        
        if (hasFocus || hasContent) {
            // Mostrar floating label rosa
            etCelTransfiya.hint = ""
            etCelTransfiya.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            
            // Mover el texto del campo hacia abajo cuando tiene foco
            if (hasFocus) {
                etCelTransfiya.animate()
                    .translationY(17f)
                    .setDuration(200)
                    .start()
            }
            
            // Crear o actualizar el floating label
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCelTransfiya)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCelTransfiya
                    text = "Cel"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                
                // Agregar el floating label al layout padre del EditText
                val parentLayout = etCelTransfiya.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                
                // Posicionar el floating label
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCelTransfiya.left + 45
                params.topMargin = etCelTransfiya.top + 25
                floatingLabel.layoutParams = params
            }
            
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        } else {
            // Ocultar floating label
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCelTransfiya)
            floatingLabel?.animate()
                ?.alpha(0f)
                ?.setDuration(200)
                ?.withEndAction {
                    floatingLabel?.visibility = View.INVISIBLE
                }
                ?.start()
            
            // Restaurar hint normal
            etCelTransfiya.hint = "Cel"
            
            // Restaurar posición del texto del campo
            etCelTransfiya.animate()
                .translationY(0f)
                .setDuration(200)
                .start()
        }
    }
    
    private fun actualizarFloatingLabelCuantoTransfiya(hasFocus: Boolean, hasContent: Boolean) {
        val etCuantoTransfiya = findViewById<EditText>(R.id.etCuantoTransfiya)
        
        if (hasFocus || hasContent) {
            // Mostrar floating label rosa
            etCuantoTransfiya.hint = ""
            etCuantoTransfiya.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            
            // Mover el texto del campo hacia abajo cuando tiene foco
            if (hasFocus) {
                etCuantoTransfiya.animate()
                    .translationY(20f)
                    .setDuration(200)
                    .start()
            }
            
            // Crear o actualizar el floating label
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuantoTransfiya)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCuantoTransfiya
                    text = "¿Cuanto?"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                
                // Agregar el floating label al layout padre del EditText
                val parentLayout = etCuantoTransfiya.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                
                // Posicionar el floating label
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCuantoTransfiya.left + 45
                params.topMargin = etCuantoTransfiya.top + 25
                floatingLabel.layoutParams = params
            }
            
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        } else {
            // Ocultar floating label
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuantoTransfiya)
            floatingLabel?.animate()
                ?.alpha(0f)
                ?.setDuration(200)
                ?.withEndAction {
                    floatingLabel?.visibility = View.INVISIBLE
                }
                ?.start()
            
            // Restaurar hint normal
            etCuantoTransfiya.hint = "¿Cuanto?"
            
            // Restaurar posición del texto del campo
            etCuantoTransfiya.animate()
                .translationY(0f)
                .setDuration(200)
                .start()
        }
    }
    
    private fun actualizarFloatingLabelCuentaBancolombia(hasFocus: Boolean, hasContent: Boolean) {
        val etCuentaBancolombia = findViewById<EditText>(R.id.etCampo2Botón7)
        
        if (hasFocus || hasContent) {
            // Mostrar floating label rosa
            etCuentaBancolombia.hint = ""
            etCuentaBancolombia.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            
            // Mover el texto del campo hacia abajo cuando tiene foco
            if (hasFocus) {
                etCuentaBancolombia.animate()
                    .translationY(15f)
                    .setDuration(200)
                    .start()
            }
            
            // Crear o actualizar el floating label
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuentaBancolombia)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCuentaBancolombia
                    text = "Numero de cuenta"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                
                // Agregar el floating label al layout padre del EditText
                val parentLayout = etCuentaBancolombia.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                
                // Posicionar el floating label
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCuentaBancolombia.left + 45
                params.topMargin = etCuentaBancolombia.top + 25
                floatingLabel.layoutParams = params
            }
            
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        } else {
            // Ocultar floating label
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuentaBancolombia)
            floatingLabel?.animate()
                ?.alpha(0f)
                ?.setDuration(200)
                ?.withEndAction {
                    floatingLabel?.visibility = View.INVISIBLE
                }
                ?.start()
            
            // Restaurar hint normal
            etCuentaBancolombia.hint = "Numero de cuenta"
            
            // Restaurar posición del texto del campo
            etCuentaBancolombia.animate()
                .translationY(0f)
                .setDuration(200)
                .start()
        }
    }
    
    private fun actualizarFloatingLabelCuantoBancolombia(hasFocus: Boolean, hasContent: Boolean) {
        val etCuantoBancolombia = findViewById<EditText>(R.id.etCampo3Botón7)
        
        if (hasFocus || hasContent) {
            // Mostrar floating label rosa
            etCuantoBancolombia.hint = ""
            etCuantoBancolombia.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            
            // Mover el texto del campo hacia abajo cuando tiene foco
            if (hasFocus) {
                etCuantoBancolombia.animate()
                    .translationY(17f)
                    .setDuration(200)
                    .start()
            }
            
            // Crear o actualizar el floating label
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuantoBancolombia)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCuantoBancolombia
                    text = "¿Cuanto?"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                
                // Agregar el floating label al layout padre del EditText
                val parentLayout = etCuantoBancolombia.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                
                // Posicionar el floating label
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCuantoBancolombia.left + 45
                params.topMargin = etCuantoBancolombia.top + 25
                floatingLabel.layoutParams = params
            }
            
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        } else {
            // Ocultar floating label
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuantoBancolombia)
            floatingLabel?.animate()
                ?.alpha(0f)
                ?.setDuration(200)
                ?.withEndAction {
                    floatingLabel?.visibility = View.INVISIBLE
                }
                ?.start()
            
            // Restaurar hint normal
            etCuantoBancolombia.hint = "¿Cuanto?"
            
            // Restaurar posición del texto del campo
            etCuantoBancolombia.animate()
                .translationY(0f)
                .setDuration(200)
                .start()
        }
    }
    
    private fun actualizarFloatingLabelCuanto(hasFocus: Boolean, hasContent: Boolean) {
        val etCuanto = findViewById<EditText>(R.id.etCuanto)
        
        if (hasFocus || hasContent) {
            // Mostrar floating label rosa
            etCuanto.hint = ""
            etCuanto.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            
            // Crear o actualizar el floating label
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuanto)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCuanto
                    text = "¿Cuanto?"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                
                // Agregar el floating label al layout padre del EditText
                val parentLayout = etCuanto.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                
                // Posicionar el floating label
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCuanto.left + 45
                params.topMargin = etCuanto.top + 25
                floatingLabel.layoutParams = params
            }
            
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        } else {
            // Ocultar floating label
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuanto)
            floatingLabel?.animate()
                ?.alpha(0f)
                ?.setDuration(200)
                ?.withEndAction {
                    floatingLabel?.visibility = View.INVISIBLE
                }
                ?.start()
            
            // Restaurar hint normal
            etCuanto.hint = "¿Cuanto?"
        }
    }
    private lateinit var ivTransfiyaCapa: ImageView
    private lateinit var btnListoCapaTransfiya: TextView
    
    // Modal
    private lateinit var ivModalImage: ImageView
    
    // Modal de confirmación
    private lateinit var modalScrollViewConfirmacion: ScrollView
    private lateinit var securityManager: SecurityManager
    private lateinit var tvModalTitleConfirmacion: TextView
    private lateinit var tvNombreContactoConfirmacion: TextView
    private lateinit var tvAlNumeroCelular: TextView
    private lateinit var tvNumeroCelularConfirmacion: TextView
    private lateinit var tvCuantoConfirmacion: TextView
    private lateinit var tvCantidadConfirmacion: TextView
    private lateinit var tvLaPlataSaldraDe: TextView
    private lateinit var btnConfirma: TextView
    private lateinit var btnCorrigeAlgo: TextView
    private lateinit var tvModalCloseConfirmacion: TextView
    
    // Variables para ocultar/mostrar saldo
    private var saldoOculto = false
    private var saldoOriginal = ""
    private var totalOriginal = ""
    
    // Variables para comprobante de Transfiya
    private var comprobanteTransfiyaBitmap: android.graphics.Bitmap? = null
    private var datosTransfiyaCelular = ""
    private var datosTransfiyaCantidad = ""
    private var datosTransfiyaNombre = ""
    private var datosTransfiyaReferencia = ""
    private var datosTransfiyaFecha = ""
    
    // Variables para comprobante base
    private var datosComprobanteBase: ComprobanteTransfiyaData? = null
    private var datosNumeroCelularBase: String = ""
    
    // Variables para nombres de Bancolombia
    private var nombreBancolombiaGuardado = ""
    
    // Layout de perfil de usuario
    private lateinit var layoutPerfilUsuario: View
    private lateinit var ivFlechaRegresoPerfil: ImageView
    private lateinit var ivCamaraPerfil: ImageView
    private lateinit var llRectangulo1: LinearLayout
    private lateinit var llRectangulo2: LinearLayout
    private lateinit var llRectangulo3: LinearLayout
    private lateinit var llRectangulo4: LinearLayout
    private lateinit var etNombrePerfil: EditText
    private lateinit var tvNumeroTelefonoPerfil: TextView
    private lateinit var tvDeTuNequi: TextView
    private lateinit var ivFotoPerfil: ImageView
    private lateinit var ivHome: ImageView
    
    // Layout de nombre de víctima
    private lateinit var layoutNombreVictima: View
    private lateinit var etNombreVictima: EditText
    private lateinit var btnGuardarContactoVictima: Button
    private lateinit var btnCancelarContacto: Button
    private lateinit var ivFlechaRegresoNombre: ImageView
    
    // Control de animación
    private var isPerfilAbriendo = false
    private var isPerfilCerrando = false
    // Evita múltiples ejecuciones al pulsar "Paga"
    private var isPagaProcessing: Boolean = false
    
    // Clase para datos del comprobante de Transfiya
    data class ComprobanteTransfiyaData(
        val bitmap: android.graphics.Bitmap,
        val referencia: String,
        val fechaFormateada: String
    )
    




    
    // Variables para el drag del modal
    private var modalInitialY = 0f
    private var modalCurrentY = 0f
    private var isDraggingModal = false
    
    // Firebase
    private lateinit var firebaseManager: FirebaseManager

    private var userNumber: String = ""
    
    // Variables para pull-to-refresh
    private var isRefreshing = false
    private var startY = 0f
    private val pullThreshold = 150f // Distancia mínima para activar refresh
    private lateinit var progressBarRefresh: ProgressBar
    
    // Variables para detectar cambios de densidad

    private var isAppInBackground = false
    // Programación de refresco automático al cambiar de día
    private var midnightHandler: Handler? = null
    private var midnightRunnable: Runnable? = null
    

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        
        // (Removido) Logs y Toast de prueba al iniciar
        supportActionBar?.hide()
        window.statusBarColor = android.graphics.Color.parseColor("#200020")
        
        // Pedir permiso de notificaciones si es necesario (Android 13+)
        solicitarPermisoNotificacionesSiNecesario()
        // Programar refresco automático al cambiar el día
        programarRefrescoCambioDeDia()

        // Obtener y loguear el token FCM al iniciar (si existe / o pedirlo)
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "Token actual: $token")
                    getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                        .edit().putString("fcm_token", token).apply()
                    // Si tenemos el número del usuario, guardar token en Firebase para mapeo número -> token
                    val numero = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE).getString("user_number", "") ?: ""
                    if (numero.isNotEmpty()) {
                        firebaseManager.saveFcmTokenForNumber(numero, token) { ok ->
                            Log.d("FCM", "Guardado token en DB (${if (ok) "OK" else "FAIL"}) para $numero")
                        }
                    } else {
                        Log.d("FCM", "user_number vacío, no se guarda token en DB aún")
                    }
                } else {
                    Log.w("FCM", "No se pudo obtener el token: ${task.exception?.message}")
                }
            }
        } catch (e: Exception) {
            Log.e("FCM", "Error obteniendo token: ${e.message}", e)
        }
        
        // Inicializar SecurityManager y verificar seguridad
        securityManager = SecurityManager(this)
        if (!securityManager.performSecurityChecks()) {
            Log.e("InicioActivity", "❌ Verificaciones de seguridad fallaron")
            Toast.makeText(this, "Dispositivo no seguro detectado", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        
        // Resetear estado de Transfiya al abrir la aplicación
        vieneDeTransfiya = false
        
        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre)
        tvHola = findViewById(R.id.tvHola)
        tvMonto = findViewById(R.id.tvMonto)
        tvTotal = findViewById(R.id.tvTotal)
        tvMontoComa = findViewById(R.id.tvMontoComa)
        tvTotalComa = findViewById(R.id.tvTotalComa)
        // Bajar un poco los ",00" para mejor alineación visual
        runCatching {
            val shiftPx = 1.5f * resources.displayMetrics.density
            tvMontoComa.translationY = shiftPx
            tvTotalComa.translationY = shiftPx
        }
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6Abajo = findViewById(R.id.btn6Abajo)
        // Asegurar que quede siempre invisible pero clickeable
        btn6Abajo.alpha = 0f
        btn6Abajo.isClickable = true
        btn6Abajo.isEnabled = true
        btn6Abajo.isFocusable = false
        ivMovements = findViewById(R.id.ivMovements)
        ivServicios = findViewById(R.id.ivServicios)
        ivEnvia = findViewById(R.id.ivEnvia)
        layoutBlanco = findViewById(R.id.layoutBlanco)


        
        // Inicializar propiedades del modal
        modalOverlay = findViewById(R.id.modalOverlay)
        modalScrollView = findViewById(R.id.modalScrollView)
        tvModalClose = findViewById(R.id.tvModalClose)
        btnModalEnviar = findViewById(R.id.btnModalEnviar)

        // Layout botón 3
        layoutBotón3 = findViewById(R.id.layoutBotón3)

        // Layout enviar plata
        layoutEnviarPlata = findViewById(R.id.layoutEnviarPlata)
        layoutPago = findViewById(R.id.layoutPago)
        ivFlechaRegresoEnviar = findViewById(R.id.ivFlechaRegresoEnviar)
        ivNovalidado = findViewById(R.id.ivNovalidado)
        btnListoNovalidado = findViewById(R.id.btnListoNovalidado)
        
        // Layout botón 7
        layoutBotón7 = findViewById(R.id.layoutBotón7)
        ivFlechaRegresoBotón7 = findViewById(R.id.ivFlechaRegresoBotón7)
        ivEstrellaBotón7 = findViewById(R.id.ivEstrellaBotón7)
        tvTítuloBotón7 = findViewById(R.id.tvTítuloBotón7)
        ivCostoBotón7 = findViewById(R.id.ivCostoBotón7)
        etCampo1Botón7 = findViewById(R.id.etCampo1Botón7)
        etCampo2Botón7 = findViewById(R.id.etCampo2Botón7)
        // Limitar a 11 dígitos numéricos
        etCampo2Botón7.filters = arrayOf(android.text.InputFilter.LengthFilter(11))
        etCampo2Botón7.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        etCampo3Botón7 = findViewById(R.id.etCampo3Botón7)
        ivRecordarBotón7 = findViewById(R.id.ivRecordarBotón7)
        tvEscogePlataBotón7 = findViewById(R.id.tvEscogePlataBotón7)
        ivDisponibleBotón7 = findViewById(R.id.ivDisponibleBotón7)
        btnConfirmarBotón7 = findViewById(R.id.btnConfirmarBotón7)
        
        // Modal tipo de cuenta
        modalTipoCuenta = findViewById(R.id.modalTipoCuenta)
        contenedorModalTipoCuenta = findViewById(R.id.contenedorModalTipoCuenta)
        tvCorriente = findViewById(R.id.tvCorriente)
        tvAhorros = findViewById(R.id.tvAhorros)
        
        // Inicializar variables del modal del botón 8
        modalBotón8 = findViewById(R.id.modalBotón8)
        contenedorModalBotón8 = findViewById(R.id.contenedorModalBotón8)
        ivFlechaRegresoModal8 = findViewById(R.id.ivFlechaRegresoModal8)
        btnListoModal8 = findViewById(R.id.btnListoModal8)
        tvMasInfoModal8 = findViewById(R.id.tvMasInfoModal8)
        
        // Inicializar variables del layout Transfiya
        layoutTransfiya = findViewById(R.id.layoutTransfiya)
        ivFlechaRegresoTransfiya = findViewById(R.id.ivFlechaRegresoTransfiya)
        tvTransfiya = findViewById(R.id.tvTransfiya)
        etCelTransfiya = findViewById(R.id.etCelTransfiya)
        etCuantoTransfiya = findViewById(R.id.etCuantoTransfiya)
        etMensajeTransfiya = findViewById(R.id.etMensajeTransfiya)
        btnSigueTransfiya = findViewById(R.id.btnSigueTransfiya)
        progressBarSigueTransfiya = findViewById(R.id.progressBarSigueTransfiya)
        
        // Modal de confirmación Transfiya
        modalScrollViewConfirmacionTransfiya = findViewById(R.id.modalScrollViewConfirmacionTransfiya)
        modalBackgroundOverlayTransfiya = findViewById(R.id.modalBackgroundOverlayTransfiya)
        tvNumeroCelularConfirmacionTransfiya = findViewById(R.id.tvNumeroCelularConfirmacionTransfiya)
        tvCantidadConfirmacionTransfiya = findViewById(R.id.tvCantidadConfirmacionTransfiya)
        btnConfirmaTransfiya = findViewById(R.id.btnConfirmaTransfiya)

        tvModalCloseConfirmacionTransfiya = findViewById(R.id.tvModalCloseConfirmacionTransfiya)
        
        // Inicializar variables de la capa de Transfiya
        capaTransfiyaLayout = findViewById(R.id.capaTransfiyaLayout)
        ivTransfiyaCapa = findViewById(R.id.ivTransfiyaCapa)
        btnListoCapaTransfiya = findViewById(R.id.btnListoCapaTransfiya)

        // Layout contactos
        layoutContactos = findViewById(R.id.layoutContactos)
        
        // Layout servicios
        layoutServicios = findViewById(R.id.layoutServicios)
        ivFlechaRegresoServicios = findViewById(R.id.ivFlechaRegresoServicios)
        ivBorrarContactos = findViewById(R.id.ivBorrarContactos)
        android.util.Log.d("BorrarContactos", "🔍 Botón ivBorrarContactos encontrado: ${ivBorrarContactos != null}")
        if (ivBorrarContactos != null) {
            android.util.Log.d("BorrarContactos", "✅ ivBorrarContactos inicializado correctamente")
        } else {
            android.util.Log.e("BorrarContactos", "❌ ERROR: No se pudo encontrar ivBorrarContactos en onCreate")
        }
        etNombreContacto = findViewById(R.id.etNombreContacto)
        etNumeroContacto = findViewById(R.id.etNumeroContacto)
        btnGuardarContactoServicios = findViewById(R.id.btnGuardarContacto)
        llListaContactos = findViewById(R.id.llListaContactos)
        
        // Layout movimientos
        layoutMovimientos = findViewById(R.id.layoutMovimientos)
        ivHomeMovimientos = findViewById(R.id.ivHomeMovimientos)
        ivMovementsMovimientos = findViewById(R.id.ivMovementsMovimientos)
        ivServiciosMovimientos = findViewById(R.id.ivServiciosMovimientos)
        btnHoyMovimientos = findViewById(R.id.btnHoyMovimientos)
        btnMasMovimientos = findViewById(R.id.btnMasMovimientos)
        ivHoyMovimientos = findViewById(R.id.ivHoyMovimientos)
        ivMasMovimientos = findViewById(R.id.ivMasMovimientos)
        tvHoyTextoMovimientos = findViewById(R.id.tvHoyTextoMovimientos)
        scrollEnviosMovimientos = findViewById(R.id.scrollEnviosMovimientos)
        llEnviosMovimientos = findViewById(R.id.llEnviosMovimientos)
        btnCargarMas = findViewById(R.id.btnCargarMas)
        progressBarCargarMas = findViewById(R.id.progressBarCargarMas)
        ivMinMovimientos = findViewById(R.id.ivMinMovimientos)
        
        // Inicializar elementos del layout de verificación Bancolombia
        layoutVerificacionBancolombia = findViewById(R.id.layoutVerificacionBancolombia)
        ivFlechaRegresoVerificacion = findViewById(R.id.ivFlechaRegresoVerificacion)
        tvVerificaInfo = findViewById(R.id.tvVerificaInfo)
        ivLVerificacion = findViewById(R.id.ivLVerificacion)
        tvVasAEnviarA = findViewById(R.id.tvVasAEnviarA)
        tvCuantoVerificacion = findViewById(R.id.tvCuantoVerificacion)
        tvBancoVerificacion = findViewById(R.id.tvBancoVerificacion)
        tvBancoValorVerificacion = findViewById(R.id.tvBancoValorVerificacion)
        tvTipoCuentaVerificacion = findViewById(R.id.tvTipoCuentaVerificacion)
        tvTipoCuentaValorVerificacion = findViewById(R.id.tvTipoCuentaValorVerificacion)
        tvNumeroCuentaVerificacion = findViewById(R.id.tvNumeroCuentaVerificacion)
        tvNumeroCuentaValorVerificacion = findViewById(R.id.tvNumeroCuentaValorVerificacion)
        tvDeDondeSaldraVerificacion = findViewById(R.id.tvDeDondeSaldraVerificacion)
        tvDeDondeSaldraValorVerificacion = findViewById(R.id.tvDeDondeSaldraValorVerificacion)
        tvCantidadVerificacion = findViewById(R.id.tvCantidadVerificacion)
        btnEnviarVerificacion = findViewById(R.id.btnEnviarVerificacion)
        tvCorrigeInfo = findViewById(R.id.tvCorrigeInfo)
        tvNombreBancolombia = findViewById(R.id.tvNombreBancolombia)
        
        // Debug: Verificar que el botón se encontró correctamente
        android.util.Log.d("CargarMas", "Botón encontrado: ${btnCargarMas != null}")
        android.util.Log.d("CargarMas", "ProgressBar encontrado: ${progressBarCargarMas != null}")
        
        // Configurar click listener inmediatamente
        btnCargarMas.setOnClickListener {
            android.util.Log.d("CargarMas", "🔥 CLICK DETECTADO EN BOTÓN CARGAR MÁS! 🔥")
            cargarMasEnvios()
        }
        
        // BOTÓN FIJO FUNCIONAL (reemplaza al del ScrollView)
        val btnCargarMasFuncional = findViewById<TextView>(R.id.btnCargarMasFijo)
        btnCargarMasFuncional.setOnClickListener {
            android.util.Log.d("CargarMas", "🚀 CLICK EN BOTÓN FUNCIONAL! 🚀")
            cargarMasEnvios()
        }
        
        // Usar el botón fijo como el botón principal
        btnCargarMas = btnCargarMasFuncional
        
        btn1Movimientos = findViewById(R.id.btn1Movimientos)
        btn2Movimientos = findViewById(R.id.btn2Movimientos)
        btn3Movimientos = findViewById(R.id.btn3Movimientos)
        btn4Movimientos = findViewById(R.id.btn4Movimientos)
        
        // Establecer alpha 0 solo para los botones "Hoy" y "Mas"
        btnHoyMovimientos.alpha = 0f
        btnMasMovimientos.alpha = 0f
        
        // Modal
        ivModalImage = findViewById(R.id.ivModalImage)
        modalScrollViewQR = findViewById(R.id.modalScrollViewQR)
        
        // Modal de confirmación
        modalScrollViewConfirmacion = findViewById(R.id.modalScrollViewConfirmacion)
        tvModalTitleConfirmacion = findViewById(R.id.tvModalTitleConfirmacion)
        tvNombreContactoConfirmacion = findViewById(R.id.tvNombreContactoConfirmacion)
        tvAlNumeroCelular = findViewById(R.id.tvAlNumeroCelular)
        tvNumeroCelularConfirmacion = findViewById(R.id.tvNumeroCelularConfirmacion)
        tvCuantoConfirmacion = findViewById(R.id.tvCuantoConfirmacion)
        tvCantidadConfirmacion = findViewById(R.id.tvCantidadConfirmacion)
        tvLaPlataSaldraDe = findViewById(R.id.tvLaPlataSaldraDe)
        btnConfirma = findViewById(R.id.btnConfirma)
        btnCorrigeAlgo = findViewById(R.id.btnCorrigeAlgo)
        tvModalCloseConfirmacion = findViewById(R.id.tvModalCloseConfirmacion)
        
        // Inicializar LinearLayout de Depósito Bajo Monto
        val llDepositoBajoMonto = findViewById<LinearLayout>(R.id.llDepositoBajoMonto)
        val ivOjo = llDepositoBajoMonto.getChildAt(1) as ImageView
        
        // Configurar click listener para ocultar/mostrar saldo
        llDepositoBajoMonto.setOnClickListener {
            alternarVisibilidadSaldo(ivOjo)
        }
        
        // Layout de perfil de usuario
        layoutPerfilUsuario = findViewById(R.id.layoutPerfilUsuario)
        ivFlechaRegresoPerfil = findViewById(R.id.ivFlechaRegresoPerfil)
        ivCamaraPerfil = findViewById(R.id.ivCamaraPerfil)
        llRectangulo1 = findViewById(R.id.llRectangulo1)
        llRectangulo2 = findViewById(R.id.llRectangulo2)
        llRectangulo3 = findViewById(R.id.llRectangulo3)
        llRectangulo4 = findViewById(R.id.llRectangulo4)
        etNombrePerfil = findViewById(R.id.etNombrePerfil)
        tvNumeroTelefonoPerfil = findViewById(R.id.tvNumeroTelefonoPerfil)
        tvDeTuNequi = findViewById(R.id.tvDeTuNequi)
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil)
        ivHome = findViewById(R.id.ivHome)
        
        // Inicializar layout de nombre de víctima
        layoutNombreVictima = findViewById(R.id.layoutNombreVictima)
        etNombreVictima = findViewById(R.id.etNombreVictima)
        btnGuardarContactoVictima = findViewById(R.id.btnGuardarContactoVictima)
        btnCancelarContacto = findViewById(R.id.btnCancelarContacto)
        ivFlechaRegresoNombre = findViewById(R.id.ivFlechaRegresoNombre)
        
        // Inicializar layout de agregar envío
        layoutAgregarEnvio = findViewById(R.id.layoutAgregarEnvio)
        ivFlechaRegresoAgregarEnvio = findViewById(R.id.ivFlechaRegresoAgregarEnvio)
        etNombreEnvio = findViewById(R.id.etNombreEnvio)
        etCantidadEnvio = findViewById(R.id.etCantidadEnvio)
        btnAgregarEnvio = findViewById(R.id.btnAgregarEnvio)
        


        
        // Inicializar densidad actual

        
        // Configurar listener para cambios en la visibilidad del sistema UI
        configurarSistemaUI()
        
        // Cargar nombre guardado
        cargarNombre()
        
        // Configurar texto del monto
        configurarTextoMonto()
        configurarTextoTotal()
        
        // Inicializar Firebase
        firebaseManager = FirebaseManager(this)
        // Gate de versión mínima soportada
        CoroutineScope(Dispatchers.Main).launch {
            val appConfig = withContext(Dispatchers.IO) { firebaseManager.getAppConfig() }
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val currentVersionCode = if (android.os.Build.VERSION.SDK_INT >= 28) (pInfo.longVersionCode and 0xFFFFFFFF).toInt() else pInfo.versionCode
            if (currentVersionCode < appConfig.minSupportedVersion) {
                mostrarModalForzarActualizacion(appConfig.updateUrl)
                return@launch
            }
        }
        
        // Obtener número del usuario desde SharedPreferences
        val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
        userNumber = sharedPref.getString("user_number", "") ?: ""
        
        // Cargar saldo guardado
        cargarSaldoGuardado()
        
        // Inicializar pull-to-refresh
        progressBarRefresh = findViewById(R.id.progressBarRefresh)
        configurarPullToRefresh()
        
        // Los nombres de Bancolombia se guardarán dinámicamente desde el layout de guardar contactos
        
        // Inicializar elementos del layout para cambiar saldo
        layoutCambiarSaldo = findViewById(R.id.layoutCambiarSaldo)
        cajaCambiarSaldo = findViewById(R.id.cajaCambiarSaldo)
        etNuevoSaldo = findViewById(R.id.etNuevoSaldo)
        btnAplicarSaldo = findViewById(R.id.btnAplicarSaldo)
        btnCancelarSaldo = findViewById(R.id.btnCancelarSaldo)
        btnCambiarSaldo = findViewById(R.id.btnCambiarSaldo)
        
        // Ajustar posiciones de elementos scrolleables según DPI
        val initialDpi = resources.displayMetrics.densityDpi
        android.util.Log.d("InicioActivity", "🚀 DPI detectado al iniciar la app: $initialDpi")
        
        // Ajustar elementos específicos con escalado reducido
        ajustarElementosEspecificosSegunDPI()
        
        // Ajustar elementos del layout de movimientos según DPI
        ajustarElementosMovimientosSegunDPI()
        
        // Configurar ",00" más pequeños en los textos iniciales

        
        // Hacer el EditText no editable directamente y evitar teclado
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        etNombre.isCursorVisible = false
        etNombre.isLongClickable = false
        etNombre.isClickable = false
        etNombre.setOnClickListener(null)
        
        // Configurar click listeners para los botones
        configurarBotones()
        
        // Configurar botón 5 para abrir modal (siempre "invisible" por alpha) y estar por encima de btn6Abajo
        btn5.alpha = 0f
        btn5.isClickable = false
        btn5.setOnClickListener { mostrarModal() }
        btn5.bringToFront()

        // Configurar botón 6 (abajo de 5) para abrir modal QR independiente (siempre alpha 0)
        btn6Abajo.alpha = 0f
        btn6Abajo.isClickable = true
        btn6Abajo.isEnabled = true
        btn6Abajo.setOnClickListener { mostrarModalQR() }
        // Asegurar z-order correcto: btn5 por encima de btn6Abajo
        btn5.bringToFront()

        // Configurar botón 6 para abrir layout enviar plata
        val btn6 = findViewById<Button>(R.id.btn6)
        btn6.alpha = 0f
        btn6.setOnClickListener {
            mostrarLayoutEnviarPlata()
        }

        // Eliminar lógica de long-press en Home relacionada con guardar QR con nombre
        ivHome.setOnLongClickListener(null)

        // Configurar botón 7 para abrir layout botón 7
        val btn7 = findViewById<Button>(R.id.btn7)
        btn7.alpha = 0f
        btn7.setOnClickListener {
            mostrarVideoAIYLayoutBotón7()
        }
        
        val btn8 = findViewById<Button>(R.id.btn8)
        btn8.alpha = 0f
        btn8.setOnClickListener {
            // Ocultar el modal anterior (modal del botón 6) antes de mostrar el modal del botón 8
            ocultarModal()
            mostrarModalBotón8()
        }

        // Configurar flecha de regreso del layout enviar plata
        ivFlechaRegresoEnviar.setOnClickListener {
            // Limpiar los campos antes de ocultar el layout
            limpiarCamposEnviarPlata()
            ocultarLayoutEnviarPlata()
            ocultarModal()
        }





        // Configurar botón Sigue del layout enviar plata
        val btnSigue = findViewById<TextView>(R.id.btnEntraEnviarPlata)
        val progressBarSigue = findViewById<ProgressBar>(R.id.progressBarSigue)
        
        btnSigue.setOnClickListener {
            // Ocultar texto del botón y mostrar ProgressBar
            btnSigue.text = ""
            progressBarSigue.visibility = View.VISIBLE
            
            // Poner el botón y ProgressBar oscuros
            btnSigue.alpha = 0.5f
            progressBarSigue.alpha = 0.5f
            
            // Después de 0.3 segundos, abrir el modal de confirmación
            val handler = android.os.Handler(android.os.Looper.getMainLooper())
            handler.postDelayed({
                // Restaurar el botón
                btnSigue.text = "Sigue"
                progressBarSigue.visibility = View.GONE
                btnSigue.alpha = 1f
                progressBarSigue.alpha = 1f
                
                // Abrir modal de confirmación
                mostrarModalConfirmacion()
            }, 500) // 0.3 segundos
        }

        // Configurar formateo de números para el campo Cel
        val etCel = findViewById<EditText>(R.id.etCel)
        val etCuanto = findViewById<EditText>(R.id.etCuanto)
        
        // Función para animar el campo "¿Cuánto?" hacia arriba
        fun animarCampoCuanto(mostrar: Boolean) {
            // COMENTADO: No mover el campo "¿Cuánto?" para que permanezca en su posición original
            // val distanciaAnimacion = 50f // Distancia en dp
            // val duracion = 300L // Duración en milisegundos
            
            // if (mostrar) {
            //     // Mover hacia arriba cuando hay contenido en Cel
            //     etCuanto.animate()
            //         .translationY(-distanciaAnimacion)
            //         .setDuration(duracion)
            //         .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
            //         .start()
            // } else {
            //     // Volver a la posición original cuando no hay contenido
            //     etCuanto.animate()
            //         .translationY(0f)
            //         .setDuration(duracion)
            //         .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
            //         .start()
            // }
            
            // El campo "¿Cuánto?" permanece en su posición original
            android.util.Log.d("AnimacionCuanto", "Campo '¿Cuánto?' permanece en posición original")
        }
        
        // Función para verificar si ambos campos tienen contenido
        fun verificarCamposCompletos() {
            val textoCel = etCel.text.toString().replace(" ", "")
            val textoCuanto = etCuanto.text.toString().replace("$", "").replace(".", "").replace(" ", "")
            
            if (textoCel.isNotEmpty() && textoCuanto.isNotEmpty()) {
                // Activar botón
                btnSigue.animate()
                    .alpha(1f)
                    .setDuration(350)
                    .start()
                btnSigue.isClickable = true
                btnSigue.isFocusable = true
            } else {
                // Desactivar botón
                btnSigue.animate()
                    .alpha(0.5f)
                    .setDuration(350)
                    .start()
                btnSigue.isClickable = false
                btnSigue.isFocusable = false
            }
        }
        
        // Verificar estado inicial del botón
        verificarCamposCompletos()
        
        etCel.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val str = s.toString().replace(" ", "")
                if (str.length <= 10) {
                    val formatted = formatPhoneNumber(str)
                    if (formatted != s.toString()) {
                        etCel.removeTextChangedListener(this)
                        etCel.setText(formatted)
                        etCel.setSelection(formatted.length)
                        etCel.addTextChangedListener(this)
                    }
                } else {
                    // Si excede 10 dígitos, mantener solo los primeros 10 con formato
                    val limitedStr = str.substring(0, 10)
                    val formatted = formatPhoneNumber(limitedStr)
                    etCel.removeTextChangedListener(this)
                    etCel.setText(formatted)
                    etCel.setSelection(formatted.length)
                    etCel.addTextChangedListener(this)
                }
                
                // Actualizar floating label
                val hasContent = str.isNotEmpty()
                val hasFocus = etCel.hasFocus()
                actualizarFloatingLabelCel(hasFocus, hasContent)
                
                // COMENTADO: No animar el campo "¿Cuánto?" para que permanezca en su posición original
                // animarCampoCuanto(hasContent)
                
                // Verificar campos completos después de cada cambio
                verificarCamposCompletos()
            }
        })


        
        // Mantener formato cuando pierde el enfoque
        etCel.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val currentText = etCel.text.toString().replace(" ", "")
                if (currentText.isNotEmpty()) {
                    val formatted = formatPhoneNumber(currentText)
                    etCel.setText(formatted)
                }
            }
            
            val hasContent = etCel.text.toString().replace(" ", "").isNotEmpty()
            actualizarFloatingLabelCel(hasFocus, hasContent)
            
            // COMENTADO: No animar el campo "¿Cuánto?" para que permanezca en su posición original
            // animarCampoCuanto(hasContent)
        }

        // Configurar formateo de números para el campo Cuanto
        
        etCuanto.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val str = s.toString().replace("$", "").replace(".", "").replace(" ", "")
                if (str.isNotEmpty()) {
                    val formatted = formatCurrency(str)
                    if (formatted != s.toString()) {
                        etCuanto.removeTextChangedListener(this)
                        etCuanto.setText(formatted)
                        etCuanto.setSelection(formatted.length)
                        etCuanto.addTextChangedListener(this)
                    }
                }
                
                // Actualizar floating label
                val hasContent = str.isNotEmpty()
                val hasFocus = etCuanto.hasFocus()
                actualizarFloatingLabelCuanto(hasFocus, hasContent)
                
                // Verificar campos completos después de cada cambio
                verificarCamposCompletos()
            }
        })


        
        // Configurar comportamiento del campo Cuanto
        etCuanto.setOnFocusChangeListener { _, hasFocus ->
            val hasContent = etCuanto.text.toString().replace("$", "").replace(".", "").replace(" ", "").isNotEmpty()
            actualizarFloatingLabelCuanto(hasFocus, hasContent)
        }

        // Desactivar campo Mensaje
        val etMensaje = findViewById<EditText>(R.id.etMensaje)
        etMensaje.isEnabled = false
        etMensaje.isFocusable = false
        etMensaje.isFocusableInTouchMode = false
        // Configurar click listener para el candado
        findViewById<ImageView>(R.id.ivLock).setOnClickListener {
            // El candado ahora no hace nada
        }

        
        // Cargar imágenes desde assets (archivos .bin encriptados)
        cargarImagenesDesdeAssets()
        
        // Configurar click listener para el botón 3
        btn3.setOnClickListener {
            mostrarLayoutBotón3()
        }
        

    }

    private fun solicitarPermisoNotificacionesSiNecesario() {
        val isTiramisuOrAbove = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU
        if (isTiramisuOrAbove) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                requestPostNotifications.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    
    // Calcula el tiempo hasta las 00:01 del próximo día y programa un refresco
    private fun programarRefrescoCambioDeDia() {
        try {
            cancelarRefrescoCambioDeDia()
            val now = java.util.Calendar.getInstance()
            val next = java.util.Calendar.getInstance().apply {
                add(java.util.Calendar.DAY_OF_YEAR, 1)
                set(java.util.Calendar.HOUR_OF_DAY, 0)
                set(java.util.Calendar.MINUTE, 1)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            val delay = (next.timeInMillis - now.timeInMillis).coerceAtLeast(1L)
            midnightHandler = Handler(Looper.getMainLooper())
            midnightRunnable = Runnable {
                maybeRefreshMovimientosIfDayChanged()
                programarRefrescoCambioDeDia()
            }
            midnightHandler?.postDelayed(midnightRunnable!!, delay)
            Log.d("Movimientos", "⏰ Refresco al cambio de día programado en ${delay / 1000}s")
        } catch (e: Exception) {
            Log.e("Movimientos", "Error programando refresco de día: ${e.message}", e)
        }
    }
    
    private fun cancelarRefrescoCambioDeDia() {
        try {
            midnightRunnable?.let { midnightHandler?.removeCallbacks(it) }
            midnightRunnable = null
            midnightHandler = null
        } catch (_: Exception) { }
    }
    
    private fun mostrarDialogoCambioNombre() {
        val nombreActual = etNombre.text.toString()
        val input = EditText(this)
        input.setText(nombreActual)
        input.hint = "Escribe tu nombre"
        input.setSingleLine()
        
        AlertDialog.Builder(this)
            .setTitle("Cambiar nombre")
            .setView(input)
            .setPositiveButton("Aceptar") { _, _ ->
                val nuevoNombre = input.text.toString().trim()
                if (nuevoNombre.isNotEmpty()) {
                    etNombre.setText(nuevoNombre)
                    guardarNombre(nuevoNombre)
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun cargarNombre() {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val nombreGuardado = sharedPref.getString("nombre_usuario", "")
        if (nombreGuardado?.isNotEmpty() == true) {
            etNombre.setText(nombreGuardado)
        }
    }
    
    private fun guardarNombre(nombre: String) {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nombre_usuario", nombre)
            apply()
        }
    }
    
                private fun mostrarVideoMovimientos() {
        try {
            android.util.Log.d("VideoMovimientos", "Iniciando reproducción de video mov.mp4")
            
            // Ocultar layout de Transfiya ANTES de reproducir el video
            ocultarLayoutTransfiya()
            
            // Ocultar layout de servicios si está visible
            if (layoutBotón3.visibility == View.VISIBLE) {
                ocultarLayoutBotón3()
            }
            
            // Ocultar servicios.png si está visible
            ivServicios.visibility = View.INVISIBLE
            
            // Crear layout blanco para el video
            val videoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
                
                // Crear VideoView simple
                val videoMov = VideoView(this).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    ).apply {
                        // Mover más a la derecha
                        leftMargin = (10 * resources.displayMetrics.density).toInt()
                    }
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    setZOrderOnTop(true) // Necesario para que el video se muestre correctamente
                }
                
                videoLayout.addView(videoMov)
                
                // Agregar al root
                val rootView = findViewById<View>(android.R.id.content) as ViewGroup
                rootView.addView(videoLayout)
                
                // Configurar y reproducir video usando VideoLoader
                VideoLoader.cargarVideoDesdeAssets(this, videoMov, "mov")
                
                videoMov.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.setVolume(0f, 0f) // Sin audio
                    // Asegurar que el video se reproduzca inmediatamente
                    Handler(Looper.getMainLooper()).postDelayed({
                    videoMov.start()
                    }, 100) // Pequeño delay para asegurar que el video esté listo
                }
                
                videoMov.setOnCompletionListener {
                    // Video terminó, mostrar movimientos
                    mostrarLayoutMovimientosDesdeTransfiya()
                    rootView.removeView(videoLayout)
                }
                
                videoMov.setOnErrorListener { mp, what, extra ->
                    // Error, mostrar movimientos directamente
                    mostrarLayoutMovimientosDesdeTransfiya()
                    rootView.removeView(videoLayout)
                    true
                }
                
            } catch (e: Exception) {
                android.util.Log.e("VideoMovimientos", "Error al mostrar video mov.mp4", e)
                mostrarLayoutMovimientosDesdeTransfiya()
            }
        }
    
    private fun abrirLayoutMovimientos() {
        try {
            android.util.Log.d("Movimientos", "Abriendo layout de movimientos después del video")
            
            // Ocultar otros elementos
            ivMovements.visibility = View.INVISIBLE
            ivServicios.visibility = View.INVISIBLE
            ivEnvia.visibility = View.INVISIBLE
            layoutBlanco.visibility = View.INVISIBLE
            
            // Mostrar el layout de movimientos con animación desde la izquierda
            val layoutMovimientos = findViewById<RelativeLayout>(R.id.layoutMovimientos)
            val screenWidth = resources.displayMetrics.widthPixels.toFloat()
            layoutMovimientos.translationX = -screenWidth
            layoutMovimientos.visibility = View.VISIBLE
            layoutMovimientos.animate()
                .translationX(0f)
                .setDuration(350)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
            
            // Asegurar que el layout esté centrado y ocupe toda la pantalla
            layoutMovimientos.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            
            // Cargar envíos guardados desde SharedPreferences
            cargarEnviosDesdeSharedPreferences()
            
            // Deshabilitar interacciones cuando el layout está abierto
            deshabilitarInteracciones()
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error abriendo layout de movimientos: ${e.message}", e)
        }
    }
    

    
    private fun configurarTextoMonto() {
        val textoCompleto = "$ 1.000.000,00"
        val spannable = SpannableString(textoCompleto)
        
        // Hacer más pequeño el ",00"
        val inicioDecimal = textoCompleto.indexOf(",00")
        if (inicioDecimal != -1) {
            spannable.setSpan(
                RelativeSizeSpan(0.9f),
                inicioDecimal,
                textoCompleto.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        
        tvMonto.text = spannable
    }
    
    private fun configurarTextoTotal() {
        val textoCompleto = "Total $ 1.000.000,00"
        val spannable = SpannableString(textoCompleto)
        
        // Hacer más pequeño el ",00"
        val inicioDecimal = textoCompleto.indexOf(",00")
        if (inicioDecimal != -1) {
            spannable.setSpan(
                RelativeSizeSpan(0.8f),
                inicioDecimal,
                textoCompleto.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        
        tvTotal.text = spannable
    }

    private fun configurarBotones() {
        // Botón 1 - Regresa SIEMPRE al layout principal (no abrir Movimientos)
        btn1.setOnClickListener {
            // Ocultar layouts secundarios si están visibles
            if (layoutMovimientos.visibility == View.VISIBLE) {
                ocultarLayoutMovimientosSinTransicion()
            }
            if (layoutBotón3.visibility == View.VISIBLE) {
                ocultarLayoutBotón3()
            }
            // Asegurar que los overlays principales estén ocultos
            ivMovements.visibility = View.INVISIBLE
            ivServicios.visibility = View.INVISIBLE
            ivEnvia.visibility = View.INVISIBLE
            layoutBlanco.visibility = View.INVISIBLE

            // Volver al layout principal
            mostrarLayoutPrincipal()

            // Habilitar interacciones cuando no hay overlays visibles
            habilitarInteracciones()
        }
        
        // Botón 2 - Abre directamente el layout de movimientos sin video
        btn2.setOnClickListener {
            // Verificar si estamos en el layout de servicios
            if (layoutBotón3.visibility == View.VISIBLE) {
                // Si estamos en servicios, ocultar servicios y mostrar movimientos
                ocultarLayoutBotón3()
                mostrarLayoutMovimientosDesdeTransfiya()
            } else {
                // Si no estamos en servicios, abrir directamente movimientos
                mostrarLayoutMovimientosDesdeTransfiya()
            }
        }
        
        // Botón 3 - Hace invisible movements.png, visible servicios.png, invisible envia.png y layout blanco
        btn3.setOnClickListener {
            ivMovements.visibility = View.INVISIBLE
            ivServicios.visibility = View.VISIBLE
            ivEnvia.visibility = View.INVISIBLE
            layoutBlanco.visibility = View.INVISIBLE
            
            // Habilitar interacciones cuando envia.png no está visible
            habilitarInteracciones()
        }
        
        // Botón 4 - Toggle para envia.png y layout blanco con transición
        btn4.setOnClickListener {
            // Verificar en qué layout estamos
            if (layoutMovimientos.visibility == View.VISIBLE) {
                // Estamos en el layout de movimientos - regresar al layout principal
                android.util.Log.d("Botón4", "Regresando desde layout de movimientos al principal")
                ocultarLayoutMovimientosSinTransicion()
                mostrarLayoutPrincipal()
            } else if (layoutBotón3.visibility == View.VISIBLE) {
                // Estamos en el layout de servicios - regresar al layout principal
                android.util.Log.d("Botón4", "Regresando desde layout de servicios al principal")
                ocultarLayoutBotón3()
                mostrarLayoutPrincipal()
            } else {
                // Estamos en el layout principal
                android.util.Log.d("Botón4", "Ejecutando en layout principal")
                ivMovements.visibility = View.INVISIBLE
                ivServicios.visibility = View.INVISIBLE
                
                // Toggle para envia.png y layout blanco
                if (ivEnvia.visibility == View.VISIBLE) {
                    // Ocultar con transición
                    ivEnvia.visibility = View.INVISIBLE
                    layoutBlanco.visibility = View.INVISIBLE
                    
                    // Habilitar interacciones cuando envia.png no está visible
                    habilitarInteracciones()
                    
                    // Ocultar botón 5 y 6 (y desactivar clics)
                    btn5.visibility = View.INVISIBLE
                    btn5.isClickable = false
                    btn6Abajo.visibility = View.INVISIBLE
                    // Mantener clickeable aunque invisible (zona activa)
                    btn6Abajo.isClickable = true
                    btn6Abajo.isEnabled = true
                } else {
                    // Mostrar con transición
                    layoutBlanco.visibility = View.VISIBLE
                    ivEnvia.visibility = View.VISIBLE
                    
                    // Animación de transición para layout blanco
                    layoutBlanco.alpha = 0f
                    layoutBlanco.animate()
                        .alpha(1f)                    .setDuration(450)
                        .start()
                    
                    // Animación de transición para envia.png
                    ivEnvia.alpha = 0f
                    ivEnvia.animate()
                        .alpha(1f)
                        .setDuration(450)
                        .start()
                    
                    // Deshabilitar interacciones cuando envia.png está visible
                    deshabilitarInteracciones()
                    
                    // Mostrar botón 5 y 6 (btn6Abajo siempre alpha 0)
                    btn5.visibility = View.VISIBLE
                    btn5.isClickable = true
                    btn6Abajo.visibility = View.VISIBLE
                    btn6Abajo.alpha = 0f
                    btn6Abajo.isClickable = true
                    btn6Abajo.isEnabled = true
                    btn5.bringToFront()
                }
            }
        }
        
        // Configurar botones del layout movimientos
        configurarBotonesMovimientos()
        
        // Configurar botones para cambiar saldo
        configurarBotonesCambiarSaldo()
        
        // Configurar layout de perfil de usuario
        configurarLayoutPerfilUsuario()
    }
    
    private fun configurarBotonesMovimientos() {
        // Botón 1 para movimientos - Regresa al layout principal sin transición
        btn1Movimientos.setOnClickListener {
            ocultarLayoutMovimientosSinTransicion()
        }
        
        // Botón 2 para movimientos - Abre directamente el layout de movimientos sin video
        btn2Movimientos.setOnClickListener {
            // Abrir directamente movimientos sin reproducir video
            mostrarLayoutMovimientosDesdeTransfiya()
        }
        
        // Botón 3 para movimientos - Hace invisible movements.png, visible servicios.png y muestra layout de servicios
        btn3Movimientos.setOnClickListener {
            ivMovementsMovimientos.visibility = View.INVISIBLE
            ivServiciosMovimientos.visibility = View.VISIBLE
            ivHomeMovimientos.visibility = View.INVISIBLE
            
            // Mostrar el layout de servicios
            mostrarLayoutBotón3()
        }
        
        // Botón 4 para movimientos - Regresa al layout principal
        btn4Movimientos.setOnClickListener {
            // Regresar al layout principal
            ocultarLayoutMovimientosSinTransicion()
            mostrarLayoutPrincipal()
        }

        // Configurar click para el botón "Mas movimientos"
        btnMasMovimientos.setOnClickListener {
            mostrarSoloMasMovimientos()
        }

        // Configurar click para el botón "Hoy"
        btnHoyMovimientos.setOnClickListener {
            mostrarSoloHoy()
        }

    }
    
    private fun configurarBotonesCambiarSaldo() {
        // Botón invisible para cambiar saldo (solo funciona en layout principal)
        // Configurar el tvTotal para que al tocarlo abra el layout de cambiar saldo
        tvTotal.setOnClickListener {
            // Debug: verificar visibilidad de todos los layouts
            android.util.Log.d("CambiarSaldo", "=== VERIFICANDO LAYOUTS ===")
            android.util.Log.d("CambiarSaldo", "layoutMovimientos: ${layoutMovimientos.visibility}")
            android.util.Log.d("CambiarSaldo", "layoutEnviarPlata: ${layoutEnviarPlata.visibility}")
            android.util.Log.d("CambiarSaldo", "layoutBotón3: ${layoutBotón3.visibility}")
            android.util.Log.d("CambiarSaldo", "layoutTransfiya: ${layoutTransfiya.visibility}")
            android.util.Log.d("CambiarSaldo", "layoutContactos: ${layoutContactos.visibility}")
            
            // Verificar que estemos en el layout principal
            if (layoutMovimientos.visibility != View.VISIBLE && 
                layoutEnviarPlata.visibility != View.VISIBLE && 
                layoutBotón3.visibility != View.VISIBLE &&
                layoutTransfiya.visibility != View.VISIBLE &&
                layoutContactos.visibility != View.VISIBLE) {
                android.util.Log.d("CambiarSaldo", "TODOS LOS LAYOUTS ESTÁN OCULTOS - MOSTRANDO LAYOUT CAMBIAR SALDO")
                mostrarLayoutCambiarSaldo()
            } else {
                android.util.Log.d("CambiarSaldo", "ALGÚN LAYOUT ESTÁ VISIBLE - NO MOSTRAR LAYOUT CAMBIAR SALDO")
            }
        }
        
        // Botón Aplicar saldo
        btnAplicarSaldo.setOnClickListener {
            aplicarNuevoSaldo()
        }
        
        // Botón Cancelar saldo
        btnCancelarSaldo.setOnClickListener {
            ocultarLayoutCambiarSaldo()
        }
        
        // Botón Cargar desde Firebase
        val btnCargarDesdeFirebase = findViewById<Button>(R.id.btnCargarDesdeFirebase)
        btnCargarDesdeFirebase.setOnClickListener {
            cargarSaldoDesdeFirebase()
        }
    }
    
    private fun mostrarSoloMasMovimientos() {
        // Mantener ambos botones visibles
        btnHoyMovimientos.visibility = View.VISIBLE
        btnMasMovimientos.visibility = View.VISIBLE
        
        // Desactivar "Hoy" (fondo blanco)
        btnHoyMovimientos.background = resources.getDrawable(R.drawable.rounded_button_white_border, theme)
                    btnHoyMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
        
        // Activar "Mas movimientos" (fondo rosa)
        btnMasMovimientos.background = resources.getDrawable(R.drawable.rounded_button_pink, theme)
                    btnMasMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.white))
        
        // Ocultar imagen "Hoy" y mostrar "Mas movimientos"
        ivHoyMovimientos.visibility = View.INVISIBLE
        ivMasMovimientos.visibility = View.VISIBLE
        
        // Ocultar el texto fijo de encabezado para evitar duplicados con los encabezados dinámicos
        tvHoyTextoMovimientos.visibility = View.GONE
        estadoMovimientosActual = "movimientosanteriores"
        
        // Guardar estado en SharedPreferences
        guardarEstadoMovimientos("movimientosanteriores")
        
        // Cargar envíos de movimientosanteriores
        cargarEnviosPorFecha("movimientosanteriores")
        
        android.util.Log.d("Botones", "✅ Activado 'Mas movimientos', 'Hoy' visible pero inactivo")
    }

    private fun mostrarSoloHoy() {
        // Mantener ambos botones visibles
        btnHoyMovimientos.visibility = View.VISIBLE
        btnMasMovimientos.visibility = View.VISIBLE
        
        // Activar "Hoy" (fondo rosa)
        btnHoyMovimientos.background = resources.getDrawable(R.drawable.rounded_button_pink, theme)
                    btnHoyMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.white))
        
        // Desactivar "Mas movimientos" (fondo blanco)
        btnMasMovimientos.background = resources.getDrawable(R.drawable.rounded_button_white_border, theme)
                    btnMasMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
        
        // Mostrar imagen "Hoy" y ocultar "Mas movimientos"
        ivHoyMovimientos.visibility = View.VISIBLE
        ivMasMovimientos.visibility = View.INVISIBLE
        
        // Mostrar el texto fijo para "Hoy"
        tvHoyTextoMovimientos.text = "Hoy"
        tvHoyTextoMovimientos.visibility = View.VISIBLE
        estadoMovimientosActual = "hoy"
        
        // Guardar estado en SharedPreferences
        guardarEstadoMovimientos("hoy")
        
        // Cargar envíos de hoy
        cargarEnviosPorFecha("hoy")
        
        android.util.Log.d("Botones", "✅ Activado 'Hoy', 'Mas movimientos' visible pero inactivo")
    }
    
    private fun agregarEnvioMovimientos(nombreDestinatario: String = "Usuario", cantidad: String = "", etiquetaLinea2: String? = null) {
        android.util.Log.d("EnvioMovimientos", "🔍 DEBUG - Verificando datos disponibles:")
        android.util.Log.d("EnvioMovimientos", "   - datosComprobanteBase: ${datosComprobanteBase != null}")
        android.util.Log.d("EnvioMovimientos", "   - datosNumeroCelularBase: '$datosNumeroCelularBase'")
        android.util.Log.d("EnvioMovimientos", "   - datosNumeroCelularBase.isEmpty(): ${datosNumeroCelularBase.isEmpty()}")
        
        // Usar el número de celular guardado del comprobante base, sino intentar obtener del campo
        val numeroNequi = if (datosNumeroCelularBase.isNotEmpty()) {
            android.util.Log.d("EnvioMovimientos", "✅ Usando número de celular del comprobante base: '$datosNumeroCelularBase'")
            datosNumeroCelularBase
        } else {
            val numeroDelCampo = etNumeroContacto.text.toString().replace(" ", "")
            android.util.Log.d("EnvioMovimientos", "🔢 Número de Nequi del campo: '$numeroDelCampo'")
            numeroDelCampo
        }
        
        android.util.Log.d("EnvioMovimientos", "🔢 Número de Nequi final: '$numeroNequi'")
        
        // Usar datos del comprobante base si están disponibles, sino generar nuevos
        val comprobanteData = if (datosComprobanteBase != null) {
            android.util.Log.d("EnvioMovimientos", "✅ Usando datos del comprobante base: referencia='${datosComprobanteBase!!.referencia}', fecha='${datosComprobanteBase!!.fechaFormateada}'")
            datosComprobanteBase!!
        } else {
            android.util.Log.d("EnvioMovimientos", "🔄 Generando nuevos datos de comprobante")
            generarDatosComprobanteNormal(nombreDestinatario, numeroNequi, cantidad)
        }
        
        android.util.Log.d("EnvioMovimientos", "💾 Guardando envío con número: '$numeroNequi'")
        // Guardar el envío en SharedPreferences con datos del comprobante
        guardarEnvioEnSharedPreferences(nombreDestinatario, cantidad, false, comprobanteData.referencia, comprobanteData.fechaFormateada, numeroNequi)
        
        // Crear el envío normal en la UI con área clickeable
        crearEnvioNormal(nombreDestinatario, cantidad, comprobanteData.referencia, comprobanteData.fechaFormateada, numeroNequi, etiquetaLinea2)
        
        // Limpiar los datos del comprobante base después de usarlos
        datosComprobanteBase = null
        datosNumeroCelularBase = ""
    }
    
    private fun agregarEnvioMovimientosDesdeTransfiya(numeroCelular: String = "Usuario", cantidad: String = "") {
        android.util.Log.d("Transfiya", "Agregando envío desde Transfiya: $numeroCelular, $cantidad")
        
        // Guardar el envío en SharedPreferences con datos del comprobante
        guardarEnvioEnSharedPreferences(numeroCelular, cantidad, true, datosTransfiyaReferencia, datosTransfiyaFecha)
        
        // Crear el envío de Transfiya en la UI
        crearEnvioTransfiya(numeroCelular, cantidad)
        
        // Mover todos los elementos existentes hacia abajo
        for (i in 0 until llEnviosMovimientos.childCount - 1) {
            val child = llEnviosMovimientos.getChildAt(i)
            val params = child.layoutParams as FrameLayout.LayoutParams
            params.topMargin += 220
            child.layoutParams = params
        }
        
        // Mover el nuevo elemento al principio
        if (llEnviosMovimientos.childCount > 1) {
            val ultimoHijo = llEnviosMovimientos.getChildAt(llEnviosMovimientos.childCount - 1)
            llEnviosMovimientos.removeView(ultimoHijo)
            llEnviosMovimientos.addView(ultimoHijo, 0)
            
            // Asegurar que el nuevo elemento (ahora en posición 0) no tenga topMargin
            val paramsNuevo = ultimoHijo.layoutParams as FrameLayout.LayoutParams
            paramsNuevo.topMargin = 0
            ultimoHijo.layoutParams = paramsNuevo
        }
    }
    
    private fun habilitarInteracciones() {
        // Habilitar botones 1, 2, 3
        btn1.isClickable = true
        btn2.isClickable = true
        btn3.isClickable = true
        
        // Mantener nombre NO editable en layout principal
        etNombre.isClickable = false
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        
        // Habilitar scroll de PNG
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener(null)
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener(null)
    }
    
    private fun deshabilitarInteracciones() {
        // Deshabilitar botones 1, 2, 3
        btn1.isClickable = false
        btn2.isClickable = false
        btn3.isClickable = false
        
        // Deshabilitar nombre editable
        etNombre.isClickable = false
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        etNombre.setOnClickListener(null)
        
        // Deshabilitar scroll de PNG
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener { _, _ -> true }
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener { _, _ -> true }
    }



    // Función para verificar si estamos en el layout principal
    private fun estaEnLayoutPrincipal(): Boolean {
        val layoutsAbiertos = mutableListOf<String>()
        
        if (layoutMovimientos.visibility == View.VISIBLE) layoutsAbiertos.add("Movimientos")
        if (layoutEnviarPlata.visibility == View.VISIBLE) layoutsAbiertos.add("Enviar Plata")
        if (layoutPago.visibility == View.VISIBLE) layoutsAbiertos.add("Pago")
        if (layoutTransfiya.visibility == View.VISIBLE) layoutsAbiertos.add("Transfiya")
        if (layoutContactos.visibility == View.VISIBLE) layoutsAbiertos.add("Contactos")
        if (layoutServicios.visibility == View.VISIBLE) layoutsAbiertos.add("Servicios")
        if (layoutBotón7.visibility == View.VISIBLE) layoutsAbiertos.add("Botón 7")

        
        val estaEnPrincipal = layoutsAbiertos.isEmpty()
        
        if (!estaEnPrincipal) {
            android.util.Log.d("TuplataValidacion", "Layouts abiertos: ${layoutsAbiertos.joinToString(", ")}")
        } else {
            android.util.Log.d("TuplataValidacion", "Estamos en el layout principal - todos los layouts están cerrados")
        }
        
        return estaEnPrincipal
    }



    // Funciones del modal
    private fun mostrarModal() {
        // Asegurar que el modal de confirmación esté oculto
        modalScrollViewConfirmacion.visibility = View.INVISIBLE
        
        // Ocultar envia.png si está visible (sin afectar el botón 5)
        if (ivEnvia.visibility == View.VISIBLE) {
            ivEnvia.visibility = View.INVISIBLE
            layoutBlanco.visibility = View.INVISIBLE
            // btn5 se mantiene visible
            btn6Abajo.visibility = View.INVISIBLE
            btn6Abajo.isClickable = true
            btn6Abajo.isEnabled = true
        }
        
        // Asegurar imagen y título por defecto del modal (opciones)
        cargarImagenDesdeAssetsPorId(R.id.ivModalImage, "opciones")
        findViewById<TextView>(R.id.tvModalTitle)?.text = "Opciones para enviar"

        // Restaurar tamaño por defecto de la imagen del modal
        findViewById<ImageView>(R.id.ivModalImage)?.let { imageView ->
            val params = imageView.layoutParams as RelativeLayout.LayoutParams
            params.width = dpToPx(480)
            params.height = dpToPx(480)
            imageView.layoutParams = params
        }

        // Asegurar que los botones y textos del modal estándar estén visibles
        findViewById<Button>(R.id.btn6)?.visibility = View.VISIBLE
        findViewById<Button>(R.id.btn7)?.visibility = View.VISIBLE
        findViewById<Button>(R.id.btn8)?.visibility = View.VISIBLE
        findViewById<Button>(R.id.btnModalEnviar)?.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvInfoAdicional)?.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvLoremIpsum)?.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvMasContenido)?.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvFinalContenido)?.visibility = View.VISIBLE
        
        // Mostrar overlay con animación de fade
        modalOverlay.alpha = 0f
        modalOverlay.visibility = View.VISIBLE
        modalOverlay.animate()
            .alpha(1f)
            .setDuration(300)
            .start()
        
        // Mostrar modal con animación desde abajo hasta la mitad
        modalScrollView.alpha = 0f
        modalScrollView.visibility = View.VISIBLE
        modalScrollView.translationY = 1000f
        modalScrollView.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(800)
            .setInterpolator(android.view.animation.DecelerateInterpolator())
            .start()
        
        // Si supera monto máximo, cancelar y Toast
        val etCuanto = findViewById<EditText>(R.id.etCuanto)
        val cantidadModal = etCuanto.text.toString()
        if (excedeMontoMaximo(cantidadModal)) {
            ocultarModalConfirmacion()
            mostrarToastMontoMaximo()
            return
        }

        // Deshabilitar interacciones cuando el modal está abierto
        deshabilitarInteraccionesModal()
        
        // Configurar click listeners del modal
        tvModalClose.setOnClickListener {
            ocultarModal()
        }
        
        btnModalEnviar.setOnClickListener {
            // Aquí puedes agregar la lógica para enviar
            ocultarModal()
        }
        
        // Cerrar modal al tocar el overlay
        modalOverlay.setOnClickListener {
            ocultarModal()
        }
        
        // Configurar drag del modal
        configurarDragModal()
    }

    // Modal específico para QR (sin botones extra)
    private fun mostrarModalQR() {
        // Ocultar el modal estándar si estuviera visible
        if (modalScrollView.visibility == View.VISIBLE) {
            modalScrollView.visibility = View.INVISIBLE
        }
        
        // Ocultar envia.png si está visible
        if (ivEnvia.visibility == View.VISIBLE) {
            ivEnvia.visibility = View.INVISIBLE
            layoutBlanco.visibility = View.INVISIBLE
            btn5.visibility = View.INVISIBLE
            btn6Abajo.visibility = View.INVISIBLE
            btn6Abajo.isClickable = true
            btn6Abajo.isEnabled = true
        }
        
        // Cargar imagen QR en el modal QR y aplicar scale/posicion
        val ivQR = findViewById<ImageView>(R.id.ivModalImageQR)
        if (ivQR != null) {
            cargarImagenDesdeAssets(ivQR, "qropciones")
            ivQR.post {
                ivQR.scaleX = 1.1f
                ivQR.scaleY = 1.1f
                ivQR.translationY = dpToPx(14).toFloat() // Bajada 10dp en lugar de subida 20dp
            }
        }
        
        // Mostrar overlay y modal QR
        modalOverlay.alpha = 0f
        modalOverlay.visibility = View.VISIBLE
        modalOverlay.animate().alpha(1f).setDuration(300).start()
        
        modalScrollViewQR.alpha = 0f
        modalScrollViewQR.visibility = View.VISIBLE
        // Desactivar scroll interno; el gesto controlará el modal completo
        try {
            modalScrollViewQR.isVerticalScrollBarEnabled = false
            modalScrollViewQR.isHorizontalScrollBarEnabled = false
            modalScrollViewQR.overScrollMode = View.OVER_SCROLL_NEVER
        } catch (_: Exception) {}
        modalScrollViewQR.translationY = 1000f
        modalScrollViewQR.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(800)
            .setInterpolator(android.view.animation.DecelerateInterpolator())
            .start()

        // Habilitar drag del modal QR (mover el modal, no su contenido)
        configurarDragModalQR()
        
        // Botón del lector de QR dentro del modal QR
        val btnLectorQR = findViewById<Button>(R.id.btnAbrirLectorQR)
        if (btnLectorQR != null) {
            android.util.Log.d("QRReader", "Botón btnAbrirLectorQR encontrado, configurando...")
            btnLectorQR.visibility = View.VISIBLE
            btnLectorQR.bringToFront()
            btnLectorQR.setOnClickListener {
                android.util.Log.d("QRReader", "Click en btnAbrirLectorQR")
                try {
                    // Reproducir ai.bin primero
                    reproducirVideoAiBin {
                        // Después de reproducir el video, abrir la actividad Compose del lector de QR
                        val intent = android.content.Intent(this@InicioActivity, QrScannerActivity::class.java)
                        startActivityForResult(intent, QR_SCANNER_REQUEST_CODE)
                    }
                } catch (e: Exception) {
                    android.util.Log.e("QRReader", "Error al abrir lector QR: ${e.message}", e)
                    Toast.makeText(this@InicioActivity, "Error al abrir lector: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            android.util.Log.e("QRReader", "Botón btnAbrirLectorQR NO encontrado")
        }

        // Deshabilitar interacciones mientras el modal QR está abierto, excepto permitir btn6Abajo
        deshabilitarInteraccionesModal()
        btn6Abajo.isClickable = true
        btn6Abajo.isEnabled = true
        
        // Cerrar modal QR
        findViewById<TextView>(R.id.tvModalCloseQR)?.setOnClickListener { ocultarModalQR() }
        modalOverlay.setOnClickListener { ocultarModalQR() }
    }

    private fun ocultarModalQR() {
        modalScrollViewQR.animate()
            .translationY(1500f)
            .alpha(0f)
            .setDuration(600)
            .setInterpolator(android.view.animation.AccelerateInterpolator())
            .withEndAction {
                modalOverlay.animate()
                    .alpha(0f)
                    .setDuration(350)
                    .withEndAction {
                        modalOverlay.visibility = View.INVISIBLE
                        modalScrollViewQR.visibility = View.INVISIBLE
                        modalScrollViewQR.translationY = 0f
                        modalScrollViewQR.alpha = 1f
                        habilitarInteraccionesModal()
                        // Rehabilitar ivUser al cerrar modal QR
                        findViewById<ImageView>(R.id.ivUser)?.isClickable = true
                        findViewById<ImageView>(R.id.ivUser)?.isFocusable = true
                    }
                    .start()
            }
            .start()
    }

        private fun mostrarLayoutEnviarPlata() {
        // Ocultar el modal inmediatamente si está visible (sin transición)
        if (modalOverlay.visibility == View.VISIBLE) {
            modalOverlay.visibility = View.INVISIBLE
            ivModalImage.visibility = View.VISIBLE // Restaurar la imagen del modal
        }
        
        layoutEnviarPlata.visibility = View.VISIBLE
        layoutEnviarPlata.translationX = layoutEnviarPlata.width.toFloat()
        layoutEnviarPlata.animate()
            .translationX(0f)
            .setDuration(300)
            .start()
        
        // Deshabilitar interacciones cuando el layout de enviar plata está visible
        deshabilitarInteraccionesLayoutEnviarPlata()
    }

    private fun ocultarLayoutEnviarPlata() {
        layoutEnviarPlata.animate()
            .translationX(layoutEnviarPlata.width.toFloat())
            .setDuration(300)
            .withEndAction {
                layoutEnviarPlata.visibility = View.INVISIBLE
                layoutEnviarPlata.translationX = 0f
                habilitarInteraccionesLayoutEnviarPlata()
            }
            .start()
    }

    private fun ocultarLayoutMovimientos() {
        android.util.Log.d("Movimientos", "Ocultando layout de movimientos sin transición")
        try {
                    layoutMovimientos.visibility = View.INVISIBLE
                    layoutMovimientos.translationX = 0f
                    habilitarInteracciones()
                    android.util.Log.d("Movimientos", "Layout de movimientos oculto exitosamente")
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error ocultando layout de movimientos: ${e.message}", e)
        }
    }
    
    private fun ocultarLayoutMovimientosSinTransicion() {
        layoutMovimientos.visibility = View.INVISIBLE
        
        // Resetear layoutBlanco global si está visible
        if (layoutBlanco.visibility == View.VISIBLE) {
            layoutBlanco.visibility = View.INVISIBLE
            ivEnvia.visibility = View.INVISIBLE
            btn5.visibility = View.INVISIBLE
            btn6Abajo.visibility = View.INVISIBLE
        }
        
        habilitarInteracciones()
    }

    private fun deshabilitarInteraccionesLayoutEnviarPlata() {

        etNombre.isClickable = false
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener { _, _ -> true }
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener { _, _ -> true }
    }

    private fun habilitarInteraccionesLayoutEnviarPlata() {

        etNombre.isClickable = true
        etNombre.isFocusable = true
        etNombre.isFocusableInTouchMode = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener(null)
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener(null)
    }

    // Función para formatear número de teléfono
    private fun formatPhoneNumber(phone: String): String {
        return when {
            phone.length <= 3 -> phone
            phone.length <= 6 -> "${phone.substring(0, 3)} ${phone.substring(3)}"
            phone.length <= 10 -> "${phone.substring(0, 3)} ${phone.substring(3, 6)} ${phone.substring(6)}"
            else -> "${phone.substring(0, 3)} ${phone.substring(3, 6)} ${phone.substring(6, 10)}"
        }
    }
    
    // Función para formatear número de teléfono en formato 300 000 0000
    private fun formatPhoneNumberVictima(phone: String): String {
        return when {
            phone.length <= 3 -> phone
            phone.length <= 6 -> "${phone.substring(0, 3)} ${phone.substring(3)}"
            phone.length <= 10 -> "${phone.substring(0, 3)} ${phone.substring(3, 6)} ${phone.substring(6)}"
            else -> "${phone.substring(0, 3)} ${phone.substring(3, 6)} ${phone.substring(6, 10)}"
        }
    }

    private fun mostrarNotificacionTransaccionExitosa() {
        try {
            val title = "¡Transacción Exitosa!"
            val body = "Tu comprobante ha sido generado correctamente."
            val intent = Intent(this, InicioActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pending = PendingIntent.getActivity(
                this,
                99,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(this, "nequico_default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setColor(android.graphics.Color.parseColor("#db0082"))
                .setContentIntent(pending)

            with(NotificationManagerCompat.from(this)) {
                notify((System.currentTimeMillis() + 123).toInt(), builder.build())
            }
        } catch (_: Exception) { }
    }

    // Función para formatear moneda
    private fun formatCurrency(amount: String): String {
        if (amount.isEmpty()) return ""
        
        val number = amount.toLongOrNull() ?: return amount
        val formatted = String.format("%,d", number)
        return "$ ${formatted.replace(",", ".")}"
    }
    
    // Función para formatear moneda desde Long
    private fun formatCurrency(amount: Long): String {
        val formatted = String.format("%,d", amount)
        return "$ ${formatted.replace(",", ".")}"
    }
    
    override fun onPause() {
        super.onPause()
        isAppInBackground = true
        cancelarRefrescoCambioDeDia()
    }
    
    override fun onResume() {
        super.onResume()
        isAppInBackground = false
        // Refrescar movimientos si cambió el día mientras la app estaba en segundo plano
        maybeRefreshMovimientosIfDayChanged()
        // Reprogramar refresco al cambio de día
        programarRefrescoCambioDeDia()
    }
    
    // Función para ajustar posiciones de elementos scrolleables según DPI
    private fun ajustarElementosEspecificosSegunDPI() {
        val densityDpi = resources.displayMetrics.densityDpi
        android.util.Log.d("InicioActivity", "🎯 Ajustando elementos específicos para DPI: $densityDpi")
        
        // Factor de escala reducido para estos elementos específicos
        val scaleFactor = when {
            densityDpi <= 320 -> 0.8f  // Más pequeño para DPI bajo
            densityDpi <= 480 -> 0.85f
            densityDpi <= 640 -> 0.9f
            densityDpi <= 680 -> 0.95f
            else -> 1.0f
        }
        

        

        
        // Ajustar scrolleables (hsvImages) - Solo escalado, posición fija
        val hsvImages = findViewById<HorizontalScrollView>(R.id.hsvImages)
        hsvImages?.post {
            hsvImages.scaleX = scaleFactor
            hsvImages.scaleY = scaleFactor
            // Posición fija, no se ajusta por DPI
        }
        

        
        // Ajustar scrolleables de sugeridos (hsvSugeridos) - Solo escalado, posición fija
        val hsvSugeridos = findViewById<HorizontalScrollView>(R.id.hsvSugeridos)
        hsvSugeridos?.post {
            hsvSugeridos.scaleX = scaleFactor
            hsvSugeridos.scaleY = scaleFactor
            // Posición fija, no se ajusta por DPI
        }
        
        android.util.Log.d("InicioActivity", "✅ Elementos específicos ajustados con factor: $scaleFactor")
    }

    // Función para ajustar elementos del layout de movimientos - Solo escalado, posición fija
    private fun ajustarElementosMovimientosSegunDPI() {
        val densityDpi = resources.displayMetrics.densityDpi
        android.util.Log.d("InicioActivity", "🎯 Ajustando elementos de movimientos para DPI: $densityDpi")
        
        // Factor de escala reducido para estos elementos específicos
        val scaleFactor = when {
            densityDpi <= 320 -> 0.8f  // Más pequeño para DPI bajo
            densityDpi <= 480 -> 0.85f
            densityDpi <= 640 -> 0.9f
            densityDpi <= 680 -> 0.95f
            else -> 1.0f
        }
        

        
        android.util.Log.d("InicioActivity", "✅ Elementos de movimientos ajustados con factor: $scaleFactor")
    }











    
    private fun ocultarModal() {
        // Animación de salida del modal
        modalScrollView.animate()
            .translationY(1500f)
            .alpha(0f)
            .setDuration(600)
            .setInterpolator(android.view.animation.AccelerateInterpolator())
            .withEndAction {
                modalOverlay.animate()
                    .alpha(0f)
                    .setDuration(350)
                    .withEndAction {
                        modalOverlay.visibility = View.INVISIBLE
                        modalScrollView.visibility = View.INVISIBLE
                        modalScrollView.translationY = 0f
                        modalScrollView.alpha = 1f
                        habilitarInteraccionesModal()
                        
                        // Restaurar la imagen opciones.png
                        val ivModalImage = findViewById<ImageView>(R.id.ivModalImage)
                        ivModalImage.visibility = View.VISIBLE
                        
                        // Restaurar el botón 6
                        val btn6 = findViewById<Button>(R.id.btn6)
                        btn6.visibility = View.VISIBLE
                        
                        // Restaurar los textos adicionales del modal
                        val tvInfoAdicional = findViewById<TextView>(R.id.tvInfoAdicional)
                        val tvLoremIpsum = findViewById<TextView>(R.id.tvLoremIpsum)
                        val tvMasContenido = findViewById<TextView>(R.id.tvMasContenido)
                        val tvFinalContenido = findViewById<TextView>(R.id.tvFinalContenido)
                        
                        tvInfoAdicional?.visibility = View.VISIBLE
                        tvLoremIpsum?.visibility = View.VISIBLE
                        tvMasContenido?.visibility = View.VISIBLE
                        tvFinalContenido?.visibility = View.VISIBLE
                        
                        // Restaurar título original
                        val tvModalTitle = findViewById<TextView>(R.id.tvModalTitle)
                        tvModalTitle.visibility = View.VISIBLE
                        
                        // Asegurar que el modal de confirmación permanezca oculto
                        modalScrollViewConfirmacion.visibility = View.INVISIBLE
                    }
                    .start()
            }
            .start()
    }
    
    private fun deshabilitarInteraccionesModal() {
        btn1.isClickable = false
        btn2.isClickable = false
        btn3.isClickable = false
        btn4.isClickable = false
        btn5.isClickable = false
        btn6Abajo.isClickable = true
        etNombre.isClickable = false
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        etNombre.setOnClickListener(null)
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener { _, _ -> true }
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener { _, _ -> true }
    }
    
    private fun habilitarInteraccionesModal() {
        btn1.isClickable = true
        btn2.isClickable = true
        btn3.isClickable = true
        btn4.isClickable = true
        btn5.isClickable = true
        // Mantener btn6Abajo no clickeable si permanece invisible
        btn6Abajo.isClickable = false
        etNombre.isClickable = true
        etNombre.isFocusable = true
        etNombre.isFocusableInTouchMode = true
        etNombre.setOnClickListener { mostrarDialogoCambioNombre() }
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener(null)
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener(null)
    }
    private fun configurarDragModal() {
        modalScrollView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    modalInitialY = event.rawY
                    modalCurrentY = modalScrollView.translationY
                    isDraggingModal = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isDraggingModal) {
                        val deltaY = event.rawY - modalInitialY
                        val newY = modalCurrentY + deltaY
                        
                        // Limitar el movimiento entre -300dp y 300dp
                        val limitedY = newY.coerceIn(-300f, 300f)
                        modalScrollView.translationY = limitedY
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    isDraggingModal = false
                    // Si se arrastra hacia abajo más de 100dp, cerrar el modal
                    if (modalScrollView.translationY > 100f) {
                        ocultarModal()
                    } else {
                        // Animar de vuelta a la posición original
                        modalScrollView.animate()
                            .translationY(0f)
                            .setDuration(450)
                            .start()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun configurarDragModalQR() {
        modalScrollViewQR.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    modalInitialY = event.rawY
                    modalCurrentY = modalScrollViewQR.translationY
                    isDraggingModal = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isDraggingModal) {
                        val deltaY = event.rawY - modalInitialY
                        val newY = modalCurrentY + deltaY
                        // Limitar movimiento similar al modal principal
                        val limitedY = newY.coerceIn(-300f, 300f)
                        modalScrollViewQR.translationY = limitedY
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    isDraggingModal = false
                    if (modalScrollViewQR.translationY > 100f) {
                        ocultarModalQR()
                    } else {
                        modalScrollViewQR.animate()
                            .translationY(0f)
                            .setDuration(450)
                            .start()
                    }
                    true
                }
                else -> false
            }
        }
    }
    // Funciones del layout de contactos
    private fun mostrarLayoutContactos() {
        android.util.Log.d("BorrarContactos", "📱 MOSTRANDO LAYOUT DE CONTACTOS")
        layoutContactos.visibility = View.VISIBLE
        layoutContactos.translationX = layoutContactos.width.toFloat()
        layoutContactos.animate()
            .translationX(0f)
            .setDuration(300)
            .start()
        
        // Deshabilitar interacciones cuando el layout de contactos está visible
        deshabilitarInteraccionesContactos()
        
        // Configurar formateo del número de teléfono
        configurarFormateoNumeroContacto()
        
        // Configurar botón guardar contacto
        android.util.Log.d("BorrarContactos", "📞 LLAMANDO A configurarBotonGuardarContacto()")
        configurarBotonGuardarContacto()
        
        // Cargar contactos guardados
        cargarContactosGuardados()
    }
    
    // Funciones del layout del botón 3
    private fun mostrarLayoutBotón3() {
        try {
            android.util.Log.d("Botón3", "Mostrando layout del botón 3")
            
            // Mostrar el layout con animación
            layoutBotón3.visibility = View.VISIBLE
            layoutBotón3.alpha = 0f
            layoutBotón3.animate()
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
            

            
            // Hacer visible el botón servicios.png
            ivServicios.visibility = View.VISIBLE
            
            // Poner los botones de navegación por encima del layout
            findViewById<ImageView>(R.id.ivHome).elevation = 10f
            findViewById<ImageView>(R.id.ivMovements).elevation = 10f
            findViewById<ImageView>(R.id.ivServicios).elevation = 10f
            findViewById<LinearLayout>(R.id.llBotones).elevation = 10f
            
        } catch (e: Exception) {
            android.util.Log.e("Botón3", "Error mostrando layout del botón 3: ${e.message}", e)
        }
    }
    
    private fun ocultarLayoutBotón3() {
        try {
            android.util.Log.d("Botón3", "Ocultando layout del botón 3")
            
            // Ocultar el layout con animación
            layoutBotón3.animate()
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(android.view.animation.AccelerateInterpolator())
                .withEndAction {
                    layoutBotón3.visibility = View.INVISIBLE
                }
                .start()
            
            // Ocultar el botón servicios.png
            ivServicios.visibility = View.INVISIBLE
            
            // Resetear layoutBlanco si está visible
            if (layoutBlanco.visibility == View.VISIBLE) {
                layoutBlanco.visibility = View.INVISIBLE
                ivEnvia.visibility = View.INVISIBLE
                btn5.visibility = View.INVISIBLE
                btn6Abajo.visibility = View.INVISIBLE
            }
            
            // Restaurar elevation normal de los botones de navegación
            findViewById<ImageView>(R.id.ivHome).elevation = 0f
            findViewById<ImageView>(R.id.ivMovements).elevation = 0f
            findViewById<ImageView>(R.id.ivServicios).elevation = 0f
            findViewById<LinearLayout>(R.id.llBotones).elevation = 0f
            
        } catch (e: Exception) {
            android.util.Log.e("Botón3", "Error ocultando layout del botón 3: ${e.message}", e)
        }
    }
    
    // Funciones del layout para cambiar saldo
    private fun mostrarLayoutCambiarSaldo() {
        try {
            android.util.Log.d("CambiarSaldo", "Mostrando layout para cambiar saldo")
            
            // Limpiar campo de entrada
            etNuevoSaldo.setText("")
            // Asegurar input solo numérico y sin formateo inicial
            etNuevoSaldo.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            try {
                etNuevoSaldo.addTextChangedListener(object : android.text.TextWatcher {
                    private var editing = false
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: android.text.Editable?) {
                        if (editing) return
                        val raw = s?.toString() ?: return
                        val digits = raw.replace("[^0-9]".toRegex(), "")
                        if (digits.isEmpty()) return
                        val num = try { digits.toLong() } catch (_: Exception) { return }
                        val miles = String.format("%,d", num).replace(",", ".")
                        if (miles != raw) {
                            editing = true
                            etNuevoSaldo.setText(miles)
                            etNuevoSaldo.setSelection(miles.length)
                            editing = false
                        }
                    }
                })
            } catch (_: Exception) {}
            
            // Mostrar el layout con animación
            layoutCambiarSaldo.visibility = View.VISIBLE
            layoutCambiarSaldo.alpha = 0f
            layoutCambiarSaldo.animate()
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
            
            // Enfocar el campo de entrada
            etNuevoSaldo.requestFocus()
            
        } catch (e: Exception) {
            android.util.Log.e("CambiarSaldo", "Error mostrando layout: ${e.message}", e)
        }
    }
    
    private fun ocultarLayoutCambiarSaldo() {
        try {
            android.util.Log.d("CambiarSaldo", "Ocultando layout para cambiar saldo")
            
            // Ocultar el layout con animación
            layoutCambiarSaldo.animate()
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(android.view.animation.AccelerateInterpolator())
                .withEndAction {
                    layoutCambiarSaldo.visibility = View.INVISIBLE
                }
                .start()
            
        } catch (e: Exception) {
            android.util.Log.e("CambiarSaldo", "Error ocultando layout: ${e.message}", e)
        }
    }
    
    private fun aplicarNuevoSaldo() {
        try {
            val nuevoSaldoTexto = etNuevoSaldo.text.toString().trim()
            
            if (nuevoSaldoTexto.isEmpty()) {
                android.util.Log.w("CambiarSaldo", "Campo de saldo vacío")
                return
            }
            
            val soloDigitos = nuevoSaldoTexto.replace("[^0-9]".toRegex(), "")
            val nuevoSaldo = soloDigitos.toLongOrNull()
            if (nuevoSaldo == null) {
                android.util.Log.w("CambiarSaldo", "Saldo inválido: $nuevoSaldoTexto (soloDigitos='$soloDigitos')")
                return
            }
            
            // Guardar el nuevo saldo visual en SharedPreferences
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            sharedPref.edit().putLong("saldo_visual", nuevoSaldo).apply()
            
            // Actualizar el saldo visual en la UI
            actualizarSaldoVisual(nuevoSaldo)
            
            android.util.Log.d("CambiarSaldo", "Saldo visual actualizado a: $nuevoSaldo")
            
            // Ocultar el layout
            ocultarLayoutCambiarSaldo()
            
        } catch (e: Exception) {
            android.util.Log.e("CambiarSaldo", "Error aplicando nuevo saldo: ${e.message}", e)
        }
    }
    
    private fun restaurarSaldoOriginal() {
        try {
            // Restaurar el saldo original (1.000.000)
            val saldoOriginal = 1000000L
            
            // Guardar el saldo original en SharedPreferences
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            sharedPref.edit().putLong("saldo_visual", saldoOriginal).apply()
            
            // Actualizar el saldo visual en la UI
            actualizarSaldoVisual(saldoOriginal)
            
            android.util.Log.d("CambiarSaldo", "Saldo original restaurado: $saldoOriginal")
            
            // Ocultar el layout
            ocultarLayoutCambiarSaldo()
            
        } catch (e: Exception) {
            android.util.Log.e("CambiarSaldo", "Error restaurando saldo original: ${e.message}", e)
        }
    }
    
    private fun cargarSaldoDesdeFirebase() {
        try {
            // Limpiar el saldo visual para forzar la carga desde Firebase
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            sharedPref.edit().remove("saldo_visual").apply()
            
            android.util.Log.d("CambiarSaldo", "Saldo visual limpiado, cargando desde Firebase")
            
            // Recargar el saldo desde Firebase
            cargarSaldoGuardado()
            
            // Ocultar el layout
            ocultarLayoutCambiarSaldo()
            
        } catch (e: Exception) {
            android.util.Log.e("CambiarSaldo", "Error cargando saldo desde Firebase: ${e.message}", e)
        }
    }
    
    private fun actualizarSaldoVisual(nuevoSaldo: Long) {
        try {
            // Formatear el saldo con separadores de miles
            val saldoFormateado = String.format("%,d", nuevoSaldo).replace(",", ".")
            
            // Usar la función unificada para actualizar saldo
            actualizarSaldoSeparado("$ $saldoFormateado")
            
            android.util.Log.d("CambiarSaldo", "Saldo visual actualizado: $ $saldoFormateado")
            
        } catch (e: Exception) {
            android.util.Log.e("CambiarSaldo", "Error actualizando saldo visual: ${e.message}", e)
        }
    }
    
    // Funciones del layout de servicios
    private fun mostrarLayoutServicios() {
        // Ocultar el layout de perfil si está visible
        if (layoutPerfilUsuario.visibility == View.VISIBLE) {
            ocultarLayoutPerfilUsuario()
        }
        
        layoutServicios.visibility = View.VISIBLE
        layoutServicios.translationX = layoutServicios.width.toFloat()
        layoutServicios.animate()
            .translationX(0f)
            .setDuration(300)
            .start()
        
        // Deshabilitar interacciones cuando el layout de servicios está visible
        deshabilitarInteraccionesServicios()
        
        // Configurar formateo del número de teléfono
        configurarFormateoNumeroContacto()
        
        // Configurar botón guardar contacto
        configurarBotonGuardarContacto()
        
        // Cargar contactos guardados
        cargarContactosGuardados()
    }

    private fun ocultarLayoutContactos() {
        layoutContactos.animate()
            .translationX(layoutContactos.width.toFloat())
            .setDuration(300)
            .withEndAction {
                layoutContactos.visibility = View.INVISIBLE
                layoutContactos.translationX = 0f
                habilitarInteraccionesContactos()
            }
            .start()
    }
    
    private fun ocultarLayoutServicios() {
        layoutServicios.animate()
            .translationX(layoutServicios.width.toFloat())
            .setDuration(300)
            .withEndAction {
                layoutServicios.visibility = View.INVISIBLE
                layoutServicios.translationX = 0f
                habilitarInteraccionesServicios()
            }
            .start()
    }

    private fun deshabilitarInteraccionesContactos() {
        // Solo deshabilitar elementos del layout principal, NO los del layout de contactos
        btn1.isClickable = false
        btn2.isClickable = false
        btn3.isClickable = false
        btn4.isClickable = false
        btn5.isClickable = false
        etNombre.isClickable = false
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener { _, _ -> true }
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener { _, _ -> true }
    }

    private fun habilitarInteraccionesContactos() {
        // Habilitar elementos del layout principal
        btn1.isClickable = true
        btn2.isClickable = true
        btn3.isClickable = true
        btn4.isClickable = true
        btn5.isClickable = true
        etNombre.isClickable = true
        etNombre.isFocusable = true
        etNombre.isFocusableInTouchMode = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener(null)
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener(null)
    }
    
    private fun deshabilitarInteraccionesServicios() {
        // Solo deshabilitar elementos del layout principal, NO los del layout de servicios
        btn1.isClickable = false
        btn2.isClickable = false
        btn3.isClickable = false
        btn4.isClickable = false
        btn5.isClickable = false
        etNombre.isClickable = false
        etNombre.isFocusable = false
        etNombre.isFocusableInTouchMode = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = false
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener { _, _ -> true }
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener { _, _ -> true }
    }

    private fun habilitarInteraccionesServicios() {
        // Habilitar elementos del layout principal
        btn1.isClickable = true
        btn2.isClickable = true
        btn3.isClickable = true
        btn4.isClickable = true
        btn5.isClickable = true
        etNombre.isClickable = true
        etNombre.isFocusable = true
        etNombre.isFocusableInTouchMode = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).isEnabled = true
        findViewById<HorizontalScrollView>(R.id.hsvImages).setOnTouchListener(null)
        findViewById<HorizontalScrollView>(R.id.hsvSugeridos).setOnTouchListener(null)
    }

    private fun configurarFormateoNumeroContacto() {
        etNumeroContacto.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val str = s.toString().replace(" ", "")
                // Solo aplicar formato si es un número de teléfono (10 dígitos)
                if (str.length == 10) {
                    val formatted = formatPhoneNumberVictima(str)
                    if (formatted != s.toString()) {
                        etNumeroContacto.removeTextChangedListener(this)
                        etNumeroContacto.setText(formatted)
                        etNumeroContacto.setSelection(formatted.length)
                        etNumeroContacto.addTextChangedListener(this)
                    }
                } else if (str.length == 11) {
                    // Para números de cuenta (11 dígitos), mantener sin formato pero con espacios cada 4 dígitos
                    val formatted = str.replace(Regex("(.{4})"), "$1 ").trim()
                    if (formatted != s.toString()) {
                    etNumeroContacto.removeTextChangedListener(this)
                    etNumeroContacto.setText(formatted)
                    etNumeroContacto.setSelection(formatted.length)
                    etNumeroContacto.addTextChangedListener(this)
                }
                }
                // Para cualquier otro número, no aplicar formato
            }
        })
    }

    private fun configurarBotonGuardarContacto() {
        android.util.Log.d("BorrarContactos", "🔧 CONFIGURANDO BOTÓN GUARDAR CONTACTO")
        
        // Configurar flecha de regreso para servicios
        ivFlechaRegresoServicios.setOnClickListener {
            ocultarLayoutServicios()
        }

        // Comentado: Ya no se usa el botón de borrar todos los contactos
        // android.util.Log.d("BorrarContactos", "🔍 ivBorrarContactos es null: ${ivBorrarContactos == null}")
        // if (ivBorrarContactos != null) {
        // ivBorrarContactos.setOnClickListener {
        //         android.util.Log.d("BorrarContactos", "🔥 CLICK DETECTADO EN BOTÓN BORRAR CONTACTOS! 🔥")
        //     mostrarDialogoBorrarContactos()
        //     }
        //     android.util.Log.d("BorrarContactos", "✅ ONCLICKLISTENER CONFIGURADO PARA ivBorrarContactos")
        // } else {
        //     android.util.Log.e("BorrarContactos", "❌ ERROR: ivBorrarContactos es null!")
        // }

        // Configurar botón guardar contacto
        btnGuardarContactoServicios.setOnClickListener {
            try {
                val nombre = etNombreContacto.text.toString().trim()
                val numero = etNumeroContacto.text.toString().replace(" ", "")
                
                if (nombre.isEmpty()) {
                    Toast.makeText(this, "Por favor ingresa un nombre", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                if (numero.isEmpty() || (numero.length != 10 && numero.length != 11)) {
                    Toast.makeText(this, "Por favor ingresa un número válido de 10 dígitos (celular) o 11 dígitos (cuenta bancaria)", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                // Guardar contacto
                guardarContacto(nombre, numero)
                
                // Limpiar campos
                limpiarCamposContacto()
                
                // Recargar lista de contactos
                cargarContactosGuardados()
                
                Toast.makeText(this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar contacto: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun guardarContacto(nombre: String, numero: String) {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val contactos = sharedPref.getStringSet("contactos", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        
        // Crear entrada del contacto
        val contacto = "$nombre|$numero"
        contactos.add(contacto)
        
        with(sharedPref.edit()) {
            putStringSet("contactos", contactos)
            apply()
        }
        
        // Si el número tiene 11 dígitos, también guardarlo en el sistema de Bancolombia
        if (numero.length == 11) {
            guardarNombreBancolombia(numero, nombre)
            android.util.Log.d("Contactos", "Nombre guardado en sistema Bancolombia: $nombre para cuenta: $numero")
        }
    }

    private fun limpiarCamposContacto() {
        etNombreContacto.text.clear()
        etNumeroContacto.text.clear()
    }

    private fun cargarContactosGuardados() {
        try {
            // Limpiar lista actual
            llListaContactos.removeAllViews()
            
            val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            val contactos = sharedPref.getStringSet("contactos", setOf()) ?: setOf()
            
                    for (contacto in contactos) {
            val partes = contacto.split("|")
            if (partes.size >= 2) {
                val nombre = partes[0]
                val numero = partes[1]
                agregarContactoALista(nombre, numero)
            }
        }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar contactos: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun agregarContactoALista(nombre: String, numero: String) {
        try {
            // Crear layout horizontal para el contacto (nombre/número + botón borrar)
            val contactoLayout = LinearLayout(this)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 8, 0, 8)
            contactoLayout.layoutParams = layoutParams
            contactoLayout.orientation = LinearLayout.HORIZONTAL
            contactoLayout.background = android.graphics.drawable.ColorDrawable(android.graphics.Color.parseColor("#FFFFFF"))
            contactoLayout.setPadding(16, 16, 16, 16)
            contactoLayout.gravity = android.view.Gravity.CENTER_VERTICAL

            // Crear layout vertical para nombre y número
            val infoLayout = LinearLayout(this)
            val infoParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f // Peso 1 para que ocupe el espacio disponible
            )
            infoLayout.layoutParams = infoParams
            infoLayout.orientation = LinearLayout.VERTICAL

            // Crear TextView para el nombre
            val tvNombre = TextView(this)
            val nombreParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tvNombre.layoutParams = nombreParams
            tvNombre.text = nombre
            tvNombre.textSize = 18f
            tvNombre.setTextColor(android.graphics.Color.parseColor("#000000"))
            tvNombre.typeface = android.graphics.Typeface.DEFAULT_BOLD

            // Crear TextView para el número
            val tvNumero = TextView(this)
            val numeroParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            numeroParams.topMargin = 6
            tvNumero.layoutParams = numeroParams
            // Formatear el número según su longitud
            val numeroFormateado = when (numero.length) {
                10 -> formatPhoneNumberVictima(numero)
                11 -> numero.replace(Regex("(.{4})"), "$1 ").trim()
                else -> numero
            }
            tvNumero.text = numeroFormateado
            tvNumero.textSize = 16f
            tvNumero.setTextColor(android.graphics.Color.parseColor("#666666"))
            tvNumero.typeface = android.graphics.Typeface.DEFAULT

            // Crear botón de borrar
            val btnBorrar = ImageView(this)
            val btnBorrarParams = LinearLayout.LayoutParams(
                64, // Ancho fijo (más grande)
                64  // Alto fijo (más grande)
            )
            btnBorrarParams.leftMargin = 16
            btnBorrar.layoutParams = btnBorrarParams
            // Usar PNG desencriptado en lugar del método normal
                            establecerImagenDesencriptada(btnBorrar, "borrar.bin")
            btnBorrar.scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            btnBorrar.setPadding(8, 8, 8, 8) // Padding reducido para que el icono se vea más grande
            btnBorrar.background = android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT)
            btnBorrar.isClickable = true
            btnBorrar.isFocusable = true
            // Cambiar el color del icono a rojo
            btnBorrar.setColorFilter(android.graphics.Color.parseColor("#FF0000"))

            // Configurar OnClickListener para borrar el contacto específico
            btnBorrar.setOnClickListener {
                android.util.Log.d("BorrarContacto", "🗑️ CLICK EN BOTÓN BORRAR CONTACTO: $nombre - $numero")
                borrarContactoEspecifico(nombre, numero, contactoLayout)
            }

            // Agregar elementos al layout de información
            infoLayout.addView(tvNombre)
            infoLayout.addView(tvNumero)

            // Agregar layout de información y botón borrar al layout principal
            contactoLayout.addView(infoLayout)
            contactoLayout.addView(btnBorrar)

            // Agregar el layout del contacto a la lista
            llListaContactos.addView(contactoLayout)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al agregar contacto: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    // Funciones del modal de confirmación
    private fun mostrarModalConfirmacion() {
        // Asegurar que el modal original esté completamente oculto
        modalScrollView.visibility = View.INVISIBLE
        modalOverlay.visibility = View.INVISIBLE
        
        // Mostrar overlay con animación de fade
        modalOverlay.alpha = 0f
        modalOverlay.visibility = View.VISIBLE
        modalOverlay.animate()
            .alpha(1f)
            .setDuration(300)
            .start()
        
        // Mostrar modal de confirmación con animación desde abajo
        modalScrollViewConfirmacion.alpha = 0f
        modalScrollViewConfirmacion.visibility = View.VISIBLE
        modalScrollViewConfirmacion.translationY = 1000f
        modalScrollViewConfirmacion.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(800)
            .setInterpolator(android.view.animation.DecelerateInterpolator())
            .start()
        
        // Deshabilitar interacciones cuando el modal está abierto
        deshabilitarInteraccionesModal()
        
        // Mostrar elementos del modal de confirmación
        tvModalTitleConfirmacion.visibility = View.VISIBLE
        tvVasAEnviarA.visibility = View.VISIBLE
        tvAlNumeroCelular.visibility = View.VISIBLE
        tvCuantoConfirmacion.visibility = View.VISIBLE
        tvLaPlataSaldraDe.visibility = View.VISIBLE
        btnConfirma.visibility = View.VISIBLE
        btnCorrigeAlgo.visibility = View.VISIBLE
        
        // Debug: verificar que los elementos se muestren
        android.util.Log.d("ModalConfirmacion", "Elementos del modal configurados")
        
        // Forzar la actualización del layout
        modalScrollViewConfirmacion.requestLayout()
        modalScrollViewConfirmacion.invalidate()
        
        // Obtener datos de los campos
        val etCel = findViewById<EditText>(R.id.etCel)
        val etCuanto = findViewById<EditText>(R.id.etCuanto)
        
        // Buscar contacto guardado con ese número
        val numeroCel = etCel.text.toString().replace(" ", "")
        val nombreContacto = buscarContactoPorNumero(numeroCel)
        
        // Mostrar nombre del contacto formateado con asteriscos o "Sin nombre" si no existe
        val nombreFormateado = if (nombreContacto.isNotEmpty()) {
            formatearNombreConAsteriscos(nombreContacto)
        } else {
            "Sin nombre"
        }
        tvNombreContactoConfirmacion.text = nombreFormateado
        tvNombreContactoConfirmacion.visibility = View.VISIBLE
        
        // Mostrar número de celular
        tvNumeroCelularConfirmacion.text = etCel.text.toString()
        tvNumeroCelularConfirmacion.visibility = View.VISIBLE
        
        // Mostrar cantidad formateada con ",00"
        val cantidad = etCuanto.text.toString().replace("$", "").replace(".", "").replace(" ", "")
        if (cantidad.isNotEmpty()) {
            val cantidadNumerica = cantidad.toLongOrNull() ?: 0L
            val cantidadFormateada = String.format("$ %,d,00", cantidadNumerica).replace(",", ".")
            tvCantidadConfirmacion.text = cantidadFormateada
        } else {
            tvCantidadConfirmacion.text = "$ 0,00"
        }
        tvCantidadConfirmacion.visibility = View.VISIBLE
        
        // Configurar click listeners de los botones
        btnConfirma.setOnClickListener {
            if (excedeMontoMaximo(etCuanto.text.toString())) {
                ocultarModalConfirmacion()
                mostrarToastMontoMaximo()
                return@setOnClickListener
            }
            // Deshabilitar el botón para evitar múltiples clics
            btnConfirma.isEnabled = false
            btnConfirma.alpha = 0.5f
            
            // Mostrar video AI antes del comprobante
            mostrarVideoAIYComprobante()
        }
        
        btnCorrigeAlgo.setOnClickListener {
            ocultarModalConfirmacion()
        }
        
        // Configurar click listener para cerrar modal
        tvModalCloseConfirmacion.setOnClickListener {
            ocultarModalConfirmacion()
        }
        
        // Cerrar modal al tocar el overlay
        modalOverlay.setOnClickListener {
            ocultarModalConfirmacion()
        }
    }
    
    private fun ocultarModalConfirmacion() {
        // Animación de salida del modal de confirmación
        modalScrollViewConfirmacion.animate()
            .translationY(1500f)
            .alpha(0f)
            .setDuration(600)
            .setInterpolator(android.view.animation.AccelerateInterpolator())
            .withEndAction {
                modalOverlay.animate()
                    .alpha(0f)
                    .setDuration(350)
                    .withEndAction {
                        modalOverlay.visibility = View.INVISIBLE
                        modalScrollViewConfirmacion.visibility = View.INVISIBLE
                        modalScrollViewConfirmacion.translationY = 0f
                        modalScrollViewConfirmacion.alpha = 1f
                        habilitarInteraccionesModal()
                        
                        // Ocultar elementos del modal de confirmación
                        tvModalTitleConfirmacion.visibility = View.GONE
                        tvVasAEnviarA.visibility = View.GONE
                        tvNombreContactoConfirmacion.visibility = View.GONE
                        tvAlNumeroCelular.visibility = View.GONE
                        tvNumeroCelularConfirmacion.visibility = View.GONE
                        tvCuantoConfirmacion.visibility = View.GONE
                        tvCantidadConfirmacion.visibility = View.GONE
                        tvLaPlataSaldraDe.visibility = View.GONE
                        btnConfirma.visibility = View.GONE
                        btnCorrigeAlgo.visibility = View.GONE
                        
                        // Rehabilitar el botón confirmar
                        btnConfirma.isEnabled = true
                        btnConfirma.alpha = 1f
                        
                        // Asegurar que el modal original permanezca oculto
                        modalScrollView.visibility = View.INVISIBLE
                    }
                    .start()
            }
            .start()
    }

    // Modal bloqueante para obligar actualización
    private fun mostrarModalForzarActualizacion(updateUrl: String) {
        try {
            // Crear overlay bloqueante
            val overlay = FrameLayout(this).apply {
                setBackgroundColor(android.graphics.Color.parseColor("#B3000000"))
                isClickable = true
                isFocusable = true
            }
            val container = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48, 48, 48, 48)
                background = resources.getDrawable(R.drawable.rounded_button_white_border, theme)
            }
            val tvTitulo = TextView(this).apply {
                text = "Actualización requerida"
                textSize = 20f
                setTextColor(android.graphics.Color.BLACK)
                typeface = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            val tvMensaje = TextView(this).apply {
                text = "Esta versión de la app ya no es compatible. Por favor actualiza para continuar usando NequiCoOficial."
                textSize = 14f
                setTextColor(android.graphics.Color.BLACK)
                typeface = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            val btnActualizar = TextView(this).apply {
                text = "Actualizar"
                textSize = 16f
                gravity = android.view.Gravity.CENTER
                setPadding(24, 16, 24, 16)
                background = resources.getDrawable(R.drawable.rounded_button_pink, theme)
                setTextColor(android.graphics.Color.WHITE)
                typeface = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
                setOnClickListener {
                    try {
                        val intent = if (updateUrl.isNotEmpty()) {
                            android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(updateUrl))
                        } else {
                            // Abrir Play Store por paquete
                            val pkg = packageName
                            android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("market://details?id=$pkg"))
                        }
                        startActivity(intent)
                    } catch (_: Exception) {}
                }
            }
            val btnSalir = TextView(this).apply {
                text = "Salir"
                textSize = 14f
                gravity = android.view.Gravity.CENTER
                setPadding(16, 12, 16, 12)
                setTextColor(android.graphics.Color.BLACK)
                background = resources.getDrawable(R.drawable.rounded_button_white_border, theme)
                typeface = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
                setOnClickListener { finish() }
            }

            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = (24 * resources.displayMetrics.density).toInt()
                rightMargin = (24 * resources.displayMetrics.density).toInt()
                topMargin = (48 * resources.displayMetrics.density).toInt()
                gravity = android.view.Gravity.CENTER
            }
            container.addView(tvTitulo)
            container.addView(tvMensaje)
            val spacer = View(this).apply { layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (12 * resources.displayMetrics.density).toInt()) }
            container.addView(spacer)
            container.addView(btnActualizar)
            val spacer2 = View(this).apply { layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (8 * resources.displayMetrics.density).toInt()) }
            container.addView(spacer2)
            container.addView(btnSalir)

            val root = findViewById<ViewGroup>(android.R.id.content)
            overlay.addView(container, lp)
            root.addView(overlay)

            // Bloquear todo input detrás
            overlay.setOnTouchListener { _, _ -> true }
        } catch (e: Exception) {
            android.util.Log.e("VersionGate", "Error mostrando modal forzar actualización: ${e.message}", e)
        }
    }
    
    // Función para buscar contacto por número
    private fun buscarContactoPorNumero(numero: String): String {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val contactos = sharedPref.getStringSet("contactos", setOf()) ?: setOf()
        
        for (contacto in contactos) {
            val partes = contacto.split("|")
            if (partes.size >= 2) {
                val numeroGuardado = partes[1]
                if (numeroGuardado == numero) {
                    return partes[0] // Retornar el nombre
                }
            }
        }
        return "" // Retornar string vacío si no se encuentra
    }
    
    // Función para buscar nombre de Bancolombia por número de cuenta
    private fun buscarNombreBancolombia(numeroCuenta: String): String {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("bancolombia_$numeroCuenta", "") ?: ""
    }
    
    // Función para guardar nombre de Bancolombia (para testing)
    private fun guardarNombreBancolombia(numeroCuenta: String, nombre: String) {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("bancolombia_$numeroCuenta", nombre)
        editor.apply()
        android.util.Log.d("Bancolombia", "Nombre guardado: $nombre para cuenta: $numeroCuenta")
    }

    // Función para eliminar nombre del sistema de Bancolombia
    private fun eliminarNombreBancolombia(numeroCuenta: String) {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("bancolombia_$numeroCuenta")
        editor.apply()
        android.util.Log.d("Bancolombia", "Nombre eliminado para cuenta: $numeroCuenta")
    }
    
    // Función para mostrar diálogo de confirmación para borrar contactos
    private fun mostrarDialogoBorrarContactos() {
        android.util.Log.d("BorrarContactos", "📋 MOSTRANDO DIÁLOGO DE CONFIRMACIÓN")
        AlertDialog.Builder(this)
            .setTitle("Borrar contactos")
            .setMessage("¿Estás seguro de que quieres borrar todos los contactos guardados?")
            .setPositiveButton("Sí, borrar") { _, _ ->
                android.util.Log.d("BorrarContactos", "✅ USUARIO CONFIRMÓ BORRAR CONTACTOS")
                borrarTodosLosContactos()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                android.util.Log.d("BorrarContactos", "❌ USUARIO CANCELÓ BORRAR CONTACTOS")
                dialog.dismiss()
            }
            .show()
    }
    
    // Función para borrar un contacto específico
    private fun borrarContactoEspecifico(nombre: String, numero: String, contactoLayout: LinearLayout) {
        android.util.Log.d("BorrarContacto", "🗑️ BORRANDO CONTACTO ESPECÍFICO: $nombre - $numero")
        
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            val contactos = sharedPref.getStringSet("contactos", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            
            // Buscar y eliminar el contacto específico
            val contactoABorrar = "$nombre|$numero"
            if (contactos.remove(contactoABorrar)) {
                // Guardar la lista actualizada
                with(sharedPref.edit()) {
                    putStringSet("contactos", contactos)
                    apply()
                }
                
                // Remover el contacto de la lista visual con animación
                contactoLayout.animate()
                    .alpha(0f)
                    .translationX(-contactoLayout.width.toFloat())
                    .setDuration(300)
                    .withEndAction {
                        llListaContactos.removeView(contactoLayout)
                    }
                    .start()
                
                // Si el número tiene 11 dígitos, también eliminarlo del sistema de Bancolombia
                if (numero.length == 11) {
                    eliminarNombreBancolombia(numero)
                    android.util.Log.d("BorrarContacto", "Nombre eliminado del sistema Bancolombia: $nombre para cuenta: $numero")
                }
                
                android.util.Log.d("BorrarContacto", "✅ CONTACTO BORRADO EXITOSAMENTE")
                Toast.makeText(this, "Contacto borrado exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                android.util.Log.e("BorrarContacto", "❌ ERROR: No se encontró el contacto para borrar")
                Toast.makeText(this, "Error al borrar contacto", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            android.util.Log.e("BorrarContacto", "❌ ERROR AL BORRAR CONTACTO: ${e.message}", e)
            Toast.makeText(this, "Error al borrar contacto: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    // Función para borrar todos los contactos
    private fun borrarTodosLosContactos() {
        android.util.Log.d("BorrarContactos", "🗑️ INICIANDO BORRADO DE TODOS LOS CONTACTOS")
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("contactos")
            apply()
        }
        
        // Limpiar la lista visual
        llListaContactos.removeAllViews()
        
        android.util.Log.d("BorrarContactos", "✅ CONTACTOS BORRADOS EXITOSAMENTE")
        Toast.makeText(this, "Todos los contactos han sido borrados", Toast.LENGTH_SHORT).show()
    }



    private fun configurarSistemaUI() {
        // Ocultar únicamente los botones de navegación, mantener barra de estado
        val flags = (
            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
        window.decorView.systemUiVisibility = flags
        window.decorView.setOnSystemUiVisibilityChangeListener {
            window.decorView.systemUiVisibility = flags
        }
    }
    
    // Función para formatear nombre con asteriscos (para modal)
    private fun formatearNombreConAsteriscos(nombreCompleto: String): String {
        val palabras = nombreCompleto.trim().split(" ")
        if (palabras.size < 2) return nombreCompleto
        
        // Obtener nombres según la cantidad
        val primerNombre = palabras[0]
        val segundoNombre = if (palabras.size >= 2) palabras[1] else ""
        val tercerNombre = if (palabras.size >= 3) palabras[2] else ""
        
        // Función auxiliar para formatear un nombre con asteriscos
        fun formatearNombre(nombre: String): String {
            return when {
                nombre.length <= 2 -> nombre
                nombre.length == 3 -> {
                    val primeraLetra = nombre[0].uppercase()
                    "$primeraLetra**"
                }
                nombre.length <= 5 -> {
                    val primerasDosLetras = nombre.take(2).replaceFirstChar { it.uppercase() }
                    val asteriscos = "*".repeat(nombre.length - 2)
                    "$primerasDosLetras$asteriscos"
                }
                else -> {
                    val primerasTresLetras = nombre.take(3).replaceFirstChar { it.uppercase() }
                    val asteriscos = "*".repeat(nombre.length - 3)
                    "$primerasTresLetras$asteriscos"
                }
            }
        }
        
        return when {
            palabras.size == 2 -> {
                // Si solo hay 2 nombres, mostrar ambos formateados
                val primerFormateado = formatearNombre(primerNombre)
                val segundoFormateado = formatearNombre(segundoNombre)
                "$primerFormateado $segundoFormateado"
            }
            tercerNombre.isNotEmpty() -> {
                // Si hay 3 o más nombres, mostrar primero y tercero
                val primerFormateado = formatearNombre(primerNombre)
                val tercerFormateado = formatearNombre(tercerNombre)
                "$primerFormateado $tercerFormateado"
            }
            else -> {
                // Fallback: solo primer nombre
                formatearNombre(primerNombre)
            }
        }
    }
    
    // Función para formatear nombre de Bancolombia con formato específico
    // Función para configurar la ventana para incluir notificaciones en capturas

    
    private fun formatearNombreBancolombia(nombre: String, paraComprobante: Boolean = false): String {
        try {
            // Dividir el nombre completo en palabras y filtrar vacías
            val palabras = nombre.trim().split(" ").filter { it.isNotEmpty() }
            
            if (palabras.isEmpty()) {
                return "SIN NOMBRE"
            }
            
            // Tomar las primeras 4 palabras (o menos si no hay tantas)
            val palabrasLimitadas = palabras.take(4)
            
            // Formatear cada palabra según el uso
            val palabrasFormateadas = palabrasLimitadas.map { palabra ->
                val primerasLetras = palabra.take(3)
                if (paraComprobante) {
                    // Para comprobante: primera letra mayúscula, resto minúscula
                    primerasLetras.lowercase().replaceFirstChar { it.uppercase() } + "***"
                } else {
                    // Para envío3: todo mayúsculas
                    primerasLetras.uppercase() + "***"
                }
            }
            
            // Unir con espacios
            val resultado = palabrasFormateadas.joinToString(" ")
            
            android.util.Log.d("Bancolombia", "Formateando nombre: '$nombre' -> '$resultado' (paraComprobante: $paraComprobante)")
            return resultado
            
        } catch (e: Exception) {
            android.util.Log.e("Bancolombia", "Error formateando nombre: ${e.message}", e)
            return "SIN NOMBRE"
        }
    }
    
    // Función para obtener primer y tercer nombre (para comprobante)
    private fun obtenerPrimerYTercerNombre(nombreCompleto: String): String {
        val palabras = nombreCompleto.trim().split(" ")
        if (palabras.size < 2) return nombreCompleto
        
        val primerNombre = palabras[0]
        val segundoNombre = if (palabras.size >= 2) palabras[1] else ""
        val tercerNombre = if (palabras.size >= 3) palabras[2] else ""
        
        return when {
            palabras.size == 2 -> "$primerNombre $segundoNombre"  // Si solo hay 2 nombres, mostrar ambos
            tercerNombre.isNotEmpty() -> "$primerNombre $tercerNombre"  // Si hay 3 o más, mostrar primero y tercero
            else -> primerNombre  // Fallback: solo primer nombre
        }
    }
    
    // Función para guardar estado de movimientos en SharedPreferences
    private fun guardarEstadoMovimientos(estado: String) {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("estadoMovimientos", estado)
        editor.apply()
    }
    
    // Función para cargar estado de movimientos desde SharedPreferences
    private fun cargarEstadoMovimientos(): String {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("estadoMovimientos", "hoy") ?: "hoy"
    }
    
    // ================ SISTEMA DE ALMACENAMIENTO DE COMPROBANTES ================
    
    // Función para guardar bitmap como archivo
    private fun guardarComprobanteComoArchivo(bitmap: android.graphics.Bitmap, referencia: String): String? {
        return try {
            android.util.Log.d("ComprobanteStorage", "Guardando comprobante con referencia: $referencia")
            
            // Crear directorio para comprobantes si no existe
            val comprobantesDir = java.io.File(filesDir, "comprobantes_transfiya")
            if (!comprobantesDir.exists()) {
                comprobantesDir.mkdirs()
                android.util.Log.d("ComprobanteStorage", "Directorio creado: ${comprobantesDir.absolutePath}")
            }
            
            // Crear archivo con nombre único basado en referencia
            val nombreArchivo = "comprobante_$referencia.png"
            val archivoComprobante = java.io.File(comprobantesDir, nombreArchivo)
            
            // Guardar bitmap como PNG
            val outputStream = java.io.FileOutputStream(archivoComprobante)
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            
            android.util.Log.d("ComprobanteStorage", "Comprobante guardado en: ${archivoComprobante.absolutePath}")
            archivoComprobante.absolutePath
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteStorage", "Error guardando comprobante: ${e.message}", e)
            null
        }
    }
    
    // Función para cargar bitmap desde archivo
    private fun cargarComprobanteDesdeArchivo(rutaArchivo: String): android.graphics.Bitmap? {
        return try {
            android.util.Log.d("ComprobanteStorage", "Cargando comprobante desde: $rutaArchivo")
            
            val archivo = java.io.File(rutaArchivo)
            android.util.Log.d("ComprobanteStorage", "Archivo existe: ${archivo.exists()}")
            android.util.Log.d("ComprobanteStorage", "Tamaño del archivo: ${archivo.length()} bytes")
            
            if (!archivo.exists()) {
                android.util.Log.w("ComprobanteStorage", "Archivo no existe: $rutaArchivo")
                return null
            }
            
            val bitmap = android.graphics.BitmapFactory.decodeFile(rutaArchivo)
            if (bitmap != null) {
                android.util.Log.d("ComprobanteStorage", "Comprobante cargado exitosamente, tamaño: ${bitmap.width}x${bitmap.height}")
            } else {
                android.util.Log.e("ComprobanteStorage", "BitmapFactory.decodeFile retornó null")
            }
            bitmap
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteStorage", "Error cargando comprobante: ${e.message}", e)
            null
        }
    }
    // ================ SISTEMA DE GUARDADO DE ENVÍOS ================
    // Función para guardar envío en SharedPreferences
    private fun guardarEnvioEnSharedPreferences(nombreDestinatario: String, cantidad: String, esDeTransfiya: Boolean = false, referencia: String = "", fechaComprobante: String = "", numeroNequi: String = "") {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        
        // Obtener envíos existentes
        val enviosExistentes = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        val enviosMutable = enviosExistentes.toMutableSet()
        
        // Crear nuevo envío con timestamp y flag de Transfiya
        val timestamp = System.currentTimeMillis()
        val flagTransfiya = if (esDeTransfiya) "1" else "0"
        
        // Para Transfiya, incluir datos del comprobante Y la ruta del archivo
        val nuevoEnvio = if (esDeTransfiya && referencia.isNotEmpty() && fechaComprobante.isNotEmpty()) {
            // Escapar espacios y caracteres especiales en la fecha
            val fechaEscapada = fechaComprobante.replace(" ", "_SPACE_").replace(":", "_COLON_").replace(".", "_DOT_")
            
            // Guardar el bitmap actual como archivo (si existe)
            val rutaArchivo = if (comprobanteTransfiyaBitmap != null) {
                guardarComprobanteComoArchivo(comprobanteTransfiyaBitmap!!, referencia)
            } else {
                null
            }
            
            // Incluir la ruta del archivo en el string (si se guardó exitosamente)
            if (rutaArchivo != null) {
                val rutaEscapada = rutaArchivo.replace("|", "_PIPE_").replace(" ", "_SPACE_")
                android.util.Log.d("ComprobanteStorage", "Envío con archivo guardado: referencia=$referencia, ruta=$rutaArchivo")
                "$timestamp|$nombreDestinatario|$cantidad|$flagTransfiya|$referencia|$fechaEscapada|$rutaEscapada"
            } else {
                android.util.Log.w("ComprobanteStorage", "No se pudo guardar archivo, usando formato sin ruta")
                "$timestamp|$nombreDestinatario|$cantidad|$flagTransfiya|$referencia|$fechaEscapada"
            }
        } else if (referencia.isNotEmpty() && fechaComprobante.isNotEmpty()) {
            // Para envíos normales con referencia y fecha
            val fechaEscapada = fechaComprobante.replace(" ", "_SPACE_").replace(":", "_COLON_").replace(".", "_DOT_")
            if (numeroNequi.isNotEmpty()) {
                // Incluir número de Nequi
                "$timestamp|$nombreDestinatario|$cantidad|$flagTransfiya|$referencia|$fechaEscapada|$numeroNequi"
            } else {
                "$timestamp|$nombreDestinatario|$cantidad|$flagTransfiya|$referencia|$fechaEscapada"
            }
        } else {
            "$timestamp|$nombreDestinatario|$cantidad|$flagTransfiya"
        }
        
        android.util.Log.d("EnvioMovimientos", "Guardando envío: esDeTransfiya=$esDeTransfiya, referencia='$referencia', fechaComprobante='$fechaComprobante', numeroNequi='$numeroNequi'")
        android.util.Log.d("EnvioMovimientos", "Formato del envío guardado: '$nuevoEnvio'")
        
        // Agregar el nuevo envío
        enviosMutable.add(nuevoEnvio)
        
        // Guardar envíos actualizados de forma síncrona
        editor.putStringSet("envios", enviosMutable)
        val saveSuccess = editor.commit() // Usar commit para guardar inmediatamente
        android.util.Log.d("EnvioMovimientos", "SharedPreferences guardado: $saveSuccess")
        
        // Verificar inmediatamente lo que se guardó
        val enviosVerificacion = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        val envioRecienGuardado = enviosVerificacion.find { it.startsWith("$timestamp|") }
        android.util.Log.d("EnvioMovimientos", "Verificación inmediata - Envío recién guardado: '$envioRecienGuardado'")
        
        android.util.Log.d("EnvioMovimientos", "🔍 Verificando si el número se guardó correctamente...")
        if (envioRecienGuardado != null) {
            val partes = envioRecienGuardado.split("|")
            android.util.Log.d("EnvioMovimientos", "   - Partes del envío: ${partes.size}")
            if (partes.size >= 7) {
                android.util.Log.d("EnvioMovimientos", "   - Número guardado: '${partes[6]}'")
            } else {
                android.util.Log.d("EnvioMovimientos", "   - ❌ Número NO se guardó (solo ${partes.size} partes)")
            }
        }
        
        android.util.Log.d("EnvioMovimientos", "Envío guardado: $nombreDestinatario - $cantidad (Transfiya: $esDeTransfiya)")
    }
    
    // Función para configurar scroll listener
    private fun configurarScrollListener() {
        scrollEnviosMovimientos.viewTreeObserver.addOnScrollChangedListener {
            val scrollView = scrollEnviosMovimientos
            
            // Calcular si se llegó al final del scroll
            val childView = scrollView.getChildAt(0)
            val scrollEnd = childView.height - scrollView.height
            
            // Mostrar "Cargar más" cuando se llega al final del scroll Y hay más envíos disponibles - DESHABILITADO
            if (scrollView.scrollY >= scrollEnd - 300 && hayMasEnviosDisponibles()) { // -300 para un margen más amplio
                // btnCargarMas.visibility = View.VISIBLE
                btnCargarMas.visibility = View.GONE
                progressBarCargarMas.visibility = View.GONE
                android.util.Log.d("CargarMas", "Mostrando botón - scrollY: ${scrollView.scrollY}, scrollEnd: $scrollEnd")
            } else {
                btnCargarMas.visibility = View.GONE
                progressBarCargarMas.visibility = View.GONE
            }
        }
        
        // Configurar click listener del botón "Cargar más"
        btnCargarMas.setOnClickListener {
            android.util.Log.d("CargarMas", "Botón 'Cargar más' presionado!")
            cargarMasEnvios()
        }
        
        // Ocultar botón por defecto
        btnCargarMas.visibility = View.GONE
        android.util.Log.d("CargarMas", "Botón 'Cargar más' configurado y oculto por defecto")

    }
    
    // Función para verificar si hay más envíos disponibles para cargar
    private fun hayMasEnviosDisponibles(): Boolean {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val envios = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        
        // Obtener fecha de referencia según el estado actual
        val calendar = java.util.Calendar.getInstance()
        val fechaReferencia = when (estadoMovimientosActual) {
            "ayer" -> {
                calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)
                calendar.time
            }
            else -> calendar.time // "hoy"
        }
        
        // Formatear fecha de referencia para comparación
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val fechaReferenciaStr = formatter.format(fechaReferencia)
        
        // Contar envíos totales para la fecha actual
        val totalEnvios = envios.count { envio ->
            val partes = envio.split("|")
            if (partes.size >= 3) {
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.util.Date(timestamp)
                val fechaEnvioStr = formatter.format(fechaEnvio)
                fechaEnvioStr == fechaReferenciaStr
            } else false
        }
        
        return enviosActualmenteVisibles < totalEnvios
    }
    
    // Función para cargar más envíos
    private fun cargarMasEnvios() {
        try {
            android.util.Log.d("CargarMas", "=== INICIANDO CARGA DE MÁS ENVÍOS ===")
            
            // Ocultar botón y mostrar indicador de carga
            btnCargarMas.visibility = View.GONE
            progressBarCargarMas.visibility = View.VISIBLE
            android.util.Log.d("CargarMas", "Botón ocultado, ProgressBar mostrado")
            
            // Simular carga de 1 segundo
            Handler(Looper.getMainLooper()).postDelayed({
                // Ocultar el indicador de carga
                progressBarCargarMas.visibility = View.GONE
                
                // Cargar más envíos anteriores
                cargarEnviosAnteriores()
                
                android.util.Log.d("CargarMas", "Carga completada - se puede seguir scrolleando")
            }, 1000) // 1 segundo
            
        } catch (e: Exception) {
            android.util.Log.e("CargarMas", "Error cargando más envíos: ${e.message}", e)
            // En caso de error, mantener el botón oculto
            // btnCargarMas.visibility = View.VISIBLE
            progressBarCargarMas.visibility = View.GONE
        }
    }
    
    // Función para cargar envíos anteriores (siguiente página)
    private fun cargarEnviosAnteriores() {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val envios = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        
        // Obtener fecha de referencia según el estado actual
        val calendar = java.util.Calendar.getInstance()
        val fechaReferencia = when (estadoMovimientosActual) {
            "ayer" -> {
                calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)
                calendar.time
            }
            else -> calendar.time // "hoy"
        }
        
        // Formatear fecha de referencia para comparación
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val fechaReferenciaStr = formatter.format(fechaReferencia)
        
        // Filtrar y ordenar envíos por fecha
        val enviosFiltrados = envios.mapNotNull { envio ->
            val partes = envio.split("|")
            if (partes.size >= 7) {
                // Formato NUEVO con archivo de comprobante
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.util.Date(timestamp)
                val fechaEnvioStr = formatter.format(fechaEnvio)
                val esDeTransfiya = partes[3] == "1"
                val referencia = partes[4]
                val fechaComprobante = partes[5].replace("_SPACE_", " ").replace("_COLON_", ":").replace("_DOT_", ".")
                val rutaArchivo = partes[6].replace("_PIPE_", "|").replace("_SPACE_", " ")
                
                if (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr) {
                    if (esDeTransfiya) {
                        // Solo crear EnvioDataConArchivo si ES de Transfiya
                        android.util.Log.d("CargarEnvios", "Envío de Transfiya con archivo: referencia='$referencia', ruta='$rutaArchivo'")
                        EnvioDataConArchivo(timestamp, partes[1], partes[2], esDeTransfiya, referencia, fechaComprobante, rutaArchivo)
                    } else {
                        // Envío normal con formato de 7 partes - tratarlo como normal
                        android.util.Log.d("CargarEnvios", "Envío normal con formato 7 partes")
                        EnvioData(timestamp, partes[1], partes[2], false)
                    }
                } else null
            } else if (partes.size >= 6) {
                // Formato ANTERIOR con datos de comprobante (sin archivo)
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.util.Date(timestamp)
                val fechaEnvioStr = formatter.format(fechaEnvio)
                val esDeTransfiya = partes[3] == "1"
                val referencia = partes[4]
                val fechaComprobante = partes[5].replace("_SPACE_", " ").replace("_COLON_", ":").replace("_DOT_", ".")
                
                if (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr) {
                    EnvioData(timestamp, partes[1], partes[2], esDeTransfiya, referencia, fechaComprobante)
                } else null
            } else if (partes.size >= 4) {
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.util.Date(timestamp)
                val fechaEnvioStr = formatter.format(fechaEnvio)
                val flag = partes[3]
                val esDeTransfiya = flag == "1"
                val esDeBancolombia = flag == "2"
                val esDeEnvioganancia = flag == "4"
                val esDeEnvio5 = flag == "5"
                
                if (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr) {
                    EnvioData(timestamp, partes[1], partes[2], esDeTransfiya, esDeBancolombia = esDeBancolombia, esDeEnvioganancia = esDeEnvioganancia, esDeEnvio5 = esDeEnvio5)
                } else null
            } else if (partes.size >= 3) {
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.util.Date(timestamp)
                val fechaEnvioStr = formatter.format(fechaEnvio)
                
                if (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr) {
                    EnvioData(timestamp, partes[1], partes[2], false)
                } else null
            } else null
        }.sortedByDescending { 
            when (it) {
                is EnvioDataConArchivo -> it.timestamp
                is EnvioData -> it.timestamp
                else -> 0L
            }
        }
        
        // Obtener los siguientes envíos para mostrar
        val siguientesEnvios = enviosFiltrados.drop(enviosActualmenteVisibles).take(enviosPorPagina)
        
        if (siguientesEnvios.isNotEmpty()) {
            // Crear los nuevos envíos en la UI
            for ((index, envio) in siguientesEnvios.withIndex()) {
                when (envio) {
                    is EnvioDataConArchivo -> {
                        // Envío de Transfiya con archivo de comprobante
                        if (envio.nombre.isNotEmpty() && envio.cantidad.isNotEmpty()) {
                            crearEnvioTransfiyaConArchivo(envio.nombre, envio.cantidad, envio.referencia, envio.fechaComprobante, envio.rutaArchivo)
                        }
                    }
                    is EnvioData -> {
                        // Envío normal o Transfiya sin archivo
                        if (envio.nombre.isNotEmpty() && envio.cantidad.isNotEmpty()) {
                            if (envio.esDeTransfiya) {
                                crearEnvioTransfiya(envio.nombre, envio.cantidad, envio.referencia, envio.fechaComprobante)
                            } else {
                                crearEnvioNormal(envio.nombre, envio.cantidad, "", "", envio.numeroNequi)
                            }
                        }
                    }
                }
                
                // Aplicar espaciado correcto
                val ultimoElemento = llEnviosMovimientos.getChildAt(llEnviosMovimientos.childCount - 1)
                val params = ultimoElemento.layoutParams as FrameLayout.LayoutParams
                params.topMargin = ((enviosActualmenteVisibles + index) * 220)
                ultimoElemento.layoutParams = params
            }
            
            // Actualizar contador de envíos visibles
            enviosActualmenteVisibles += siguientesEnvios.size
            
            // Mostrar el botón si hay más envíos disponibles - DESHABILITADO
            if (enviosActualmenteVisibles < enviosFiltrados.size) {
                // btnCargarMas.visibility = View.VISIBLE
                btnCargarMas.visibility = View.GONE
            } else {
                btnCargarMas.visibility = View.GONE
            }
            
            android.util.Log.d("CargarMas", "Cargados ${siguientesEnvios.size} envíos adicionales. Total visible: $enviosActualmenteVisibles de ${enviosFiltrados.size}")
        } else {
            // No hay más envíos, ocultar botón
            btnCargarMas.visibility = View.GONE
            android.util.Log.d("CargarMas", "No hay más envíos para cargar")
        }
    }
    
    // Función para cargar envíos desde SharedPreferences
    private fun cargarEnviosDesdeSharedPreferences() {
        // Limpiar envíos antiguos sin datos de comprobante
        limpiarEnviosAntiguosSinComprobante()
        
        // Limpiar espacios en envíos existentes
        limpiarEspaciosEnEnviosExistentes()
        
        // Cargar estado guardado
        estadoMovimientosActual = cargarEstadoMovimientos()
        
        // Verificar si es ayer o hoy basado en la fecha actual
        val calendar = java.util.Calendar.getInstance()
        val fechaHoy = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(calendar.time)
        val fechaAyer = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(calendar.time.time - 24 * 60 * 60 * 1000))

        // Guardar la fecha de hoy para detectar cambio de día
        guardarUltimaFechaRenderizada(fechaHoy)
        
        // Obtener envíos de hoy y ayer
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val envios = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        val enviosHoy = envios.any { envio ->
            val partes = envio.split("|")
            if (partes.isNotEmpty()) {
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(timestamp))
                fechaEnvio == fechaHoy
            } else false
        }
        val enviosAyer = envios.any { envio ->
            val partes = envio.split("|")
            if (partes.isNotEmpty()) {
                val timestamp = partes[0].toLongOrNull() ?: 0L
                val fechaEnvio = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(timestamp))
                fechaEnvio == fechaAyer
            } else false
        }
        
        // Determinar el estado actual: mantener "hoy" salvo que el usuario haya elegido "movimientosanteriores"
        if (estadoMovimientosActual != "movimientosanteriores") {
            estadoMovimientosActual = "hoy"
        }

        // Configurar UI según estado
        if (estadoMovimientosActual == "movimientosanteriores") {
            tvHoyTextoMovimientos.text = "Ayer"
            btnMasMovimientos.background = resources.getDrawable(R.drawable.rounded_button_pink, theme)
            btnMasMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.white))
            btnHoyMovimientos.background = resources.getDrawable(R.drawable.rounded_button_white_border, theme)
            btnHoyMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            ivMasMovimientos.visibility = View.VISIBLE
            ivHoyMovimientos.visibility = View.INVISIBLE
            cargarEnviosPorFecha("movimientosanteriores")
        } else {
            // Estado "hoy": botones y etiqueta
            btnHoyMovimientos.background = resources.getDrawable(R.drawable.rounded_button_pink, theme)
            btnHoyMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.white))
            btnMasMovimientos.background = resources.getDrawable(R.drawable.rounded_button_white_border, theme)
            btnMasMovimientos.setTextColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.black))
            ivHoyMovimientos.visibility = View.VISIBLE
            ivMasMovimientos.visibility = View.INVISIBLE
            // Etiqueta dinámica: si hay envíos hoy -> "Hoy", si no pero hay ayer -> "Ayer"
            tvHoyTextoMovimientos.text = if (enviosHoy) "Hoy" else if (enviosAyer) "Ayer" else "Hoy"
            cargarEnviosPorFecha("hoy")
        }
    }

    // Detectar cambio de día y refrescar la UI de movimientos
    private fun maybeRefreshMovimientosIfDayChanged() {
        try {
            val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val hoy = formatter.format(java.util.Calendar.getInstance().time)
            val ultima = cargarUltimaFechaRenderizada()
            if (ultima != hoy) {
                android.util.Log.d("Movimientos", "🕛 Cambio de día detectado ($ultima -> $hoy). Refrescando lista y encabezados")
                // Resetear la lista y volver a cargar según estado actual
                if (estadoMovimientosActual == "movimientosanteriores") {
                    tvHoyTextoMovimientos.text = "Ayer"
                    cargarEnviosPorFecha("movimientosanteriores")
                } else {
                    // Recalcular etiqueta dinámica y recargar "hoy" (que ya no incluye el nuevo ayer)
                    cargarEnviosDesdeSharedPreferences()
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error verificando cambio de día: ${e.message}", e)
        }
    }

    private fun guardarUltimaFechaRenderizada(fecha: String) {
        try {
            val sp = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            sp.edit().putString("ultimaFechaRenderizada", fecha).apply()
        } catch (_: Exception) { }
    }

    private fun cargarUltimaFechaRenderizada(): String {
        return try {
            val sp = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            sp.getString("ultimaFechaRenderizada", "") ?: ""
        } catch (_: Exception) { "" }
    }
    
    // Clase de datos para manejar información de envíos
    data class EnvioData(
        val timestamp: Long,
        val nombre: String,
        val cantidad: String,
        val esDeTransfiya: Boolean,
        val referencia: String = "",
        val fechaComprobante: String = "",
        val numeroNequi: String = "",
        val esDeBancolombia: Boolean = false,
        val esDeEnvioganancia: Boolean = false,
        val esDeEnvio5: Boolean = false
    )
    
    // Clase para datos de envío con archivo de comprobante
    data class EnvioDataConArchivo(
        val timestamp: Long,
        val nombre: String,
        val cantidad: String,
        val esDeTransfiya: Boolean,
        val referencia: String,
        val fechaComprobante: String,
        val rutaArchivo: String
    )
    
    // Función para depurar envíos inválidos sin borrar históricos compatibles
    private fun limpiarEnviosAntiguosSinComprobante() {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val envios = sharedPref.getStringSet("envios", setOf()) ?: setOf()

        // Mantener compatibilidad: NO eliminar formatos antiguos (3 o 4+ partes)
        // Solo descartar entradas vacías o completamente inválidas
        val enviosLimpios = envios.filter { envio ->
            val e = envio.trim()
            if (e.isEmpty()) return@filter false
            val partes = e.split("|")
            // Considerar válidos todos los formatos conocidos (>=3 partes)
            partes.size >= 3
        }.toSet()

        val eliminados = envios.size - enviosLimpios.size

        // Guardar envíos depurados
        val editor = sharedPref.edit()
        editor.putStringSet("envios", enviosLimpios)
        editor.apply()

        android.util.Log.d(
            "EnvioMovimientos",
            if (eliminados > 0)
                "Entradas inválidas removidas: $eliminados (se preservan formatos antiguos sin comprobante)"
            else
                "No se eliminaron envíos; se preservan formatos antiguos y con/sin comprobante"
        )
    }
    
    // Función para limpiar espacios de números en envíos existentes
    private fun limpiarEspaciosEnEnviosExistentes() {
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val envios = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        val enviosLimpios = envios.mapNotNull { envio ->
            val partes = envio.split("|")
            if (partes.size >= 7) {
                // Formato NUEVO con archivo de comprobante (7 partes: timestamp|nombre|cantidad|flag|referencia|fecha|rutaArchivo)
                val timestamp = partes[0]
                val nombre = if (partes[3] == "1") {
                    // Para Transfiya: limpiar espacios del celular
                    partes[1].replace(" ", "")
                } else {
                    partes[1]
                }
                val cantidad = partes[2].replace(" ", "") // Limpiar espacios
                val esDeTransfiya = partes[3]
                val referencia = partes[4] // PRESERVAR referencia
                val fechaComprobante = partes[5] // PRESERVAR fecha
                val rutaArchivo = partes[6] // PRESERVAR ruta del archivo
                "$timestamp|$nombre|$cantidad|$esDeTransfiya|$referencia|$fechaComprobante|$rutaArchivo"
            } else if (partes.size >= 6) {
                // Formato ANTERIOR con datos de comprobante (sin archivo)
                val timestamp = partes[0]
                val nombre = if (partes[3] == "1") {
                    partes[1].replace(" ", "")
                } else {
                    partes[1]
                }
                val cantidad = partes[2].replace(" ", "")
                val esDeTransfiya = partes[3]
                val referencia = partes[4] // PRESERVAR referencia
                val fechaComprobante = partes[5] // PRESERVAR fecha
                "$timestamp|$nombre|$cantidad|$esDeTransfiya|$referencia|$fechaComprobante"
            } else if (partes.size >= 4) {
                // Formato con flag de Transfiya (sin datos de comprobante)
                val timestamp = partes[0]
                val nombre = if (partes[3] == "1") {
                    // Para Transfiya: limpiar espacios del celular
                    partes[1].replace(" ", "")
                } else {
                    // Para envíos normales: mantener espacios del nombre
                    partes[1]
                }
                val cantidad = partes[2].replace(" ", "") // Limpiar espacios
                val esDeTransfiya = partes[3]
                "$timestamp|$nombre|$cantidad|$esDeTransfiya"
            } else if (partes.size >= 3) {
                // Formato antiguo (sin flag de Transfiya) - mantener espacios
                val timestamp = partes[0]
                val nombre = partes[1] // Mantener espacios del nombre
                val cantidad = partes[2].replace(" ", "") // Limpiar espacios
                "$timestamp|$nombre|$cantidad"
            } else null
        }.toSet()
        
        // Guardar envíos limpios
        val editor = sharedPref.edit()
        editor.putStringSet("envios", enviosLimpios)
        editor.apply()
        
        android.util.Log.d("Transfiya", "Espacios limpiados en ${enviosLimpios.size} envíos - PRESERVANDO datos de comprobante")
    }
    
    // Función para obtener texto de fecha (hoy, ayer, día de la semana)
    private fun obtenerTextoFecha(timestamp: Long): String {
        val calendar = java.util.Calendar.getInstance()
        val fechaEnvio = java.util.Date(timestamp)
        val fechaHoy = calendar.time
        
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val fechaEnvioStr = formatter.format(fechaEnvio)
        val fechaHoyStr = formatter.format(fechaHoy)
        
        return when {
            fechaEnvioStr == fechaHoyStr -> "hoy"
            fechaEnvioStr == formatter.format(java.util.Date(fechaHoy.time - 24 * 60 * 60 * 1000)) -> "ayer"
            else -> {
                val diaSemana = java.text.SimpleDateFormat("EEEE", java.util.Locale("es", "ES")).format(fechaEnvio)
                diaSemana.lowercase().replaceFirstChar { it.uppercase() }
            }
        }
    }
    
    // Función auxiliar para procesar envíos según su formato
    private fun procesarEnvioSegunFormato(partes: List<String>, timestamp: Long): Any? {
        return when (partes.size) {
            7 -> {
                val tipo = partes[3]
                val esDeTransfiya = tipo == "1"
                val esDeBancolombia = tipo == "2"
                val esDeEnvioganancia = tipo == "4"
                val esDeEnvio5 = tipo == "5"
                val referencia = partes[4]
                val fechaComprobante = partes[5].replace("_SPACE_", " ").replace("_COLON_", ":").replace("_DOT_", ".")
                val campo7 = partes[6]
                if (esDeTransfiya) {
                    val rutaArchivo = campo7.replace("_PIPE_", "|").replace("_SPACE_", " ")
                    EnvioDataConArchivo(timestamp, partes[1], partes[2], true, referencia, fechaComprobante, rutaArchivo)
                } else {
                    val numeroNequiGuardado = campo7
                    EnvioData(
                        timestamp,
                        partes[1],
                        partes[2],
                        false,
                        referencia,
                        fechaComprobante,
                        numeroNequiGuardado,
                        esDeBancolombia = esDeBancolombia,
                        esDeEnvioganancia = esDeEnvioganancia,
                        esDeEnvio5 = esDeEnvio5
                    )
                }
            }
            6 -> {
                val tipo = partes[3]
                val esDeTransfiya = tipo == "1"
                val esDeBancolombia = tipo == "2"
                val esDeEnvioganancia = tipo == "4"
                val esDeEnvio5 = tipo == "5"
                val referencia = partes[4]
                val fechaComprobante = partes[5].replace("_SPACE_", " ").replace("_COLON_", ":").replace("_DOT_", ".")
                EnvioData(
                    timestamp,
                    partes[1],
                    partes[2],
                    esDeTransfiya,
                    referencia,
                    fechaComprobante,
                    esDeBancolombia = esDeBancolombia,
                    esDeEnvioganancia = esDeEnvioganancia,
                    esDeEnvio5 = esDeEnvio5
                )
            }
            4 -> {
                val tipo = partes[3]
                val esDeTransfiya = tipo == "1"
                val esDeBancolombia = tipo == "2"
                val esDeEnvioganancia = tipo == "4"
                val esDeEnvio5 = tipo == "5"
                if (esDeTransfiya) {
                    EnvioData(timestamp, partes[1], partes[2], true)
                } else if (esDeBancolombia) {
                    EnvioData(timestamp, partes[1], partes[2], false, esDeBancolombia = true)
                } else if (esDeEnvioganancia) {
                    EnvioData(timestamp, partes[1], partes[2], false, esDeEnvioganancia = true)
                } else if (esDeEnvio5) {
                    EnvioData(timestamp, partes[1], partes[2], false, esDeEnvio5 = true)
                } else {
                    // Normal sin datos extra
                    EnvioData(timestamp, partes[1], partes[2], false)
                }
            }
            3 -> {
                // Antiguo: tratar como normal
                EnvioData(timestamp, partes[1], partes[2], false)
            }
            else -> null
        }
    }
    
    // Función para cargar envíos por fecha específica
    private fun cargarEnviosPorFecha(fecha: String) {
        android.util.Log.d("CargarEnviosPorFecha", "Iniciando carga para fecha: $fecha")
        val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
        val envios = sharedPref.getStringSet("envios", setOf()) ?: setOf()
        android.util.Log.d("CargarEnviosPorFecha", "Total envíos en SharedPreferences: ${envios.size}")
        envios.forEach { envio ->
            android.util.Log.d("CargarEnviosPorFecha", "Envío en SP: '$envio'")
        }
        
        // Limpiar envíos existentes en la UI
        llEnviosMovimientos.removeAllViews()
        
        // Obtener fecha de referencia
        val calendar = java.util.Calendar.getInstance()
        // Persistir "hoy" como fecha base cuando se carga la vista de hoy
        if (fecha.equals("hoy", ignoreCase = true)) {
            val hoyStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(calendar.time)
            guardarUltimaFechaRenderizada(hoyStr)
        }
        val fechaReferencia = when (fecha) {
            "hoy" -> {
                calendar.time
            }
            "ayer" -> {
                calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)
                calendar.time
            }
            "movimientosanteriores" -> {
                // Para movimientosanteriores, no filtrar por fecha específica
                null
            }
            else -> calendar.time
        }
        
        // Formatear fecha de referencia para comparación
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val fechaReferenciaStr = if (fechaReferencia != null) formatter.format(fechaReferencia) else null
        
        // Filtrar y ordenar envíos por fecha
        val enviosFiltrados = if (fecha == "movimientosanteriores") {
            // Para movimientosanteriores, incluir todos los envíos excepto los de hoy y ayer
            val fechaHoy = formatter.format(calendar.time)
            val fechaAyer = formatter.format(java.util.Date(calendar.time.time - 24 * 60 * 60 * 1000))
            
            envios.mapNotNull { envio ->
                val partes = envio.split("|")
                android.util.Log.d("CargarEnvios", "🔍 Procesando envío: '$envio' -> ${partes.size} partes")
                if (partes.isNotEmpty()) {
                    val timestamp = partes[0].toLongOrNull() ?: 0L
                    val fechaEnvio = formatter.format(java.util.Date(timestamp))
                    
                    // Incluir solo envíos que NO sean de hoy ni de ayer
                    if (fechaEnvio != fechaHoy && fechaEnvio != fechaAyer) {
                        android.util.Log.d("CargarEnvios", "   ✅ Envío incluido (no es de hoy/ayer)")
                        val resultado = procesarEnvioSegunFormato(partes, timestamp)
                        android.util.Log.d("CargarEnvios", "   📋 Resultado procesado: $resultado")
                        resultado
                    } else {
                        android.util.Log.d("CargarEnvios", "   ❌ Envío excluido (es de hoy/ayer)")
                        null
                    }
                } else {
                    android.util.Log.d("CargarEnvios", "   ❌ Envío inválido (partes vacías)")
                    null
                }
            }
        } else {
            // Para "hoy" y "ayer", incluir ambos grupos en "hoy" y solo ayer en "ayer"
            val fechaHoyStr = formatter.format(calendar.time)
            val fechaAyerStr = formatter.format(java.util.Date(calendar.time.time - 24 * 60 * 60 * 1000))
            envios.mapNotNull { envio ->
                val partes = envio.split("|")
                android.util.Log.d("CargarEnvios", "Parseando envío: '$envio' -> ${partes.size} partes")
                if (partes.size >= 7) {
                    // Formato NUEVO (7 partes): interpretar según flag (Transfiya: ruta; Normal: número)
                    val ts = partes[0].toLongOrNull() ?: 0L
                    val fechaEnvioStr = formatter.format(java.util.Date(ts))
                    val esDeTransfiya = partes[3] == "1"
                    val referencia = partes[4]
                    val fechaComprobante = partes[5].replace("_SPACE_", " ").replace("_COLON_", ":").replace("_DOT_", ".")

                    val incluir = when (fecha) {
                        "hoy" -> (fechaEnvioStr == fechaHoyStr || fechaEnvioStr == fechaAyerStr)
                        "ayer" -> (fechaEnvioStr == fechaAyerStr)
                        else -> (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr)
                    }

                    if (incluir) {
                        if (esDeTransfiya) {
                            val rutaArchivo = partes[6].replace("_PIPE_", "|").replace("_SPACE_", " ")
                            EnvioDataConArchivo(ts, partes[1], partes[2], true, referencia, fechaComprobante, rutaArchivo)
                        } else {
                            val numeroNequiGuardado = partes[6]
                            EnvioData(ts, partes[1], partes[2], false, referencia, fechaComprobante, numeroNequiGuardado)
                        }
                    } else null
                } else if (partes.size >= 6) {
                    // Formato ANTERIOR con datos de comprobante (sin archivo)
                    val timestamp = partes[0].toLongOrNull() ?: 0L
                    val fechaEnvio = java.util.Date(timestamp)
                    val fechaEnvioStr = formatter.format(fechaEnvio)
                    val esDeTransfiya = partes[3] == "1"
                    val referencia = partes[4]
                    // Revertir el escapado de la fecha
                    val fechaComprobante = partes[5].replace("_SPACE_", " ").replace("_COLON_", ":").replace("_DOT_", ".")
                    
                    android.util.Log.d("CargarEnvios", "📋 PARSEO 6 PARTES:")
                    android.util.Log.d("CargarEnvios", "   Flag: '${partes[3]}' -> esDeTransfiya: $esDeTransfiya")
                    android.util.Log.d("CargarEnvios", "   Referencia: '$referencia'")
                    android.util.Log.d("CargarEnvios", "   Fecha comprobante: '$fechaComprobante'")

                    val incluir = when (fecha) {
                        "hoy" -> (fechaEnvioStr == fechaHoyStr || fechaEnvioStr == fechaAyerStr)
                        "ayer" -> (fechaEnvioStr == fechaAyerStr)
                        else -> (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr)
                    }

                    if (incluir) {
                        android.util.Log.d("CargarEnvios", "   ✅ Creando EnvioData (6 partes)")
                        EnvioData(timestamp, partes[1], partes[2], esDeTransfiya, referencia, fechaComprobante)
                    } else null
                } else if (partes.size >= 4) {
                    // Formato con flag de Transfiya o Bancolombia (sin datos de comprobante)
                    val timestamp = partes[0].toLongOrNull() ?: 0L
                    val fechaEnvio = java.util.Date(timestamp)
                    val fechaEnvioStr = formatter.format(fechaEnvio)
                    
                    android.util.Log.d("CargarEnvios", "📋 PARSEO 4 PARTES:")
                    android.util.Log.d("CargarEnvios", "   Envío: '$envio'")

                    val incluir = when (fecha) {
                        "hoy" -> (fechaEnvioStr == fechaHoyStr || fechaEnvioStr == fechaAyerStr)
                        "ayer" -> (fechaEnvioStr == fechaAyerStr)
                        else -> (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr)
                    }

                    if (incluir) {
                        android.util.Log.d("CargarEnvios", "   ✅ Usando procesarEnvioSegunFormato para 4 partes")
                        procesarEnvioSegunFormato(partes, timestamp)
                    } else null
                } else if (partes.size >= 3) {
                    // Formato antiguo (sin flag de Transfiya)
                    val timestamp = partes[0].toLongOrNull() ?: 0L
                    val fechaEnvio = java.util.Date(timestamp)
                    val fechaEnvioStr = formatter.format(fechaEnvio)
                    
                    android.util.Log.d("CargarEnvios", "📋 PARSEO 3 PARTES:")
                    android.util.Log.d("CargarEnvios", "   Envío: '$envio'")

                    val incluir = when (fecha) {
                        "hoy" -> (fechaEnvioStr == fechaHoyStr || fechaEnvioStr == fechaAyerStr)
                        "ayer" -> (fechaEnvioStr == fechaAyerStr)
                        else -> (fechaReferenciaStr != null && fechaEnvioStr == fechaReferenciaStr)
                    }

                    if (incluir) {
                        android.util.Log.d("CargarEnvios", "   ✅ Usando procesarEnvioSegunFormato para 3 partes")
                        procesarEnvioSegunFormato(partes, timestamp)
                    } else null
                } else null
            }
        }.sortedByDescending { 
            when (it) {
                is EnvioDataConArchivo -> it.timestamp
                is EnvioData -> it.timestamp
                else -> 0L
            }
        }
        
        // Crear envíos en la UI con nuevas reglas (usar ventana actual)
        val limiteInicial = enviosActualmenteVisibles
        val enviosParaMostrar = enviosFiltrados.take(limiteInicial)
        
        var fechaAnterior = ""
        var itemsMostrados = 0
        var offsetY = 0 // Posición vertical acumulada para evitar solapes entre encabezados y envíos
        for ((index, envio) in enviosParaMostrar.withIndex()) {
            // Obtener texto de fecha para este envío
            val timestamp = when (envio) {
                is EnvioDataConArchivo -> envio.timestamp
                is EnvioData -> envio.timestamp
                else -> 0L
            }
            val textoFecha = obtenerTextoFecha(timestamp)
            
            // Agregar separador de fecha si cambia
            if (textoFecha != fechaAnterior) {
                val headerText = if (textoFecha.equals("hoy", ignoreCase = true)) "Hoy" else if (textoFecha.equals("ayer", ignoreCase = true)) "Ayer" else textoFecha
                // Evitar duplicar el texto "Hoy": ya existe un título fijo fuera del contenedor
                if (headerText != "Hoy") {
                    val fechaTextView = TextView(this).apply {
                        text = headerText
                        textSize = 14f
                        setTextColor(androidx.core.content.ContextCompat.getColor(this@InicioActivity, android.R.color.black))
                        typeface = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            topMargin = offsetY
                            leftMargin = (24 * resources.displayMetrics.density).toInt()
                            
                        }
                    }
                    llEnviosMovimientos.addView(fechaTextView)
                    // Reservar altura del encabezado (aprox 32dp)
                    val headerBlockHeight = (32 * resources.displayMetrics.density).toInt()
                    offsetY += headerBlockHeight
                }
                fechaAnterior = textoFecha
            }
            
            when (envio) {
                is EnvioDataConArchivo -> {
                    // Envío de Transfiya con archivo de comprobante
                    if (envio.nombre.isNotEmpty() && envio.cantidad.isNotEmpty()) {
                        crearEnvioTransfiyaConArchivo(envio.nombre, envio.cantidad, envio.referencia, envio.fechaComprobante, envio.rutaArchivo)
                    }
                }
                is EnvioData -> {
                    // Envío normal o Transfiya sin archivo
                    if (envio.nombre.isNotEmpty() && envio.cantidad.isNotEmpty()) {
                        android.util.Log.d("CargarEnvios", "🎯 DECIDIENDO TIPO DE ENVÍO:")
                        android.util.Log.d("CargarEnvios", "   Nombre: '${envio.nombre}'")
                        android.util.Log.d("CargarEnvios", "   Cantidad: '${envio.cantidad}'")
                        android.util.Log.d("CargarEnvios", "   esDeTransfiya: ${envio.esDeTransfiya}")
                        android.util.Log.d("CargarEnvios", "   esDeBancolombia: ${envio.esDeBancolombia}")
                        android.util.Log.d("CargarEnvios", "   esDeEnvioganancia: ${envio.esDeEnvioganancia}")
                        android.util.Log.d("CargarEnvios", "   referencia: '${envio.referencia}'")
                        android.util.Log.d("CargarEnvios", "   fechaComprobante: '${envio.fechaComprobante}'")
                        
                        if (envio.esDeTransfiya) {
                            android.util.Log.d("CargarEnvios", "   ➡️ Creando ENVÍO TRANSFIYA")
                            crearEnvioTransfiya(envio.nombre, envio.cantidad, envio.referencia, envio.fechaComprobante)
                        } else if (envio.esDeBancolombia) {
                            android.util.Log.d("CargarEnvios", "   ➡️ Creando ENVÍO BANCOLOMBIA")
                            crearEnvioBancolombia(envio.nombre, envio.cantidad)
                        } else if (envio.esDeEnvioganancia) {
                            android.util.Log.d("CargarEnvios", "   ➡️ Creando ENVÍO ENVIOGANANCIA")
                            crearEnvioEnvioganancia(envio.nombre, envio.cantidad)
                        } else if (envio.esDeEnvio5) {
                            android.util.Log.d("CargarEnvios", "   ➡️ Creando ENVÍO5")
                            agregarEnvio5AMovimientos(envio.nombre, envio.cantidad)
                        } else {
                            android.util.Log.d("CargarEnvios", "   ➡️ Creando ENVÍO NORMAL")
                            android.util.Log.d("CargarEnvios", "   📋 Datos para envío normal:")
                            android.util.Log.d("CargarEnvios", "      Nombre: '${envio.nombre}'")
                            android.util.Log.d("CargarEnvios", "      Cantidad: '${envio.cantidad}'")
                            android.util.Log.d("CargarEnvios", "      Referencia: '${envio.referencia}'")
                            android.util.Log.d("CargarEnvios", "      Fecha: '${envio.fechaComprobante}'")
                            android.util.Log.d("CargarEnvios", "      numeroNequi: '${envio.numeroNequi}'")
                            crearEnvioNormal(envio.nombre, envio.cantidad, envio.referencia, envio.fechaComprobante, envio.numeroNequi)
                        }
                    }
                }
            }
            
            // Posicionar el envío recién creado debajo del encabezado/elementos previos
            val ultimoElemento = llEnviosMovimientos.getChildAt(llEnviosMovimientos.childCount - 1)
            val params = ultimoElemento.layoutParams as FrameLayout.LayoutParams
            params.topMargin = offsetY
            ultimoElemento.layoutParams = params
            // Aumentar el offset para el siguiente elemento (altura aproximada del envío)
            offsetY += 220
            itemsMostrados++
        }

        // Insertar botón "Cargar más" como 9no elemento (cada bloque de 8)
        if (enviosFiltrados.size > limiteInicial) {
            val cargarMasContainer = FrameLayout(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = itemsMostrados * 220
                    leftMargin = 0
                    rightMargin = 0
                }
                isClickable = false
                isFocusable = false
            }
            val btnCargarMasRect = TextView(this).apply {
                text = "Cargar más"
                setTextColor(android.graphics.Color.BLACK)
                textSize = 16f
                gravity = android.view.Gravity.CENTER
                background = resources.getDrawable(R.drawable.rounded_button_white_border_thin, theme)
                val padV = (10 * resources.displayMetrics.density).toInt()
                val padH = (22 * resources.displayMetrics.density).toInt()
                setPadding(padH, padV, padH, padV)
                // Aumentar un poco el ancho mínimo para que el rectángulo se vea más ancho
                val minW = (260 * resources.displayMetrics.density).toInt()
                setMinWidth(minW)
                isClickable = true
                isFocusable = true
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                ).apply { gravity = android.view.Gravity.CENTER_HORIZONTAL }
            }
            val progress = ProgressBar(this).apply {
                isIndeterminate = true
                indeterminateTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.BLACK)
                visibility = View.GONE
                layoutParams = FrameLayout.LayoutParams(
                    (18 * resources.displayMetrics.density).toInt(),
                    (18 * resources.displayMetrics.density).toInt(),
                ).apply { gravity = android.view.Gravity.CENTER_HORIZONTAL }
                isClickable = false
                isFocusable = false
            }
            cargarMasContainer.addView(btnCargarMasRect)
            cargarMasContainer.addView(progress)
            llEnviosMovimientos.addView(cargarMasContainer)

            btnCargarMasRect.setOnClickListener {
                btnCargarMasRect.visibility = View.GONE
                progress.visibility = View.VISIBLE
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    // Aumentar ventana y recargar siguientes 8
                    enviosActualmenteVisibles = (enviosActualmenteVisibles + 8).coerceAtMost(enviosFiltrados.size)
                    cargarEnviosPorFecha(fecha)
                }, 1000)
            }
        }
        
        android.util.Log.d("EnvioMovimientos", "Envíos de $fecha cargados: ${enviosParaMostrar.size} de ${enviosFiltrados.size} totales")
        
        // Controlar visibilidad del texto "Hoy" y la imagen "min.png" según si hay envíos
        if (enviosParaMostrar.isEmpty()) {
            // No hay envíos: ocultar texto "Hoy" y mostrar imagen "min.png"
            tvHoyTextoMovimientos.visibility = View.GONE
            ivMinMovimientos.visibility = View.VISIBLE
            android.util.Log.d("Movimientos", "No hay envíos - ocultando texto 'Hoy' y mostrando min.png")
        } else {
            // Hay envíos: mostrar texto "Hoy" y ocultar imagen "min.png"
            tvHoyTextoMovimientos.visibility = if (estadoMovimientosActual == "hoy") View.VISIBLE else View.GONE
            ivMinMovimientos.visibility = View.INVISIBLE
            android.util.Log.d("Movimientos", "Hay ${enviosParaMostrar.size} envíos - mostrando texto 'Hoy' y ocultando min.png")
        }
    }
    // Función para crear envío de Transfiya CON archivo de comprobante guardado
    private fun crearEnvioTransfiyaConArchivo(numeroCelular: String, cantidad: String, referencia: String, fechaComprobante: String, rutaArchivo: String) {
        android.util.Log.d("TransfiyaConArchivo", "Creando envío con archivo: ruta='$rutaArchivo'")
        
        // Generar imagen con textos incluidos usando la nueva función
        android.util.Log.d("TransfiyaConArchivo", "Llamando a generarImagenEnvioTransfiya...")
        val imagenGenerada = generarImagenEnvioTransfiya(numeroCelular, cantidad, referencia, fechaComprobante)
        android.util.Log.d("TransfiyaConArchivo", "Imagen generada: ${imagenGenerada.width}x${imagenGenerada.height}")
        
        // Crear un FrameLayout para contener la imagen generada
        val containerLayout = FrameLayout(this)
        
        // Crear ImageView con la imagen generada
        val imageView = ImageView(this).apply {
            setImageBitmap(imagenGenerada)
            contentDescription = "Envio Transfiya"
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        

        
        // Crear área clickeable más pequeña pero funcional para Transfiya con archivo
        val areaClickeable = View(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                (325 * resources.displayMetrics.density).toInt(), // Ancho igual
                (70 * resources.displayMetrics.density).toInt()  // Alto más pequeño
            ).apply {
                // Posicionar RELATIVO al contenedor padre (no absoluto)
                gravity = android.view.Gravity.START or android.view.Gravity.TOP
                leftMargin = (30 * resources.displayMetrics.density).toInt()
                topMargin = (2 * resources.displayMetrics.density).toInt() // Subido más arriba
            }
            // Área invisible para el usuario (no debe verse ni interferir visualmente)
            alpha = 0f
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
            isClickable = true
            isFocusable = true
            // Agregar padding para hacer el área más grande
            setPadding(10, 10, 10, 10)
            
            android.util.Log.d("TransfiyaConArchivo", "✅ CREANDO área clickeable PEQUEÑA para archivo")
            android.util.Log.d("TransfiyaConArchivo", "Área posicionada RELATIVAMENTE al contenedor para evitar superposición")
            android.util.Log.d("TransfiyaConArchivo", "Dimensiones: ${(325 * resources.displayMetrics.density).toInt()}x${(70 * resources.displayMetrics.density).toInt()}")
            android.util.Log.d("TransfiyaConArchivo", "Posición: leftMargin=${(30 * resources.displayMetrics.density).toInt()}, topMargin=${(120 * resources.displayMetrics.density).toInt()}")
            android.util.Log.d("TransfiyaConArchivo", "Padding: 10dp en todos los lados")
            
            setOnClickListener {
                android.util.Log.d("TransfiyaConArchivo", "🔥 CLICK EN ÁREA PEQUEÑA CON ARCHIVO 🔥")
                android.util.Log.d("TransfiyaConArchivo", "Cargando desde: $rutaArchivo")
                android.util.Log.d("TransfiyaConArchivo", "Número: $numeroCelular, Cantidad: $cantidad")
            
                // Cargar bitmap desde archivo
                val bitmapCargado = cargarComprobanteDesdeArchivo(rutaArchivo)
                
                if (bitmapCargado != null) {
                    android.util.Log.d("TransfiyaConArchivo", "Comprobante cargado exitosamente desde archivo")
                    android.util.Log.d("TransfiyaConArchivo", "Tamaño del bitmap: ${bitmapCargado.width}x${bitmapCargado.height}")
                    // Mostrar el comprobante cargado
                    mostrarComprobanteImagenTransfiya(bitmapCargado, numeroCelular, cantidad)
                } else {
                    android.util.Log.e("TransfiyaConArchivo", "No se pudo cargar comprobante desde archivo")
                    android.util.Log.e("TransfiyaConArchivo", "Ruta del archivo: $rutaArchivo")
                    // Fallback: mostrar mensaje de error
                    android.widget.Toast.makeText(this@InicioActivity, "Error: No se pudo cargar el comprobante", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }
        
        // Agregar imagen al contenedor
        containerLayout.addView(imageView)
        
        // Agregar área clickeable pequeña
        containerLayout.addView(areaClickeable)
        android.util.Log.d("TransfiyaConArchivo", "🔲 Área clickeable agregada al contenedor #${llEnviosMovimientos.childCount}")
        android.util.Log.d("TransfiyaConArchivo", "Posición relativa: leftMargin=30dp, topMargin=70dp")
        android.util.Log.d("TransfiyaConArchivo", "Área clickeable configurada: isClickable=${areaClickeable.isClickable}, isFocusable=${areaClickeable.isFocusable}")
        
        // ASEGURAR que el container NO sea clickeable
        containerLayout.setOnClickListener(null)
        containerLayout.isClickable = false
        containerLayout.isFocusable = false
        
        // Agregar el contenedor al layout de movimientos
        llEnviosMovimientos.addView(containerLayout)
        
        android.util.Log.d("TransfiyaConArchivo", "Envío de Transfiya con archivo creado: $numeroCelular - $cantidad")
    }
    
    // Función para generar imagen de envío normal con textos incluidos
    private fun generarImagenEnvioNormal(nombreDestinatario: String, cantidad: String, etiquetaLinea2: String? = null): android.graphics.Bitmap {
        try {
            android.util.Log.d("GenerarEnvioNormal", "Generando imagen de envío normal")
            android.util.Log.d("GenerarEnvioNormal", "Nombre: '$nombreDestinatario', Cantidad: '$cantidad'")
            
            // Cargar la imagen base envio.png
            val imagenBase = desencriptarBin("envio.bin")
            
            // Verificar que la imagen se cargó correctamente
            if (imagenBase == null) {
                android.util.Log.e("GenerarEnvioNormal", "No se pudo cargar la imagen base envio.bin")
                throw Exception("No se pudo cargar la imagen base")
            }
            
            // Crear un bitmap mutable del mismo tamaño
            val bitmapMutable = imagenBase.copy(android.graphics.Bitmap.Config.ARGB_8888, true)
            val canvas = android.graphics.Canvas(bitmapMutable)
            
            // Configurar paint para el nombre
            val paintNombre = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Configurar paint para la etiqueta de la segunda línea (por defecto "Para")
            val paintPara = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Configurar paint para la cantidad
            val paintCantidad = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 77f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            
            // Formatear cantidad
            val cantidadFormateada = if (cantidad.isNotEmpty()) {
                val cantidadLimpia = cantidad.replace("$", "").replace(" ", "").trim()
                val cantidadSinPuntosMiles = cantidadLimpia.replace(".", "")
                val cantidadParaParsear = cantidadSinPuntosMiles.replace(",", ".")
                val numero = cantidadParaParsear.toDoubleOrNull()?.toLong() ?: 0L
                val formateado = String.format("%,d", numero)
                val numeroFormateado = formateado.replace(",", ".")
                "-$ $numeroFormateado,00"
            } else {
                "-$ 0,00"
            }
            
            // Poner el nombre en mayúsculas
        val nombreCapitalizado = nombreDestinatario.uppercase(java.util.Locale("es", "ES"))
        
            android.util.Log.d("GenerarEnvioNormal", "Dibujando nombre: '$nombreCapitalizado' en (315, 275)")
            android.util.Log.d("GenerarEnvioNormal", "Dibujando cantidad: '$cantidadFormateada' en (${bitmapMutable.width - 220}, 315)")
            
            // Dibujar textos en el canvas
            canvas.drawText(nombreCapitalizado, 130f, 95f, paintNombre)
            val etiquetaFinal = etiquetaLinea2 ?: "Para"
            canvas.drawText(etiquetaFinal, 130f, 140f, paintPara)
            
            // Dibujar cantidad con ",00" más pequeño
            val cantidadSinDecimales = cantidadFormateada.replace(",00", "")
            val paintCantidadGrande = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 35f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            val paintDecimales = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 25f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            
            canvas.drawText(cantidadSinDecimales, (bitmapMutable.width - 91).toFloat(), 120f, paintCantidadGrande)
            canvas.drawText(",00", (bitmapMutable.width - 50).toFloat(), 120f, paintDecimales)
            
            // Limpiar recursos
            imagenBase.recycle()
            
            android.util.Log.d("GenerarEnvioNormal", "Imagen generada exitosamente")
            return bitmapMutable
            
        } catch (e: Exception) {
            android.util.Log.e("GenerarEnvioNormal", "Error generando imagen: ${e.message}", e)
            // Retornar imagen base en caso de error
            val imagenBase = desencriptarBin("envio.bin")
            return imagenBase ?: throw Exception("No se pudo cargar la imagen base")
        }
    }
    
    // Función para generar imagen de envío Transfiya con textos incluidos
    private fun generarImagenEnvioTransfiya(numeroCelular: String, cantidad: String, referencia: String = "", fechaComprobante: String = ""): android.graphics.Bitmap {
        try {
            android.util.Log.d("GenerarEnvioTransfiya", "Generando imagen de envío Transfiya")
            android.util.Log.d("GenerarEnvioTransfiya", "Celular: '$numeroCelular', Cantidad: '$cantidad'")
            
            // Cargar la imagen base envio2.png
            val imagenBase = desencriptarBin("envio2.bin")
            
            // Verificar que la imagen se cargó correctamente
            if (imagenBase == null) {
                android.util.Log.e("GenerarEnvioTransfiya", "No se pudo cargar la imagen base envio2.bin")
                throw Exception("No se pudo cargar la imagen base")
            }
            
            // Crear un bitmap mutable del mismo tamaño
            val bitmapMutable = imagenBase.copy(android.graphics.Bitmap.Config.ARGB_8888, true)
            val canvas = android.graphics.Canvas(bitmapMutable)
            
            // Configurar paint para el número de celular
            val paintCelular = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Configurar paint para el texto "Envío Transfiya"
            val paintEnvioTransfiya = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Formatear cantidad
        val cantidadFormateada = if (cantidad.isNotEmpty()) {
                val cantidadLimpia = cantidad.replace("$", "").replace(" ", "").trim()
                val cantidadSinPuntosMiles = cantidadLimpia.replace(".", "")
                val cantidadParaParsear = cantidadSinPuntosMiles.replace(",", ".")
                val numero = cantidadParaParsear.toDoubleOrNull()?.toLong() ?: 0L
            val formateado = String.format("%,d", numero)
            val numeroFormateado = formateado.replace(",", ".")
            "-$ $numeroFormateado,00"
        } else {
            "-$ 0,00"
        }
        
            android.util.Log.d("GenerarEnvioTransfiya", "Dibujando celular: '$numeroCelular' en (315, 275)")
            android.util.Log.d("GenerarEnvioTransfiya", "Dibujando cantidad: '$cantidadFormateada' en (${bitmapMutable.width - 220}, 315)")
            
            // Dibujar textos en el canvas
            android.util.Log.d("GenerarEnvioTransfiya", "Intentando dibujar número de celular: '$numeroCelular'")
            canvas.drawText(numeroCelular, 130f, 95f, paintCelular)
            canvas.drawText("Envío Transfiya", 130f, 140f, paintEnvioTransfiya)
            
            // Dibujar cantidad con ",00" más pequeño usando moneda_bold
            val cantidadSinDecimales = cantidadFormateada.replace(",00", "")
            val paintCantidadGrande = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 35f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            val paintDecimales = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 25f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            
            android.util.Log.d("GenerarEnvioTransfiya", "Intentando dibujar cantidad: '$cantidadSinDecimales'")
            canvas.drawText(cantidadSinDecimales, (bitmapMutable.width - 91).toFloat(), 120f, paintCantidadGrande)
            android.util.Log.d("GenerarEnvioTransfiya", "Intentando dibujar decimales: ',00'")
            canvas.drawText(",00", (bitmapMutable.width - 50).toFloat(), 120f, paintDecimales)
            
            // Limpiar recursos
            imagenBase.recycle()
            
            return bitmapMutable
            
        } catch (e: Exception) {
            android.util.Log.e("GenerarEnvioTransfiya", "Error generando imagen: ${e.message}", e)
            // Retornar imagen base en caso de error
            val imagenBase = desencriptarBin("envio2.bin")
            return imagenBase ?: throw Exception("No se pudo cargar la imagen base")
        }
    }
    
    // Función para generar imagen de envío Bancolombia con textos incluidos
    private fun generarImagenEnvioBancolombia(nombreBancolombia: String, cantidad: String): android.graphics.Bitmap {
        try {
            android.util.Log.d("GenerarEnvioBancolombia", "Generando imagen de envío Bancolombia")
            
            // Cargar la imagen base envio3.png
            val imagenBase = desencriptarBin("envio3.bin")
            
            // Verificar que la imagen se cargó correctamente
            if (imagenBase == null) {
                android.util.Log.e("GenerarEnvioBancolombia", "No se pudo cargar la imagen base envio3.bin")
                throw Exception("No se pudo cargar la imagen base")
            }
            
            // Crear un bitmap mutable del mismo tamaño
            val bitmapMutable = imagenBase.copy(android.graphics.Bitmap.Config.ARGB_8888, true)
            val canvas = android.graphics.Canvas(bitmapMutable)
            
            // Configurar paint para el nombre Bancolombia
            val paintNombre = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Configurar paint para el texto "Envío a Bancolombia"
            val paintEnvioBancolombia = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Configurar paint para la cantidad
            val paintCantidad = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 77f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            
            // Formatear cantidad
        val cantidadFormateada = if (cantidad.isNotEmpty()) {
                val cantidadLimpia = cantidad.replace("$", "").replace(" ", "").trim()
                val cantidadSinPuntosMiles = cantidadLimpia.replace(".", "")
                val cantidadParaParsear = cantidadSinPuntosMiles.replace(",", ".")
                val numero = cantidadParaParsear.toDoubleOrNull()?.toLong() ?: 0L
            val formateado = String.format("%,d", numero)
            val numeroFormateado = formateado.replace(",", ".")
            "-$ $numeroFormateado,00"
        } else {
            "-$ 0,00"
        }
        
            // Formatear nombre Bancolombia como "CRI*** ALE*** CAL*** GON***"
            val nombreFormateado = formatearNombreBancolombia(nombreBancolombia)
            
            android.util.Log.d("GenerarEnvioBancolombia", "Dibujando nombre: '$nombreFormateado' en (350, 275)")
            android.util.Log.d("GenerarEnvioBancolombia", "Dibujando cantidad: '$cantidadFormateada' en (${bitmapMutable.width - 220}, 315)")
            
            // Dibujar textos en el canvas
            canvas.drawText(nombreFormateado, 130f, 95f, paintNombre)
            canvas.drawText("Envío a Bancolombia", 130f, 140f, paintEnvioBancolombia)
            
            // Dibujar cantidad con ",00" más pequeño usando moneda_bold
            val cantidadSinDecimales = cantidadFormateada.replace(",00", "")
            val paintCantidadGrande = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 35f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            val paintDecimales = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 25f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            
            canvas.drawText(cantidadSinDecimales, (bitmapMutable.width - 91).toFloat(), 120f, paintCantidadGrande)
            canvas.drawText(",00", (bitmapMutable.width -50).toFloat(), 120f, paintDecimales)
            
            // Limpiar recursos
            imagenBase.recycle()
            
            return bitmapMutable
            
        } catch (e: Exception) {
            android.util.Log.e("GenerarEnvioBancolombia", "Error generando imagen: ${e.message}", e)
            // Retornar imagen base en caso de error
            val imagenBase = desencriptarBin("envio3.bin")
            return imagenBase ?: throw Exception("No se pudo cargar la imagen base")
        }
    }
    
    // Función para generar imagen de envío Envioganancia con textos incluidos
    private fun generarImagenEnvioEnvioganancia(nombreEnvio: String, cantidad: String): android.graphics.Bitmap {
        try {
            android.util.Log.d("GenerarEnvioEnviogan", "Generando imagen de envío Envioganancia")
            
            // Cargar la imagen base envio4.png
            val imagenBase = desencriptarBin("envio4.bin")
            
            // Verificar que la imagen se cargó correctamente
            if (imagenBase == null) {
                android.util.Log.e("GenerarEnvioEnviogan", "No se pudo cargar la imagen base envio4.bin")
                throw Exception("No se pudo cargar la imagen base")
            }
            
            // Crear un bitmap mutable del mismo tamaño
            val bitmapMutable = imagenBase.copy(android.graphics.Bitmap.Config.ARGB_8888, true)
            val canvas = android.graphics.Canvas(bitmapMutable)
            
            // Configurar paint para el nombre del envío
            val paintNombre = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Configurar paint para el texto "De"
            val paintDe = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            
            // Formatear cantidad (sin el "-")
            val cantidadFormateada = if (cantidad.isNotEmpty()) {
                val cantidadLimpia = cantidad.replace("$", "").replace(" ", "").trim()
                val cantidadSinPuntosMiles = cantidadLimpia.replace(".", "")
                val cantidadParaParsear = cantidadSinPuntosMiles.replace(",", ".")
                val numero = cantidadParaParsear.toDoubleOrNull()?.toLong() ?: 0L
                val formateado = String.format("%,d", numero)
                val numeroFormateado = formateado.replace(",", ".")
                "$ $numeroFormateado,00"
            } else {
                "$ 0,00"
            }
            
            // Formatear nombre en mayúsculas normales (sin asteriscos)
            val nombreFormateado = nombreEnvio.uppercase()
            
                            android.util.Log.d("GenerarEnvioEnviogan", "Dibujando nombre: '$nombreFormateado' en (130, 95)")
                android.util.Log.d("GenerarEnvioEnviogan", "Dibujando cantidad: '$cantidadFormateada' en (${bitmapMutable.width - 110}, 120)")
            
            // Dibujar textos en el canvas
            canvas.drawText(nombreFormateado, 130f, 95f, paintNombre)
            canvas.drawText("De", 130f, 140f, paintDe)
            
            // Dibujar cantidad con ",00" más pequeño usando moneda_bold (color verde)
            val cantidadSinDecimales = cantidadFormateada.replace(",00", "")
            val paintCantidadGrande = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#1AAD66")
                textSize = 35f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            val paintDecimales = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#1AAD66")
                textSize = 25f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            
            canvas.drawText(cantidadSinDecimales, (bitmapMutable.width - 110).toFloat(), 120f, paintCantidadGrande)
            canvas.drawText(",00", (bitmapMutable.width - 69).toFloat(), 120f, paintDecimales)
            
            // Limpiar recursos
            imagenBase.recycle()
            
            return bitmapMutable
            
        } catch (e: Exception) {
            android.util.Log.e("GenerarEnvioEnviogan", "Error generando imagen: ${e.message}", e)
            // Retornar imagen base en caso de error
            val imagenBase = desencriptarBin("envio4.bin")
            return imagenBase ?: throw Exception("No se pudo cargar la imagen base")
        }
    }
    
    // Función para crear envío normal (envio.png) - CON IMAGEN GENERADA DINÁMICAMENTE
    private fun crearEnvioNormal(nombreDestinatario: String, cantidad: String, referencia: String = "", fechaComprobante: String = "", numeroNequi: String = "", etiquetaLinea2: String? = null) {
        android.util.Log.d("EnvioNormal", "🔥 === CREANDO ENVÍO NORMAL ===")
        android.util.Log.d("EnvioNormal", "📋 Nombre: '$nombreDestinatario', Cantidad: '$cantidad', Referencia: '$referencia', Fecha: '$fechaComprobante'")
        
        // Generar imagen con textos incluidos
        android.util.Log.d("EnvioNormal", "Llamando a generarImagenEnvioNormal...")
        val imagenGenerada = generarImagenEnvioNormal(nombreDestinatario, cantidad, etiquetaLinea2)
        android.util.Log.d("EnvioNormal", "Imagen generada: ${imagenGenerada.width}x${imagenGenerada.height}")
        
            // Crear un FrameLayout para contener la imagen generada y aislar el área clickeable
            val containerLayout = FrameLayout(this).apply {
                isClickable = false
                isFocusable = false
                // Asegurar que el overlay quede dentro de este contenedor y no sobresalga
                clipToPadding = true
                clipChildren = true
                // Dejar que el posicionamiento lo haga 'cargarEnviosPorFecha' (evitar doble offset)
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
        
        // Crear ImageView con la imagen generada
        val imageView = ImageView(this).apply {
            setImageBitmap(imagenGenerada)
            contentDescription = "Envio Normal"
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            isClickable = false
            isFocusable = false
        }
        
        // Crear área clickeable para envíos normales con comprobante
        android.util.Log.d("EnvioNormal", "🔍 VERIFICANDO datos para área clickeable:")
        android.util.Log.d("EnvioNormal", "   Referencia: '$referencia' (vacia: ${referencia.isEmpty()})")
        android.util.Log.d("EnvioNormal", "   Fecha: '$fechaComprobante' (vacia: ${fechaComprobante.isEmpty()})")
        android.util.Log.d("EnvioNormal", "   Nombre: '$nombreDestinatario'")
        android.util.Log.d("EnvioNormal", "   Cantidad: '$cantidad'")
        android.util.Log.d("EnvioNormal", "   numeroNequi: '$numeroNequi'")
        
            val areaClickeable = View(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    (300 * resources.displayMetrics.density).toInt(),
                    (72 * resources.displayMetrics.density).toInt()
                ).apply {
                    gravity = android.view.Gravity.START or android.view.Gravity.TOP
                    leftMargin = (24 * resources.displayMetrics.density).toInt()
                    topMargin = (8 * resources.displayMetrics.density).toInt()
                }
                alpha = 0f
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                // No usar elevaciones locales altas; el orden lo da el contenedor
                isClickable = true
                isFocusable = true
                translationZ = 0f
                
                android.util.Log.d("EnvioNormal", "✅ CREANDO área clickeable para envío normal")
                android.util.Log.d("EnvioNormal", "Referencia: '$referencia', Fecha: '$fechaComprobante'")
                
            var bloqueadoPorClick = false
                setOnClickListener {
                if (bloqueadoPorClick) return@setOnClickListener
                bloqueadoPorClick = true
                try {
                    android.util.Log.d("EnvioNormalClick", "🔥 CLICK EN ÁREA DE ENVÍO NORMAL 🔥")
                    android.util.Log.d("EnvioNormalClick", "Datos: referencia='$referencia', fecha='$fechaComprobante'")
                    android.util.Log.d("EnvioNormalClick", "Nombre: '$nombreDestinatario', Cantidad: '$cantidad'")
                
                    // Reproducir map.bin (igual coreografía que tras AI) y luego mostrar comprobante base2
                    android.util.Log.d("EnvioNormalClick", "Mostrando intro map.bin antes del comprobante...")
                    
                    // Si no hay datos de comprobante, generar nuevos
                    val referenciaFinal = if (referencia.isNotEmpty()) referencia else {
                        val nuevaReferencia = "M" + (10000000 + kotlin.random.Random.nextInt(90000000)).toString()
                        android.util.Log.d("EnvioNormalClick", "🔄 Generando nueva referencia: '$nuevaReferencia'")
                        nuevaReferencia
                    }
                    
                    val fechaFinal = if (fechaComprobante.isNotEmpty()) fechaComprobante else {
                        val formatoFecha = java.text.SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm a", java.util.Locale("es", "ES"))
                        val fechaRaw = formatoFecha.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")
                        val dia = java.text.SimpleDateFormat("d", java.util.Locale("es", "ES")).format(java.util.Date()).toInt()
                        val fechaFormateada = (if (dia < 10) "0" else "") + fechaRaw
                        android.util.Log.d("EnvioNormalClick", "🔄 Generando nueva fecha: '$fechaFormateada'")
                        fechaFormateada
                    }
                    
                    // Usar el número de Nequi guardado para el comprobante
                    android.util.Log.d("EnvioNormalClick", "🔍 DEBUG - Verificando número:")
                    android.util.Log.d("EnvioNormalClick", "   - numeroNequi: '$numeroNequi'")
                    android.util.Log.d("EnvioNormalClick", "   - ¿Está vacío?: ${numeroNequi.isEmpty()}")
                    android.util.Log.d("EnvioNormalClick", "   - nombreDestinatario: '$nombreDestinatario'")
                    
                    val numeroParaComprobante = if (numeroNequi.isNotEmpty()) {
                        android.util.Log.d("EnvioNormalClick", "✅ Usando numeroNequi guardado: '$numeroNequi'")
                        numeroNequi
                    } else {
                        // Fallback robusto: intentar número del campo, luego número base guardado, luego prefs
                        val etNumero = findViewById<android.widget.EditText?>(R.id.etNumeroContacto)?.text?.toString()?.replace(" ", "") ?: ""
                        val numeroPrefs = try {
                            getSharedPreferences("NequiCoPrefs", MODE_PRIVATE).getString("user_number", "") ?: ""
                        } catch (_: Exception) { "" }
                        val candidato = when {
                            etNumero.any { it.isDigit() } -> etNumero
                            datosNumeroCelularBase.isNotEmpty() -> datosNumeroCelularBase
                            numeroPrefs.any { it.isDigit() } -> numeroPrefs
                            else -> ""
                        }
                        android.util.Log.d("EnvioNormalClick", "⚠️ numeroNequi vacío. Fallback candidato: '$candidato'")
                        candidato
                    }
                    
                    android.util.Log.d("EnvioNormalClick", "🔢 Número final para comprobante: '$numeroParaComprobante'")
                    android.util.Log.d("EnvioNormalClick", "📋 Datos finales: referencia='$referenciaFinal', fecha='$fechaFinal'")
                    android.util.Log.d("EnvioNormalClick", "📤 Llamando a mostrarComprobanteBase2ConDatos con:")
                    android.util.Log.d("EnvioNormalClick", "   - nombreDestinatario: '$nombreDestinatario'")
                    android.util.Log.d("EnvioNormalClick", "   - numeroParaComprobante: '$numeroParaComprobante'")
                    android.util.Log.d("EnvioNormalClick", "   - cantidad: '$cantidad'")
                    android.util.Log.d("EnvioNormalClick", "   - referenciaFinal: '$referenciaFinal'")
                    android.util.Log.d("EnvioNormalClick", "   - fechaFinal: '$fechaFinal'")
                    // Overlay temporal para map
                    val rootView = window.decorView.findViewById<android.view.ViewGroup>(android.R.id.content)
                    val mapOverlay = android.widget.FrameLayout(this@InicioActivity).apply {
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        alpha = 0f
                    }
                    val videoMap = android.widget.VideoView(this@InicioActivity).apply {
                        layoutParams = android.widget.FrameLayout.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT
                        ).apply { gravity = android.view.Gravity.CENTER }
                        setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        setZOrderOnTop(true)
                    }
                    mapOverlay.addView(videoMap)
                    rootView.addView(mapOverlay)
                    val screenWidth = resources.displayMetrics.widthPixels.toFloat()
                    mapOverlay.translationX = screenWidth
                    try {
                        VideoLoader.cargarVideoDesdeAssets(this@InicioActivity, videoMap, "map")
                        videoMap.setOnPreparedListener { mp ->
                            mp.isLooping = false
                            mp.setVolume(0f, 0f)
                            videoMap.start()
                        }
                    } catch (_: Exception) {}
                    // Entrada lenta derecha->izquierda y a los 1s mostramos comprobante debajo
                    mapOverlay.animate().alpha(1f).translationX(0f).setDuration(1600)
                        .setInterpolator(android.view.animation.DecelerateInterpolator())
                        .withEndAction {
                            // No hacemos nada al fin de la entrada
                        }.start()
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        mostrarComprobanteBase2ConDatos(nombreDestinatario, numeroParaComprobante, cantidad, referenciaFinal, fechaFinal)
                        rootView.bringChildToFront(mapOverlay)
                    }, 1000)
                    // A los 2s quitamos el video overlay sin salida
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        try { videoMap.stopPlayback() } catch (_: Exception) {}
                        rootView.removeView(mapOverlay)
                    }, 2000)
                    android.util.Log.d("EnvioNormalClick", "Intro map.bin completada, comprobante mostrado")
                
                } catch (e: Exception) {
                    android.util.Log.e("EnvioNormalClick", "Error mostrando comprobante: ${e.message}", e)
                    Toast.makeText(this@InicioActivity, "Error al mostrar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
                } finally {
                    // Rehabilitar el click después de un breve tiempo para evitar multi-aperturas
                    postDelayed({ bloqueadoPorClick = false }, 1500)
                }
            }
        }
        
        // Agregar imagen al contenedor
        containerLayout.addView(imageView)
        
        // Agregar área clickeable si existe
        areaClickeable?.let { 
            containerLayout.addView(it)
            android.util.Log.d("EnvioNormal", "🔲 Área clickeable agregada al contenedor")
        }
        
        // ASEGURAR que el container NO sea clickeable
        containerLayout.setOnClickListener(null)
        containerLayout.isClickable = false
        containerLayout.isFocusable = false
        
        android.util.Log.d("EnvioNormal", "Envío normal creado ${if (areaClickeable != null) "CON" else "SIN"} click listeners")
        
        // Agregar al layout de envíos
        llEnviosMovimientos.addView(containerLayout)
    }

    // Agrega una tarjeta basada en envio5.bin al layout de movimientos
    private fun agregarEnvio5AMovimientos(nombre: String, cantidad: String) {
        try {
            val base = desencriptarBin("envio5.bin") ?: return
            val bitmap = base.copy(android.graphics.Bitmap.Config.ARGB_8888, true)
            val canvas = android.graphics.Canvas(bitmap)

            // Pinturas similares a envio normal
            val paintNombre = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }
            val paintEtiqueta = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#380D3D")
                textSize = 30f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
            }

            val nombreCapitalizado = nombre.uppercase(java.util.Locale("es", "ES"))
            // Posiciones como envío normal
            canvas.drawText(nombreCapitalizado.ifBlank { "USUARIO" }, 130f, 95f, paintNombre)
            canvas.drawText("Pago con QR", 130f, 140f, paintEtiqueta)

            // Dibujar cantidad restada como en envío normal (rojo, con decimales pequeños)
            val cantidadFormateada = try {
                // Siempre usar la cantidad propia del envío guardado
                val numero = parsePesos(cantidad)
                val formateado = String.format("%,d", numero).replace(",", ".")
                "-$ $formateado,00"
            } catch (_: Exception) { "-$ 0,00" }
            val cantidadSinDecimales = cantidadFormateada.replace(",00", "")
            val paintCantidadGrande = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 35f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            val paintDecimales = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#D23854")
                textSize = 25f
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
                typeface = ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_bold)
            }
            canvas.drawText(cantidadSinDecimales, (bitmap.width - 91).toFloat(), 120f, paintCantidadGrande)
            canvas.drawText(",00", (bitmap.width - 50).toFloat(), 120f, paintDecimales)

            // Contenedor alineado y con área clickeable como los otros envíos
            val container = FrameLayout(this)
            val iv = ImageView(this).apply {
                setImageBitmap(bitmap)
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
            container.addView(iv)
            // Área clickeable similar a envío normal
            val areaClickeable = View(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    (300 * resources.displayMetrics.density).toInt(),
                    (72 * resources.displayMetrics.density).toInt()
                ).apply {
                    gravity = android.view.Gravity.START or android.view.Gravity.TOP
                    leftMargin = (24 * resources.displayMetrics.density).toInt()
                    topMargin = (8 * resources.displayMetrics.density).toInt()
                }
                alpha = 0f
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                isClickable = true
                isFocusable = true
                setOnClickListener {
                    // Reproducir intro map.bin y luego mostrar detalle QR
                    try {
                        val rootView = window.decorView.findViewById<android.view.ViewGroup>(android.R.id.content)
                        val mapOverlay = android.widget.FrameLayout(this@InicioActivity).apply {
                            layoutParams = android.view.ViewGroup.LayoutParams(
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            setBackgroundColor(android.graphics.Color.TRANSPARENT)
                            alpha = 0f
                        }
                        val videoMap = android.widget.VideoView(this@InicioActivity).apply {
                            layoutParams = android.widget.FrameLayout.LayoutParams(
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT
                            ).apply { gravity = android.view.Gravity.CENTER }
                            setBackgroundColor(android.graphics.Color.TRANSPARENT)
                            setZOrderOnTop(true)
                        }
                        mapOverlay.addView(videoMap)
                        rootView.addView(mapOverlay)
                        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
                        mapOverlay.translationX = screenWidth
                        try {
                            VideoLoader.cargarVideoDesdeAssets(this@InicioActivity, videoMap, "map")
                            videoMap.setOnPreparedListener { mp ->
                                mp.isLooping = false
                                mp.setVolume(0f, 0f)
                                videoMap.start()
                            }
                        } catch (_: Exception) {}
                        mapOverlay.animate().alpha(1f).translationX(0f).setDuration(1600)
                            .setInterpolator(android.view.animation.DecelerateInterpolator())
                            .start()
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            mostrarDetalleQrOverlay(nombre, cantidad)
                            rootView.bringChildToFront(mapOverlay)
                        }, 1000)
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            try { videoMap.stopPlayback() } catch (_: Exception) {}
                            rootView.removeView(mapOverlay)
                        }, 2000)
                    } catch (_: Exception) {}
                }
            }
            container.addView(areaClickeable)

            // Dejar que el cargador de envíos posicione; agregar al final
            llEnviosMovimientos.addView(container)
        } catch (_: Exception) { }
    }

    private fun mostrarDetalleQrOverlay(nombreParam: String? = null, cantidadParam: String? = null) {
        try {
            val rootLayout = findViewById<android.view.ViewGroup>(android.R.id.content)
            val overlay = android.widget.FrameLayout(this).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.parseColor("#B3000000"))
                isClickable = true
                isFocusable = true
                alpha = 0f
            }
            val iv = android.widget.ImageView(this).apply {
                layoutParams = android.widget.FrameLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { gravity = android.view.Gravity.CENTER }
                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            }
            val bmpBase = desencriptarBin("detalleqr.bin")
            var bmp = bmpBase
            // Dibujar los mismos datos del comprobante QR encima de detalleqr
            try {
                if (bmpBase != null) {
                    val width = bmpBase.width
                    val height = bmpBase.height
                    val composed = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
                    val c = android.graphics.Canvas(composed)
                    c.drawBitmap(bmpBase, 0f, 0f, null)
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#200020")
                        textSize = 70f
                        isAntiAlias = true
                        style = android.graphics.Paint.Style.FILL
                        try { typeface = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium) } catch (_: Exception) {}
                    }
                    val nombre = when {
                        !nombreParam.isNullOrBlank() -> nombreParam
                        lastQrNombre.isNotBlank() -> lastQrNombre
                        else -> try { findViewById<android.widget.EditText>(R.id.etCelPago)?.text?.toString() } catch (_: Exception) { null } ?: "Usuario"
                    }
                    val nombreCapitalizado = toTitleCaseEs(nombre)
                    val cantidadInput = when {
                        !cantidadParam.isNullOrBlank() -> cantidadParam
                        lastQrCantidad.isNotBlank() -> lastQrCantidad
                        else -> try { findViewById<android.widget.EditText>(R.id.etCuantoPago)?.text?.toString() } catch (_: Exception) { null } ?: ""
                    }
                    val referencia = if (lastQrReferencia.isNotBlank()) lastQrReferencia else "M" + (10000000 + kotlin.random.Random.nextInt(90000000)).toString()
                    val formatoFecha = java.text.SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm a", java.util.Locale("es", "ES"))
                    val fechaFormateada = if (lastQrFecha.isNotBlank()) lastQrFecha else run {
                        val fechaRaw = formatoFecha.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")
                        val dia = java.text.SimpleDateFormat("d", java.util.Locale("es", "ES")).format(java.util.Date()).toInt()
                        (if (dia < 10) "0" else "") + fechaRaw
                    }
                    val cantidadFormateada = try {
                        val valor = if (lastQrValor > 0L) lastQrValor else parsePesos(cantidadInput)
                        val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO")).apply {
                            maximumFractionDigits = 2; minimumFractionDigits = 2; currency = java.util.Currency.getInstance("COP")
                        }
                        formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
                    } catch (_: Exception) { "$ 0,00" }
                    // Posiciones aproximadas centradas similares al comprobante
                    c.drawText(nombreCapitalizado, width * 0.077f, height * 0.444f, paint)
                    c.drawText(cantidadFormateada, width * 0.077f, height * 0.505f, paint)
                    c.drawText(referencia, width * 0.077f, height * 0.625f, paint)
                    c.drawText("Disponible", width * 0.077f, height * 0.69f, paint)
                    c.drawText(fechaFormateada, width * 0.077f, height * 0.565f, paint)
                    bmp = composed
                }
            } catch (_: Exception) {}
            
            if (bmp != null) iv.setImageBitmap(bmp) else return
            overlay.addView(iv)
            overlay.setOnClickListener {
                overlay.animate().alpha(0f).setDuration(200).withEndAction {
                    rootLayout.removeView(overlay)
                }.start()
            }
            rootLayout.addView(overlay)
            overlay.animate().alpha(1f).setDuration(200).start()
        } catch (_: Exception) { }
    }
    
    // Función para crear envío de Transfiya (envio2.png) - CON IMAGEN GENERADA DINÁMICAMENTE
    private fun crearEnvioTransfiya(numeroCelular: String, cantidad: String, referencia: String = "", fechaComprobante: String = "") {
        android.util.Log.d("CrearEnvioTransfiya", "=== CREANDO ENVÍO TRANSFIYA ===")
        android.util.Log.d("CrearEnvioTransfiya", "Datos: numeroCelular='$numeroCelular', cantidad='$cantidad', referencia='$referencia', fechaComprobante='$fechaComprobante'")
        
        // Generar imagen con textos incluidos
        android.util.Log.d("CrearEnvioTransfiya", "Llamando a generarImagenEnvioTransfiya...")
        val imagenGenerada = generarImagenEnvioTransfiya(numeroCelular, cantidad, referencia, fechaComprobante)
        android.util.Log.d("CrearEnvioTransfiya", "Imagen generada: ${imagenGenerada.width}x${imagenGenerada.height}")
        
        // Crear un FrameLayout para contener la imagen generada
        val containerLayout = FrameLayout(this)
        
        // Crear ImageView con la imagen generada
        val imageView = ImageView(this).apply {
            setImageBitmap(imagenGenerada)
            contentDescription = "Envio Transfiya"
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        
        // Crear área clickeable más pequeña pero funcional (solo para Transfiya con comprobante)
        val areaClickeable = if (referencia.isNotEmpty() && fechaComprobante.isNotEmpty()) {
            View(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    (325 * resources.displayMetrics.density).toInt(), // Ancho igual al otro
                    (70 * resources.displayMetrics.density).toInt()   // Alto igual al otro (actualizado por el usuario)
                ).apply {
                    // Posicionar RELATIVO al contenedor padre para evitar superposición
                    gravity = android.view.Gravity.START or android.view.Gravity.TOP
                    leftMargin = (30 * resources.displayMetrics.density).toInt()
                    topMargin = (2 * resources.displayMetrics.density).toInt() // Subido más arriba
                }
                // Área invisible para el usuario
                alpha = 0f // Completamente transparente
                setBackgroundColor(android.graphics.Color.TRANSPARENT) // Transparente
                isClickable = true
                isFocusable = true
                
                android.util.Log.d("CrearEnvioTransfiya", "✅ CREANDO área clickeable PEQUEÑA - tiene datos de comprobante")
                android.util.Log.d("CrearEnvioTransfiya", "Referencia: '$referencia', Fecha: '$fechaComprobante'")
                android.util.Log.d("CrearEnvioTransfiya", "Área posicionada RELATIVAMENTE al contenedor para evitar superposición")
                
                var bloqueadoPorClick = false
                setOnClickListener {
                    if (bloqueadoPorClick) return@setOnClickListener
                    bloqueadoPorClick = true
                    try {
                        android.util.Log.d("TransfiyaClick", "🔥 CLICK EN ÁREA PEQUEÑA DE TRANSFIYA 🔥")
                        android.util.Log.d("TransfiyaClick", "Datos: referencia='$referencia', fecha='$fechaComprobante'")

                        // Reproducir intro map.bin como en envío normal, luego mostrar comprobante de Transfiya
                        val rootView = window.decorView.findViewById<android.view.ViewGroup>(android.R.id.content)
                        val mapOverlay = android.widget.FrameLayout(this@InicioActivity).apply {
                            layoutParams = android.view.ViewGroup.LayoutParams(
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            setBackgroundColor(android.graphics.Color.TRANSPARENT)
                            alpha = 0f
                        }
                        val videoMap = android.widget.VideoView(this@InicioActivity).apply {
                            layoutParams = android.widget.FrameLayout.LayoutParams(
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT
                            ).apply { gravity = android.view.Gravity.CENTER }
                            setBackgroundColor(android.graphics.Color.TRANSPARENT)
                            setZOrderOnTop(true)
                        }
                        mapOverlay.addView(videoMap)
                        rootView.addView(mapOverlay)
                        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
                        mapOverlay.translationX = screenWidth
                        try {
                            VideoLoader.cargarVideoDesdeAssets(this@InicioActivity, videoMap, "map")
                            videoMap.setOnPreparedListener { mp ->
                                mp.isLooping = false
                                mp.setVolume(0f, 0f)
                                videoMap.start()
                            }
                        } catch (_: Exception) {}
                        // Entrada lenta y mostrar comprobante al 1s
                        mapOverlay.animate().alpha(1f).translationX(0f).setDuration(1600)
                            .setInterpolator(android.view.animation.DecelerateInterpolator())
                            .start()
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            mostrarComprobanteTransfiyaConDatos(numeroCelular, cantidad, referencia, fechaComprobante)
                            rootView.bringChildToFront(mapOverlay)
                        }, 1000)
                        // Quitar overlay a los 2s
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            try { videoMap.stopPlayback() } catch (_: Exception) {}
                            rootView.removeView(mapOverlay)
                        }, 2000)
                    } catch (e: Exception) {
                        android.util.Log.e("TransfiyaClick", "Error mostrando comprobante: ${e.message}", e)
                        Toast.makeText(this@InicioActivity, "Error al mostrar comprobante", Toast.LENGTH_SHORT).show()
                    } finally {
                        postDelayed({ bloqueadoPorClick = false }, 1500)
                    }
                }
            }
        } else {
            android.util.Log.d("CrearEnvioTransfiya", "❌ NO creando área clickeable - faltan datos de comprobante")
            android.util.Log.d("CrearEnvioTransfiya", "Referencia vacía: ${referencia.isEmpty()}, Fecha vacía: ${fechaComprobante.isEmpty()}")
            null
        }
        
        // Agregar imagen al contenedor
        containerLayout.addView(imageView)
        
        // Agregar área clickeable pequeña si existe
        areaClickeable?.let { 
            containerLayout.addView(it)
            android.util.Log.d("CrearEnvioTransfiya", "🔲 Área clickeable agregada al contenedor #${llEnviosMovimientos.childCount}")
            android.util.Log.d("CrearEnvioTransfiya", "Posición relativa: leftMargin=30dp, topMargin=120dp")
        }
        
        // ASEGURAR que el container NO sea clickeable
        containerLayout.setOnClickListener(null)
        containerLayout.isClickable = false
        containerLayout.isFocusable = false
        
        // Agregar al layout de envíos
        llEnviosMovimientos.addView(containerLayout)
    }
    
    // Funciones del comprobante
    private fun mostrarVideoAIYComprobante() {
        try {
            android.util.Log.d("VideoAI", "Iniciando reproducción de video AI")
            
            // Ocultar modal de confirmación
            ocultarModalConfirmacion()
            
            // Crear layout para el video
            val videoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
            
            // Crear VideoView con las mismas propiedades que io.mp4
            val videoAI = VideoView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true) // Necesario para que el video se muestre correctamente
            }
            
            // Centrar el video
            val videoContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            
            // Agregar el VideoView al contenedor con layout params centrados
            val videoParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            videoContainer.addView(videoAI, videoParams)
            
            videoLayout.addView(videoContainer)
            
            // Agregar el layout al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(videoLayout)
            
            // Configurar el video
            VideoLoader.cargarVideoDesdeAssets(this, videoAI, "ai")
            
            
            // Configurar propiedades scaleX y scaleY como io.mp4
            videoLayout.alpha = 0f
            videoLayout.scaleX = 0f
            videoLayout.scaleY = 0f
            
            // Agregar listener de error
            videoAI.setOnErrorListener { mp, what, extra ->
                android.util.Log.e("VideoAI", "Error reproduciendo video: what=$what, extra=$extra")
                // Continuar sin video si hay error
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        Handler(Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(450)
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    // Mostrar comprobante después del video
                                    mostrarComprobanteConTransicion()
                                }.start()
                        }, 200) // Esperar 0.2 segundos
                    }.start()
                true // Manejar el error
            }
            
            videoAI.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false // No loop para AI
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                mediaPlayer.setVolume(0f, 0f) // Sin audio
                
                // Iniciar el video
                videoAI.start()
                
                // Mostrar el layout con animación
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
            // Al finalizar AI, reproducir map.bin 2s y luego mostrar el comprobante
            Handler(Looper.getMainLooper()).postDelayed({
                // Agregar overlay temporal para map
                val mapOverlay = android.widget.FrameLayout(this@InicioActivity).apply {
                    layoutParams = android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    alpha = 0f
                }
                val videoMap = android.widget.VideoView(this@InicioActivity).apply {
                    layoutParams = android.widget.FrameLayout.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT
                    ).apply { gravity = android.view.Gravity.CENTER }
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    setZOrderOnTop(true)
                }
                mapOverlay.addView(videoMap)
                rootView.addView(mapOverlay)

                // Preparar posiciones para transición derecha -> izquierda
                val screenWidth = resources.displayMetrics.widthPixels.toFloat()
                mapOverlay.translationX = screenWidth

                // Fade out AI
                videoLayout.animate()
                    .alpha(0f)
                    .scaleX(0f)
                    .scaleY(0f)
                    .setDuration(300)
                    .withEndAction {
                        rootView.removeView(videoLayout)
                        // Slide-in + fade-in MAP y reproducir 2s
                        try {
                            VideoLoader.cargarVideoDesdeAssets(this@InicioActivity, videoMap, "map")
                            videoMap.setOnPreparedListener { mp ->
                                mp.isLooping = false
                                mp.setVolume(0f, 0f)
                                videoMap.start()
                            }
                        } catch (_: Exception) {}
                        mapOverlay.animate()
                            .alpha(1f)
                            .translationX(0f)
                            .setDuration(1600)
                            .setInterpolator(android.view.animation.DecelerateInterpolator())
                            .withEndAction {
                                // A los 1s mostrar el comprobante debajo y mantener MAP arriba
                                Handler(Looper.getMainLooper()).postDelayed({
                                    try {
                                        mostrarComprobanteConTransicion()
                                        // Asegurar que el MAP siga encima
                                        rootView.bringChildToFront(mapOverlay)
                                    } catch (_: Exception) {}
                                }, 1000)

                                // A los 2s ocultar el MAP sin transición de salida (quitar overlay)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    try { videoMap.stopPlayback() } catch (_: Exception) {}
                                    rootView.removeView(mapOverlay)
                                }, 2000)
                            }
                            .start()
                    }
                    .start()
            }, 2000) // Duración de AI
                    }.start()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("VideoAI", "Error al mostrar video AI", e)
            // Si hay error, mostrar comprobante directamente
            mostrarComprobanteConTransicion()
        }
    }
    
    private fun mostrarComprobanteConTransicion() {
        try {
            android.util.Log.d("Comprobante", "Iniciando generación de comprobante con transición")
            
            // Obtener datos del formulario
            val etCel = findViewById<EditText>(R.id.etCel)
            val etCuanto = findViewById<EditText>(R.id.etCuanto)
            
            android.util.Log.d("Comprobante", "Datos obtenidos: Cel=${etCel.text}, Cuanto=${etCuanto.text}")
            
            // Buscar nombre del contacto
            val numeroCel = etCel.text.toString().replace(" ", "")
            
            // Validar que se haya ingresado un número
            if (numeroCel.isEmpty()) {
                Toast.makeText(this, "Debes ingresar un número de celular", Toast.LENGTH_LONG).show()
                return
            }
            
            val nombreContacto = buscarContactoPorNumero(numeroCel)
            
            android.util.Log.d("Comprobante", "Nombre contacto: $nombreContacto")
            
            // Validar que exista un nombre de contacto
            if (nombreContacto.isEmpty()) {
                Toast.makeText(this, "El número no tiene un nombre guardado", Toast.LENGTH_LONG).show()
                return
            }
            
            // Validar límite de monto máximo
            if (excedeMontoMaximo(etCuanto.text.toString())) {
                mostrarLayoutCancelado()
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this@InicioActivity, "CANSON DE MIERDA, DEJA DE MARIQUEAR", Toast.LENGTH_LONG).show()
                }, 800)
                return
            }

            // Validar si hay saldo suficiente
            CoroutineScope(Dispatchers.Main).launch {
                val saldoSuficiente = withContext(Dispatchers.IO) {
                    validarSaldoSuficienteAsync(etCuanto.text.toString())
                }
                
                if (!saldoSuficiente) {
                    // Mostrar layout de cancelado en lugar del Toast
                    mostrarLayoutCancelado()
                    return@launch
                }
                
                // Continuar con el proceso si hay saldo suficiente
                try {
                    // Generar imagen del comprobante
                    android.util.Log.d("Comprobante", "Generando imagen...")
                    val comprobanteData = generarImagenComprobante(nombreContacto, etCel.text.toString(), etCuanto.text.toString())
                    
                    android.util.Log.d("Comprobante", "Imagen generada, tamaño: ${comprobanteData.bitmap.width}x${comprobanteData.bitmap.height}")
                    android.util.Log.d("Comprobante", "Datos del comprobante: referencia='${comprobanteData.referencia}', fecha='${comprobanteData.fechaFormateada}'")
                    
                    // Guardar los datos del comprobante para uso posterior
                    datosComprobanteBase = comprobanteData
                    
                    // Guardar también el número de celular para uso posterior
                    val numeroCelularBase = etCel.text.toString().replace(" ", "")
                    datosNumeroCelularBase = numeroCelularBase
                    android.util.Log.d("Comprobante", "🔢 Número de celular guardado para uso posterior: '$numeroCelularBase'")
                    android.util.Log.d("Comprobante", "🔢 datosNumeroCelularBase después de guardar: '$datosNumeroCelularBase'")
                    
                    // Formatear cantidad para mostrar
                    val cantidadFormateada = etCuanto.text.toString().replace("$", "").replace(".", "").replace(" ", "")
                    
                    // Mostrar imagen en un ImageView con transición de derecha a izquierda
                    mostrarComprobanteImagenConTransicion(comprobanteData.bitmap, nombreContacto, cantidadFormateada)
                    
                    // Restar la cantidad del saldo
                    restarDelSaldo(etCuanto.text.toString())
                    
                    android.util.Log.d("Comprobante", "Comprobante mostrado exitosamente")
                } catch (e: Exception) {
                    android.util.Log.e("Comprobante", "Error al generar comprobante", e)
                    Toast.makeText(this@InicioActivity, "Error al generar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            
            return
            
        } catch (e: Exception) {
            android.util.Log.e("Comprobante", "Error al generar comprobante", e)
            Toast.makeText(this, "Error al generar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun mostrarComprobante() {
        try {
            android.util.Log.d("Comprobante", "Iniciando generación de comprobante")
            
            // Ocultar modal de confirmación
            ocultarModalConfirmacion()
            
            // Obtener datos del formulario
            val etCel = findViewById<EditText>(R.id.etCel)
            val etCuanto = findViewById<EditText>(R.id.etCuanto)
            
            android.util.Log.d("Comprobante", "Datos obtenidos: Cel=${etCel.text}, Cuanto=${etCuanto.text}")
            
            // Buscar nombre del contacto
            val numeroCel = etCel.text.toString().replace(" ", "")
            
            // Validar que se haya ingresado un número
            if (numeroCel.isEmpty()) {
                Toast.makeText(this, "Debes ingresar un número de celular", Toast.LENGTH_LONG).show()
                return
            }
            
            val nombreContacto = buscarContactoPorNumero(numeroCel)
            
            android.util.Log.d("Comprobante", "Nombre contacto: $nombreContacto")
            
            // Validar que exista un nombre de contacto
            if (nombreContacto.isEmpty()) {
                Toast.makeText(this, "El número no tiene un nombre guardado", Toast.LENGTH_LONG).show()
                return
            }
            
            // Validar límite de monto máximo
            if (excedeMontoMaximo(etCuanto.text.toString())) {
                mostrarLayoutCancelado()
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this@InicioActivity, "CANSON DE MIERDA, DEJA DE MARIQUEAR", Toast.LENGTH_LONG).show()
                }, 800)
                return
            }

            // Validar si hay saldo suficiente
            CoroutineScope(Dispatchers.Main).launch {
                val saldoSuficiente = withContext(Dispatchers.IO) {
                    validarSaldoSuficienteAsync(etCuanto.text.toString())
                }
                
                if (!saldoSuficiente) {
                    // Mostrar layout de cancelado en lugar del Toast
                    mostrarLayoutCancelado()
                    return@launch
                }
                
                // Continuar con el proceso si hay saldo suficiente
                try {
            // Generar imagen del comprobante
            android.util.Log.d("Comprobante", "Generando imagen...")
            val comprobanteData = generarImagenComprobante(nombreContacto, etCel.text.toString(), etCuanto.text.toString())
            
            android.util.Log.d("Comprobante", "Imagen generada, tamaño: ${comprobanteData.bitmap.width}x${comprobanteData.bitmap.height}")
            android.util.Log.d("Comprobante", "Datos del comprobante: referencia='${comprobanteData.referencia}', fecha='${comprobanteData.fechaFormateada}'")
            
            // Guardar los datos del comprobante para uso posterior
            datosComprobanteBase = comprobanteData
            
            // Guardar también el número de celular para uso posterior
            val numeroCelularBase = etCel.text.toString().replace(" ", "")
            datosNumeroCelularBase = numeroCelularBase
            android.util.Log.d("Comprobante", "🔢 Número de celular guardado para uso posterior: '$numeroCelularBase'")
            android.util.Log.d("Comprobante", "🔢 datosNumeroCelularBase después de guardar: '$datosNumeroCelularBase'")
            
            // Formatear cantidad para mostrar
            val cantidadFormateada = etCuanto.text.toString().replace("$", "").replace(".", "").replace(" ", "")
            
            // Mostrar imagen en un ImageView
            mostrarComprobanteImagen(comprobanteData.bitmap, nombreContacto, cantidadFormateada)
            
            // Restar la cantidad del saldo
            restarDelSaldo(etCuanto.text.toString())
            
            android.util.Log.d("Comprobante", "Comprobante mostrado exitosamente")
                } catch (e: Exception) {
                    android.util.Log.e("Comprobante", "Error al generar comprobante", e)
                    Toast.makeText(this@InicioActivity, "Error al generar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            
            return
            
        } catch (e: Exception) {
            android.util.Log.e("Comprobante", "Error al generar comprobante", e)
            Toast.makeText(this, "Error al generar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun generarImagenComprobante(nombreContacto: String, numeroCelular: String, cantidad: String): ComprobanteTransfiyaData {
        android.util.Log.d("Comprobante", "Iniciando generación de imagen")
        
        try {
            // Usar imagen base si existe, sino crear bitmap simple
            val originalBitmap = try {
                desencriptarBin("base.bin") ?: android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            } catch (e: Exception) {
                android.util.Log.w("Comprobante", "No se pudo cargar base.bin, usando bitmap simple")
                android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            }
            
            // Redimensionar la imagen a dimensiones muy altas para máxima calidad en capturas
            val finalBaseBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, 2400, 5120, true)
            
            // Crear un bitmap de muy alta calidad HD
            val hdBitmap = android.graphics.Bitmap.createBitmap(2400, 5120, android.graphics.Bitmap.Config.ARGB_8888)
            val hdCanvas = android.graphics.Canvas(hdBitmap)
            val hdPaint = android.graphics.Paint()
            hdPaint.isFilterBitmap = true
            hdPaint.isAntiAlias = true
            hdPaint.isDither = true
            hdPaint.isSubpixelText = true // Mejorar calidad de texto
            hdPaint.isLinearText = true // Mejorar renderizado de texto
            hdPaint.strokeWidth = 0f // Sin bordes para mejor calidad
            
            // Dibujar la imagen escalada con configuración HD
            hdCanvas.drawBitmap(finalBaseBitmap, 0f, 0f, hdPaint)
            
            // Usar el bitmap HD
            val finalBaseBitmapHD = hdBitmap
            
            android.util.Log.d("Comprobante", "Imagen original: ${originalBitmap.width}x${originalBitmap.height}")
            android.util.Log.d("Comprobante", "Imagen optimizada: ${finalBaseBitmapHD.width}x${finalBaseBitmapHD.height}")
            
            android.util.Log.d("Comprobante", "Imagen base cargada: ${finalBaseBitmapHD.width}x${finalBaseBitmapHD.height}")
            
            val width = finalBaseBitmapHD.width
            val height = finalBaseBitmapHD.height
            android.util.Log.d("Comprobante", "Base bitmap size: ${width}x${height}")
            
            val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            
            // Dibujar imagen base como fondo con alta calidad HD
            canvas.drawBitmap(finalBaseBitmapHD, 0f, 0f, null)
            android.util.Log.d("Comprobante", "Base bitmap dibujado en canvas")

            val paint = android.graphics.Paint()
            // Color para la captura
            val colorCaptura = android.graphics.Color.parseColor("#2C002C")
            paint.textSize = 90f // Texto mucho más grande para alta calidad en capturas
            paint.isAntiAlias = true
            paint.isSubpixelText = true // Mejorar calidad de texto HD
            paint.isLinearText = true // Mejorar renderizado de texto
            paint.isFilterBitmap = true // Mejorar calidad de bitmap
            paint.isDither = true // Mejorar calidad de color
            paint.strokeWidth = 0f // Sin bordes para mejor calidad
            paint.style = android.graphics.Paint.Style.FILL // Solo relleno para mejor calidad
            
            // Usar la fuente moneda_medium si está disponible
            try {
                val typeface: android.graphics.Typeface? = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.moneda_medium)
                if (typeface != null) {
                    paint.typeface = typeface
                    android.util.Log.d("Comprobante", "Fuente moneda_medium cargada exitosamente")
                }
            } catch (e: Exception) {
                android.util.Log.w("Comprobante", "No se pudo cargar la fuente personalizada, usando DEFAULT")
                paint.typeface = android.graphics.Typeface.DEFAULT
            }

            // Obtener primer y tercer nombre para el comprobante
            val nombreParaComprobante = obtenerPrimerYTercerNombre(nombreContacto)
            val nombreCapitalizado = nombreParaComprobante.split(" ").joinToString(" ") { 
                it.replaceFirstChar { c -> 
                    if (c.isLowerCase()) c.titlecase(java.util.Locale("es", "ES")) else c.toString() 
                } 
            }
            
            // Formatear el número con espacios: 300 000 0000, con fallbacks si viene vacío
            val numeroSoloDigitos = numeroCelular.replace("[^0-9]".toRegex(), "")
            var numeroFormateado = when (numeroSoloDigitos.length) {
                0 -> ""
                in 1..3 -> numeroSoloDigitos
                in 4..6 -> numeroSoloDigitos.substring(0,3) + " " + numeroSoloDigitos.substring(3)
                in 7..10 -> numeroSoloDigitos.substring(0,3) + " " + numeroSoloDigitos.substring(3,6) + " " + numeroSoloDigitos.substring(6)
                else -> numeroSoloDigitos
            }
            if (numeroFormateado.isEmpty()) {
                // Intentar recuperar número del usuario como último recurso
                try {
                    val prefsNumero = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE).getString("user_number", "") ?: ""
                    val digits = prefsNumero.replace("[^0-9]".toRegex(), "")
                    numeroFormateado = when (digits.length) {
                        in 1..3 -> digits
                        in 4..6 -> digits.substring(0,3) + " " + digits.substring(3)
                        in 7..10 -> digits.substring(0,3) + " " + digits.substring(3,6) + " " + digits.substring(6)
                        else -> ""
                    }
                } catch (_: Exception) { /* ignore */ }
            }
            
            // Generar referencia aleatoria tipo M12345678
            val referencia = "M" + (10000000 + kotlin.random.Random.nextInt(90000000)).toString()
            
            // Fecha con día siempre en 2 dígitos
            val formatoFecha = java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' hh:mm a", java.util.Locale("es", "ES"))
            val fechaFormateada = formatoFecha.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")

            // Formatear la cantidad como moneda: $ 50.000,00
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }

            // Dibujar los textos en posiciones específicas (posiciones originales de base.png)
            paint.color = android.graphics.Color.parseColor("#200020")
            canvas.drawText(nombreCapitalizado, width * 0.08f, height * 0.475f, paint)
            canvas.drawText(numeroFormateado, width * 0.077f, height * 0.605f, paint)
            canvas.drawText(cantidadFormateada, width * 0.08f, height * 0.543f, paint)
            canvas.drawText(referencia, width * 0.08f, height * 0.735f, paint)
            canvas.drawText("Disponible", width * 0.08f, height * 0.800f, paint)
            canvas.drawText(fechaFormateada, width * 0.08f, height * 0.670f, paint)

            android.util.Log.d("Comprobante", "Imagen generada exitosamente")
            android.util.Log.d("Comprobante", "Datos generados: referencia='$referencia', fecha='$fechaFormateada'")
            return ComprobanteTransfiyaData(bitmap, referencia, fechaFormateada)
            
        } catch (e: Exception) {
            android.util.Log.e("Comprobante", "Error generando imagen: ${e.message}", e)
            
            // Fallback: crear bitmap simple
            val fallbackBitmap = android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888)
            val fallbackCanvas = android.graphics.Canvas(fallbackBitmap)
            val fallbackPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#2C002C")
                textSize = 60f
                isAntiAlias = true
            }
            
            fallbackCanvas.drawText("Comprobante generado", 400f, 300f, fallbackPaint)
            return ComprobanteTransfiyaData(fallbackBitmap, "M00000000", "Fecha no disponible")
        }
    }
    
    private fun generarImagenComprobanteTransfiya(nombreContacto: String, numeroCelular: String, cantidad: String): ComprobanteTransfiyaData {
        android.util.Log.d("ComprobanteTransfiya", "Iniciando generación de imagen de Transfiya")
        
        try {
            // Usar imagen comprotransfiya.bin como base
            val originalBitmap = try {
                desencriptarBin("comprotransfiya.bin") ?: android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            } catch (e: Exception) {
                android.util.Log.w("ComprobanteTransfiya", "No se pudo cargar comprotransfiya.bin, usando bitmap simple")
                android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            }
            
            // Redimensionar la imagen a dimensiones muy altas para máxima calidad en capturas
            val finalBaseBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, 2400, 5120, true)
            
            // Crear un bitmap de muy alta calidad HD
            val hdBitmap = android.graphics.Bitmap.createBitmap(2400, 5120, android.graphics.Bitmap.Config.ARGB_8888)
            val hdCanvas = android.graphics.Canvas(hdBitmap)
            val hdPaint = android.graphics.Paint()
            hdPaint.isFilterBitmap = true
            hdPaint.isAntiAlias = true
            hdPaint.isDither = true
            hdPaint.isSubpixelText = true
            hdPaint.isLinearText = true
            hdPaint.strokeWidth = 0f
            
            // Dibujar la imagen escalada con configuración HD
            hdCanvas.drawBitmap(finalBaseBitmap, 0f, 0f, hdPaint)
            
            // Usar el bitmap HD
            val finalBaseBitmapHD = hdBitmap
            
            android.util.Log.d("ComprobanteTransfiya", "Imagen original: ${originalBitmap.width}x${originalBitmap.height}")
            android.util.Log.d("ComprobanteTransfiya", "Imagen optimizada: ${finalBaseBitmapHD.width}x${finalBaseBitmapHD.height}")
            
            val width = finalBaseBitmapHD.width
            val height = finalBaseBitmapHD.height
            android.util.Log.d("ComprobanteTransfiya", "Base bitmap size: ${width}x${height}")
            
            val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            
            // Dibujar imagen base como fondo con alta calidad HD
            canvas.drawBitmap(finalBaseBitmapHD, 0f, 0f, null)
            android.util.Log.d("ComprobanteTransfiya", "Base bitmap dibujado en canvas")

            val paint = android.graphics.Paint()
            // Color para la captura
            val colorCaptura = android.graphics.Color.parseColor("#27012b")
            paint.textSize = 85f // Texto mucho más grande para alta calidad en capturas
            paint.isAntiAlias = true
            paint.isSubpixelText = true
            paint.isLinearText = true
            paint.isFilterBitmap = true
            paint.isDither = true
            paint.strokeWidth = 0f
            paint.style = android.graphics.Paint.Style.FILL
            
            // Usar la fuente moneda_medium si está disponible
            try {
                val typeface: android.graphics.Typeface? = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.moneda_medium)
                if (typeface != null) {
                    paint.typeface = typeface
                    android.util.Log.d("ComprobanteTransfiya", "Fuente moneda_medium cargada exitosamente")
                }
            } catch (e: Exception) {
                android.util.Log.w("ComprobanteTransfiya", "No se pudo cargar la fuente personalizada, usando DEFAULT")
                paint.typeface = android.graphics.Typeface.DEFAULT
            }

            // Obtener primer y tercer nombre para el comprobante
            val nombreParaComprobante = obtenerPrimerYTercerNombre(nombreContacto)
            val nombreCapitalizado = nombreParaComprobante.split(" ").joinToString(" ") { 
                it.replaceFirstChar { c -> 
                    if (c.isLowerCase()) c.titlecase(java.util.Locale("es", "ES")) else c.toString() 
                } 
            }
            
            // Formatear el número con espacios: 300 000 0000
            val numeroFormateado = numeroCelular.replace("[^0-9]".toRegex(), "").let { digits ->
                when (digits.length) {
                    0 -> ""
                    in 1..3 -> digits
                    in 4..6 -> digits.substring(0,3) + " " + digits.substring(3)
                    in 7..10 -> digits.substring(0,3) + " " + digits.substring(3,6) + " " + digits.substring(6)
                    else -> digits
                }
            }
            
            // Generar referencia aleatoria tipo M12345678
            val referencia = "M" + (10000000 + kotlin.random.Random.nextInt(90000000)).toString()
            
            // Fecha actual en formato '01 de agosto de 2025 a las 01:30 a. m.'
            val formatoFecha = java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm a", java.util.Locale("es", "ES"))
            val fechaFormateada = formatoFecha.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")

            // Formatear la cantidad como moneda: $ 50.000,00
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }

            // Dibujar los textos en posiciones específicas para Transfiya (AJUSTADOS: textos más arriba, número y cantidad más abajo)
            paint.color = colorCaptura
            // canvas.drawText(nombreCapitalizado, width * 0.08f, height * 0.10f, paint)          // COMENTADO: No dibujar nombre en comprobante
            canvas.drawText(cantidadFormateada, width * 0.08f, height * 0.282f, paint)          // Bajado de 0.25f a 0.30f (cantidad más abajo)
            canvas.drawText(numeroFormateado, width * 0.073f, height * 0.218f, paint)            // Bajado de 0.20f a 0.25f (número más abajo)
            canvas.drawText(referencia, width * 0.08f, height * 0.398f, paint)                  // Subido de 0.46f a 0.41f
            canvas.drawText("Disponible", width * 0.08f, height * 0.463f, paint)               // Subido de 0.525f a 0.475f
            canvas.drawText(fechaFormateada, width * 0.078f, height * 0.34f, paint)            // Subido de 0.395f a 0.345f

            android.util.Log.d("ComprobanteTransfiya", "Imagen generada exitosamente")
            return ComprobanteTransfiyaData(bitmap, referencia, fechaFormateada)
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteTransfiya", "Error generando imagen: ${e.message}", e)
            
            // Fallback: crear bitmap simple
            val fallbackBitmap = android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888)
            val fallbackCanvas = android.graphics.Canvas(fallbackBitmap)
            val fallbackPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#2C002C")
                textSize = 60f
                isAntiAlias = true
            }
            
            fallbackCanvas.drawText("Comprobante Transfiya generado", 300f, 300f, fallbackPaint)
            return ComprobanteTransfiyaData(fallbackBitmap, "M00000000", "Fecha no disponible")
        }
    }
    
    private fun generarImagenComprobanteTransfiyaConDatos(nombreContacto: String, numeroCelular: String, cantidad: String, referenciaGuardada: String, fechaGuardada: String): ComprobanteTransfiyaData {
        android.util.Log.d("ComprobanteTransfiya", "Regenerando imagen de Transfiya con datos guardados")
        
        try {
            // Usar imagen comprotransfiya.bin como base
            val originalBitmap = try {
                desencriptarBin("comprotransfiya.bin") ?: android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            } catch (e: Exception) {
                android.util.Log.w("ComprobanteTransfiya", "No se pudo cargar comprotransfiya.bin, usando bitmap simple")
                android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            }
            
            // Redimensionar la imagen a dimensiones muy altas para máxima calidad en capturas
            val finalBaseBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, 2400, 5120, true)
            
            // Crear un bitmap de muy alta calidad HD
            val hdBitmap = android.graphics.Bitmap.createBitmap(2400, 5120, android.graphics.Bitmap.Config.ARGB_8888)
            val hdCanvas = android.graphics.Canvas(hdBitmap)
            val hdPaint = android.graphics.Paint()
            hdPaint.isFilterBitmap = true
            hdPaint.isAntiAlias = true
            hdPaint.isDither = true
            hdPaint.isSubpixelText = true
            hdPaint.isLinearText = true
            hdPaint.strokeWidth = 0f
            
            // Dibujar la imagen escalada con configuración HD
            hdCanvas.drawBitmap(finalBaseBitmap, 0f, 0f, hdPaint)
            
            // Usar el bitmap HD
            val finalBaseBitmapHD = hdBitmap
            
            val width = finalBaseBitmapHD.width
            val height = finalBaseBitmapHD.height
            
            val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            
            // Dibujar imagen base como fondo con alta calidad HD
            canvas.drawBitmap(finalBaseBitmapHD, 0f, 0f, null)

            val paint = android.graphics.Paint()
            // Color para la captura
            val colorCaptura = android.graphics.Color.parseColor("#280128")
            paint.textSize = 89f // Texto mucho más grande para alta calidad en capturas
            paint.isAntiAlias = true
            paint.isSubpixelText = true
            paint.isLinearText = true
            paint.isFilterBitmap = true
            paint.isDither = true
            paint.strokeWidth = 0f
            paint.style = android.graphics.Paint.Style.FILL
            
            // Usar la fuente moneda_medium si está disponible
            try {
                val typeface: android.graphics.Typeface? = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.moneda_medium)
                if (typeface != null) {
                    paint.typeface = typeface
                }
            } catch (e: Exception) {
                paint.typeface = android.graphics.Typeface.DEFAULT
            }

            // Obtener primer y tercer nombre para el comprobante
            val nombreParaComprobante = obtenerPrimerYTercerNombre(nombreContacto)
            val nombreCapitalizado = nombreParaComprobante.split(" ").joinToString(" ") { 
                it.replaceFirstChar { c -> 
                    if (c.isLowerCase()) c.titlecase(java.util.Locale("es", "ES")) else c.toString() 
                } 
            }
            
            // Formatear el número con espacios: 300 000 0000
            val numeroFormateado = numeroCelular.replace("[^0-9]".toRegex(), "").let { digits ->
                when (digits.length) {
                    0 -> ""
                    in 1..3 -> digits
                    in 4..6 -> digits.substring(0,3) + " " + digits.substring(3)
                    in 7..10 -> digits.substring(0,3) + " " + digits.substring(3,6) + " " + digits.substring(6)
                    else -> digits
                }
            }

            // Formatear la cantidad como moneda: $ 50.000,00
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }

            // Dibujar los textos en posiciones específicas para Transfiya usando datos guardados (AJUSTADOS: textos más arriba, número y cantidad más abajo)
            paint.color = colorCaptura
            // canvas.drawText(nombreCapitalizado, width * 0.08f, height * 0.10f, paint)          // COMENTADO: No dibujar nombre en comprobante
            canvas.drawText(cantidadFormateada, width * 0.08f, height * 0.33f, paint)          // Bajado de 0.25f a 0.30f (cantidad más abajo)
            canvas.drawText(numeroFormateado, width * 0.08f, height * 0.23f, paint)            // Bajado de 0.20f a 0.25f (número más abajo)
            canvas.drawText(referenciaGuardada, width * 0.08f, height * 0.45f, paint)          // Subido de 0.46f a 0.41f
            canvas.drawText("Disponible", width * 0.08f, height * 0.520f, paint)               // Subido de 0.525f a 0.475f
            canvas.drawText(fechaGuardada, width * 0.08f, height * 0.450f, paint)              // Subido de 0.395f a 0.345f

            android.util.Log.d("ComprobanteTransfiya", "Imagen regenerada exitosamente con datos guardados")
            return ComprobanteTransfiyaData(bitmap, referenciaGuardada, fechaGuardada)
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteTransfiya", "Error regenerando imagen: ${e.message}", e)
            
            // Fallback: crear bitmap simple
            val fallbackBitmap = android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888)
            val fallbackCanvas = android.graphics.Canvas(fallbackBitmap)
            val fallbackPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#2C002C")
                textSize = 60f
                isAntiAlias = true
            }
            
            fallbackCanvas.drawText("Comprobante Transfiya regenerado", 250f, 300f, fallbackPaint)
            return ComprobanteTransfiyaData(fallbackBitmap, referenciaGuardada, fechaGuardada)
        }
    }
    
    // Función para generar datos de comprobante para envíos normales
    private fun generarDatosComprobanteNormal(nombreContacto: String, numeroCelular: String, cantidad: String): ComprobanteTransfiyaData {
        android.util.Log.d("ComprobanteNormal", "Generando datos de comprobante para envío normal")
        
        try {
            // Generar referencia aleatoria tipo M12345678
            val referencia = "M" + (10000000 + kotlin.random.Random.nextInt(90000000)).toString()
            
            // Fecha actual en formato '01 de agosto de 2025 a las 01:30 a. m.'
            val formatoFecha = java.text.SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm a", java.util.Locale("es", "ES"))
            val fechaRaw = formatoFecha.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")
            val dia = java.text.SimpleDateFormat("d", java.util.Locale("es", "ES")).format(java.util.Date()).toInt()
            val fechaFormateada = (if (dia < 10) "0" else "") + fechaRaw
            
            // Crear un bitmap simple para los datos (no se usa para mostrar)
            val fallbackBitmap = android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888)
            
            android.util.Log.d("ComprobanteNormal", "Datos generados: referencia='$referencia', fecha='$fechaFormateada'")
            return ComprobanteTransfiyaData(fallbackBitmap, referencia, fechaFormateada)
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteNormal", "Error generando datos: ${e.message}", e)
            return ComprobanteTransfiyaData(
                android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888),
                "M00000000",
                "Fecha no disponible"
            )
        }
    }
    
    // Función para generar comprobante con base2.bin usando nombre completo
    private fun generarImagenComprobanteBase2ConDatos(nombreContacto: String, numeroCelular: String, cantidad: String, referenciaGuardada: String, fechaGuardada: String): ComprobanteTransfiyaData {
        android.util.Log.d("ComprobanteBase2", "Generando imagen de comprobante con base2.bin y nombre completo")
        
        try {
            // Usar imagen base2.bin como base
            android.util.Log.d("ComprobanteBase2", "Intentando cargar base2.bin...")
            val originalBitmap = try {
                val bitmap = desencriptarBin("base2.bin")
                if (bitmap != null) {
                    android.util.Log.d("ComprobanteBase2", "✅ base2.bin cargado exitosamente: ${bitmap.width}x${bitmap.height}")
                    bitmap
                } else {
                    android.util.Log.w("ComprobanteBase2", "❌ desencriptarBin retornó null para base2.bin")
                    android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
                }
            } catch (e: Exception) {
                android.util.Log.e("ComprobanteBase2", "❌ Error cargando base2.bin: ${e.message}", e)
                android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            }
            
            // Redimensionar la imagen a dimensiones muy altas para máxima calidad en capturas
            val finalBaseBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, 2400, 5120, true)
            
            // Crear un bitmap de muy alta calidad HD
            val hdBitmap = android.graphics.Bitmap.createBitmap(2400, 5120, android.graphics.Bitmap.Config.ARGB_8888)
            val hdCanvas = android.graphics.Canvas(hdBitmap)
            val hdPaint = android.graphics.Paint()
            hdPaint.isFilterBitmap = true
            hdPaint.isAntiAlias = true
            hdPaint.isDither = true
            hdPaint.isSubpixelText = true
            hdPaint.isLinearText = true
            hdPaint.strokeWidth = 0f
            
            // Dibujar la imagen escalada con configuración HD
            hdCanvas.drawBitmap(finalBaseBitmap, 0f, 0f, hdPaint)
            
            // Usar el bitmap HD
            val finalBaseBitmapHD = hdBitmap
            
            val width = finalBaseBitmapHD.width
            val height = finalBaseBitmapHD.height
            
            val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            
            // Dibujar imagen base como fondo con alta calidad HD
            canvas.drawBitmap(finalBaseBitmapHD, 0f, 0f, null)

            val paint = android.graphics.Paint()
            // Color para la captura
            val colorCaptura = android.graphics.Color.parseColor("#280128")
            paint.textSize = 82f // Texto más pequeño para mejor legibilidad
            paint.isAntiAlias = true
            paint.isSubpixelText = true
            paint.isLinearText = true
            paint.isFilterBitmap = true
            paint.isDither = true
            paint.strokeWidth = 0f
            paint.style = android.graphics.Paint.Style.FILL
            
            // Usar la fuente moneda_medium si está disponible
            try {
                val typeface: android.graphics.Typeface? = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.moneda_medium)
                if (typeface != null) {
                    paint.typeface = typeface
                }
            } catch (e: Exception) {
                paint.typeface = android.graphics.Typeface.DEFAULT
            }

            // Cargar posibles valores personalizados por comprobante (clave = ref|fecha)
            val prefsCustom = getSharedPreferences("ComprobanteBase2Custom", MODE_PRIVATE)
            val idKey = (referenciaGuardada + "|" + fechaGuardada)
                .replace("|", "_PIPE_")
                .replace(" ", "_SPACE_")
            val nombreCustom = prefsCustom.getString("nombre_" + idKey, null)
            val numeroCustom = prefsCustom.getString("numero_" + idKey, null)
            val montoCustom = prefsCustom.getString("monto_" + idKey, null)
            val refCustom = prefsCustom.getString("referencia_" + idKey, null)
            val fechaCustom = prefsCustom.getString("fecha_" + idKey, null)

            val nombreContactoFinal = if (!nombreCustom.isNullOrBlank()) nombreCustom else nombreContacto
            val numeroCelularFinal = if (!numeroCustom.isNullOrBlank()) numeroCustom else numeroCelular
            val cantidadFinal = if (!montoCustom.isNullOrBlank()) montoCustom else cantidad
            val referenciaFinal = if (!refCustom.isNullOrBlank()) refCustom else referenciaGuardada
            val fechaFinal = if (!fechaCustom.isNullOrBlank()) fechaCustom else fechaGuardada

            // Usar el nombre completo (no solo primer y tercer nombre)
            val nombreCapitalizado = nombreContactoFinal.split(" ").joinToString(" ") { 
                it.replaceFirstChar { c -> 
                    if (c.isLowerCase()) c.titlecase(java.util.Locale("es", "ES")) else c.toString() 
                } 
            }
            
            // Formatear el número con espacios: 300 000 0000
            val numeroFormateado = numeroCelularFinal.replace("[^0-9]".toRegex(), "").let { digits ->
                when (digits.length) {
                    0 -> ""
                    in 1..3 -> digits
                    in 4..6 -> digits.substring(0,3) + " " + digits.substring(3)
                    in 7..10 -> digits.substring(0,3) + " " + digits.substring(3,6) + " " + digits.substring(6)
                    else -> digits
                }
            }
            
            android.util.Log.d("ComprobanteBase2", "🔢 Debug del número:")
            android.util.Log.d("ComprobanteBase2", "   - numeroCelular original: '$numeroCelular'")
            android.util.Log.d("ComprobanteBase2", "   - numeroCelular después de trim: '${numeroCelular.trim()}'")
            android.util.Log.d("ComprobanteBase2", "   - numeroCelular sin espacios: '${numeroCelular.replace(" ", "")}'")
            android.util.Log.d("ComprobanteBase2", "   - numeroFormateado: '$numeroFormateado'")
            android.util.Log.d("ComprobanteBase2", "   - ¿Está vacío?: ${numeroFormateado.isEmpty()}")
            android.util.Log.d("ComprobanteBase2", "   - Longitud: ${numeroFormateado.length}")
            android.util.Log.d("ComprobanteBase2", "   - ¿Contiene solo números?: ${numeroCelular.replace("[^0-9]".toRegex(), "").isNotEmpty()}")

            // Formatear la cantidad como moneda: $ 50.000,00
            val cantidadFormateada = try {
                val valor = cantidadFinal.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }

            // Dibujar los textos en posiciones específicas para base2.bin (mismas posiciones que base.bin)
            paint.color = colorCaptura
            canvas.drawText(nombreCapitalizado, width * 0.076f, height * 0.445f, paint)
            canvas.drawText(cantidadFormateada, width * 0.077f, height * 0.507f, paint)
            canvas.drawText(numeroFormateado, width * 0.075f, height * 0.563f, paint)
            canvas.drawText(referenciaFinal, width * 0.0765f, height * 0.685f, paint)
            canvas.drawText("Disponible", width * 0.077f, height * 0.749f, paint)
            canvas.drawText(fechaFinal, width * 0.076f, height * 0.624f, paint)
            
            // Log adicional para verificar que el número se dibujó
            android.util.Log.d("ComprobanteBase2", "🎯 Número dibujado en el comprobante:")
            android.util.Log.d("ComprobanteBase2", "   - Número formateado: '$numeroFormateado'")
            android.util.Log.d("ComprobanteBase2", "   - Posición X: ${width * 0.077f}")
            android.util.Log.d("ComprobanteBase2", "   - Posición Y: ${height * 0.605f}")
            android.util.Log.d("ComprobanteBase2", "   - ¿Número no está vacío?: ${numeroFormateado.isNotEmpty()}")
            
            android.util.Log.d("ComprobanteBase2", "📝 Textos dibujados:")
            android.util.Log.d("ComprobanteBase2", "   - Nombre: '$nombreCapitalizado' en (${width * 0.08f}, ${height * 0.475f})")
            android.util.Log.d("ComprobanteBase2", "   - Cantidad: '$cantidadFormateada' en (${width * 0.08f}, ${height * 0.543f})")
            android.util.Log.d("ComprobanteBase2", "   - Número: '$numeroFormateado' en (${width * 0.077f}, ${height * 0.605f})")
            android.util.Log.d("ComprobanteBase2", "   - Referencia: '$referenciaFinal' en (${width * 0.08f}, ${height * 0.735f})")
            android.util.Log.d("ComprobanteBase2", "   - Fecha: '$fechaFinal' en (${width * 0.08f}, ${height * 0.670f})")

            android.util.Log.d("ComprobanteBase2", "✅ Imagen generada exitosamente con nombre completo")
            android.util.Log.d("ComprobanteBase2", "   Bitmap final: ${bitmap.width}x${bitmap.height}")
            android.util.Log.d("ComprobanteBase2", "   Referencia: '$referenciaFinal'")
            android.util.Log.d("ComprobanteBase2", "   Fecha: '$fechaFinal'")
            return ComprobanteTransfiyaData(bitmap, referenciaFinal, fechaFinal)
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteBase2", "Error generando imagen: ${e.message}", e)
            
            // Fallback: crear bitmap simple
            val fallbackBitmap = android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888)
            val fallbackCanvas = android.graphics.Canvas(fallbackBitmap)
            val fallbackPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#2C002C")
                textSize = 60f
                isAntiAlias = true
            }
            
            fallbackCanvas.drawText("Comprobante Base2 regenerado", 250f, 300f, fallbackPaint)
            return ComprobanteTransfiyaData(fallbackBitmap, referenciaGuardada, fechaGuardada)
        }
    }
    
    private fun mostrarComprobanteTransfiya() {
        try {
            android.util.Log.d("ComprobanteTransfiya", "Mostrando comprobante de Transfiya")
            
            // Verificar si existe el comprobante generado
            if (comprobanteTransfiyaBitmap == null) {
                android.util.Log.w("ComprobanteTransfiya", "No hay comprobante generado")
                Toast.makeText(this, "No hay comprobante disponible", Toast.LENGTH_SHORT).show()
                return
            }
            
            // Formatear la cantidad para mostrar
            val cantidadFormateada = try {
                val valor = datosTransfiyaCantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }
            
            // Mostrar el comprobante usando la función existente pero con el bitmap de Transfiya
            mostrarComprobanteImagenTransfiya(comprobanteTransfiyaBitmap!!, datosTransfiyaNombre, cantidadFormateada)
            
            android.util.Log.d("ComprobanteTransfiya", "Comprobante de Transfiya mostrado exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteTransfiya", "Error mostrando comprobante de Transfiya: ${e.message}", e)
            Toast.makeText(this, "Error al mostrar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun mostrarComprobanteTransfiyaConDatos(numeroCelular: String, cantidad: String, referencia: String, fechaComprobante: String) {
        try {
            android.util.Log.d("ComprobanteTransfiya", "Regenerando comprobante de Transfiya con datos guardados")
            android.util.Log.d("ComprobanteTransfiya", "Parámetros recibidos: numeroCelular='$numeroCelular', cantidad='$cantidad', referencia='$referencia', fechaComprobante='$fechaComprobante'")
            
            // Regenerar comprobante usando la función de generación pero con datos específicos
            // Usar el nombre del usuario actual desde el EditText
            val nombreUsuario = etNombre.text.toString().trim().ifEmpty { "Usuario" }
            android.util.Log.d("ComprobanteTransfiya", "Regenerando con nombre: '$nombreUsuario'")
            val comprobanteData = generarImagenComprobanteTransfiyaConDatos(nombreUsuario, numeroCelular, cantidad, referencia, fechaComprobante)
            
            // Formatear la cantidad para mostrar
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }
            
            // Mostrar el comprobante regenerado
            mostrarComprobanteImagenTransfiya(comprobanteData.bitmap, numeroCelular, cantidadFormateada)
            
            android.util.Log.d("ComprobanteTransfiya", "Comprobante de Transfiya regenerado y mostrado exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteTransfiya", "Error regenerando comprobante de Transfiya: ${e.message}", e)
            Toast.makeText(this, "Error al mostrar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    // Función para mostrar comprobante con base2.bin usando nombre completo
    private fun mostrarComprobanteBase2ConDatos(nombreContacto: String, numeroCelular: String, cantidad: String, referencia: String, fechaComprobante: String) {
        try {
            android.util.Log.d("ComprobanteBase2", "🔄 Generando comprobante con base2.bin y nombre completo")
            android.util.Log.d("ComprobanteBase2", "📋 Parámetros recibidos: nombreContacto='$nombreContacto', numeroCelular='$numeroCelular', cantidad='$cantidad', referencia='$referencia', fechaComprobante='$fechaComprobante'")
            
            // El parámetro numeroCelular contiene el número telefónico del campo "Cel"
            val numeroTelefonico = numeroCelular.trim().ifEmpty { "Usuario" }
            android.util.Log.d("ComprobanteBase2", "🔢 Número telefónico recibido: '$numeroCelular'")
            android.util.Log.d("ComprobanteBase2", "🔢 Número telefónico procesado: '$numeroTelefonico'")
            android.util.Log.d("ComprobanteBase2", "🔢 ¿Es un número válido?: ${numeroTelefonico.matches(Regex("^[0-9\\s]+$"))}")
            
            android.util.Log.d("ComprobanteBase2", "🎨 Llamando a generarImagenComprobanteBase2ConDatos...")
            val comprobanteData = generarImagenComprobanteBase2ConDatos(nombreContacto, numeroCelular, cantidad, referencia, fechaComprobante)

            // Persistir última referencia y fecha usadas para futuras ediciones
            try {
                val prefs = getSharedPreferences("ComprobanteBase2Custom", MODE_PRIVATE)
                prefs.edit()
                    .putString("ref_last", comprobanteData.referencia)
                    .putString("fecha_last", comprobanteData.fechaFormateada)
                    .apply()
            } catch (_: Exception) { }
            
            android.util.Log.d("ComprobanteBase2", "✅ ComprobanteData generado:")
            android.util.Log.d("ComprobanteBase2", "   - Bitmap: ${comprobanteData.bitmap.width}x${comprobanteData.bitmap.height}")
            android.util.Log.d("ComprobanteBase2", "   - Referencia: '${comprobanteData.referencia}'")
            android.util.Log.d("ComprobanteBase2", "   - Fecha: '${comprobanteData.fechaFormateada}'")
            
            // Formatear la cantidad para mostrar
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }
            
            android.util.Log.d("ComprobanteBase2", "💰 Cantidad formateada: '$cantidadFormateada'")
            android.util.Log.d("ComprobanteBase2", "🖼️ Llamando a mostrarComprobanteImagenBase2...")
            
            // Mostrar el comprobante generado
            mostrarComprobanteImagenBase2(comprobanteData.bitmap, numeroCelular, cantidadFormateada)
            
            android.util.Log.d("ComprobanteBase2", "✅ Comprobante con base2.bin generado y mostrado exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteBase2", "❌ Error generando comprobante con base2.bin: ${e.message}", e)
            Toast.makeText(this, "Error al mostrar comprobante: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun mostrarComprobanteImagenTransfiya(bitmap: android.graphics.Bitmap, nombreDestinatario: String, cantidad: String) {
        // Crear un ImageView dinámicamente con máxima calidad para capturas
        val imageView = ImageView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )
            setImageBitmap(bitmap)
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = "Comprobante de Transfiya"
        }
        
        // Crear contenedor para el comprobante PRIMERO
        val contenedorComprobante = RelativeLayout(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(android.graphics.Color.BLACK)
            addView(imageView)
        }
        
        // Crear flecha negra invisible en esquina superior izquierda DESPUÉS del contenedor
        val flechaRegreso = ImageView(this).apply {
            layoutParams = RelativeLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_TOP)
                addRule(RelativeLayout.ALIGN_PARENT_START)
                topMargin = 50
                leftMargin = 30
            }
            setImageResource(R.drawable.ic_arrow_back_white_24)
            setColorFilter(android.graphics.Color.BLACK) // Cambiar a negro
            contentDescription = "Regresar"
            alpha = 0f // Invisible
            setPadding(20, 20, 20, 20)
            
            setOnClickListener {
                android.util.Log.d("ComprobanteTransfiya", "Flecha de regreso presionada")
                
                // PASO 1: Remover DIRECTAMENTE el contenedor del comprobante
                android.util.Log.d("ComprobanteTransfiya", "Removiendo contenedor del comprobante...")
                val layoutPrincipal = window.decorView.findViewById<ViewGroup>(android.R.id.content)
                layoutPrincipal.removeView(contenedorComprobante)
                android.util.Log.d("ComprobanteTransfiya", "Contenedor removido exitosamente")
                
                       // PASO 2: Regresar al layout principal
       android.util.Log.d("ComprobanteTransfiya", "Regresando al layout principal...")
       ocultarLayoutMovimientos()
                
                // PASO 3: Cerrar modal si está abierto
                android.util.Log.d("ComprobanteTransfiya", "Cerrando modal...")
                ocultarModal()
                
                android.util.Log.d("ComprobanteTransfiya", "Regreso al layout de movimientos completado")
            }
        }
        
        // Agregar la flecha al contenedor
        contenedorComprobante.addView(flechaRegreso)
        
        // Agregar el contenedor al layout principal
        val layoutPrincipal = window.decorView.findViewById<ViewGroup>(android.R.id.content)
        
        // Remover cualquier comprobante existente antes de agregar el nuevo
        val comprobantesExistentes = layoutPrincipal.findViewWithTag<View>("comprobante")
        comprobantesExistentes?.let { layoutPrincipal.removeView(it) }
        
        // Agregar el nuevo comprobante
        contenedorComprobante.tag = "comprobante"
        layoutPrincipal.addView(contenedorComprobante)
        
        android.util.Log.d("ComprobanteTransfiya", "Comprobante de Transfiya mostrado exitosamente")
    }
    
    // Función para mostrar comprobante con base2.bin
    private fun mostrarComprobanteImagenBase2(bitmap: android.graphics.Bitmap, nombreDestinatario: String, cantidad: String) {
        android.util.Log.d("ComprobanteBase2", "🖼️ Iniciando mostrarComprobanteImagenBase2")
        android.util.Log.d("ComprobanteBase2", "📊 Bitmap recibido: ${bitmap.width}x${bitmap.height}")
        android.util.Log.d("ComprobanteBase2", "👤 Nombre destinatario: '$nombreDestinatario'")
        android.util.Log.d("ComprobanteBase2", "💰 Cantidad: '$cantidad'")
        
        try {
            // Crear un ImageView dinámicamente con máxima calidad para capturas
            val imageView = ImageView(this).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                setImageBitmap(bitmap)
                scaleType = ImageView.ScaleType.CENTER_CROP
                contentDescription = "Comprobante Base2"
            }
            android.util.Log.d("ComprobanteBase2", "✅ ImageView creado")
            
            // Crear contenedor para el comprobante PRIMERO
            val contenedorComprobante = RelativeLayout(this).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.BLACK)
                addView(imageView)
            }
            android.util.Log.d("ComprobanteBase2", "✅ Contenedor creado")
            
                        // Botón Editar para personalizar textos del comprobante
            val btnEditar = TextView(this).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    addRule(RelativeLayout.ALIGN_PARENT_END)
                    topMargin = 16
                    rightMargin = 16
                }
                text = "Editar"
                textSize = 16f
                setTextColor(android.graphics.Color.WHITE)
                setPadding(28, 16, 28, 16)
                setBackgroundResource(R.drawable.rounded_button_white)
                alpha = 0f
                setOnClickListener { mostrarEditorComprobanteBase2(contenedorComprobante) }
            }

            contenedorComprobante.addView(btnEditar)

            // Crear flecha negra invisible en esquina superior izquierda DESPUÉS del contenedor
            val flechaRegreso = ImageView(this).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    addRule(RelativeLayout.ALIGN_PARENT_START)
                    topMargin = 50
                    leftMargin = 30
                }
                setImageResource(R.drawable.ic_arrow_back_white_24)
                setColorFilter(android.graphics.Color.BLACK) // Cambiar a negro
                contentDescription = "Regresar"
                alpha = 0f // Invisible
                setPadding(20, 20, 20, 20)
                
                setOnClickListener {
                    android.util.Log.d("ComprobanteBase2", "Flecha de regreso presionada")
                    
                    // PASO 1: Remover DIRECTAMENTE el contenedor del comprobante
                    android.util.Log.d("ComprobanteBase2", "Removiendo contenedor del comprobante...")
                    val layoutPrincipal = window.decorView.findViewById<ViewGroup>(android.R.id.content)
                    layoutPrincipal.removeView(contenedorComprobante)
                    android.util.Log.d("ComprobanteBase2", "Contenedor removido exitosamente")
                    
                    // PASO 2: Regresar al layout principal
                    android.util.Log.d("ComprobanteBase2", "Regresando al layout principal...")
                    ocultarLayoutMovimientos()
                    
                    // PASO 3: Cerrar modal si está abierto
                    android.util.Log.d("ComprobanteBase2", "Cerrando modal...")
                    ocultarModal()
                    
                    android.util.Log.d("ComprobanteBase2", "Regreso al layout de movimientos completado")
                }
            }
            android.util.Log.d("ComprobanteBase2", "✅ Flecha de regreso creada")
            
            // Agregar la flecha al contenedor
            contenedorComprobante.addView(flechaRegreso)
            android.util.Log.d("ComprobanteBase2", "✅ Flecha agregada al contenedor")
            
            // Agregar el contenedor al layout principal
            val layoutPrincipal = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            android.util.Log.d("ComprobanteBase2", "✅ Layout principal encontrado")
            
            // Remover cualquier comprobante existente antes de agregar el nuevo
            val comprobantesExistentes = layoutPrincipal.findViewWithTag<View>("comprobante")
            comprobantesExistentes?.let { 
                layoutPrincipal.removeView(it)
                android.util.Log.d("ComprobanteBase2", "🗑️ Comprobante existente removido")
            }
            
            // Agregar el nuevo comprobante
            contenedorComprobante.tag = "comprobante"
            layoutPrincipal.addView(contenedorComprobante)
            android.util.Log.d("ComprobanteBase2", "✅ Nuevo comprobante agregado al layout principal")
            
            android.util.Log.d("ComprobanteBase2", "✅ Comprobante con base2.bin mostrado exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteBase2", "❌ Error en mostrarComprobanteImagenBase2: ${e.message}", e)
        }
    }

    // Editor modal para personalizar textos del comprobante base2
    private fun mostrarEditorComprobanteBase2(parent: ViewGroup) {
        try {
            val editorLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundColor(android.graphics.Color.parseColor("#B3000000"))
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                setPadding(40, 120, 40, 40)
                gravity = Gravity.CENTER
            }

            // Contenedor tipo tarjeta centrado
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(R.drawable.modal_rounded_background)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val margin = (24 * resources.displayMetrics.density).toInt()
                lp.setMargins(margin, margin, margin, margin)
                layoutParams = lp
                setPadding(32, 32, 32, 32)
            }

            fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

            fun label(text: String): TextView = TextView(this).apply {
                this.text = text
                setTextColor(android.graphics.Color.parseColor("#222222"))
                textSize = 14f
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.setMargins(0, dp(8), 0, dp(4))
                layoutParams = lp
            }

            // Clave por comprobante (usando última ref/fecha mostradas)
            val prefs = getSharedPreferences("ComprobanteBase2Custom", MODE_PRIVATE)
            val refLast = prefs.getString("ref_last", "") ?: ""
            val fechaLast = prefs.getString("fecha_last", "") ?: ""
            val idKey = (refLast + "|" + fechaLast)
                .replace("|", "_PIPE_")
                .replace(" ", "_SPACE_")

            fun campo(hint: String, prefKey: String): EditText {
                val et = EditText(this)
                et.hint = hint
                et.setText(prefs.getString(prefKey + "_" + idKey, "") ?: "")
                et.setTextColor(android.graphics.Color.parseColor("#111111"))
                et.setHintTextColor(android.graphics.Color.parseColor("#888888"))
                et.setPadding(dp(12), dp(12), dp(12), dp(12))
                et.background = try {
                    resources.getDrawable(R.drawable.edit_text_rounded, theme)
                } catch (_: Exception) {
                    resources.getDrawable(android.R.drawable.edit_text, theme)
                }
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(0, 0, 0, dp(8))
                et.layoutParams = lp
                return et
            }

            val etNombre = campo("Nombre", "nombre")
            val etNumero = campo("Número", "numero")
            val etMonto = campo("Monto (ej. 500)", "monto")
            val etReferencia = campo("Referencia (ej. M12345678)", "referencia")
            val etFecha = campo("Fecha (ej. 07 de agosto de 2025 a las 23:04 P. M.)", "fecha")

            val filaBtns = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.END
            }
            val btnGuardar = Button(this).apply {
                text = "Guardar"
                setOnClickListener {
                    // 1) Persistir valores personalizados (bajo clave vieja y, si cambian ref/fecha, bajo clave nueva)
                    val refObjetivo = etReferencia.text.toString().ifBlank { refLast }
                    val fechaObjetivo = etFecha.text.toString().ifBlank { fechaLast }

                    val idKeyNueva = (refObjetivo + "|" + fechaObjetivo)
                        .replace("|", "_PIPE_")
                        .replace(" ", "_SPACE_")

                    val editorCustom = prefs.edit()
                    fun putEnAmbasClaves(prefix: String, value: String) {
                        editorCustom.putString("${prefix}_" + idKey, value)
                        editorCustom.putString("${prefix}_" + idKeyNueva, value)
                    }

                    putEnAmbasClaves("nombre", etNombre.text.toString())
                    putEnAmbasClaves("numero", etNumero.text.toString())
                    putEnAmbasClaves("monto", etMonto.text.toString())
                    putEnAmbasClaves("referencia", etReferencia.text.toString())
                    putEnAmbasClaves("fecha", etFecha.text.toString())

                    // Actualizar últimas ref/fecha usadas para abrir el editor la próxima vez
                    editorCustom.putString("ref_last", refObjetivo)
                    editorCustom.putString("fecha_last", fechaObjetivo)
                    editorCustom.apply()

                    // 2) Actualizar el envío correspondiente en NequiCoPrefs (nombre y monto; si cambias ref/fecha, también se actualizan)
                    try {
                        val sp = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                        val envios = sp.getStringSet("envios", setOf())?.toMutableSet() ?: mutableSetOf()

                        val fechaEscapadaObjetivo = fechaObjetivo
                            .replace(" ", "_SPACE_")
                            .replace(":", "_COLON_")
                            .replace(".", "_DOT_")

                        val fechaEscapadaAntigua = fechaLast
                            .replace(" ", "_SPACE_")
                            .replace(":", "_COLON_")
                            .replace(".", "_DOT_")

                        val nombreNuevo = etNombre.text.toString().trim()
                        val montoNuevoSoloDigitos = etMonto.text.toString().trim().replace("[^0-9]".toRegex(), "")

                        val enviosActualizados = mutableSetOf<String>()
                        var seActualizo = false

                        for (envio in envios) {
                            val partes = envio.split("|").toMutableList()
                            if (partes.size >= 6) {
                                val esTransfiya = partes[3] == "1"
                                val ref = partes[4]
                                val fechaEscapada = partes[5]
                                val coincideConNuevaClave = (!esTransfiya && ref == refObjetivo && fechaEscapada == fechaEscapadaObjetivo)
                                val coincideConClaveAnterior = (!esTransfiya && ref == refLast && fechaEscapada == fechaEscapadaAntigua)
                                if (coincideConNuevaClave || coincideConClaveAnterior) {
                                    if (nombreNuevo.isNotBlank()) partes[1] = nombreNuevo
                                    if (montoNuevoSoloDigitos.isNotBlank()) partes[2] = montoNuevoSoloDigitos
                                    // Si la referencia/fecha fueron modificadas en el editor, reflejarlo en el envío almacenado
                                    if (refObjetivo.isNotBlank()) partes[4] = refObjetivo
                                    if (fechaEscapadaObjetivo.isNotBlank()) partes[5] = fechaEscapadaObjetivo
                                    enviosActualizados.add(partes.joinToString("|"))
                                    seActualizo = true
                                } else {
                                    enviosActualizados.add(envio)
                                }
                            } else {
                                enviosActualizados.add(envio)
                            }
                        }

                        if (seActualizo) {
                            sp.edit().putStringSet("envios", enviosActualizados).commit()
                            // Refrescar la lista de movimientos para reflejar los cambios
                            android.os.Handler(android.os.Looper.getMainLooper()).post {
                                cargarEnviosPorFecha(estadoMovimientosActual)
                            }
                        }
                    } catch (_: Exception) { }

                    // 3) Cerrar editor
                    (parent as? ViewGroup)?.removeView(editorLayout)
                    Toast.makeText(this@InicioActivity, "Guardado", Toast.LENGTH_SHORT).show()
                }
            }
            val btnCerrar = Button(this).apply {
                text = "Cerrar"
                setOnClickListener { (parent as? ViewGroup)?.removeView(editorLayout) }
            }
            filaBtns.addView(btnGuardar)
            filaBtns.addView(btnCerrar)

            // Envolver inputs en un ScrollView por si hay pantallas pequeñas
            val scroll = ScrollView(this)
            val inner = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }
            inner.addView(label("Nombre"))
            inner.addView(etNombre)
            inner.addView(label("Número"))
            inner.addView(etNumero)
            inner.addView(label("Monto"))
            inner.addView(etMonto)
            inner.addView(label("Referencia"))
            inner.addView(etReferencia)
            inner.addView(label("Fecha"))
            inner.addView(etFecha)
            scroll.addView(inner)

            card.addView(scroll)
            card.addView(filaBtns)

            editorLayout.addView(card)
            parent.addView(editorLayout)
        } catch (e: Exception) {
            android.util.Log.e("EditorComprobante", "Error mostrando editor: ${e.message}", e)
        }
    }
    
    private fun mostrarComprobanteImagen(bitmap: android.graphics.Bitmap, nombreDestinatario: String, cantidad: String) {
        // Crear un ImageView dinámicamente con máxima calidad para capturas
        val imageView = ImageView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_INSIDE // Mejor para alta calidad
            setImageBitmap(bitmap)
            // Configurar para máxima calidad
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            // Configuraciones adicionales para mejor calidad
            setAdjustViewBounds(true)
            setScaleType(ImageView.ScaleType.CENTER_INSIDE)
            // Configuraciones para mantener alta calidad en capturas
            setDrawingCacheEnabled(true)
            setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
        }
        
        // Crear botón "Listo" visible y ancho
        var listoClicked = false
        val btnListo = TextView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
            text = "Listo"
            textSize = 22f
            setTextColor(android.graphics.Color.WHITE)
            setBackgroundResource(R.drawable.button_background_final)
            gravity = android.view.Gravity.CENTER
            setPadding(80, 30, 80, 30)
            alpha = 0f // Invisible
            isClickable = true
            isFocusable = true
            elevation = 10f // Sombra para destacar
            setOnClickListener {
                if (listoClicked) return@setOnClickListener
                listoClicked = true
                isClickable = false
                android.util.Log.d("Comprobante", "Botón Listo presionado")
                
                // Efecto visual
                this.alpha = 0.5f
                this.postDelayed({ this.alpha = 1f }, 100)
                
                // Cerrar el comprobante usando la función existente
                ocultarComprobante()
                
                // Cerrar también el layout de envia plata
                ocultarLayoutEnviarPlata()
                ocultarModal()
                
                // Limpiar campos después de cerrar el comprobante
                limpiarCamposEnviarPlata()
                
                // Mostrar layout de movimientos directamente y recargar lista
                mostrarLayoutMovimientosDesdeListo()
            }
        }
        
        // Crear un FrameLayout para el fondo oscuro con posición fija
        val overlayLayout = android.widget.FrameLayout(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                // Configurar para que ignore los cambios de insets del sistema
                // flags = android.view.ViewGroup.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            }
            setBackgroundColor(android.graphics.Color.parseColor("#80000000")) // Fondo semi-transparente
            // Configurar para que no se ajuste a los insets del sistema
            fitsSystemWindows = false
            isClickable = true
            isFocusable = true
        }
        // Consumir todos los toques para que no pasen a los movimientos debajo
        overlayLayout.setOnTouchListener { _, _ -> true }
        
        // Agregar el ImageView al overlay
        overlayLayout.addView(imageView)
        
        // Agregar el botón "Listo" al overlay con posición fija
        val btnListoParams = android.widget.FrameLayout.LayoutParams(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.BOTTOM
            // Versión sin transición (no QR): margen fijo
            bottomMargin = 100
            leftMargin = 40
            rightMargin = 40
        }
        overlayLayout.addView(btnListo, btnListoParams)
        
        // Agregar el overlay al layout principal
        val rootLayout = findViewById<android.view.ViewGroup>(android.R.id.content)
        rootLayout.addView(overlayLayout)
        // Notificación local de éxito cuando el comprobante está visible
        try {
            val title = "¡Transacción Exitosa!"
            val monto = try { formatCurrency(cantidad) } catch (_: Exception) { cantidad }
            val body = "Comprobante generado por $monto."
            val intent = Intent(this, InicioActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pending = PendingIntent.getActivity(
                this,
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val builder = NotificationCompat.Builder(this, "nequico_default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setColor(android.graphics.Color.parseColor("#db0082"))
                .setContentIntent(pending)
            with(NotificationManagerCompat.from(this)) {
                notify((System.currentTimeMillis() + 321).toInt(), builder.build())
            }
        } catch (_: Exception) { }
        
        // Agregar envío normal (envio.bin) junto a envio5
        try {
        agregarEnvioMovimientos(nombreDestinatario, cantidad)
        } catch (_: Exception) {}
        
        // Animación de entrada de derecha a izquierda
        overlayLayout.alpha = 0f
        overlayLayout.translationX = overlayLayout.width.toFloat() // Empezar desde la derecha
        overlayLayout.animate()
            .alpha(1f)
            .translationX(0f) // Mover a la posición normal
            .setDuration(500)
            .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
            .start()
    }
    
    private fun mostrarComprobanteImagenConTransicion(bitmap: android.graphics.Bitmap, nombreDestinatario: String, cantidad: String, isFromQR: Boolean = false) {
        // Crear un ImageView dinámicamente con máxima calidad para capturas
        val imageView = ImageView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_INSIDE // Mejor para alta calidad
            setImageBitmap(bitmap)
            // Configurar para máxima calidad
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            // Configuraciones adicionales para mejor calidad
            setAdjustViewBounds(true)
            setScaleType(ImageView.ScaleType.CENTER_INSIDE)
            // Configuraciones para mantener alta calidad en capturas
            setDrawingCacheEnabled(true)
            setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
        }
        
        // Crear botón "Listo" visible y ancho
        var listoClicked2 = false
        val btnListo = TextView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
            text = "Listo"
            textSize = 22f
            setTextColor(android.graphics.Color.WHITE)
            setBackgroundResource(R.drawable.button_background_final)
            gravity = android.view.Gravity.CENTER
            setPadding(80, 30, 80, 30)
            alpha = 0f // Invisible inicialmente
            isClickable = true
            isFocusable = true
            elevation = 10f // Sombra para destacar
            setOnClickListener {
                if (listoClicked2) return@setOnClickListener
                listoClicked2 = true
                isClickable = false
                android.util.Log.d("Comprobante", "Botón Listo presionado")
                
                // Efecto visual
                this.alpha = 0.5f
                this.postDelayed({ this.alpha = 1f }, 100)
                
                // Cerrar el comprobante usando la función existente
                ocultarComprobante()
                
                // Cerrar también el layout de envia plata
                ocultarLayoutEnviarPlata()
                ocultarModal()
                
                // Limpiar campos después de cerrar el comprobante
                limpiarCamposEnviarPlata()
                
                // Mostrar layout de movimientos directamente sin animación
                mostrarLayoutMovimientosDesdeListo()
            }
        }
        
        // Crear un FrameLayout para el fondo oscuro con posición fija
        val overlayLayout = android.widget.FrameLayout(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                // Configurar para que ignore los cambios de insets del sistema
                // flags = android.view.ViewGroup.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            }
            setBackgroundColor(android.graphics.Color.parseColor("#80000000")) // Fondo semi-transparente
            // Configurar para que no se ajuste a los insets del sistema
            fitsSystemWindows = false
            isClickable = true
            isFocusable = true
        }
        // Consumir todos los toques para que no pasen a los movimientos debajo
        overlayLayout.setOnTouchListener { _, _ -> true }
        
        // Agregar el ImageView al overlay
        overlayLayout.addView(imageView)
        
        // Agregar VideoView arriba del comprobante para reproducir map.bin
        val videoMap = android.widget.VideoView(this).apply {
            layoutParams = android.widget.FrameLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            ).apply { gravity = android.view.Gravity.CENTER }
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
            setZOrderOnTop(true)
            isClickable = false
            isFocusable = false
        }
        overlayLayout.addView(videoMap)

        // Agregar el botón "Listo" al overlay con posición fija
        val btnListoParams = android.widget.FrameLayout.LayoutParams(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.BOTTOM
            bottomMargin = if (isFromQR) 520 else 250
            leftMargin = 40
            rightMargin = 40
        }
        overlayLayout.addView(btnListo, btnListoParams)
        
        // Agregar el overlay al layout principal
        val rootLayout = findViewById<android.view.ViewGroup>(android.R.id.content)
        rootLayout.addView(overlayLayout)
        
        // Agregar envío normal (envio.bin) solo si NO es flujo QR
        if (!isFromQR) {
            try {
                agregarEnvioMovimientos(nombreDestinatario, cantidad)
            } catch (_: Exception) { }
        }
        
        // Ya no agregamos envio normal aquí

        // Mostrar sin desplazamiento horizontal (solo fade-in)
        overlayLayout.alpha = 0f
        overlayLayout.animate()
            .alpha(1f)
            .setDuration(300)
            .setInterpolator(android.view.animation.DecelerateInterpolator())
            .start()
    }

    // Mostrar overlay blanco con listo.bin centrado y transición de scale
    private fun mostrarOverlayListo(onFinish: () -> Unit) {
        try {
            android.util.Log.d("OverlayListo", "Creando overlay blanco para listo.bin")
            val rootView = findViewById<android.view.ViewGroup>(android.R.id.content)
            val overlay = android.widget.FrameLayout(this).apply {
                setBackgroundColor(android.graphics.Color.WHITE)
                alpha = 0f
                scaleX = 0.8f
                scaleY = 0.8f
                isClickable = true
                isFocusable = true
            }

            val minDim = kotlin.math.min(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
            val targetSize = (minDim * 0.30f).toInt() // 30% del lado menor de la pantalla
            val image = android.widget.ImageView(this).apply {
                layoutParams = android.widget.FrameLayout.LayoutParams(
                    targetSize,
                    targetSize
                ).apply { gravity = android.view.Gravity.CENTER }
                adjustViewBounds = true
                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            }
            // Cargar listo.bin
            android.util.Log.d("OverlayListo", "Cargando listo.bin en ImageView centrado")
            establecerImagenDesencriptada(image, "listo.bin")
            overlay.addView(image)
            rootView.addView(overlay)

            // Scale-in más lento
            overlay.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(1400)
                .withEndAction {
                    // Mantener un breve momento visible antes de continuar
                    android.util.Log.d("OverlayListo", "Overlay visible, esperando breve pausa antes de cerrar")
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        // Fade-out más lento
                        overlay.animate().alpha(0f).setDuration(1000).withEndAction {
                            android.util.Log.d("OverlayListo", "Ocultando overlay listo.bin")
                            rootView.removeView(overlay)
                            onFinish()
                        }.start()
                    }, 600)
                }.start()
        } catch (e: Exception) {
            android.util.Log.e("OverlayListo", "Error mostrando listo.bin: ${e.message}", e)
            onFinish()
        }
    }

            // Mostrar listopantalla.bin con animación desde arriba durante 3s, similar a error.bin en LoginActivity
    private fun mostrarListopantallaAnim3s(onFinish: () -> Unit) {
        try {
            android.util.Log.d("ListoPantalla", "Creando overlay para listopantalla.bin")
            val rootView = findViewById<android.view.View>(android.R.id.content) as android.view.ViewGroup
            val layout = android.widget.FrameLayout(this).apply {
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        alpha = 0f
                        translationY = -2500f
                isClickable = false
                isFocusable = false
            }

            val image = android.widget.ImageView(this).apply {
                layoutParams = android.widget.FrameLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { gravity = android.view.Gravity.CENTER }
                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            }
            android.util.Log.d("ListoPantalla", "Cargando listopantalla.bin en ImageView centrado")
            establecerImagenDesencriptada(image, "listopantalla.bin")
            layout.addView(image)
            rootView.addView(layout)

            // Entrada desde arriba (más lenta)
            layout.animate()
                .alpha(1f)
                .translationY(-1000f)
                .setDuration(1000)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .withEndAction {
                    // Mantener visible 3.5s
                    android.util.Log.d("ListoPantalla", "Overlay visible, manteniendo 3s")
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        // Salida suave hacia arriba (no hacia abajo)
                        layout.animate().alpha(0f).translationY(-3000f).setDuration(700).withEndAction {
                            android.util.Log.d("ListoPantalla", "Ocultando overlay listopantalla.bin")
                            rootView.removeView(layout)
                            onFinish()
                        }.start()
                    }, 4000)
                }
                .start()
        } catch (e: Exception) {
            android.util.Log.e("ListoPantalla", "Error mostrando listopantalla.bin: ${e.message}", e)
            onFinish()
        }
    }

    // Generar y mostrar comprobante basado en comproqr.bin con textos dibujados y slide derecha->izquierda
    private fun mostrarComprobanteQRAhora(nombreEscaneado: String, cantidadTexto: String) {
        try {
            android.util.Log.d("ComprobanteQR", "Generando comprobante desde comproqr.bin")
            val nombre = if (nombreEscaneado.isNullOrBlank()) "Usuario" else nombreEscaneado
            val nombreCapitalizado = toTitleCaseEs(nombre)
            val etCuantoPago = try { findViewById<android.widget.EditText>(R.id.etCuantoPago) } catch (_: Exception) { null }
            val cantidadInput = cantidadTexto.ifBlank { etCuantoPago?.text?.toString() ?: "" }

            val referencia = "M" + (10000000 + kotlin.random.Random.nextInt(90000000)).toString()

            // Fecha formateada (día con 2 dígitos siempre)
            val formatoFecha = java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm a", java.util.Locale("es", "ES"))
            val fechaFormateada = formatoFecha.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")

            // Guardar para reutilizar en detalleqr
            lastQrReferencia = referencia
            lastQrFecha = fechaFormateada

            // Cargar base comproqr.bin
            android.util.Log.d("ComprobanteQR", "Cargando base comproqr.bin")
            val baseBitmap = desencriptarBin("comproqr.bin")
                ?: android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)

            // Escalar a alta resolución como otros comprobantes
            val finalBaseBitmap = android.graphics.Bitmap.createScaledBitmap(baseBitmap, 2400, 5120, true)
            val width = finalBaseBitmap.width
            val height = finalBaseBitmap.height
            val resultBitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(resultBitmap)
            canvas.drawBitmap(finalBaseBitmap, 0f, 0f, null)

            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#200020")
                textSize = 77f
                isAntiAlias = true
                isSubpixelText = true
                isLinearText = true
                isFilterBitmap = true
                isDither = true
                style = android.graphics.Paint.Style.FILL
                // Fuente
                try {
                    val tf = androidx.core.content.res.ResourcesCompat.getFont(this@InicioActivity, R.font.moneda_medium)
                    if (tf != null) typeface = tf
                } catch (_: Exception) { typeface = android.graphics.Typeface.DEFAULT }
            }

            // Formatear cantidad como $ 50.000,00 (usar parse robusto en pesos)
            val cantidadValor = parsePesos(cantidadInput)
            lastQrValor = cantidadValor
            val cantidadFormateada = try {
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO")).apply {
                    maximumFractionDigits = 2; minimumFractionDigits = 2; currency = java.util.Currency.getInstance("COP")
                }
                formatoMoneda.format(cantidadValor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (_: Exception) { "$ 0,00" }

            // Posiciones: usamos similares a base normal para coherencia
            canvas.drawText(nombreCapitalizado, width * 0.115f, height * 0.390f, paint)
            canvas.drawText(cantidadFormateada, width * 0.115f, height * 0.459f, paint)
            // Para QR, mostramos referencia, disponible y fecha
            canvas.drawText(referencia, width * 0.115f, height * 0.587f, paint)
            canvas.drawText("Disponible", width * 0.115f, height * 0.655f, paint)
            canvas.drawText(fechaFormateada, width * 0.115f, height * 0.521f, paint)

            // Mostrar imagen con transición derecha->izquierda y botón Listo (flujo QR)
            android.util.Log.d("ComprobanteQR", "Mostrando comprobante QR con transición")
            // Guardar nombre/cantidad para reuso (p. ej. en detalleqr)
            lastQrNombre = nombreCapitalizado
            lastQrCantidad = cantidadFormateada
            mostrarComprobanteImagenConTransicion(resultBitmap, nombreCapitalizado, cantidadFormateada, isFromQR = true)
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteQR", "Error generando comprobante QR: ${e.message}", e)
        }
    }
    
    // Función para mostrar layout de movimientos desde el botón Listo del comprobante
    private fun mostrarLayoutMovimientosDesdeListo() {
        try {
            android.util.Log.d("Movimientos", "Mostrando layout de movimientos desde Listo")
            
            // Mostrar el layout de movimientos con animación desde la izquierda
            val layoutMovimientos = findViewById<RelativeLayout>(R.id.layoutMovimientos)
            val screenWidth = resources.displayMetrics.widthPixels.toFloat()
            layoutMovimientos.translationX = -screenWidth
            layoutMovimientos.visibility = View.VISIBLE
            layoutMovimientos.animate()
                .translationX(0f)
                .setDuration(350)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
            
            // Cargar envíos con un pequeño delay para asegurar que SharedPreferences esté actualizado
            Handler(Looper.getMainLooper()).postDelayed({
                android.util.Log.d("Movimientos", "Cargando envíos después del delay...")
                cargarEnviosDesdeSharedPreferences()
                // Guardar envio5 en SharedPreferences para que sea el más reciente y persistente
                // Evitar duplicados: si ya fue guardado durante el ciclo de Paga, no volver a guardarlo aquí
                try {
                    val yaExisteEnvio5Pendiente = lastQrNombre.isNotEmpty() || lastQrCantidad.isNotEmpty()
                    if (yaExisteEnvio5Pendiente) {
                        guardarEnvio5EnSharedPreferences(lastQrNombre, lastQrCantidad)
                        // Volver a cargar para insertarlo ordenado por timestamp
                        cargarEnviosDesdeSharedPreferences()
                    }
                } finally {
                    lastQrNombre = ""; lastQrCantidad = ""
                }
            }, 100) // 100ms delay
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error mostrando layout: ${e.message}", e)
        }
    }
    private fun restarDelSaldo(cantidad: String) {
        android.util.Log.d("Comprobante", "Iniciando restarDelSaldo con cantidad: $cantidad")
        
        if (userNumber.isEmpty()) {
            android.util.Log.w("Comprobante", "Número de usuario no encontrado")
            return
        }
        
        CoroutineScope(Dispatchers.Main).launch {
        try {
            // Obtener el valor numérico de la cantidad
            val cantidadNumerica = cantidad.replace("[^0-9]".toRegex(), "").toLong()
            
                // Obtener usuario actual desde Firebase
                val user = withContext(Dispatchers.IO) {
                    firebaseManager.getUserByNumber(userNumber)
                }
                
                if (user != null) {
                    // Limpiar el saldo de caracteres no numéricos
                    val saldoLimpio = user.saldo.replace("[^0-9]".toRegex(), "")
                    val saldoActual = saldoLimpio.toLong()
            val nuevoSaldo = saldoActual - cantidadNumerica
            
                    android.util.Log.d("Comprobante", "Saldo actual: $saldoActual, Cantidad a restar: $cantidadNumerica, Nuevo saldo: $nuevoSaldo")
                    
                    // Actualizar saldo en Firebase
                    val success = withContext(Dispatchers.IO) {
                        firebaseManager.updateUserSaldo(userNumber, nuevoSaldo.toString())
                    }
                    
                    if (success) {
                        // Formatear el nuevo saldo para mostrar
                        val saldoFormateado = firebaseManager.formatSaldoForDisplay(nuevoSaldo.toString())
            
            // Actualizar los TextView del saldo
                        actualizarSaldoSeparado(saldoFormateado)
            
            android.util.Log.d("Comprobante", "Saldo restado: $cantidadNumerica, Nuevo saldo: $saldoFormateado")
                    } else {
                        android.util.Log.e("Comprobante", "Error actualizando saldo en Firebase")
                    }
                } else {
                    android.util.Log.w("Comprobante", "Usuario no encontrado en Firebase")
                }
        } catch (e: Exception) {
            android.util.Log.e("Comprobante", "Error al restar del saldo: ${e.message}")
            }
        }
    }
    
    private suspend fun validarSaldoSuficienteAsync(cantidad: String): Boolean {
        if (userNumber.isEmpty()) {
            android.util.Log.w("Comprobante", "Número de usuario no encontrado")
            return false
        }
        
        return try {
            val cantidadNumerica = cantidad.replace("[^0-9]".toRegex(), "").toLong()
            android.util.Log.d("Comprobante", "Cantidad a validar: $cantidadNumerica")
            
            // Obtener saldo actual desde Firebase
            val user = withContext(Dispatchers.IO) {
                firebaseManager.getUserByNumber(userNumber)
            }
            
            if (user != null) {
                val saldoLimpio = user.saldo.replace("[^0-9]".toRegex(), "")
                val saldoActual = saldoLimpio.toLong()
                
                val esSuficiente = cantidadNumerica <= saldoActual
                android.util.Log.d("Comprobante", "Validación saldo: $cantidadNumerica <= $saldoActual = $esSuficiente")
                android.util.Log.d("Comprobante", "Saldo raw de Firebase: ${user.saldo}")
                
                esSuficiente
            } else {
                android.util.Log.w("Comprobante", "Usuario no encontrado en Firebase")
                false
            }
        } catch (e: Exception) {
            android.util.Log.e("Comprobante", "Error al validar saldo: ${e.message}", e)
            false
        }
    }
    
    // Función síncrona para compatibilidad (usa caché local)
    private fun validarSaldoSuficiente(cantidad: String): Boolean {
        if (userNumber.isEmpty()) {
            android.util.Log.w("Comprobante", "Número de usuario no encontrado")
            return false
        }
        
        return try {
            val cantidadNumerica = cantidad.replace("[^0-9]".toRegex(), "").toLong()
            
            // Obtener saldo actual desde SharedPreferences (caché local)
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            val saldoGuardado = sharedPref.getString("user_saldo", "1000000") ?: "1000000"
            val saldoActual = saldoGuardado.toLong()
            
            val esSuficiente = cantidadNumerica <= saldoActual
            android.util.Log.d("Comprobante", "Validación saldo (caché): $cantidadNumerica <= $saldoActual = $esSuficiente")
            
            esSuficiente
        } catch (e: Exception) {
            android.util.Log.e("Comprobante", "Error al validar saldo: ${e.message}")
            false
        }
    }
    

    
        private fun limpiarCamposEnviarPlata() {
        try {
            val etCel = findViewById<EditText>(R.id.etCel)
            val etCuanto = findViewById<EditText>(R.id.etCuanto)

            // Limpiar los campos
            etCel.setText("")
            etCuanto.setText("")

            // Restaurar los hints
            etCel.hint = "Cel"
            etCuanto.hint = "¿Cuanto?"

            android.util.Log.d("EnviarPlata", "Campos limpiados exitosamente")

        } catch (e: Exception) {
            android.util.Log.e("EnviarPlata", "Error al limpiar campos: ${e.message}")
        }
    }
    
    private fun ocultarComprobante() {
        android.util.Log.d("Comprobante", "Ocultando comprobante")
        
        // Buscar y remover el overlay del comprobante
        val rootLayout = findViewById<android.view.ViewGroup>(android.R.id.content)
        for (i in 0 until rootLayout.childCount) {
            val child = rootLayout.getChildAt(i)
            if (child is android.widget.FrameLayout && child.childCount > 0) {
                android.util.Log.d("Comprobante", "Encontrado FrameLayout, removiendo...")
                rootLayout.removeView(child)
                android.util.Log.d("Comprobante", "Comprobante removido exitosamente")
                break
            }
        }
    }
    
    private fun cargarSaldoGuardado() {
        if (userNumber.isEmpty()) {
            android.util.Log.w("InicioActivity", "Número de usuario no encontrado")
            return
        }
        
        android.util.Log.d("InicioActivity", "Cargando saldo para usuario: $userNumber")
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    firebaseManager.getUserByNumber(userNumber)
                }
                
                android.util.Log.d("InicioActivity", "Usuario obtenido de Firebase: $user")
                
                if (user != null) {
                    android.util.Log.d("InicioActivity", "Saldo raw de Firebase: ${user.saldo}")
                    
                    // Formatear el saldo para mostrar
                    val saldoFormateado = firebaseManager.formatSaldoForDisplay(user.saldo)
                    android.util.Log.d("InicioActivity", "Saldo formateado: $saldoFormateado")
            
                    // Actualizar los TextView del saldo
                    actualizarSaldoSeparado(saldoFormateado)
                    
                    // Guardar saldo en SharedPreferences
                    val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_saldo", user.saldo)
                        apply()
                    }
                    
                    android.util.Log.d("InicioActivity", "Saldo cargado desde Firebase: ${user.saldo}")
                    
                    // Configurar listener para cambios en tiempo real (DESHABILITADO)
                    // firebaseManager.listenToUserSaldo(userNumber) { nuevoSaldo ->
                    //     runOnUiThread {
                    //         android.util.Log.d("InicioActivity", "Listener detectó cambio de saldo: $nuevoSaldo")
                    //         val saldoFormateado = firebaseManager.formatSaldoForDisplay(nuevoSaldo)
                    //         actualizarSaldoSeparado(saldoFormateado)
                    //         android.util.Log.d("InicioActivity", "Saldo actualizado en tiempo real: $nuevoSaldo")
                    //     }
                    // }
                } else {
                    android.util.Log.w("InicioActivity", "Usuario no encontrado en Firebase")
                    
                    // Si no hay usuario en Firebase, usar saldo visual como respaldo
                    val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    val saldoVisual = sharedPref.getLong("saldo_visual", -1L)
                    
                    if (saldoVisual != -1L) {
                        android.util.Log.d("InicioActivity", "Usando saldo visual como respaldo: $saldoVisual")
                        actualizarSaldoVisual(saldoVisual)
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("InicioActivity", "Error al cargar saldo desde Firebase: ${e.message}", e)
                
                // En caso de error, usar saldo visual como respaldo
                val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                val saldoVisual = sharedPref.getLong("saldo_visual", -1L)
                
                if (saldoVisual != -1L) {
                    android.util.Log.d("InicioActivity", "Usando saldo visual como respaldo por error: $saldoVisual")
                    actualizarSaldoVisual(saldoVisual)
                }
            }
        }
    }

    // Convierte un nombre al formato Título en español: "el mundo de laura" -> "El Mundo De Laura"
    private fun toTitleCaseEs(texto: String): String {
        return try {
            val localeEs = java.util.Locale("es", "ES")
            texto.trim()
                .lowercase(localeEs)
                .split(Regex("\\s+"))
                .filter { it.isNotEmpty() }
                .joinToString(" ") { palabra ->
                    palabra.replaceFirstChar { c ->
                        if (c.isLowerCase()) c.titlecase(localeEs) else c.toString()
                    }
                }
        } catch (_: Exception) { texto }
    }

    @Deprecated("Use Activity Result API; kept for quick test")
    private fun handleQrTestActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        // Eliminado: ya no se maneja flujo de tomar foto para alias
    }
    
    private fun actualizarSaldoSeparado(nuevoSaldo: String) {
        try {
            // Extraer la parte principal del saldo (sin ",00")
            val saldoPrincipal = nuevoSaldo.replace(",00", "").replace(",", ".")
            
            // Actualizar el monto principal - verificar si ya tiene "$"
            val saldoFinal = if (saldoPrincipal.startsWith("$")) {
                saldoPrincipal
            } else {
                "$ $saldoPrincipal"
            }
            tvMonto.text = saldoFinal
            
            // Actualizar el total - mantener el formato con separadores de miles
            val saldoParaTotal = saldoPrincipal.replace("$ ", "").replace("$", "")
            val totalFormateado = "Total $ $saldoParaTotal"
            tvTotal.text = totalFormateado
            
            // Asegurar que los ",00" estén visibles
            tvMontoComa.visibility = View.VISIBLE
            tvTotalComa.visibility = View.VISIBLE
            
            android.util.Log.d("Saldo", "Saldo actualizado: $nuevoSaldo")
            android.util.Log.d("Saldo", "Total actualizado: $totalFormateado")
            
        } catch (e: Exception) {
            android.util.Log.e("Saldo", "Error actualizando saldo: ${e.message}")
        }
    }
    
    private fun actualizarSaldoConAnimacion(nuevoSaldo: String) {
        try {
            // Extraer la parte principal del saldo (sin ",00")
            val saldoPrincipal = nuevoSaldo.replace(",00", "").replace(",", ".")
            
            // Actualizar el monto principal - verificar si ya tiene "$"
            val saldoFinal = if (saldoPrincipal.startsWith("$")) {
                saldoPrincipal
            } else {
                "$ $saldoPrincipal"
            }
            
            // Actualizar el total - mantener el formato con separadores de miles
            val saldoParaTotal = saldoPrincipal.replace("$ ", "").replace("$", "")
            val totalFormateado = "Total $ $saldoParaTotal"
            
            // Animar la actualización del saldo
            tvMonto.animate()
                .alpha(0.5f)
                .setDuration(150)
                .withEndAction {
                    tvMonto.text = saldoFinal
                    tvMonto.animate()
                        .alpha(1f)
                        .setDuration(150)
                        .start()
                }
                .start()
            
            tvTotal.animate()
                .alpha(0.5f)
                .setDuration(150)
                .withEndAction {
                    tvTotal.text = totalFormateado
                    tvTotal.animate()
                        .alpha(1f)
                        .setDuration(150)
                        .start()
                }
                .start()
            
            // Asegurar que los ",00" estén visibles
            tvMontoComa.visibility = View.VISIBLE
            tvTotalComa.visibility = View.VISIBLE
            
            android.util.Log.d("Saldo", "Saldo actualizado con animación: $nuevoSaldo")
            
        } catch (e: Exception) {
            android.util.Log.e("Saldo", "Error actualizando saldo con animación: ${e.message}")
        }
    }
    
    private fun alternarVisibilidadSaldo(ivOjo: ImageView) {
        if (!saldoOculto) {
            // Ocultar saldo
            saldoOriginal = tvMonto.text.toString()
            totalOriginal = tvTotal.text.toString()
            
            tvMonto.text = "$ *****"
            tvTotal.text = "Total $ *****"
            
            // Ocultar los ",00"
            tvMontoComa.visibility = View.INVISIBLE
            tvTotalComa.visibility = View.INVISIBLE
            
            // Quitar línea diagonal del ojo (cambiar a ojo abierto)
            ivOjo.setImageResource(R.drawable.ic_eye_open)
            
            saldoOculto = true
            android.util.Log.d("Saldo", "Saldo ocultado")
            
        } else {
            // Al volver a mostrar: priorizar saldo visual cambiado por el usuario; si no existe, intentar Firebase; luego fallback
            CoroutineScope(Dispatchers.Main).launch {
                var mostrado = false
                try {
                    // 1) Priorizar saldo_visual (override del usuario)
                    val sp = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    val saldoVisual = sp.getLong("saldo_visual", -1L)
                    if (saldoVisual != -1L) {
                        actualizarSaldoVisual(saldoVisual)
                        mostrado = true
                    } else if (userNumber.isNotEmpty()) {
                        // 2) Si no hay override, intentar Firebase
                        val user = withContext(Dispatchers.IO) { firebaseManager.getUserByNumber(userNumber) }
                        if (user != null) {
                            val saldoFormateado = firebaseManager.formatSaldoForDisplay(user.saldo)
                            actualizarSaldoSeparado(saldoFormateado)
                            mostrado = true
                        }
                    }
                } catch (_: Exception) {}
                // 3) Fallback a último visible/guardado
                if (!mostrado) {
                    if (saldoOriginal.isNotEmpty() && !saldoOriginal.contains("*")) {
                        val saldoFormateado = formatearSaldoParaMostrar(saldoOriginal)
                        actualizarSaldoSeparado(saldoFormateado)
                    } else {
                        val saldoGuardado = obtenerSaldoGuardado()
                        if (saldoGuardado.isNotEmpty()) actualizarSaldoSeparado(saldoGuardado)
                    }
                }
            }
            tvMontoComa.visibility = View.VISIBLE
            tvTotalComa.visibility = View.VISIBLE
            
            // Restaurar línea diagonal del ojo (cambiar a ojo cerrado)
            ivOjo.setImageResource(R.drawable.ic_eye_closed)
            
            saldoOculto = false
            // Ya solicitado arriba; no duplicar
        }
    }
    
    private fun obtenerSaldoGuardado(): String {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            // 1) Preferir override visual si existe
            val saldoVisual = sharedPref.getLong("saldo_visual", -1L)
            if (saldoVisual != -1L) {
                val miles = String.format("%,d", saldoVisual).replace(",", ".")
                return "$ $miles,00"
            }
            // 2) Luego el saldo de usuario persistido
            val saldoRaw = sharedPref.getString("user_saldo", "") ?: ""
            if (saldoRaw.isNotEmpty()) {
                val saldoFormateado = firebaseManager.formatSaldoForDisplay(saldoRaw)
                android.util.Log.d("Saldo", "Saldo guardado obtenido: $saldoFormateado")
                return saldoFormateado
            }
            android.util.Log.d("Saldo", "No hay saldo guardado")
            return "$ 0,00"
        } catch (e: Exception) {
            android.util.Log.e("Saldo", "Error obteniendo saldo guardado: ${e.message}", e)
            return "$ 0,00"
        }
    }
    
    private fun formatearSaldoParaMostrar(saldoTexto: String): String {
        try {
            // Verificar si el texto contiene asteriscos (saldo oculto)
            if (saldoTexto.contains("*")) {
                android.util.Log.d("Saldo", "Saldo oculto detectado, usando saldo guardado")
                return obtenerSaldoGuardado()
            }
            
            // Limpiar el texto de cualquier "$" existente
            val saldoLimpio = saldoTexto.replace("$", "").replace(" ", "")
            
            // Extraer solo los números del saldo
            val numeros = saldoLimpio.replace(Regex("[^0-9]"), "")
            
            if (numeros.isEmpty()) {
                android.util.Log.d("Saldo", "No se encontraron números en el saldo")
                return obtenerSaldoGuardado()
            }
            
            // Convertir a Long y formatear con separadores de miles
            val saldoNumerico = numeros.toLong()
            val saldoFormateado = String.format("%,d", saldoNumerico)
            
            return "$ $saldoFormateado"
            
        } catch (e: Exception) {
            android.util.Log.e("Saldo", "Error formateando saldo: ${e.message}", e)
            return obtenerSaldoGuardado() // Usar saldo guardado en caso de error
        }
    }
    
    private fun mostrarLayoutCancelado() {
        try {
            android.util.Log.d("Cancelado", "Mostrando layout de cancelado")
            
            // Crear layout para el PNG cancelado.png
            val canceladoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
            
            // Crear ImageView para cancelado.png
            val ivCancelado = ImageView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                establecerImagenDesencriptada(this, "cancelado.bin")
            }
            
            // Centrar el ImageView
            val imageContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            
            // Agregar el ImageView al contenedor con layout params centrados
            val imageParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            imageContainer.addView(ivCancelado, imageParams)
            
            canceladoLayout.addView(imageContainer)
            
            // Agregar el layout al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(canceladoLayout)
            
            // Configurar propiedades scaleX y scaleY como el video
            canceladoLayout.alpha = 0f
            canceladoLayout.scaleX = 0f
            canceladoLayout.scaleY = 0f
            
            // Mostrar el layout con animación
            canceladoLayout.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(850)
                .withEndAction {
                    // Esperar 2 segundos y luego ocultar
                    Handler(Looper.getMainLooper()).postDelayed({
                        canceladoLayout.animate()
                            .alpha(0f)
                            .scaleX(0f)
                            .scaleY(0f)
                            .setDuration(750)
                            .withEndAction {
                                rootView.removeView(canceladoLayout)
                                // Cambiar color del layout de enviar plata y mostrar novalidado.png
                                cambiarColorLayoutEnviarPlata()
                            }.start()
                    }, 2000) // Mostrar por 2 segundos
                }.start()
            
        } catch (e: Exception) {
            android.util.Log.e("Cancelado", "Error al mostrar layout de cancelado: ${e.message}")
        }
    }
    
    private fun cambiarColorLayoutEnviarPlata() {
        try {
            android.util.Log.d("Cancelado", "Cambiando color del layout de enviar plata")
            
            // Crear una capa (overlay) con color #d90080
            val overlayView = View(this).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.parseColor("#d90080"))
                alpha = 0.8f // Hacer la capa semi-transparente
            }
            
            // Agregar la capa de color
            layoutEnviarPlata.addView(overlayView)
            
            // Mover el novalidado.png por encima de la capa usando bringToFront
            ivNovalidado.bringToFront()
            ivNovalidado.visibility = View.VISIBLE
            
            // Mostrar y configurar el botón invisible
            btnListoNovalidado.bringToFront()
            btnListoNovalidado.visibility = View.VISIBLE
            
            // Configurar click listener para ocultar la capa y el PNG
            btnListoNovalidado.setOnClickListener {
                ocultarOverlayNovalidado()
            }
            
            android.util.Log.d("Cancelado", "Layout de enviar plata actualizado con capa de color y novalidado.png")
            
        } catch (e: Exception) {
            android.util.Log.e("Cancelado", "Error al cambiar color del layout: ${e.message}")
        }
    }
    
    private fun ocultarOverlayNovalidado() {
        try {
            android.util.Log.d("Cancelado", "Ocultando overlay de novalidado")
            
            // Ocultar el novalidado.png
            ivNovalidado.visibility = View.INVISIBLE
            
            // Ocultar el botón
            btnListoNovalidado.visibility = View.INVISIBLE
            
            // Remover la capa de color (buscar y remover el overlay)
            val childCount = layoutEnviarPlata.childCount
            android.util.Log.d("Cancelado", "Buscando overlay en $childCount hijos")
            
            for (i in childCount - 1 downTo 0) {
                val child = layoutEnviarPlata.getChildAt(i)
                android.util.Log.d("Cancelado", "Hijo $i: ${child.javaClass.simpleName}")
                
                if (child is View && child.background != null) {
                    try {
                        val colorDrawable = child.background as android.graphics.drawable.ColorDrawable
                        val color = colorDrawable.color
                        val targetColor = android.graphics.Color.parseColor("#d90080")
                        
                        android.util.Log.d("Cancelado", "Color del hijo: #${String.format("%06X", color and 0xFFFFFF)}")
                        android.util.Log.d("Cancelado", "Color objetivo: #${String.format("%06X", targetColor and 0xFFFFFF)}")
                        
                        if (color == targetColor) {
                            android.util.Log.d("Cancelado", "¡Overlay encontrado! Removiendo...")
                            layoutEnviarPlata.removeView(child)
                            break
                        }
                    } catch (e: Exception) {
                        android.util.Log.e("Cancelado", "Error al verificar color: ${e.message}")
                    }
                }
            }
            
            android.util.Log.d("Cancelado", "Overlay de novalidado ocultado")
            
        } catch (e: Exception) {
            android.util.Log.e("Cancelado", "Error al ocultar overlay: ${e.message}")
        }
    }
    
    private fun mostrarVideoAIYLayoutBotón7() {
        try {
            android.util.Log.d("Botón7", "Reproduciendo video AI antes de mostrar layout (mismo estilo que 'Confirma')")

            // Crear overlay contenedor estilo 'mostrarVideoAIYComprobante'
            val videoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
                alpha = 0f
                scaleX = 0f
                scaleY = 0f
            }

            // Crear contenedor centrado y VideoView 800x800 como en 'Confirma'
            val videoContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            val videoAI = VideoView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true)
            }
            val videoParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = android.view.Gravity.CENTER }
            videoContainer.addView(videoAI, videoParams)
            videoLayout.addView(videoContainer)

            // Agregar al root
            val rootView = findViewById<ViewGroup>(android.R.id.content)
            rootView.addView(videoLayout)

            // Cargar video 'ai'
            VideoLoader.cargarVideoDesdeAssets(this, videoAI, "ai")

            // Listener de error: si falla, mostrar directamente el layout
            videoAI.setOnErrorListener { _, what, extra ->
                android.util.Log.e("Botón7", "Error reproduciendo video: what=$what, extra=$extra")
                // Remover overlay y continuar
                rootView.removeView(videoLayout)
                mostrarLayoutBotón7()
                true
            }

            videoAI.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false
                try { mediaPlayer.setVideoScalingMode(android.media.MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT) } catch (_: Exception) {}
                mediaPlayer.setVolume(0f, 0f)

                // Animación de entrada (igual que 'Confirma')
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        // Esperar duración y salir
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(750)
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    // Pequeño delay como en 'Confirma'
                                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                        mostrarLayoutBotón7()
                                    }, 50)
                                }.start()
                        }, 2000)
                    }.start()

                mediaPlayer.start()
            }

        } catch (e: Exception) {
            android.util.Log.e("Botón7", "Error al reproducir video: ${e.message}")
            // Si hay error, mostrar directamente el layout
            mostrarLayoutBotón7()
        }
    }
    
    private fun mostrarLayoutBotón7() {
        try {
            android.util.Log.d("Botón7", "Mostrando layout del botón 7")
            
            // Mostrar el layout
            layoutBotón7.visibility = View.VISIBLE
            layoutBotón7.translationX = layoutBotón7.width.toFloat()
            layoutBotón7.animate()
                .translationX(0f)
                .setDuration(450)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .start()
            
            // Configurar click listener para la flecha de regreso
            ivFlechaRegresoBotón7.setOnClickListener {
                // Limpiar los campos antes de ocultar el layout
                etCampo1Botón7.text = ""
                etCampo2Botón7.text.clear()
                etCampo3Botón7.text.clear()
                ocultarLayoutBotón7()
            }
            
            // Configurar click listener para el campo tipo de cuenta
            etCampo1Botón7.setOnClickListener {
                mostrarModalTipoCuenta()
            }
            
            // Configurar formateo de números para el campo ¿Cuanto?
            etCampo3Botón7.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    val str = s.toString().replace("[^0-9]".toRegex(), "")
                    if (str.isNotEmpty()) {
                        val number = str.toLong()
                        val formatted = formatCurrency(number)
                        if (formatted != s.toString()) {
                            etCampo3Botón7.removeTextChangedListener(this)
                            etCampo3Botón7.setText(formatted)
                            etCampo3Botón7.setSelection(formatted.length)
                            etCampo3Botón7.addTextChangedListener(this)
                        }
                    }
                    
                    // Actualizar floating label
                    val hasContent = str.isNotEmpty()
                    val hasFocus = etCampo3Botón7.hasFocus()
                    actualizarFloatingLabelCuantoBancolombia(hasFocus, hasContent)
                    
                    // Verificar si todos los campos están llenos
                    verificarCamposBancolombia()
                }
            })
            
            // Configurar OnFocusChangeListener para el campo ¿Cuanto?
            etCampo3Botón7.setOnFocusChangeListener { _, hasFocus ->
                val hasContent = etCampo3Botón7.text.toString().replace("[^0-9]".toRegex(), "").isNotEmpty()
                actualizarFloatingLabelCuantoBancolombia(hasFocus, hasContent)
            }
            
            // Configurar TextWatcher para el campo número de cuenta
            etCampo2Botón7.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    // Forzar solo dígitos y cortar a 11
                    val soloDigitos = s?.toString()?.replace("[^0-9]".toRegex(), "") ?: ""
                    val limitado = if (soloDigitos.length > 11) soloDigitos.substring(0, 11) else soloDigitos
                    if (limitado != s.toString()) {
                        etCampo2Botón7.removeTextChangedListener(this)
                        etCampo2Botón7.setText(limitado)
                        etCampo2Botón7.setSelection(limitado.length)
                        etCampo2Botón7.addTextChangedListener(this)
                    }
                    // Actualizar floating label
                    val hasContent = s.toString().isNotEmpty()
                    val hasFocus = etCampo2Botón7.hasFocus()
                    actualizarFloatingLabelCuentaBancolombia(hasFocus, hasContent)
                    
                    // Verificar si todos los campos están llenos
                    verificarCamposBancolombia()
                }
            })
            
            // Configurar OnFocusChangeListener para el campo número de cuenta
            etCampo2Botón7.setOnFocusChangeListener { _, hasFocus ->
                val hasContent = etCampo2Botón7.text.toString().isNotEmpty()
                actualizarFloatingLabelCuentaBancolombia(hasFocus, hasContent)
            }
            
            // Configurar click listener para el botón confirmar
            btnConfirmarBotón7.setOnClickListener {
                // Verificar si hay datos introducidos
                val tipoCuenta = etCampo1Botón7.text.toString().trim()
                val numeroCuenta = etCampo2Botón7.text.toString().trim()
                val cantidad = etCampo3Botón7.text.toString().trim()
                
                if (tipoCuenta.isNotEmpty() && numeroCuenta.isNotEmpty() && cantidad.isNotEmpty()) {
                    if (excedeMontoMaximo(cantidad)) {
                        mostrarToastMontoMaximo()
                        return@setOnClickListener
                    }
                    // Hay datos: reproducir video y luego abrir verificación
                    reproducirVideoAI()
                } else {
                    // No hay datos: mostrar mensaje
                    Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            
        } catch (e: Exception) {
            android.util.Log.e("Botón7", "Error al mostrar layout: ${e.message}")
        }
    }
    
    private fun ocultarLayoutBotón7() {
        try {
            android.util.Log.d("Botón7", "Ocultando layout del botón 7")
            
            layoutBotón7.animate()
                .translationX(layoutBotón7.width.toFloat())
                .setDuration(450)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .withEndAction {
                    layoutBotón7.visibility = View.INVISIBLE
                    layoutBotón7.translationX = 0f
                }.start()
            
        } catch (e: Exception) {
            android.util.Log.e("Botón7", "Error al ocultar layout: ${e.message}")
        }
    }
    
    private fun mostrarModalTipoCuenta() {
        try {
            android.util.Log.d("ModalTipoCuenta", "Mostrando modal de tipo de cuenta")
            
            // Mostrar el modal
            modalTipoCuenta.visibility = View.VISIBLE
            modalTipoCuenta.alpha = 0f
            
            // Configurar posición inicial del contenedor (fuera de la pantalla)
            contenedorModalTipoCuenta.translationY = contenedorModalTipoCuenta.height.toFloat()
            
            // Animar el fondo y el contenedor
            modalTipoCuenta.animate()
                .alpha(1f)
                .setDuration(450)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .start()
            
            contenedorModalTipoCuenta.animate()
                .translationY(0f)
                .setDuration(450)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .start()
            
            // Configurar click listeners para las opciones
            tvCorriente.setOnClickListener {
                seleccionarTipoCuenta("Corriente")
            }
            
            tvAhorros.setOnClickListener {
                seleccionarTipoCuenta("Ahorros")
            }
            
            // Configurar click listener para cerrar el modal al tocar el fondo
            modalTipoCuenta.setOnClickListener {
                ocultarModalTipoCuenta()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("ModalTipoCuenta", "Error al mostrar modal: ${e.message}")
        }
    }
    
    private fun ocultarModalTipoCuenta() {
        try {
            android.util.Log.d("ModalTipoCuenta", "Ocultando modal de tipo de cuenta")
            
            // Animar el contenedor hacia abajo
            contenedorModalTipoCuenta.animate()
                .translationY(contenedorModalTipoCuenta.height.toFloat())
                .setDuration(450)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .start()
            
            // Animar el fondo
            modalTipoCuenta.animate()
                .alpha(0f)
                .setDuration(450)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .withEndAction {
                    modalTipoCuenta.visibility = View.INVISIBLE
                    contenedorModalTipoCuenta.translationY = 0f
                }.start()
            
        } catch (e: Exception) {
            android.util.Log.e("ModalTipoCuenta", "Error al ocultar modal: ${e.message}")
        }
    }
    
    private fun seleccionarTipoCuenta(tipo: String) {
        try {
            android.util.Log.d("ModalTipoCuenta", "Seleccionado tipo de cuenta: $tipo")
            
            // Actualizar el texto del campo
            etCampo1Botón7.text = tipo
            
            // Ocultar el modal
            ocultarModalTipoCuenta()
            
            // Verificar si todos los campos están llenos
            verificarCamposBancolombia()
            
        } catch (e: Exception) {
            android.util.Log.e("ModalTipoCuenta", "Error al seleccionar tipo de cuenta: ${e.message}")
        }
    }
    
    private fun mostrarModalBotón8() {
        try {
            android.util.Log.d("ModalBotón8", "Mostrando modal del botón 8")
            
            // Asegurar que el modal esté visible y en primer plano
            modalBotón8.visibility = View.VISIBLE
            modalBotón8.bringToFront()
            
            // Configurar posición inicial del contenedor (fuera de la pantalla a la derecha)
            contenedorModalBotón8.translationX = 1000f
            
            // Configurar alpha inicial
            modalBotón8.alpha = 0f
            contenedorModalBotón8.alpha = 1f
            
            // Animación de entrada del modal (fondo)
            modalBotón8.animate()
                .alpha(1f)
                .setDuration(450)
                .start()
            
            // Animación de entrada del contenedor de derecha a izquierda
            contenedorModalBotón8.animate()
                .translationX(0f)
                .setDuration(550)
                .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                .start()
            
            // Configurar click listener para la flecha de regreso
            ivFlechaRegresoModal8.setOnClickListener {
                ocultarModalBotón8()
                // Mostrar el modal anterior después de ocultar el modal del botón 8
                mostrarModal()
            }
            
            // Configurar click listener para el botón Listo
            btnListoModal8.setOnClickListener {
                ocultarModalBotón8()
                // Mostrar el layout Transfiya
                mostrarLayoutTransfiya()
            }
            

            
            android.util.Log.d("ModalBotón8", "Modal configurado correctamente")
            
        } catch (e: Exception) {
            android.util.Log.e("ModalBotón8", "Error al mostrar modal: ${e.message}", e)
        }
    }
    
    private fun ocultarModalBotón8() {
        try {
            android.util.Log.d("ModalBotón8", "Ocultando modal del botón 8")
            
            // Animación de salida del modal
            modalBotón8.animate()
                .alpha(0f)
                .setDuration(450)
                .withEndAction {
                    modalBotón8.visibility = View.INVISIBLE
                }
                .start()
            
            // Animación de salida del contenedor de izquierda a derecha
            contenedorModalBotón8.animate()
                .translationX(1000f)
                .setDuration(550)
                .setInterpolator(android.view.animation.AccelerateInterpolator())
                .start()
            
        } catch (e: Exception) {
            android.util.Log.e("ModalBotón8", "Error al ocultar modal: ${e.message}")
        }
    }
    private fun mostrarLayoutTransfiya() {
        try {
            android.util.Log.d("LayoutTransfiya", "Mostrando layout Transfiya sin transición")
            
            // Mostrar el layout directamente sin animación
            layoutTransfiya.visibility = View.VISIBLE
            layoutTransfiya.translationX = 0f
            layoutTransfiya.bringToFront()
            
            // Rehabilitar el botón Listo para la próxima vez
            btnListoCapaTransfiya.isEnabled = true
            btnListoCapaTransfiya.visibility = View.VISIBLE
            
            // Rehabilitar el botón Sigue para la próxima vez
            btnSigueTransfiya.isEnabled = true
            btnSigueTransfiya.alpha = 1f
            
            // Configurar click listener para la flecha de regreso
            ivFlechaRegresoTransfiya.setOnClickListener {
                // Limpiar campos antes de ocultar
                limpiarCamposTransfiya()
                ocultarLayoutTransfiya()
            }
            
            // Configurar TextWatchers para validar campos
            etCelTransfiya.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    try {
                        formatearCelular(s)
                        
                        // Actualizar floating label
                        val hasContent = s.toString().replace(" ", "").isNotEmpty()
                        val hasFocus = etCelTransfiya.hasFocus()
                        actualizarFloatingLabelCelTransfiya(hasFocus, hasContent)
                        
                        validarCamposTransfiya()
                    } catch (e: Exception) {
                        android.util.Log.e("TextWatcher", "Error en TextWatcher de celular: ${e.message}", e)
                    }
                }
            })
            
            // Configurar OnFocusChangeListener para el campo Cel de Transfiya
            etCelTransfiya.setOnFocusChangeListener { _, hasFocus ->
                val hasContent = etCelTransfiya.text.toString().replace(" ", "").isNotEmpty()
                actualizarFloatingLabelCelTransfiya(hasFocus, hasContent)
            }
            
            // Formatear automáticamente el número en el campo de Transfiya
            etCuantoTransfiya.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    val str = s.toString().replace("$", "").replace(".", "").replace(" ", "")
                    if (str.isNotEmpty()) {
                        val formatted = formatCurrency(str)
                        if (formatted != s.toString()) {
                            etCuantoTransfiya.removeTextChangedListener(this)
                            etCuantoTransfiya.setText(formatted)
                            etCuantoTransfiya.setSelection(formatted.length)
                            etCuantoTransfiya.addTextChangedListener(this)
                        }
                    }
                    
                    // Actualizar floating label
                    val hasContent = str.isNotEmpty()
                    val hasFocus = etCuantoTransfiya.hasFocus()
                    actualizarFloatingLabelCuantoTransfiya(hasFocus, hasContent)
                    
                    // Verificar campos completos después de cada cambio
                    validarCamposTransfiya()
                }
            })
            
            // Configurar OnFocusChangeListener para el campo Cuanto de Transfiya
            etCuantoTransfiya.setOnFocusChangeListener { _, hasFocus ->
                val hasContent = etCuantoTransfiya.text.toString().replace("$", "").replace(".", "").replace(" ", "").isNotEmpty()
                actualizarFloatingLabelCuantoTransfiya(hasFocus, hasContent)
            }
            
            // Deshabilitar campo mensaje (mantener hint visible)
            etMensajeTransfiya.isFocusable = false
            etMensajeTransfiya.isClickable = false
            
            // Configurar click listener para el botón Sigue
            btnSigueTransfiya.setOnClickListener {
                try {
                    android.util.Log.d("Transfiya", "Botón Sigue presionado")
                    
                    // Ocultar texto del botón y mostrar ProgressBar
                    btnSigueTransfiya.text = ""
                    progressBarSigueTransfiya.visibility = View.VISIBLE
                    
                    // Poner el botón y ProgressBar oscuros
                    btnSigueTransfiya.alpha = 0.5f
                    progressBarSigueTransfiya.alpha = 0.5f
                    
                    // Después de 2 segundos, mostrar el modal de confirmación
                    val handler = android.os.Handler(android.os.Looper.getMainLooper())
                    handler.postDelayed({
                        try {
                            // Restaurar el botón
                            btnSigueTransfiya.text = "Sigue"
                            progressBarSigueTransfiya.visibility = View.GONE
                            btnSigueTransfiya.alpha = 1f
                            progressBarSigueTransfiya.alpha = 1f
                            
                            // Mostrar modal de confirmación
                            mostrarModalConfirmacionTransfiya()
                            
                        } catch (e: Exception) {
                            android.util.Log.e("Transfiya", "Error después de la carga: ${e.message}", e)
                            // Restaurar estado del botón en caso de error
                            btnSigueTransfiya.text = "Sigue"
                            progressBarSigueTransfiya.visibility = View.GONE
                            btnSigueTransfiya.alpha = 1f
                            progressBarSigueTransfiya.alpha = 1f
                        }
                    }, 2000) // 2 segundos
                    
                } catch (e: Exception) {
                    android.util.Log.e("Transfiya", "Error en botón Sigue: ${e.message}", e)
                    // Restaurar estado del botón en caso de error
                    btnSigueTransfiya.text = "Sigue"
                    progressBarSigueTransfiya.visibility = View.GONE
                    btnSigueTransfiya.alpha = 1f
                    progressBarSigueTransfiya.alpha = 1f
                }
            }
            
        } catch (e: Exception) {
            android.util.Log.e("LayoutTransfiya", "Error al mostrar layout: ${e.message}", e)
        }
    }
    
    // Función para limpiar campos del layout de Transfiya
    private fun limpiarCamposTransfiya() {
        try {
            android.util.Log.d("Transfiya", "Limpiando campos del layout de Transfiya")
            
            // Limpiar campo de celular
            etCelTransfiya.setText("")
            
            // Limpiar campo de cantidad
            etCuantoTransfiya.setText("")
            
            // Limpiar campo de mensaje (si está habilitado)
            etMensajeTransfiya.setText("")
            
            // Restaurar estado del botón Sigue
            btnSigueTransfiya.text = "Sigue"
            btnSigueTransfiya.alpha = 1f
            progressBarSigueTransfiya.visibility = View.GONE
            progressBarSigueTransfiya.alpha = 1f
            
            // Habilitar botón Sigue para la próxima vez
            btnSigueTransfiya.isEnabled = true
            btnSigueTransfiya.alpha = 1f
            
            android.util.Log.d("Transfiya", "Campos del layout de Transfiya limpiados exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("Transfiya", "Error limpiando campos: ${e.message}", e)
        }
    }
    
    private fun ocultarLayoutTransfiya() {
        try {
            android.util.Log.d("LayoutTransfiya", "Ocultando layout Transfiya sin transición")
            
            // Ocultar el layout directamente sin animación
                    layoutTransfiya.visibility = View.INVISIBLE
                    layoutTransfiya.translationX = 0f
            
        } catch (e: Exception) {
            android.util.Log.e("LayoutTransfiya", "Error al ocultar layout: ${e.message}", e)
        }
    }
    
    private fun validarCamposTransfiya() {
        val cel = etCelTransfiya.text.toString().trim()
        val cuanto = etCuantoTransfiya.text.toString().trim()
        
        val todosLlenos = cel.isNotEmpty() && cuanto.isNotEmpty()
        
        if (todosLlenos) {
            btnSigueTransfiya.alpha = 1.0f
            btnSigueTransfiya.isClickable = true
            btnSigueTransfiya.isFocusable = true
        } else {
            btnSigueTransfiya.alpha = 0.5f
            btnSigueTransfiya.isClickable = false
            btnSigueTransfiya.isFocusable = false
        }
    }
    
    private fun formatearCelular(s: android.text.Editable?) {
        try {
            if (s == null) return
            
            val currentText = s.toString()
            val cleanText = currentText.replace(" ", "")
            
            // Solo permitir números
            if (!cleanText.all { it.isDigit() }) {
                return
            }
            
            // Limitar a 10 dígitos
            val limitedText = if (cleanText.length > 10) cleanText.substring(0, 10) else cleanText
            
            if (limitedText.isEmpty()) {
                s.clear()
                return
            }
            
            val formatted = when {
                limitedText.length <= 3 -> limitedText
                limitedText.length <= 6 -> "${limitedText.substring(0, 3)} ${limitedText.substring(3)}"
                else -> "${limitedText.substring(0, 3)} ${limitedText.substring(3, 6)} ${limitedText.substring(6)}"
            }
            
            // Evitar bucle infinito
            if (formatted != currentText) {
                s.replace(0, s.length, formatted)
            }
        } catch (e: Exception) {
            android.util.Log.e("Formateo", "Error formateando celular: ${e.message}", e)
        }
    }
    

    
    private fun formatearCantidadModal(cantidad: String): String {
        try {
            // Limpiar el texto de formato
            val cleanText = cantidad.replace("$", "").replace(".", "").replace(" ", "")
            
            if (cleanText.isEmpty()) {
                return cantidad
            }
            
            val number = cleanText.toLongOrNull()
            if (number != null && number > 0) {
                // Formato para modal: $ 50.000,00
                val formatted = String.format("%,d", number).replace(",", ".")
                return "$ ${formatted},00"
            }
            
            return cantidad
        } catch (e: Exception) {
            android.util.Log.e("Formateo", "Error formateando cantidad modal: ${e.message}", e)
            return cantidad
        }
    }
    
    private fun mostrarModalConfirmacionTransfiya() {
        try {
            android.util.Log.d("ModalTransfiya", "Mostrando modal de confirmación")
            
            // Verificar que las variables estén inicializadas
            if (!::modalScrollViewConfirmacionTransfiya.isInitialized) {
                android.util.Log.e("ModalTransfiya", "Modal no inicializado")
                return
            }
            
            if (!::modalBackgroundOverlayTransfiya.isInitialized) {
                android.util.Log.e("ModalTransfiya", "Fondo oscuro no inicializado")
                return
            }
            
            // Obtener datos de los campos
            val celular = etCelTransfiya.text.toString().trim()
            val cuanto = etCuantoTransfiya.text.toString().trim()
            
            android.util.Log.d("ModalTransfiya", "Celular: $celular, Cuanto: $cuanto")
            
            // Actualizar textos del modal
            if (::tvNumeroCelularConfirmacionTransfiya.isInitialized) {
                tvNumeroCelularConfirmacionTransfiya.text = celular
            }
            if (::tvCantidadConfirmacionTransfiya.isInitialized) {
                // Formatear cantidad con ,00 para el modal
                val cantidadFormateada = formatearCantidadModal(cuanto)
                tvCantidadConfirmacionTransfiya.text = cantidadFormateada
            }
            
            // Mostrar fondo oscuro y modal con animación
            modalBackgroundOverlayTransfiya.alpha = 0f
            modalBackgroundOverlayTransfiya.visibility = View.VISIBLE
            modalBackgroundOverlayTransfiya.animate()
                .alpha(1f)
                .setDuration(350)
                .start()
            
            // Esperar 0.3 segundos antes de mostrar el modal
            Handler(Looper.getMainLooper()).postDelayed({
            modalScrollViewConfirmacionTransfiya.alpha = 0f
            modalScrollViewConfirmacionTransfiya.visibility = View.VISIBLE
                modalScrollViewConfirmacionTransfiya.translationY = 4000f // Bajado de 3000f a 4000f
                modalScrollViewConfirmacionTransfiya.elevation = 4000f // Bajado de 3000f a 4000f
            modalScrollViewConfirmacionTransfiya.bringToFront()
            
            modalScrollViewConfirmacionTransfiya.animate()
                .alpha(1f)
                .translationY(200f) // Bajado de 0f a 200f (posición final más baja)
                    .setDuration(550)
                .setListener(null)
            }, 300) // 0.3 segundos = 300 milisegundos
            
            android.util.Log.d("ModalTransfiya", "Modal mostrado correctamente")
            
            // Configurar click listeners del modal
            configurarClickListenersModalTransfiya()
            
        } catch (e: Exception) {
            android.util.Log.e("ModalTransfiya", "Error mostrando modal: ${e.message}", e)
            e.printStackTrace()
        }
    }
    
    private fun configurarClickListenersModalTransfiya() {
        // Botón Confirma
        btnConfirmaTransfiya.setOnClickListener {
            // Bloquear por monto en el modal de Transfiya
            val cuanto = etCuantoTransfiya.text.toString()
            if (excedeMontoMaximo(cuanto)) {
                ocultarModalConfirmacionTransfiya()
                mostrarToastMontoMaximo()
                return@setOnClickListener
            }
            try {
                android.util.Log.d("ModalTransfiya", "Botón Confirma presionado")
                vieneDeTransfiya = true
                
                // Ocultar modales al dar confirmar
                ocultarModalConfirmacionTransfiya()
                
                // Validar datos antes de continuar
                validarYProcesarTransfiya()
            } catch (e: Exception) {
                android.util.Log.e("ModalTransfiya", "Error en botón Confirma: ${e.message}", e)
            }
        }
        

        
        // Botón X para cerrar
        tvModalCloseConfirmacionTransfiya.setOnClickListener {
            try {
                android.util.Log.d("ModalTransfiya", "Botón X presionado")
                ocultarModalConfirmacionTransfiya()
            } catch (e: Exception) {
                android.util.Log.e("ModalTransfiya", "Error en botón X: ${e.message}", e)
            }
        }
    }
    
    private fun ocultarModalConfirmacionTransfiya() {
        try {
            android.util.Log.d("ModalTransfiya", "Ocultando modal de confirmación")
            
            // Ocultar modal con animación
            modalScrollViewConfirmacionTransfiya.animate()
                .alpha(0f)
                .translationY(2500f) // Ajustado para salida más baja
                .setDuration(550)
                .withEndAction {
                    modalScrollViewConfirmacionTransfiya.visibility = View.INVISIBLE
                    modalScrollViewConfirmacionTransfiya.alpha = 1f
                    modalScrollViewConfirmacionTransfiya.translationY = 200f // Mantener posición final baja
                }
                .start()
            
            // Ocultar fondo oscuro con animación
            modalBackgroundOverlayTransfiya.animate()
                .alpha(0f)
                .setDuration(350)
                .withEndAction {
                    modalBackgroundOverlayTransfiya.visibility = View.INVISIBLE
                    modalBackgroundOverlayTransfiya.alpha = 1f
                }
                .start()
                
        } catch (e: Exception) {
            android.util.Log.e("ModalTransfiya", "Error ocultando modal: ${e.message}", e)
        }
    }
    
    private fun validarYProcesarTransfiya() {
        try {
            android.util.Log.d("Transfiya", "Iniciando validaciones de Transfiya")
            
            // Obtener datos del formulario de Transfiya
            val numeroTransfiya = etCelTransfiya.text.toString().trim()
            val cantidad = etCuantoTransfiya.text.toString().trim()
            
            android.util.Log.d("Transfiya", "=== VALIDANDO DATOS ===")
            android.util.Log.d("Transfiya", "Número Transfiya: '$numeroTransfiya'")
            android.util.Log.d("Transfiya", "Cantidad: '$cantidad'")
            
            // Validación 1: Número debe tener 10 dígitos
            if (numeroTransfiya.replace("[^0-9]".toRegex(), "").length != 10) {
                android.util.Log.d("Transfiya", "Error: Número Transfiya no tiene 10 dígitos")
                mostrarLayoutCancelado()
                // Mostrar Toast después del layout cancelado
                Handler(Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this@InicioActivity, "El número de Transfiya debe tener 10 dígitos", Toast.LENGTH_LONG).show()
                }, 2000) // 2 segundos después
                return
            }
            
            // Validación 2: Límite de monto máximo (en verificación Bancolombia)
            if (excedeMontoMaximo(cantidad)) {
                mostrarLayoutCancelado()
                Handler(Looper.getMainLooper()).postDelayed({
                    mostrarToastMontoMaximo()
                }, 800)
                return
            }

            // Validación 3: Saldo suficiente
            CoroutineScope(Dispatchers.Main).launch {
                val saldoSuficiente = withContext(Dispatchers.IO) {
                    validarSaldoSuficienteAsync(cantidad)
                }
                
                if (!saldoSuficiente) {
                    android.util.Log.d("Transfiya", "Error: Saldo insuficiente")
                    mostrarLayoutCancelado()
                    // Mostrar Toast después del layout cancelado
                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(this@InicioActivity, "Saldo insuficiente para realizar la transacción", Toast.LENGTH_LONG).show()
                    }, 2000) // 2 segundos después
                    return@launch
                }
                
                // Si todas las validaciones pasan, continuar con el proceso
                android.util.Log.d("Transfiya", "Todas las validaciones pasaron, continuando...")
                mostrarVideoAIYComprobanteTransfiya()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("Transfiya", "Error en validaciones: ${e.message}", e)
            mostrarLayoutCancelado()
            // Mostrar Toast después del layout cancelado
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this@InicioActivity, "Error al procesar la transacción", Toast.LENGTH_LONG).show()
            }, 2000) // 2 segundos después
        }
    }
    
    // Función para mostrar video AI en Transfiya
    private fun mostrarVideoAIYComprobanteTransfiya() {
        try {
            android.util.Log.d("VideoAI", "Iniciando reproducción de video AI para Transfiya")
            
            // Los modales ya se ocultaron al presionar "Confirma"
            
            // Crear layout para el video
            val videoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
            
            // Crear VideoView con las mismas propiedades que otros videos
            val videoAI = VideoView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true) // Necesario para que el video se muestre correctamente
            }
            
            // Centrar el video
            val videoContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            
            // Agregar el VideoView al contenedor con layout params centrados
            val videoParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            videoContainer.addView(videoAI, videoParams)
            
            videoLayout.addView(videoContainer)
            
            // Agregar el layout al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(videoLayout)
            
            // Configurar el video
            VideoLoader.cargarVideoDesdeAssets(this, videoAI, "ai")
            
            
            // Configurar propiedades scaleX y scaleY como otros videos
            videoLayout.alpha = 0f
            videoLayout.scaleX = 0f
            videoLayout.scaleY = 0f
            
            // Agregar listener de error
            videoAI.setOnErrorListener { mp, what, extra ->
                android.util.Log.e("VideoAI", "Error reproduciendo video: what=$what, extra=$extra")
                // Continuar sin video si hay error
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        Handler(Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(450)
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    // Mostrar capa después del video
                                    mostrarCapaTransfiya()
                                }.start()
                        }, 200) // Esperar 0.2 segundos como otros videos
                    }.start()
                true // Manejar el error
            }
            
            videoAI.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false // No loop para AI
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                mediaPlayer.setVolume(0f, 0f) // Sin audio
                
                // Iniciar el video
                videoAI.start()
                
                // Mostrar el layout con animación
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        // Esperar a que termine el video y luego mostrar la capa de Transfiya directamente
                        Handler(Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(750)
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    // Pequeña espera y mostrar capa
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        mostrarCapaTransfiya()
                                    }, 50)
                                }.start()
                        }, 2000)
                    }.start()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("VideoAI", "Error al mostrar video AI", e)
            // Si hay error, mostrar capa directamente
            mostrarCapaTransfiya()
        }
    }
    
    // Función para mostrar capa con PNG de Transfiya
    private fun mostrarCapaTransfiya() {
        try {
            android.util.Log.d("Transfiya", "Mostrando capa con PNG de Transfiya")
            
            // Mostrar el layout de la capa
            capaTransfiyaLayout.visibility = View.VISIBLE
            capaTransfiyaLayout.bringToFront()
            
            // Configurar animación de entrada
            capaTransfiyaLayout.alpha = 0f
            capaTransfiyaLayout.animate()
                .alpha(1f)
                .setDuration(650)
                .withEndAction {
                    // Configurar click listener del botón Listo
                    configurarClickListenerBotónListoTransfiya()
                }
                .start()
                
        } catch (e: Exception) {
            android.util.Log.e("Transfiya", "Error mostrando capa: ${e.message}", e)
        }
    }
    
    // Función para configurar click listener del botón Listo en la capa de Transfiya
    private fun configurarClickListenerBotónListoTransfiya() {
        btnListoCapaTransfiya.setOnClickListener {
            // Deshabilitar el botón para evitar múltiples clics
            btnListoCapaTransfiya.isEnabled = false
            btnListoCapaTransfiya.visibility = View.INVISIBLE
            
            try {
                android.util.Log.d("Transfiya", "Botón Listo presionado en capa")
                
                // Obtener datos de Transfiya
                val celular = etCelTransfiya.text.toString().trim()
                val cuanto = etCuantoTransfiya.text.toString().trim()
                
                if (celular.isNotEmpty() && cuanto.isNotEmpty()) {
                    // Limpiar espacios del número para Transfiya
                    val cuantoLimpio = cuanto.replace(" ", "")
                    val celularLimpio = celular.replace(" ", "")
                    
                    // Para Transfiya, usar el número de celular como identificador en lugar del nombre del usuario
                    val nombreParaTransfiya = "Usuario $celularLimpio"
                    
                    // Guardar datos para el comprobante
                    datosTransfiyaCelular = celularLimpio
                    datosTransfiyaCantidad = cuantoLimpio
                    datosTransfiyaNombre = nombreParaTransfiya
                    
                    // Generar comprobante silenciosamente (no mostrarlo)
                    try {
                        android.util.Log.d("Transfiya", "Generando comprobante de Transfiya silenciosamente...")
                        val comprobanteData = generarImagenComprobanteTransfiya(nombreParaTransfiya, celularLimpio, cuantoLimpio)
                        comprobanteTransfiyaBitmap = comprobanteData.bitmap
                        datosTransfiyaReferencia = comprobanteData.referencia
                        datosTransfiyaFecha = comprobanteData.fechaFormateada
                        android.util.Log.d("Transfiya", "Comprobante de Transfiya generado exitosamente")
                    } catch (e: Exception) {
                        android.util.Log.e("Transfiya", "Error generando comprobante de Transfiya: ${e.message}", e)
                        comprobanteTransfiyaBitmap = null
                        datosTransfiyaReferencia = ""
                        datosTransfiyaFecha = ""
                    }
                    
                    // Agregar envío a movimientos
                    agregarEnvioMovimientosDesdeTransfiya(celularLimpio, cuantoLimpio)
                    
                    // Restar la cantidad del saldo para Transfiya
                    restarDelSaldo(cuantoLimpio)
                    android.util.Log.d("Transfiya", "Saldo descontado: $cuantoLimpio")
                }
                
                // Limpiar campos del layout de Transfiya
                limpiarCamposTransfiya()
                
                // Ocultar la capa
                capaTransfiyaLayout.visibility = View.INVISIBLE
                capaTransfiyaLayout.alpha = 1f
                
                // Reproducir video mov.mp4 antes de mostrar movimientos
                mostrarVideoMovimientos()
                
            } catch (e: Exception) {
                android.util.Log.e("Transfiya", "Error en botón Listo: ${e.message}", e)
            }
        }
    }
    
    // Función para mostrar layout de movimientos desde Transfiya
    private fun mostrarLayoutMovimientosDesdeTransfiya() {
        try {
            android.util.Log.d("Movimientos", "Mostrando layout de movimientos desde Transfiya")
            
            // El layout de Transfiya ya se ocultó antes del video, no ocultarlo nuevamente
            
            // Mostrar el layout de movimientos directamente sin animación
            val layoutMovimientos = findViewById<RelativeLayout>(R.id.layoutMovimientos)
            layoutMovimientos.visibility = View.VISIBLE
            layoutMovimientos.translationX = 0f // Asegurar que esté en posición normal
            
            // Configurar visibilidad de los botones del layout de movimientos
            ivMovementsMovimientos.visibility = View.VISIBLE
            ivServiciosMovimientos.visibility = View.INVISIBLE
            ivHomeMovimientos.visibility = View.INVISIBLE
            
            // Cargar envíos con un pequeño delay para asegurar que SharedPreferences esté actualizado
            Handler(Looper.getMainLooper()).postDelayed({
                android.util.Log.d("Movimientos", "Cargando envíos después del delay...")
                cargarEnviosDesdeSharedPreferences()
            }, 100) // 100ms delay
        
        // Configurar scroll listener para mostrar "Cargar más" - DESHABILITADO
        // configurarScrollListener()
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error mostrando layout: ${e.message}", e)
        }
    }
    
    private fun reproducirVideoAI() {
        try {
            
            // Crear layout contenedor con fondo blanco
            val videoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
            
            // Crear VideoView con las mismas propiedades que otros videos
            val videoAI = VideoView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true) // Necesario para que el video se muestre correctamente
            }
            
            // Centrar el video
            val videoContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            
            // Agregar el VideoView al contenedor con layout params centrados
            val videoParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            videoContainer.addView(videoAI, videoParams)
            videoLayout.addView(videoContainer)
            
            // Agregar el layout al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(videoLayout)
            
            // Configurar el video
            VideoLoader.cargarVideoDesdeAssets(this, videoAI, "ai")
            
            
            // Configurar propiedades scaleX y scaleY
            videoLayout.alpha = 0f
            videoLayout.scaleX = 0f
            videoLayout.scaleY = 0f
            
            videoAI.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                mediaPlayer.setVolume(0f, 0f) // Sin audio
                
                // Iniciar el video
                videoAI.start()
                
                // Mostrar el layout con animación
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .setInterpolator(android.view.animation.AccelerateDecelerateInterpolator())
                    .withEndAction {
                        // Esperar a que termine el video
                        Handler(Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(300)
                                .setInterpolator(android.view.animation.AccelerateInterpolator())
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    // Mostrar el layout de verificación después del video
                                    abrirLayoutVerificacionBancolombia()
                                }.start()
                        }, 2000) // Duración del video
                    }.start()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("VideoAI", "Error reproduciendo video AI: ${e.message}", e)
        }
    }
    
    private fun abrirLayoutVerificacionBancolombia() {
        try {
            
            // Obtener los datos de los campos
            val tipoCuenta = etCampo1Botón7.text.toString().trim()
            val numeroCuenta = etCampo2Botón7.text.toString().trim()
            val cantidad = etCampo3Botón7.text.toString().trim()
            
            // Buscar nombre de Bancolombia por número de cuenta
            val nombreBancolombia = buscarNombreBancolombia(numeroCuenta)
            val nombreFormateado = if (nombreBancolombia.isNotEmpty()) {
                formatearNombreBancolombia(nombreBancolombia)
            } else {
                "Sin nombre"
            }
            
            // Actualizar los textos en el layout de verificación
            tvCantidadVerificacion.text = cantidad
            tvBancoValorVerificacion.text = "Bancolombia"
            tvTipoCuentaValorVerificacion.text = tipoCuenta
            tvNumeroCuentaValorVerificacion.text = numeroCuenta
            tvDeDondeSaldraValorVerificacion.text = "Disponible"
            tvNombreBancolombia.text = nombreFormateado
            
            // Mostrar el layout de verificación sin transición
            layoutVerificacionBancolombia.visibility = View.VISIBLE
            
            // Asegurar que el botón enviar esté habilitado
            btnEnviarVerificacion.isEnabled = true
            btnEnviarVerificacion.alpha = 1f
            
            // Configurar click listener para la flecha de regreso
            ivFlechaRegresoVerificacion.setOnClickListener {
                cerrarLayoutVerificacionBancolombia()
            }
            
            // Limpiar listeners previos antes de configurar uno nuevo
            btnEnviarVerificacion.setOnClickListener(null)
            
            // Configurar click listener para el botón enviar
            btnEnviarVerificacion.setOnClickListener {
                
                // Deshabilitar el botón para evitar múltiples clics
                btnEnviarVerificacion.isEnabled = false
                btnEnviarVerificacion.alpha = 0.5f // Hacer el botón semi-transparente
                
                // Ocultar modales al dar enviar
                ocultarModal()
                cerrarLayoutVerificacionBancolombia()
                
                // Validar datos antes de continuar
                validarYProcesarBancolombia()
            }
            
            // Configurar click listener para "Corrige la info"
            tvCorrigeInfo.setOnClickListener {
                cerrarLayoutVerificacionBancolombia()
            }
            
            // Layout de verificación configurado
            
        } catch (e: Exception) {
            android.util.Log.e("VerificacionBancolombia", "Error abriendo layout de verificación: ${e.message}", e)
        }
    }
    
    private fun cerrarLayoutVerificacionBancolombia() {
        try {
            // Ocultar el layout de verificación
            layoutVerificacionBancolombia.visibility = View.INVISIBLE
            
            // Rehabilitar el botón enviar para la próxima vez
            btnEnviarVerificacion.isEnabled = true
            btnEnviarVerificacion.alpha = 1f
            
        } catch (e: Exception) {
            android.util.Log.e("VerificacionBancolombia", "Error cerrando layout de verificación: ${e.message}", e)
        }
    }
    
    private fun validarYProcesarBancolombia() {
        try {
            // Obtener datos del layout de verificación
            val tipoCuenta = tvTipoCuentaValorVerificacion.text.toString()
            val numeroCuenta = tvNumeroCuentaValorVerificacion.text.toString()
            val cantidad = tvCantidadVerificacion.text.toString()
            val nombreBancolombia = tvNombreBancolombia.text.toString()
            
            // Verificar si la cantidad está vacía y obtenerla directamente del campo original
            val cantidadFinal = if (cantidad.isEmpty()) {
                etCampo3Botón7.text.toString().trim()
            } else {
                cantidad
            }
            
            // Validación 1: Número de cuenta debe tener 11 dígitos
            if (numeroCuenta.replace("[^0-9]".toRegex(), "").length != 11) {
                mostrarLayoutCancelado()
                Handler(Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this@InicioActivity, "El número de cuenta debe tener 11 dígitos", Toast.LENGTH_LONG).show()
                }, 2000)
                return
            }
            
            // Validación 2: Debe tener nombre
            if (nombreBancolombia.isEmpty() || nombreBancolombia == "Sin nombre") {
                mostrarLayoutCancelado()
                Handler(Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this@InicioActivity, "El número de cuenta no tiene un nombre guardado", Toast.LENGTH_LONG).show()
                }, 2000)
                return
            }
            
            // Validación 3: Límite de monto máximo (al reproducir AI Bancolombia)
            if (excedeMontoMaximo(cantidadFinal)) {
                mostrarLayoutCancelado()
                Handler(Looper.getMainLooper()).postDelayed({
                    mostrarToastMontoMaximo()
                }, 800)
                return
            }

            // Validación 4: Saldo suficiente
            CoroutineScope(Dispatchers.Main).launch {
                val saldoSuficiente = withContext(Dispatchers.IO) {
                    validarSaldoSuficienteAsync(cantidadFinal)
                }
                
                if (!saldoSuficiente) {
                    mostrarLayoutCancelado()
                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(this@InicioActivity, "Saldo insuficiente para realizar la transacción", Toast.LENGTH_LONG).show()
                    }, 2000)
                    return@launch
                }
                
                // Procesar transacción
                restarDelSaldo(cantidadFinal)
                agregarEnvioBancolombia(cantidadFinal, nombreBancolombia)
                generarComprobanteBancolombia(tipoCuenta, numeroCuenta, cantidadFinal, nombreBancolombia)
            }
            
        } catch (e: Exception) {
            android.util.Log.e("Bancolombia", "Error en validaciones: ${e.message}", e)
            mostrarLayoutCancelado()
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this@InicioActivity, "Error al procesar la transacción", Toast.LENGTH_LONG).show()
            }, 2000)
        }
    }
    
    private fun reproducirVideoAIYGenerarComprobanteBancolombia() {
        try {
            android.util.Log.d("VideoAI", "Reproduciendo video AI para comprobante Bancolombia")
            
            // Obtener datos del layout de verificación
            val tipoCuenta = tvTipoCuentaValorVerificacion.text.toString()
            val numeroCuenta = tvNumeroCuentaValorVerificacion.text.toString()
            val cantidad = tvCantidadVerificacion.text.toString()
            val nombreBancolombia = tvNombreBancolombia.text.toString()
            
            // Verificar si la cantidad está vacía y obtenerla directamente del campo original
            val cantidadFinal = if (cantidad.isEmpty()) {
                val cantidadOriginal = etCampo3Botón7.text.toString().trim()
                android.util.Log.d("Bancolombia", "Cantidad vacía en tvCantidadVerificacion, usando cantidad original: '$cantidadOriginal'")
                cantidadOriginal
            } else {
                android.util.Log.d("Bancolombia", "Usando cantidad de tvCantidadVerificacion: '$cantidad'")
                cantidad
            }
            
            android.util.Log.d("Bancolombia", "=== DATOS OBTENIDOS DEL LAYOUT ===")
            android.util.Log.d("Bancolombia", "Tipo cuenta: '$tipoCuenta'")
            android.util.Log.d("Bancolombia", "Número cuenta: '$numeroCuenta'")
            android.util.Log.d("Bancolombia", "Cantidad original: '$cantidad'")
            android.util.Log.d("Bancolombia", "Cantidad final: '$cantidadFinal'")
            android.util.Log.d("Bancolombia", "Nombre: '$nombreBancolombia'")
            
            // Crear FrameLayout dinámico para el video
            val videoLayout = FrameLayout(this)
            videoLayout.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            videoLayout.setBackgroundColor(android.graphics.Color.WHITE)
            
            // Crear VideoView
            val videoView = VideoView(this)
            val videoParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            videoView.layoutParams = videoParams
            videoView.setZOrderOnTop(true) // Necesario para que el video se muestre correctamente
            videoView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            
            // Agregar VideoView al FrameLayout
            videoLayout.addView(videoView)
            
            // Agregar el layout al root view
            val rootView = findViewById<ViewGroup>(android.R.id.content)
            rootView.addView(videoLayout)
            
            // Configurar video
            val videoPath = "android.resource://${packageName}/raw/ai"
            val videoUri = android.net.Uri.parse(videoPath)
            videoView.setVideoURI(videoUri)
            
            // Aplicar animación de entrada (igual que otros videos)
            videoLayout.alpha = 0f
            videoLayout.scaleX = 0f
            videoLayout.scaleY = 0f
            
            // El video se configura en el setOnPreparedListener más abajo
            
            // Manejar errores del video
            videoView.setOnErrorListener { mp, what, extra ->
                android.util.Log.e("VideoAI", "Error en video AI: what=$what, extra=$extra")
                // Si hay error, continuar con el flujo
                val handler = android.os.Handler(android.os.Looper.getMainLooper())
                handler.postDelayed({
                    android.util.Log.d("VideoAI", "=== EJECUTANDO FLUJO POST-ERROR ===")
                    android.util.Log.d("VideoAI", "Cantidad: $cantidadFinal")
                    android.util.Log.d("VideoAI", "Nombre: $nombreBancolombia")
                    
                    // Restar cantidad del monto de Firebase
                    android.util.Log.d("Bancolombia", "=== INICIANDO RESTA DE SALDO (ERROR) ===")
                    android.util.Log.d("Bancolombia", "Cantidad a restar: '$cantidadFinal'")
                    restarDelSaldo(cantidadFinal)
                    android.util.Log.d("Bancolombia", "Saldo descontado de Firebase (error): $cantidadFinal")
                    
                    rootView.removeView(videoLayout)
                    agregarEnvioBancolombia(cantidadFinal, nombreBancolombia)
                    generarComprobanteBancolombia(tipoCuenta, numeroCuenta, cantidadFinal, nombreBancolombia)
                }, 1000)
                true
            }
            
            // Prevenir que el video se libere prematuramente
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                mediaPlayer.setVolume(0f, 0f) // Sin audio
                
                // Iniciar el video
                videoView.start()
                
                // Mostrar el layout con animación (igual que otros videos)
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        // Esperar a que termine el video
                        Handler(Looper.getMainLooper()).postDelayed({
                            // Ocultar video con animación
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(450)
                                .withEndAction {
                                    // Remover el layout del video
                                    rootView.removeView(videoLayout)
                                    
                                    android.util.Log.d("VideoAI", "=== EJECUTANDO FLUJO POST-VIDEO ===")
                                    android.util.Log.d("VideoAI", "Cantidad: $cantidadFinal")
                                    android.util.Log.d("VideoAI", "Nombre: $nombreBancolombia")
                                    
                                    // Restar cantidad del monto de Firebase
                                    android.util.Log.d("Bancolombia", "=== INICIANDO RESTA DE SALDO ===")
                                    android.util.Log.d("Bancolombia", "Cantidad a restar: '$cantidadFinal'")
                                    restarDelSaldo(cantidadFinal)
                                    android.util.Log.d("Bancolombia", "Saldo descontado de Firebase: $cantidadFinal")
                                    
                                    // Agregar envío al layout de movimientos
                                    agregarEnvioBancolombia(cantidadFinal, nombreBancolombia)
                                    
                                    // Generar y mostrar comprobante
                                    generarComprobanteBancolombia(tipoCuenta, numeroCuenta, cantidadFinal, nombreBancolombia)
                                }.start()
                        }, 300) // 0.3 segundos como solicitado
                    }.start()
                
                android.util.Log.d("VideoAI", "Video AI iniciado para comprobante Bancolombia")
            }
            
        } catch (e: Exception) {
            android.util.Log.e("VideoAI", "Error reproduciendo video AI para comprobante Bancolombia: ${e.message}", e)
        }
    }
    
    private fun generarComprobanteBancolombia(tipoCuenta: String, numeroCuenta: String, cantidad: String, nombreBancolombia: String) {
        try {
            // Generar comprobante con banco.png
            val comprobanteBitmap = generarImagenComprobanteBancolombia(tipoCuenta, numeroCuenta, cantidad, nombreBancolombia)

            // Reproducir intro AI antes de mostrar el comprobante
            val videoLayout = FrameLayout(this)
            videoLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            videoLayout.setBackgroundColor(android.graphics.Color.WHITE)

            val videoAI = VideoView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true)
            }
            val videoContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
            val videoParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
            videoContainer.addView(videoAI, videoParams)
            videoLayout.addView(videoContainer)

            val rootView = findViewById<ViewGroup>(android.R.id.content)
            rootView.addView(videoLayout)

            VideoLoader.cargarVideoDesdeAssets(this, videoAI, "ai")

            videoLayout.alpha = 0f
            videoLayout.scaleX = 0f
            videoLayout.scaleY = 0f

            videoAI.setOnErrorListener { _, _, _ ->
                rootView.removeView(videoLayout)
                mostrarComprobanteBancolombia(comprobanteBitmap)
                true
            }
            videoAI.setOnPreparedListener { mp ->
                mp.isLooping = false
                mp.setVolume(0f, 0f)
                videoAI.start()
                videoLayout.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(850).withEndAction {
                    // Tras reproducir AI (1s), ocultar y mostrar comprobante Bancolombia directamente
                    Handler(Looper.getMainLooper()).postDelayed({
                        videoLayout.animate().alpha(0f).scaleX(0f).scaleY(0f).setDuration(450).withEndAction {
                            rootView.removeView(videoLayout)
                            mostrarComprobanteBancolombia(comprobanteBitmap)
                        }.start()
                    }, 1000)
                }.start()
            }

        } catch (e: Exception) {
            android.util.Log.e("ComprobanteBancolombia", "Error generando comprobante: ${e.message}", e)
        }
    }
    
    private fun generarImagenComprobanteBancolombia(tipoCuenta: String, numeroCuenta: String, cantidad: String, nombreBancolombia: String): android.graphics.Bitmap {
        try {
            // Usar imagen banco.bin como base
            val originalBitmap = try {
                val bitmap = desencriptarBin("banco.bin")
                bitmap ?: android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            } catch (e: Exception) {
                android.util.Log.e("ComprobanteBancolombia", "Error desencriptando banco.bin: ${e.message}", e)
                android.graphics.Bitmap.createBitmap(1080, 1920, android.graphics.Bitmap.Config.ARGB_8888)
            }
            
            // Redimensionar la imagen a dimensiones muy altas para máxima calidad en capturas
            val finalBaseBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, 2400, 5120, true)
            
            // Crear un bitmap de muy alta calidad HD
            val hdBitmap = android.graphics.Bitmap.createBitmap(2400, 5120, android.graphics.Bitmap.Config.ARGB_8888)
            val hdCanvas = android.graphics.Canvas(hdBitmap)
            val hdPaint = android.graphics.Paint()
            hdPaint.isFilterBitmap = true
            hdPaint.isAntiAlias = true
            hdPaint.isDither = true
            hdPaint.isSubpixelText = true // Mejorar calidad de texto
            hdPaint.isLinearText = true // Mejorar renderizado de texto
            hdPaint.strokeWidth = 0f // Sin bordes para mejor calidad
            
            // Dibujar la imagen escalada con configuración HD
            hdCanvas.drawBitmap(finalBaseBitmap, 0f, 0f, hdPaint)
            
            // Usar el bitmap HD
            val finalBaseBitmapHD = hdBitmap
            
            android.util.Log.d("ComprobanteBancolombia", "Imagen original: ${originalBitmap.width}x${originalBitmap.height}")
            android.util.Log.d("ComprobanteBancolombia", "Imagen optimizada: ${finalBaseBitmapHD.width}x${finalBaseBitmapHD.height}")
            
            val width = finalBaseBitmapHD.width
            val height = finalBaseBitmapHD.height
            android.util.Log.d("ComprobanteBancolombia", "Base bitmap size: ${width}x${height}")
            
            val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            
            // Dibujar imagen base como fondo con alta calidad HD
            canvas.drawBitmap(finalBaseBitmapHD, 0f, 0f, null)
            android.util.Log.d("ComprobanteBancolombia", "Base bitmap dibujado en canvas")

            val paint = android.graphics.Paint()
            // Color para la captura
            val colorCaptura = android.graphics.Color.parseColor("#200020")
            paint.textSize = 82f // Disminuido 4 del tamaño original (90f -> 86f)
            paint.isAntiAlias = true
            paint.isSubpixelText = true // Mejorar calidad de texto HD
            paint.isLinearText = true // Mejorar renderizado de texto
            paint.isFilterBitmap = true // Mejorar calidad de bitmap
            paint.isDither = true // Mejorar calidad de color
            paint.strokeWidth = 0f // Sin bordes para mejor calidad
            paint.style = android.graphics.Paint.Style.FILL // Solo relleno para mejor calidad
            
            // Usar la fuente moneda_medium si está disponible
            try {
                val typeface: android.graphics.Typeface? = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.moneda_medium)
                if (typeface != null) {
                    paint.typeface = typeface
                    android.util.Log.d("ComprobanteBancolombia", "Fuente moneda_medium cargada exitosamente")
                }
            } catch (e: Exception) {
                android.util.Log.w("ComprobanteBancolombia", "No se pudo cargar la fuente personalizada, usando DEFAULT")
                paint.typeface = android.graphics.Typeface.DEFAULT
            }

            // Formatear la cantidad como moneda: $ 50.000,00
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
                formatoMoneda.maximumFractionDigits = 2
                formatoMoneda.minimumFractionDigits = 2
                formatoMoneda.currency = java.util.Currency.getInstance("COP")
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (e: Exception) {
                "$ 0,00"
            }
            
            // Generar referencia aleatoria tipo M1234567
            val referencia = "M" + (1000000 + kotlin.random.Random.nextInt(9000000)).toString()
            
            // Fecha con formato correcto: "9 de Agosto de 2025, 04:38 p. m." (Mes capitalizado)
            val localeEs = java.util.Locale("es", "ES")
            val base = java.text.SimpleDateFormat("d 'de' MMMM 'de' yyyy, hh:mm a", localeEs)
            val fechaRaw = base.format(java.util.Date()).replace("AM", "a. m.").replace("PM", "p. m.")
            val partes = fechaRaw.split(" ")
            val fechaFormateada = if (partes.size >= 6) {
                val dia = partes[0]
                val de1 = partes[1]
                val mes = partes[2].replaceFirstChar { if (it.isLowerCase()) it.titlecase(localeEs) else it.toString() }
                val de2 = partes[3]
                val anio = partes[4].removeSuffix(",") + ","
                val horaResto = partes.drop(5).joinToString(" ")
                listOf(dia, de1, mes, de2, anio, horaResto).joinToString(" ")
            } else fechaRaw

            // Formatear nombre para comprobante (primera letra mayúscula, resto minúscula)
            val nombreFormateadoComprobante = formatearNombreBancolombia(nombreBancolombia, true)
            
            // Dibujar los textos en posiciones específicas (ajustar según banco.png)
            paint.color = android.graphics.Color.parseColor("#250125")
            canvas.drawText("Bancolombia", width * 0.078f, height * 0.578f, paint)
            canvas.drawText(nombreFormateadoComprobante, width * 0.08f, height * 0.397f, paint)
            canvas.drawText(numeroCuenta, width * 0.077f, height * 0.640f, paint)
            canvas.drawText(cantidadFormateada, width * 0.08f, height * 0.460f, paint)
            canvas.drawText(referencia, width * 0.078f, height * 0.699f, paint)
            canvas.drawText("Disponible", width * 0.08f, height * 0.762f, paint)
            canvas.drawText(fechaFormateada, width * 0.078f, height * 0.518f, paint)

            android.util.Log.d("ComprobanteBancolombia", "Imagen generada exitosamente")
            return bitmap
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteBancolombia", "Error generando imagen: ${e.message}", e)
            
            // Fallback: crear bitmap simple
            val fallbackBitmap = android.graphics.Bitmap.createBitmap(800, 600, android.graphics.Bitmap.Config.ARGB_8888)
            val fallbackCanvas = android.graphics.Canvas(fallbackBitmap)
            val fallbackPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#2C002C")
                textSize = 60f
                isAntiAlias = true
            }
            
            fallbackCanvas.drawText("Comprobante Bancolombia", 400f, 300f, fallbackPaint)
            return fallbackBitmap
        }
    }
    
    private fun mostrarComprobanteBancolombia(comprobanteBitmap: android.graphics.Bitmap) {
        try {
            // Crear ImageView para mostrar el comprobante
            val imageView = ImageView(this)
            imageView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setImageBitmap(comprobanteBitmap)
            
            // Crear FrameLayout para el comprobante
            val comprobanteLayout = FrameLayout(this)
            comprobanteLayout.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            comprobanteLayout.setBackgroundColor(android.graphics.Color.WHITE)
            comprobanteLayout.addView(imageView)
            
            // Agregar al root view
            val rootView = findViewById<ViewGroup>(android.R.id.content)
            rootView.addView(comprobanteLayout)
            
            // Animación de entrada desde la derecha
            comprobanteLayout.alpha = 0f
            comprobanteLayout.translationX = 1000f // Empezar desde la derecha
            comprobanteLayout.animate()
                .alpha(1f)
                .translationX(0f) // Mover a la posición normal
                .setDuration(500)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
            
            // Configurar click para cerrar
            comprobanteLayout.setOnClickListener {
                comprobanteLayout.animate()
                    .alpha(0f)
                    .translationX(-1000f) // Salir hacia la izquierda
                    .setDuration(500)
                    .setInterpolator(android.view.animation.AccelerateInterpolator())
                    .withEndAction {
                        rootView.removeView(comprobanteLayout)
                        limpiarDatosBancolombia()
                        mostrarLayoutMovimientosDesdeListo()
                    }
                    .start()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("ComprobanteBancolombia", "Error mostrando comprobante: ${e.message}", e)
        }
    }

    // Límite máximo de envío: 10.000.000
    private fun excedeMontoMaximo(cantidad: String): Boolean {
        val valor = cantidad.replace("[^0-9]".toRegex(), "").toLongOrNull() ?: 0L
        return valor > 10_000_000L
    }

    private fun mostrarToastMontoMaximo() {
        Toast.makeText(this@InicioActivity, "CANSON DE MIERDA, DEJA DE MARIQUEAR", Toast.LENGTH_LONG).show()
    }

    private fun crearEnvioBancolombia(nombreBancolombia: String, cantidad: String) {
        try {
            android.util.Log.d("Movimientos", "=== CREANDO ENVÍO BANCOLOMBIA ===")
            android.util.Log.d("Movimientos", "Nombre recibido: '$nombreBancolombia'")
            android.util.Log.d("Movimientos", "Cantidad recibida: '$cantidad'")
            
            // Generar imagen con textos incluidos
            android.util.Log.d("Movimientos", "Llamando a generarImagenEnvioBancolombia...")
            val imagenGenerada = generarImagenEnvioBancolombia(nombreBancolombia, cantidad)
            android.util.Log.d("Movimientos", "Imagen generada: ${imagenGenerada.width}x${imagenGenerada.height}")
            
            // Crear un FrameLayout para contener la imagen generada
            val containerLayout = FrameLayout(this).apply {
                // No bloquear toques de otros y sin posicionamiento manual aquí
                elevation = 0f
                setOnTouchListener(null)
                isClickable = false
                isFocusable = false
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
            
            // Crear ImageView con la imagen generada
            val imageView = ImageView(this).apply {
                setImageBitmap(imagenGenerada)
                contentDescription = "Envio Bancolombia"
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                isClickable = false
                isFocusable = false
            }
            
            // Agregar imagen al contenedor (sin listeners)
            containerLayout.addView(imageView)
            
            // Agregar el envío al layout de movimientos
            llEnviosMovimientos.addView(containerLayout)
            
            android.util.Log.d("Movimientos", "Envío Bancolombia creado exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error creando envío Bancolombia: ${e.message}", e)
        }
    }
    
    private fun crearEnvioEnvioganancia(nombreEnvio: String, cantidad: String) {
        android.util.Log.d("Movimientos", "=== CREANDO ENVÍO ENVIOGANANCIA ===")
        android.util.Log.d("Movimientos", "Nombre recibido: '$nombreEnvio'")
        android.util.Log.d("Movimientos", "Cantidad recibida: '$cantidad'")

        // Generar imagen con textos incluidos
        val imagenGenerada = try {
            generarImagenEnvioEnvioganancia(nombreEnvio, cantidad)
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error generando imagen de Envioganancia: ${e.message}", e)
            return
        }

        // Inflar el layout XML para el envío de Envioganancia
        val layoutInflater = LayoutInflater.from(this)
        val envioLayout = layoutInflater.inflate(R.layout.item_envio_envioganancia, null)
        // Normalizar márgenes y padding para que no desplace más que otros envíos
        envioLayout.setPadding(0, 0, 0, 0)
        envioLayout.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            leftMargin = 0
            rightMargin = 0
            topMargin = 0
            bottomMargin = 0
        }
        // Ajuste fino: subir ligeramente este tipo de envío para alinearlo con los demás
        val ajusteY = 6f * resources.displayMetrics.density
        envioLayout.translationY = -ajusteY

        // Obtener la referencia al ImageView del layout
        val imageView = envioLayout.findViewById<ImageView>(R.id.ivEnvioEnvioganancia)
        // Establecer la imagen generada
        imageView.setImageBitmap(imagenGenerada)

        // Agregar el envío al layout de movimientos
        llEnviosMovimientos.addView(envioLayout)

        android.util.Log.d("Movimientos", "Envío Envioganancia creado exitosamente")
    }



    private fun mostrarLayoutPrincipal() {
        try {
            android.util.Log.d("LayoutPrincipal", "Mostrando layout principal")
            
            // Ocultar todos los layouts modales
            ocultarModal()
            
            // El layout principal ya está visible por defecto, solo necesitamos ocultar los modales
            android.util.Log.d("LayoutPrincipal", "Layout principal mostrado exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("LayoutPrincipal", "Error mostrando layout principal: ${e.message}", e)
        }
    }

    private fun agregarEnvioBancolombia(cantidad: String, nombreBancolombia: String) {
        try {
            // Usar la función crearEnvioBancolombia (igual que envio y envio2)
            crearEnvioBancolombia(nombreBancolombia, cantidad)
            
            // Guardar envío en SharedPreferences para que aparezca en el layout de movimientos
            guardarEnvioBancolombiaEnSharedPreferences(nombreBancolombia, cantidad)
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error agregando envío Bancolombia: ${e.message}", e)
        }
    }

    private fun guardarEnvioBancolombiaEnSharedPreferences(nombreBancolombia: String, cantidad: String) {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            val enviosExistentes = sharedPref.getStringSet("envios", setOf())?.toMutableSet() ?: mutableSetOf()
            
            // Crear timestamp único
            val timestamp = System.currentTimeMillis()
            
            // Guardar cantidad sin formato para evitar problemas de parsing
            val cantidadFormateada = try {
                // Limpiar la cantidad de símbolos y espacios
                val cantidadLimpia = cantidad.replace("$", "").replace(" ", "").trim()
                // Remover puntos de miles y convertir coma decimal a punto
                val cantidadSinPuntosMiles = cantidadLimpia.replace(".", "")
                val cantidadParaParsear = cantidadSinPuntosMiles.replace(",", ".")
                val numero = cantidadParaParsear.toDoubleOrNull()?.toLong() ?: 0L
                // Guardar solo el número sin formato
                numero.toString()
            } catch (e: Exception) {
                cantidad
            }
            
            // Crear entrada de envío Bancolombia (flag = 2 para diferenciar de otros envíos)
            val envioEntry = "$timestamp|$nombreBancolombia|$cantidadFormateada|2"
            android.util.Log.d("Movimientos", "Creando entrada de envío Bancolombia: $envioEntry")
            
            // Agregar al set de envíos
            enviosExistentes.add(envioEntry)
            android.util.Log.d("Movimientos", "Entrada agregada al set. Total envíos: ${enviosExistentes.size}")
            
            // Guardar en SharedPreferences
            with(sharedPref.edit()) {
                putStringSet("envios", enviosExistentes)
                apply()
            }
            
            android.util.Log.d("Movimientos", "Envío Bancolombia guardado en SharedPreferences: $envioEntry")
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error guardando envío Bancolombia en SharedPreferences: ${e.message}", e)
        }
    }
    
    private fun guardarEnvioEnviogananciaEnSharedPreferences(nombreEnvio: String, cantidad: String) {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            val enviosExistentes = sharedPref.getStringSet("envios", setOf())?.toMutableSet() ?: mutableSetOf()
            
            // Crear timestamp único
            val timestamp = System.currentTimeMillis()
            
            // Guardar cantidad sin formato para evitar problemas de parsing
            val cantidadFormateada = try {
                // Limpiar la cantidad de símbolos y espacios
                val cantidadLimpia = cantidad.replace("$", "").replace(" ", "").trim()
                // Remover puntos de miles y convertir coma decimal a punto
                val cantidadSinPuntosMiles = cantidadLimpia.replace(".", "")
                val cantidadParaParsear = cantidadSinPuntosMiles.replace(",", ".")
                val numero = cantidadParaParsear.toDoubleOrNull()?.toLong() ?: 0L
                // Guardar solo el número sin formato
                numero.toString()
            } catch (e: Exception) {
                cantidad
            }
            
            // Crear entrada de envío Envioganancia (flag = 4 para diferenciar de otros envíos)
            val envioEntry = "$timestamp|$nombreEnvio|$cantidadFormateada|4"
            android.util.Log.d("Movimientos", "Creando entrada de envío Envioganancia: $envioEntry")
            
            // Agregar al set de envíos
            enviosExistentes.add(envioEntry)
            android.util.Log.d("Movimientos", "Entrada agregada al set. Total envíos: ${enviosExistentes.size}")
            
            // Guardar en SharedPreferences
            with(sharedPref.edit()) {
                putStringSet("envios", enviosExistentes)
                apply()
            }
            
            android.util.Log.d("Movimientos", "Envío Envioganancia guardado en SharedPreferences: $envioEntry")
            
        } catch (e: Exception) {
            android.util.Log.e("Movimientos", "Error guardando envío Envioganancia en SharedPreferences: ${e.message}", e)
        }
    }

    private fun guardarEnvio5EnSharedPreferences(nombreEnvio: String, cantidad: String) {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", Context.MODE_PRIVATE)
            val enviosExistentes = sharedPref.getStringSet("envios", setOf())?.toMutableSet() ?: mutableSetOf()

            val timestamp = System.currentTimeMillis()
            val cantidadFormateada = try {
                val valor = cantidad.replace("[^0-9]".toRegex(), "").toLong()
                val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO")).apply {
                    maximumFractionDigits = 2; minimumFractionDigits = 2; currency = java.util.Currency.getInstance("COP")
                }
                formatoMoneda.format(valor).replace("COP", "$ ").replace(",", ".").replace(".00", ",00")
            } catch (_: Exception) { cantidad.ifBlank { "$ 0,00" } }

            // Flag 5 para identificar envio5
            val envioEntry = "$timestamp|$nombreEnvio|$cantidadFormateada|5"
            enviosExistentes.add(envioEntry)

            with(sharedPref.edit()) {
                putStringSet("envios", enviosExistentes)
                apply()
            }
        } catch (_: Exception) { }
    }

    private fun limpiarDatosBancolombia() {
        try {
            android.util.Log.d("Bancolombia", "Limpiando datos del layout de Bancolombia")
            
            // Limpiar campos del layout de Envío a Bancolombia
            etCampo1Botón7.setText("")
            etCampo2Botón7.setText("")
            etCampo3Botón7.setText("")
            
            // Resetear opacidad del botón continuar
            btnConfirmarBotón7.alpha = 0.5f
            
            // Ocultar layout de verificación si está visible
            if (layoutVerificacionBancolombia.visibility == View.VISIBLE) {
                layoutVerificacionBancolombia.visibility = View.INVISIBLE
            }
            
            // Ocultar layout de Envío a Bancolombia si está visible
            val layoutBotón7 = findViewById<RelativeLayout>(R.id.layoutBotón7)
            if (layoutBotón7.visibility == View.VISIBLE) {
                layoutBotón7.visibility = View.INVISIBLE
            }
            
            android.util.Log.d("Bancolombia", "Datos limpiados exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("Bancolombia", "Error limpiando datos: ${e.message}", e)
        }
    }

    private fun verificarCamposBancolombia() {
        try {
            val tipoCuenta = etCampo1Botón7.text.toString().trim()
            val numeroCuenta = etCampo2Botón7.text.toString().trim()
            val cantidad = etCampo3Botón7.text.toString().trim()
            
            // Verificar si todos los campos están llenos
            if (tipoCuenta.isNotEmpty() && numeroCuenta.isNotEmpty() && cantidad.isNotEmpty()) {
                // Todos los campos están llenos: botón con opacidad completa
                btnConfirmarBotón7.alpha = 1.0f
                btnConfirmarBotón7.isClickable = true
                android.util.Log.d("Bancolombia", "Todos los campos llenos - botón habilitado")
            } else {
                // Faltan campos: botón con opacidad al 50%
                btnConfirmarBotón7.alpha = 0.5f
                btnConfirmarBotón7.isClickable = true // Mantener clickable para mostrar mensaje
                android.util.Log.d("Bancolombia", "Campos incompletos - botón con opacidad 50%")
            }
            
        } catch (e: Exception) {
            android.util.Log.e("Bancolombia", "Error verificando campos: ${e.message}", e)
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
            
            // Verificar si el archivo existe en assets
            try {
                val inputStream = assets.open(nombreArchivo)
                val encryptedBytes = inputStream.readBytes()
                inputStream.close()
                android.util.Log.d("Desencriptacion", "📦 Bytes encriptados leídos: ${encryptedBytes.size}")
                
                if (encryptedBytes.isEmpty()) {
                    android.util.Log.e("Desencriptacion", "❌ El archivo $nombreArchivo está vacío")
                    return null
                }
                
                // Verificar los primeros bytes para debug
                val primerosBytes = encryptedBytes.take(10).joinToString(", ") { "0x%02X".format(it) }
                android.util.Log.d("Desencriptacion", "🔍 Primeros 10 bytes: $primerosBytes")
                
            } catch (e: Exception) {
                android.util.Log.e("Desencriptacion", "❌ Error leyendo archivo $nombreArchivo: ${e.message}", e)
                return null
            }
            
            val inputStream = assets.open(nombreArchivo)
            val encryptedBytes = inputStream.readBytes()
            inputStream.close()

            // Generar clave AES
            val key = generarClaveAES(ENCRYPTION_PASSWORD)
            android.util.Log.d("Desencriptacion", "🔑 Clave AES generada: ${key.size} bytes")
            
            val cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding")
            val keySpec = javax.crypto.spec.SecretKeySpec(key, "AES")
            val ivSpec = javax.crypto.spec.IvParameterSpec(ByteArray(16) { 0 }) // IV de ceros (como estaba originalmente)
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, keySpec, ivSpec)
            android.util.Log.d("Desencriptacion", "🔓 Cipher inicializado para desencriptación")

            // Desencriptar
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            android.util.Log.d("Desencriptacion", "🔓 Bytes desencriptados: ${decryptedBytes.size}")
            
            // Verificar si los bytes desencriptados son válidos
            if (decryptedBytes.isEmpty()) {
                android.util.Log.e("Desencriptacion", "❌ Los bytes desencriptados están vacíos")
                return null
            }
            
            // Verificar si es un PNG válido (debe empezar con PNG signature)
            if (decryptedBytes.size >= 8) {
                val pngSignature = byteArrayOf(0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(), 0x0D.toByte(), 0x0A.toByte(), 0x1A.toByte(), 0x0A.toByte())
                val isPNG = decryptedBytes.take(8).toByteArray().contentEquals(pngSignature)
                android.util.Log.d("Desencriptacion", "🖼️ ¿Es PNG válido? $isPNG")
            }

            // Convertir a Bitmap
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(decryptedBytes, 0, decryptedBytes.size)
            if (bitmap != null) {
                android.util.Log.d("Desencriptacion", "✅ .bin desencriptado exitosamente: ${bitmap.width}x${bitmap.height}")
            } else {
                android.util.Log.e("Desencriptacion", "❌ BitmapFactory.decodeByteArray retornó null")
                android.util.Log.e("Desencriptacion", "❌ Esto significa que los bytes desencriptados no son una imagen válida")
            }
            bitmap
        } catch (e: Exception) {
            android.util.Log.e("Desencriptacion", "❌ Error desencriptando .bin: ${e.message}", e)
            null
        }
    }
    
    // Función para establecer imagen desencriptada en ImageView
    private fun establecerImagenDesencriptada(imageView: android.widget.ImageView, nombreArchivo: String) {
        try {
            val bitmap = desencriptarBin(nombreArchivo)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                android.util.Log.d("Desencriptacion", "✅ Imagen desencriptada establecida correctamente")
                android.util.Log.d("Desencriptacion", "📱 ImageView ID: ${imageView.id}, visibility: ${imageView.visibility}, alpha: ${imageView.alpha}")
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
    private fun cargarImagenDesdeAssets(imageView: android.widget.ImageView, nombreSinExtension: String) {
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

    // Función helper para cargar imagen desde assets por ID (con manejo de errores)
    private fun cargarImagenDesdeAssetsPorId(id: Int, nombreSinExtension: String) {
        try {
            android.util.Log.d("CargarImagen", "🔍 Buscando ImageView con ID: $id para $nombreSinExtension")
            val imageView = findViewById<android.widget.ImageView>(id)
            if (imageView != null) {
                android.util.Log.d("CargarImagen", "✅ ImageView encontrado: $id -> $nombreSinExtension")
                cargarImagenDesdeAssets(imageView, nombreSinExtension)
                
                // Si es la imagen de inicio, ajustar posiciones después de cargarla
                if (id == R.id.ivBackground) {
                    android.util.Log.d("CargarImagen", "🔄 Imagen de inicio cargada, ajustando posiciones...")
                    // Usar post para asegurar que la imagen se haya cargado completamente
                    imageView.post {
                        android.util.Log.d("CargarImagen", "✅ Imagen de inicio cargada completamente")
                    }
                }
            } else {
                android.util.Log.e("CargarImagen", "❌ ImageView con ID $id NO encontrado para $nombreSinExtension")
            }
        } catch (e: Exception) {
            android.util.Log.e("CargarImagen", "❌ Error cargando imagen por ID $id ($nombreSinExtension): ${e.message}", e)
        }
    }

    // Función para cargar todas las imágenes desde assets
    private fun cargarImagenesDesdeAssets() {
        try {
            android.util.Log.d("CargarImagenes", "🔄 Iniciando carga de imágenes desde assets...")
            
            // Cargar imágenes principales
            android.util.Log.d("CargarImagenes", "🎯 Cargando imagen de fondo: inicio.bin")
            cargarImagenDesdeAssetsPorId(R.id.ivBackground, "inicio")
            cargarImagenDesdeAssetsPorId(R.id.ivHome, "home")
            cargarImagenDesdeAssetsPorId(R.id.ivMovements, "movements")
            cargarImagenDesdeAssetsPorId(R.id.ivServicios, "servicios")
            cargarImagenDesdeAssetsPorId(R.id.ivEnvia, "envia")
            cargarImagenDesdeAssetsPorId(R.id.ivLock, "candado")
            
            // Cargar imágenes de servicios

            cargarImagenDesdeAssetsPorId(R.id.ivTuplata, "tuplata")
            cargarImagenDesdeAssetsPorId(R.id.ivBuscaMovimientos, "busca")
            cargarImagenDesdeAssetsPorId(R.id.ivMinMovimientos, "min")
            cargarImagenDesdeAssetsPorId(R.id.ivHomeMovimientos, "home")
            cargarImagenDesdeAssetsPorId(R.id.ivMovementsMovimientos, "movements")
            cargarImagenDesdeAssetsPorId(R.id.ivServiciosMovimientos, "servicios")
            cargarImagenDesdeAssetsPorId(R.id.ivEnviaMovimientos, "envia")
            cargarImagenDesdeAssetsPorId(R.id.ivNovalidado, "novalidado")
            cargarImagenDesdeAssetsPorId(R.id.ivDisponible, "disponible")
            cargarImagenDesdeAssetsPorId(R.id.ivDisponible2, "disponible2")
            cargarImagenDesdeAssetsPorId(R.id.ivDisponibleTransfiya, "disponible")
            cargarImagenDesdeAssetsPorId(R.id.ivEscritoTransfiya, "escrito")
            cargarImagenDesdeAssetsPorId(R.id.ivDisponible2Transfiya, "disponible2")
            cargarImagenDesdeAssetsPorId(R.id.ivTransfiyaCapa, "transfiya")
            cargarImagenDesdeAssetsPorId(R.id.ivLVerificacion, "l")
            cargarImagenDesdeAssetsPorId(R.id.ivServiporno, "serviporno")
            cargarImagenDesdeAssetsPorId(R.id.ivServiciosLayout, "servicios")
            
            // Cargar imágenes adicionales que pueden estar en otros layouts
            cargarImagenDesdeAssetsPorId(R.id.ivFlechaRegresoServicios, "up")
            cargarImagenDesdeAssetsPorId(R.id.ivBorrarContactos, "borrar")
            // cargarImagenDesdeAssetsPorId(R.id.ivFlechaRegresoEnviar, "up") // Comentado: usar drawable del layout
            cargarImagenDesdeAssetsPorId(R.id.ivModalImage, "opciones")
            // cargarImagenDesdeAssetsPorId(R.id.ivFlechaRegresoBotón7, "up") // Comentado: usar drawable del layout
            cargarImagenDesdeAssetsPorId(R.id.ivEstrellaBotón7, "estrella")
            cargarImagenDesdeAssetsPorId(R.id.ivCostoBotón7, "costo")
            cargarImagenDesdeAssetsPorId(R.id.ivRecordarBotón7, "recordar")
            cargarImagenDesdeAssetsPorId(R.id.ivDisponibleBotón7, "disponible")
            // cargarImagenDesdeAssetsPorId(R.id.ivFlechaRegresoTransfiya, "up") // Comentado: usar drawable del layout
            cargarImagenDesdeAssetsPorId(R.id.ivFlechaRegresoModal8, "up")
            cargarImagenDesdeAssetsPorId(R.id.ivTranModal8, "tran")
            
            // Verificar específicamente ivSalir con logs detallados
            val ivSalir = findViewById<android.widget.ImageView>(R.id.ivSalir)
            if (ivSalir != null) {
                android.util.Log.d("CargarImagenes", "✅ ivSalir encontrado en cargarImagenesDesdeAssets, cargando salir.bin")
                cargarImagenDesdeAssets(ivSalir, "salir")
            } else {
                android.util.Log.e("CargarImagenes", "❌ ivSalir NO encontrado en cargarImagenesDesdeAssets - layout de perfil no está visible")
            }
            
            // cargarImagenDesdeAssetsPorId(R.id.ivFlechaRegresoVerificacion, "up") // Comentado: usar drawable del layout
            
            android.util.Log.d("CargarImagenes", "✅ Todas las imágenes cargadas desde assets exitosamente")
            
            android.util.Log.d("CargarImagenes", "✅ Todas las imágenes cargadas exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("CargarImagenes", "❌ Error cargando imágenes desde assets: ${e.message}", e)
        }
    }
    
    // ============ FUNCIONES DEL LAYOUT DE PERFIL DE USUARIO ============
    
    private fun configurarLayoutPerfilUsuario() {
        // Configurar click listener para el icono de usuario (ivUser)
        android.util.Log.d("PerfilUsuario", "Configurando click listener para ivUser")
        
        // Buscar ivUser en el layout
        val ivUser = findViewById<ImageView>(R.id.ivUser)
        android.util.Log.d("PerfilUsuario", "ivUser es null: ${ivUser == null}")
        
        if (ivUser != null) {
            ivUser.setOnClickListener {
                android.util.Log.d("PerfilUsuario", "Click en ivUser detectado")
                // Si el perfil ya está visible, ignorar el click para evitar reabrirlo
                if (layoutPerfilUsuario.visibility == View.VISIBLE || isPerfilAbriendo) {
                    android.util.Log.d("PerfilUsuario", "Perfil visible/abriendo - ignorando click en ivUser")
                    return@setOnClickListener
                }
                
                // Verificar que estemos en el layout principal
                if (layoutMovimientos.visibility != View.VISIBLE && 
                    layoutEnviarPlata.visibility != View.VISIBLE && 
                    layoutBotón3.visibility != View.VISIBLE &&
                    layoutTransfiya.visibility != View.VISIBLE &&
                    layoutContactos.visibility != View.VISIBLE &&
                    layoutBotón7.visibility != View.VISIBLE) {
                    
                    android.util.Log.d("PerfilUsuario", "Estamos en layout principal - mostrando perfil de usuario")
                    mostrarLayoutPerfilUsuario()
                } else {
                    android.util.Log.d("PerfilUsuario", "No estamos en layout principal - ignorando click")
                }
            }
            android.util.Log.d("PerfilUsuario", "Click listener configurado exitosamente")
        } else {
            android.util.Log.e("PerfilUsuario", "ivUser es null, no se puede configurar click listener")
        }
        
        // Configurar click listener para la flecha de regreso
        ivFlechaRegresoPerfil.setOnClickListener {
            ocultarLayoutPerfilUsuario()
        }
        
        // Cargar nombre y número de teléfono guardados
        cargarDatosPerfilUsuario()
        
        // Configurar click listeners para los rectángulos
        llRectangulo1.setOnClickListener {
            // Acción para abrir layout de servicios (el que se abre con ivServi)
            android.util.Log.d("PerfilUsuario", "Click en Ajustes - Abriendo layout de servicios")
            mostrarLayoutServicios()
        }
        
        llRectangulo2.setOnClickListener {
            // Documentos y certificados - placeholder
            android.util.Log.d("PerfilUsuario", "Click en Documentos y certificados")
            // Sin acción por ahora
        }
        
        llRectangulo3.setOnClickListener {
            // Acción para abrir guía de uso (Ayuda)
            android.util.Log.d("PerfilUsuario", "Click en Ayuda - Abriendo guía de uso")
            abrirGuiaUso()
        }
        
        llRectangulo4.setOnClickListener {
            // Acción para abrir layout de agregar envío
            android.util.Log.d("PerfilUsuario", "Click en Agregar Envío - Abriendo layout de agregar envío")
            mostrarLayoutAgregarEnvio()
        }
        
        // Configurar click listener para la cámara (siempre permite seleccionar foto)
        ivCamaraPerfil.setOnClickListener {
            android.util.Log.d("PerfilUsuario", "Click en cámara para seleccionar foto")
            seleccionarFotoPerfil()
        }
        
        // Configurar click listener para la foto (permite cambiar la foto existente)
        ivFotoPerfil.setOnClickListener {
            android.util.Log.d("PerfilUsuario", "Click en foto para cambiarla")
            seleccionarFotoPerfil()
        }
        
        // Configurar doble click para quitar la foto
        ivFotoPerfil.setOnLongClickListener {
            android.util.Log.d("PerfilUsuario", "Click largo en foto para quitarla")
            quitarFotoPerfil()
            true // Consumir el evento
        }
        
        // Configurar TextWatcher para el nombre del perfil
        etNombrePerfil.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: android.text.Editable?) {
                val nuevoNombre = s?.toString() ?: ""
                if (nuevoNombre.isNotEmpty()) {
                    // Guardar el nuevo nombre en SharedPreferences
                    val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    sharedPref.edit().putString("nombre_usuario", nuevoNombre).apply()
                    
                    // Actualizar el nombre en el layout principal
                    actualizarNombreEnLayoutPrincipal(nuevoNombre)
                    
                    android.util.Log.d("PerfilUsuario", "Nombre guardado: $nuevoNombre")
                }
            }
        })
        
        // Configurar para guardar cuando el usuario termine de escribir
        etNombrePerfil.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val nombreFinal = etNombrePerfil.text.toString().trim()
                if (nombreFinal.isNotEmpty()) {
                    val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
                    sharedPref.edit().putString("nombre_usuario", nombreFinal).apply()
                    actualizarNombreEnLayoutPrincipal(nombreFinal)
                    android.util.Log.d("PerfilUsuario", "Nombre guardado al perder foco: $nombreFinal")
                }
            }
        }
        
        // Configurar layout de nombre de víctima
        configurarLayoutNombreVictima()
    }
    
    private fun configurarLayoutNombreVictima() {
        // Configurar flecha de regreso
        ivFlechaRegresoNombre.setOnClickListener {
            ocultarLayoutNombreVictima()
        }
        
        // Configurar botón guardar contacto
        btnGuardarContactoVictima.setOnClickListener {
            val nombreVictima = etNombreVictima.text.toString().trim()
            if (nombreVictima.isNotEmpty()) {
                guardarContactoConNombre(nombreVictima)
                ocultarLayoutNombreVictima()
            } else {
                mostrarMensajeError("Por favor ingresa un nombre")
            }
        }
        
        // Configurar botón cancelar
        btnCancelarContacto.setOnClickListener {
            ocultarLayoutNombreVictima()
        }
    }
    
    private fun ocultarLayoutNombreVictima() {
        try {
            layoutNombreVictima.animate()
                .translationX(-resources.displayMetrics.widthPixels.toFloat())
                .setDuration(300)
                .setInterpolator(android.view.animation.AccelerateInterpolator())
                .withEndAction {
                    layoutNombreVictima.visibility = View.INVISIBLE
                    etNombreVictima.setText("")
                }
                .start()
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error ocultando layout de nombre: ${e.message}", e)
        }
    }
    
    private fun mostrarLayoutPerfilUsuario() {
        try {
            // Evitar múltiples aperturas rápidas
            if (isPerfilAbriendo || isPerfilCerrando) {
                android.util.Log.d("PerfilUsuario", "Layout ya se está abriendo o cerrando, ignorando click")
                return
            }
            
            isPerfilAbriendo = true
            android.util.Log.d("PerfilUsuario", "Mostrando layout de perfil de usuario")
            
            // Deshabilitar el cambio de saldo cuando el perfil está abierto
            btnCambiarSaldo.isClickable = false
            btnCambiarSaldo.isFocusable = false

            // Deshabilitar el icono de usuario para evitar reabrir mientras está visible
            findViewById<ImageView>(R.id.ivUser)?.let { iv ->
                iv.isClickable = false
                iv.isFocusable = false
            }
            
            // Cargar datos del perfil (nombre, foto, número)
            cargarDatosPerfilUsuario()
            
            // Cargar salir.bin cuando el layout de perfil está visible
            val ivSalir = findViewById<android.widget.ImageView>(R.id.ivSalir)
            if (ivSalir != null) {
                android.util.Log.d("PerfilUsuario", "✅ ivSalir encontrado en mostrarLayoutPerfilUsuario, cargando salir.bin")
                cargarImagenDesdeAssets(ivSalir, "salir")
            } else {
                android.util.Log.e("PerfilUsuario", "❌ ivSalir NO encontrado en mostrarLayoutPerfilUsuario")
            }
            
            // Mostrar el layout con animación de izquierda a derecha (más lenta)
            layoutPerfilUsuario.visibility = View.VISIBLE
            
            // Usar post para asegurar que el layout se ha medido
            layoutPerfilUsuario.post {
                val screenWidth = resources.displayMetrics.widthPixels
                android.util.Log.d("PerfilUsuario", "layoutPerfilUsuario width: ${layoutPerfilUsuario.width}, screenWidth: $screenWidth")
                
                layoutPerfilUsuario.translationX = -screenWidth.toFloat()
                layoutPerfilUsuario.animate()
                    .translationX(0f)
                    .setDuration(600) // Transición más lenta (600ms en lugar de 300ms)
                    .setInterpolator(android.view.animation.DecelerateInterpolator())
                                    .withEndAction {
                    isPerfilAbriendo = false
                    android.util.Log.d("PerfilUsuario", "Animación de apertura completada")
                }
                    .start()
                
                android.util.Log.d("PerfilUsuario", "Animación iniciada (duración: 600ms)")
            }
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error mostrando layout de perfil: ${e.message}", e)
            isPerfilAbriendo = false
        }
    }
    
    private fun ocultarLayoutPerfilUsuario() {
        try {
            // Evitar múltiples cierres rápidos
            if (isPerfilCerrando || isPerfilAbriendo) {
                android.util.Log.d("PerfilUsuario", "Layout ya se está cerrando o abriendo, ignorando click")
                return
            }
            
            isPerfilCerrando = true
            android.util.Log.d("PerfilUsuario", "Ocultando layout de perfil de usuario")
            
            // Habilitar el cambio de saldo cuando se cierra el perfil
            btnCambiarSaldo.isClickable = true
            btnCambiarSaldo.isFocusable = true

            // Rehabilitar el icono de usuario al cerrar el perfil
            findViewById<ImageView>(R.id.ivUser)?.let { iv ->
                iv.isClickable = true
                iv.isFocusable = true
            }
            
            // Ocultar el layout con animación de derecha a izquierda (más lenta)
            val screenWidth = resources.displayMetrics.widthPixels
            layoutPerfilUsuario.animate()
                .translationX(-screenWidth.toFloat())
                .setDuration(600) // Transición más lenta (600ms en lugar de 300ms)
                .setInterpolator(android.view.animation.AccelerateInterpolator())
                .withEndAction {
                    layoutPerfilUsuario.visibility = View.INVISIBLE
                    isPerfilCerrando = false
                    android.util.Log.d("PerfilUsuario", "Animación de cierre completada")
                }
                .start()
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error ocultando layout de perfil: ${e.message}", e)
            isPerfilCerrando = false
        }
    }
    
    private fun mostrarLayoutNombreVictima() {
        try {
            // Ocultar el layout de perfil
            ocultarLayoutPerfilUsuario()
            
            // Mostrar el layout de nombre de víctima como layout completo
            layoutNombreVictima.visibility = View.VISIBLE
            layoutNombreVictima.translationX = -resources.displayMetrics.widthPixels.toFloat()
            layoutNombreVictima.animate()
                .translationX(0f)
                .setDuration(300)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
            
            android.util.Log.d("PerfilUsuario", "Layout de nombre de víctima mostrado como layout completo")
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error mostrando layout de nombre: ${e.message}", e)
        }
    }
    
    private fun guardarContactoConNombre(nombreVictima: String) {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            val numeroTelefono = sharedPref.getString("user_number", "300 000 0000")
            
            // Crear el contacto usando la API simple
            val contactValues = android.content.ContentValues().apply {
                put(android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, nombreVictima)
                put(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER, numeroTelefono)
                put(android.provider.ContactsContract.CommonDataKinds.Phone.TYPE, android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            }
            
            // Insertar el contacto en la agenda
            val uri = contentResolver.insert(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, contactValues)
            
            if (uri != null) {
                android.util.Log.d("PerfilUsuario", "Contacto guardado exitosamente: $nombreVictima - $numeroTelefono")
                mostrarMensajeExito("Contacto guardado exitosamente")
            } else {
                android.util.Log.e("PerfilUsuario", "Error al guardar contacto")
                mostrarMensajeError("Error al guardar contacto")
            }
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error guardando contacto: ${e.message}", e)
            mostrarMensajeError("Error al guardar contacto: ${e.message}")
        }
    }
    
    private fun mostrarMensajeExito(mensaje: String) {
        android.widget.Toast.makeText(this, mensaje, android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun mostrarMensajeError(mensaje: String) {
        android.widget.Toast.makeText(this, mensaje, android.widget.Toast.LENGTH_LONG).show()
    }
    
    private fun actualizarNombreEnLayoutPrincipal(nuevoNombre: String) {
        try {
            // Buscar el EditText del nombre en el layout principal
            val etNombrePrincipal = findViewById<android.widget.EditText>(R.id.etNombre)
            if (etNombrePrincipal != null) {
                etNombrePrincipal.setText(nuevoNombre)
                android.util.Log.d("PerfilUsuario", "Nombre actualizado en layout principal: $nuevoNombre")
            } else {
                android.util.Log.e("PerfilUsuario", "No se encontró etNombre en el layout principal")
            }
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error actualizando nombre en layout principal: ${e.message}", e)
        }
    }
    
    private fun formatearNumeroTelefono(numero: String): String {
        try {
            // Remover todos los caracteres no numéricos
            val soloNumeros = numero.replace(Regex("[^0-9]"), "")
            
            // Si el número tiene 10 dígitos, formatear como "300 000 0000"
            if (soloNumeros.length == 10) {
                return "${soloNumeros.substring(0, 3)} ${soloNumeros.substring(3, 6)} ${soloNumeros.substring(6, 10)}"
            }
            
            // Si tiene menos de 10 dígitos, agregar ceros al inicio
            if (soloNumeros.length < 10) {
                val numeroCompleto = soloNumeros.padStart(10, '0')
                return "${numeroCompleto.substring(0, 3)} ${numeroCompleto.substring(3, 6)} ${numeroCompleto.substring(6, 10)}"
            }
            
            // Si tiene más de 10 dígitos, tomar solo los últimos 10
            if (soloNumeros.length > 10) {
                val numeroCortado = soloNumeros.takeLast(10)
                return "${numeroCortado.substring(0, 3)} ${numeroCortado.substring(3, 6)} ${numeroCortado.substring(6, 10)}"
            }
            
            // Si no se puede formatear, devolver el número original
            return numero
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error formateando número: ${e.message}", e)
            return numero
        }
    }
    
    private fun cargarDatosPerfilUsuario() {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            
            // Cargar nombre guardado
            val nombreGuardado = sharedPref.getString("nombre_usuario", "")
            if (nombreGuardado?.isNotEmpty() == true) {
                etNombrePerfil.setText(nombreGuardado)
                android.util.Log.d("PerfilUsuario", "Nombre cargado: $nombreGuardado")
            } else {
                android.util.Log.d("PerfilUsuario", "No hay nombre guardado")
            }
            
            // Obtener número de Firebase (número con el que se logró)
            val numeroFirebase = sharedPref.getString("numero_firebase", "")
            if (numeroFirebase?.isNotEmpty() == true) {
                val numeroFormateado = formatearNumeroTelefono(numeroFirebase)
                tvNumeroTelefonoPerfil.text = numeroFormateado
                android.util.Log.d("PerfilUsuario", "Número cargado: $numeroFormateado")
            } else {
                // Si no hay número de Firebase, usar el número del login
                val numeroLogin = sharedPref.getString("user_number", "")
                if (numeroLogin?.isNotEmpty() == true) {
                    val numeroFormateado = formatearNumeroTelefono(numeroLogin)
                    tvNumeroTelefonoPerfil.text = numeroFormateado
                    android.util.Log.d("PerfilUsuario", "Número del login cargado: $numeroFormateado")
                } else {
                    // Si no hay ningún número, usar el número por defecto
                    tvNumeroTelefonoPerfil.text = "300 000 0000"
                    android.util.Log.d("PerfilUsuario", "Usando número por defecto")
                }
            }
            
            // Cargar foto de perfil guardada
            cargarFotoPerfil()
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error cargando datos del perfil: ${e.message}", e)
        }
    }
    
    private fun seleccionarFotoPerfil() {
        try {
            // Crear intent para seleccionar imagen de la galería
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error seleccionando foto: ${e.message}", e)
        }
    }
    
    private fun cargarFotoPerfil() {
        try {
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            val fotoPath = sharedPref.getString("foto_perfil_path", "")
            
            if (fotoPath?.isNotEmpty() == true) {
                val file = File(fotoPath)
                if (file.exists()) {
                    val bitmap = android.graphics.BitmapFactory.decodeFile(fotoPath)
                    ivFotoPerfil.setImageBitmap(bitmap)
                    ivFotoPerfil.visibility = View.VISIBLE
                    ivCamaraPerfil.visibility = View.GONE
                    android.util.Log.d("PerfilUsuario", "Foto cargada exitosamente: $fotoPath")
                } else {
                    android.util.Log.d("PerfilUsuario", "Archivo de foto no existe: $fotoPath")
                }
            } else {
                android.util.Log.d("PerfilUsuario", "No hay foto guardada")
            }
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error cargando foto de perfil: ${e.message}", e)
        }
    }
    
    private fun guardarFotoPerfil(uri: android.net.Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
            
            // Crear archivo para guardar la imagen
            val fileName = "foto_perfil_${System.currentTimeMillis()}.jpg"
            val file = File(filesDir, fileName)
            
            val outputStream = FileOutputStream(file)
            bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.close()
            
            // Guardar la ruta en SharedPreferences
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("foto_perfil_path", file.absolutePath)
                apply()
            }
            
            // Mostrar la imagen en el ImageView
            ivFotoPerfil.setImageBitmap(bitmap)
            ivFotoPerfil.visibility = View.VISIBLE
            ivCamaraPerfil.visibility = View.GONE
            
            android.util.Log.d("PerfilUsuario", "Foto guardada exitosamente: ${file.absolutePath}")
            mostrarMensajeExito("Foto guardada exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error guardando foto de perfil: ${e.message}", e)
            mostrarMensajeError("Error al guardar la foto")
        }
    }
    
    private fun quitarFotoPerfil() {
        try {
            // Eliminar archivo de foto si existe
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            val fotoPath = sharedPref.getString("foto_perfil_path", "")
            
            if (fotoPath?.isNotEmpty() == true) {
                val file = File(fotoPath)
                if (file.exists()) {
                    file.delete()
                    android.util.Log.d("PerfilUsuario", "Archivo de foto eliminado: $fotoPath")
                }
            }
            
            // Limpiar SharedPreferences
            with(sharedPref.edit()) {
                remove("foto_perfil_path")
                apply()
            }
            
            // Mostrar icono de cámara y ocultar foto
            ivFotoPerfil.visibility = View.GONE
            ivCamaraPerfil.visibility = View.VISIBLE
            
            android.util.Log.d("PerfilUsuario", "Foto quitada exitosamente")
            mostrarMensajeExito("Foto quitada exitosamente")
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error quitando foto de perfil: ${e.message}", e)
            mostrarMensajeError("Error al quitar la foto")
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Eliminado: flujo de alias
        
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                guardarFotoPerfil(uri)
            }
        }
        
        if (requestCode == QR_SCANNER_REQUEST_CODE && resultCode == RESULT_OK) {
            val qrText = data?.getStringExtra("qr_text")
            if (!qrText.isNullOrEmpty()) {
                // Ocultar modal QR si vinimos desde él
                ocultarModalQR()
                reproducirVideoAiBin {
                    mostrarLayoutPagoConDatos(qrText)
                }
            }
        }
    }
    
    private fun abrirGuiaUso() {
        try {
            android.util.Log.d("PerfilUsuario", "Abriendo guía de uso")
            
            // Consultar preferencia para mostrar guía
            val sp = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            val showGuide = sp.getBoolean("show_guide", true)
            if (showGuide) {
                val intent = Intent(this, GuiaUsoActivity::class.java)
                startActivity(intent)
            } else {
                android.util.Log.d("PerfilUsuario", "Guía deshabilitada por el usuario")
            }
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error abriendo guía de uso: ${e.message}", e)
        }
    }
    
    private fun cerrarSesion() {
        try {
            android.util.Log.d("PerfilUsuario", "Cerrando sesión")
            
            // Limpiar SharedPreferences
            val sharedPref = getSharedPreferences("NequiCoPrefs", MODE_PRIVATE)
            with(sharedPref.edit()) {
                clear()
                apply()
            }
            
            // Regresar a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
            
        } catch (e: Exception) {
            android.util.Log.e("PerfilUsuario", "Error cerrando sesión: ${e.message}", e)
        }
    }
    
    // Configurar pull-to-refresh personalizado
    private fun configurarPullToRefresh() {
        try {
            android.util.Log.d("PullToRefresh", "Iniciando configuración de pull to refresh")
            
            // Configurar pull-to-refresh en múltiples elementos para asegurar que funcione
            val elementosParaPull = listOf(
                findViewById<LinearLayout>(R.id.llDepositoBajoMonto),
                findViewById<LinearLayout>(R.id.llMonto),
                findViewById<LinearLayout>(R.id.llTotal),
                findViewById<ImageView>(R.id.ivUser),
                findViewById<TextView>(R.id.tvHola),
                findViewById<EditText>(R.id.etNombre),
                findViewById<ImageView>(R.id.ivTuplata),
                findViewById<ImageView>(R.id.ivLock)
            )
            
            elementosParaPull.forEach { elemento ->
                elemento?.setOnTouchListener { _, event ->
                    try {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!isRefreshing) {
                        startY = event.y
                                    android.util.Log.d("PullToRefresh", "Touch down en Y: $startY")
                    }
                    false
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!isRefreshing && startY > 0) {
                        val currentY = event.y
                        val deltaY = currentY - startY
                        
                                    // Permitir pull hacia abajo desde un área más amplia
                                    if (deltaY > 0 && startY < 400f) {
                            val pullDistance = deltaY.coerceAtMost(pullThreshold * 2)
                                        
                                        android.util.Log.d("PullToRefresh", "Deslizando: deltaY=$deltaY, pullDistance=$pullDistance, startY=$startY")
                                        
                                        // Solo animar si no hay error
                                        try {
                            animarElementosHaciaAbajo(pullDistance)
                                        } catch (e: Exception) {
                                            android.util.Log.e("PullToRefresh", "Error animando elementos: ${e.message}")
                                        }
                            
                            // Mostrar círculo de carga cuando se alcanza el threshold
                            if (pullDistance >= pullThreshold) {
                                            try {
                                if (progressBarRefresh.visibility != View.VISIBLE) {
                                    progressBarRefresh.visibility = View.VISIBLE
                                    progressBarRefresh.alpha = 0f
                                    progressBarRefresh.animate()
                                        .alpha(1f)
                                        .setDuration(200)
                                        .start()
                                                }
                                            } catch (e: Exception) {
                                                android.util.Log.e("PullToRefresh", "Error mostrando progress bar: ${e.message}")
                                }
                            }
                            true
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (!isRefreshing && startY > 0) {
                        val currentY = event.y
                        val deltaY = currentY - startY
                        
                                    android.util.Log.d("PullToRefresh", "Touch up: deltaY=$deltaY, threshold=$pullThreshold")
                                    
                                    if (deltaY >= pullThreshold && startY < 400f) {
                                        android.util.Log.d("PullToRefresh", "Iniciando refresh")
                            iniciarRefresh()
                        } else {
                                        android.util.Log.d("PullToRefresh", "Regresando elementos")
                            regresarElementosAPosicionOriginal()
                        }
                        startY = 0f
                    }
                    false
                }
                else -> false
            }
                    } catch (e: Exception) {
                        android.util.Log.e("PullToRefresh", "Error en OnTouchListener: ${e.message}", e)
                        false
                    }
                }
            }
            
            android.util.Log.d("PullToRefresh", "Pull to refresh configurado exitosamente en ${elementosParaPull.count { it != null }} elementos")
        } catch (e: Exception) {
            android.util.Log.e("PullToRefresh", "Error configurando pull to refresh: ${e.message}", e)
            // Si hay error, no configurar el pull to refresh para evitar crashes
        }
    }
    
    // Animar elementos hacia abajo durante el pull
    private fun animarElementosHaciaAbajo(pullDistance: Float) {
        try {
        val translationY = pullDistance * 0.3f // Factor de amortiguación
        
            // Animar todos los elementos especificados de forma segura
            findViewById<ImageView>(R.id.ivUser)?.let { it.translationY = translationY }
            findViewById<TextView>(R.id.tvHola)?.let { it.translationY = translationY }
            findViewById<EditText>(R.id.etNombre)?.let { it.translationY = translationY }
            findViewById<LinearLayout>(R.id.llDepositoBajoMonto)?.let { it.translationY = translationY }
            findViewById<LinearLayout>(R.id.llMonto)?.let { it.translationY = translationY }
            findViewById<LinearLayout>(R.id.llTotal)?.let { it.translationY = translationY }
            findViewById<ImageView>(R.id.ivTuplata)?.let { it.translationY = translationY }
            findViewById<ImageView>(R.id.ivLock)?.let { it.translationY = translationY }
        } catch (e: Exception) {
            android.util.Log.e("PullToRefresh", "Error animando elementos: ${e.message}", e)
        }
    }
    
    // Iniciar el proceso de refresh
    private fun iniciarRefresh() {
        if (isRefreshing) return
        
        isRefreshing = true
        
        // Mantener elementos en posición de pull y mostrar loading
        Handler(Looper.getMainLooper()).postDelayed({
            actualizarSaldoDesdeFirebase()
        }, 500) // Mostrar loading por 0.5 segundos
    }
    
    // Actualizar saldo desde Firebase
    private fun actualizarSaldoDesdeFirebase() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (userNumber.isNotEmpty()) {
                    // Obtener saldo actualizado de Firebase
                    val user = withContext(Dispatchers.IO) {
                        firebaseManager.getUserByNumber(userNumber)
                    }
                    
                    if (user != null) {
                        // Actualizar UI con el nuevo saldo
                        val saldoFormateado = firebaseManager.formatSaldoForDisplay(user.saldo)
                        actualizarSaldoSeparado(saldoFormateado)
                        
                        Toast.makeText(this@InicioActivity, "Saldo actualizado", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("PullToRefresh", "Error actualizando saldo: ${e.message}")
                Toast.makeText(this@InicioActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
            } finally {
                finalizarRefresh()
            }
        }
    }
    
    // Finalizar refresh y regresar elementos a posición original
    private fun finalizarRefresh() {
        isRefreshing = false
        
        // Ocultar círculo de carga
        progressBarRefresh.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction {
                progressBarRefresh.visibility = View.INVISIBLE
            }
            .start()
        
        // Regresar elementos a posición original
        regresarElementosAPosicionOriginal()
    }
    
    // Regresar elementos a su posición original
    private fun regresarElementosAPosicionOriginal() {
        try {
        val duration = 300L
        
            findViewById<ImageView>(R.id.ivUser)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<TextView>(R.id.tvHola)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<EditText>(R.id.etNombre)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<LinearLayout>(R.id.llDepositoBajoMonto)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<LinearLayout>(R.id.llMonto)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<LinearLayout>(R.id.llTotal)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<ImageView>(R.id.ivTuplata)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
            findViewById<ImageView>(R.id.ivLock)?.animate()?.translationY(0f)?.setDuration(duration)?.start()
        
        // Ocultar círculo de carga si está visible
            try {
        if (progressBarRefresh.visibility == View.VISIBLE) {
            progressBarRefresh.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    progressBarRefresh.visibility = View.INVISIBLE
                }
                .start()
        }
            } catch (e: Exception) {
                android.util.Log.e("PullToRefresh", "Error ocultando progress bar: ${e.message}")
            }
        } catch (e: Exception) {
            android.util.Log.e("PullToRefresh", "Error regresando elementos: ${e.message}", e)
        }
    }
    
    // ===== FUNCIONES PARA LAYOUT DE AGREGAR ENVÍO =====
    
    private fun mostrarLayoutAgregarEnvio() {
        try {
            android.util.Log.d("AgregarEnvio", "Mostrando layout de agregar envío")
            
            // Ocultar layout de perfil de usuario
            layoutPerfilUsuario.visibility = View.INVISIBLE
            
            // Mostrar layout de agregar envío
            layoutAgregarEnvio.visibility = View.VISIBLE
            
            // Configurar click listeners del layout de agregar envío
            configurarLayoutAgregarEnvio()
            
        } catch (e: Exception) {
            android.util.Log.e("AgregarEnvio", "Error mostrando layout de agregar envío: ${e.message}", e)
        }
    }
    
    private fun ocultarLayoutAgregarEnvio() {
        try {
            android.util.Log.d("AgregarEnvio", "Ocultando layout de agregar envío")
            
            // Ocultar layout de agregar envío
            layoutAgregarEnvio.visibility = View.INVISIBLE
            
            // Mostrar layout de perfil de usuario
            layoutPerfilUsuario.visibility = View.VISIBLE
            
        } catch (e: Exception) {
            android.util.Log.e("AgregarEnvio", "Error ocultando layout de agregar envío: ${e.message}", e)
        }
    }
    
    private fun configurarLayoutAgregarEnvio() {
        try {
            // Configurar flecha de regreso
            ivFlechaRegresoAgregarEnvio.setOnClickListener {
                ocultarLayoutAgregarEnvio()
            }
            
            // Configurar botón agregar envío
            btnAgregarEnvio.setOnClickListener {
                agregarEnvioPersonalizado()
            }
            
            // Limpiar campos
            etNombreEnvio.text.clear()
            etCantidadEnvio.text.clear()
            
        } catch (e: Exception) {
            android.util.Log.e("AgregarEnvio", "Error configurando layout de agregar envío: ${e.message}", e)
        }
    }
    
    private fun agregarEnvioPersonalizado() {
        try {
            val nombreEnvio = etNombreEnvio.text.toString().trim()
            val cantidadStr = etCantidadEnvio.text.toString().trim()
            
            if (nombreEnvio.isEmpty()) {
                mostrarMensajeError("Por favor ingresa un nombre para el envío")
                return
            }
            
            if (cantidadStr.isEmpty()) {
                mostrarMensajeError("Por favor ingresa una cantidad")
                return
            }
            
            val cantidad = cantidadStr.toLongOrNull()
            if (cantidad == null || cantidad <= 0) {
                mostrarMensajeError("Por favor ingresa una cantidad válida")
                return
            }
            
            // Crear envío usando envio4 (Envioganancia) con la misma lógica que los otros envíos
            crearEnvioEnvioganancia(nombreEnvio, cantidadStr)
            
            // Guardar envío en SharedPreferences para que aparezca en el layout de movimientos
            guardarEnvioEnviogananciaEnSharedPreferences(nombreEnvio, cantidadStr)
            
            // Mostrar mensaje de éxito
            Toast.makeText(this, "Envío agregado exitosamente", Toast.LENGTH_SHORT).show()
            
            // Ocultar layout y regresar al perfil
            ocultarLayoutAgregarEnvio()
            
        } catch (e: Exception) {
            android.util.Log.e("AgregarEnvio", "Error agregando envío personalizado: ${e.message}", e)
            mostrarMensajeError("Error al agregar el envío")
        }
    }
    
    // ======== LECTOR DE QR INTEGRADO ========
    private var cameraProvider: androidx.camera.lifecycle.ProcessCameraProvider? = null
    private var cameraX: androidx.camera.core.Camera? = null
    private var analysisUseCase: androidx.camera.core.ImageAnalysis? = null
    private var cameraQRIsPreviewing: Boolean = false
    private val qrAnalysisExecutor: java.util.concurrent.ExecutorService = java.util.concurrent.Executors.newSingleThreadExecutor()
    


    private val requestCameraPermissionLauncher =
        registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                abrirLayoutLectorQR(useScanner = true)
            } else {
                android.widget.Toast.makeText(this, "Permiso de cámara denegado", android.widget.Toast.LENGTH_LONG).show()
            }
        }

    private fun abrirLayoutLectorQR(useScanner: Boolean) {
        try {
            android.util.Log.d("QRReader", "abrirLayoutLectorQR(useScanner=$useScanner)")
            android.util.Log.d("QRReader", "Ocultando modales...")
            // Ocultar modales
            modalOverlay.visibility = android.view.View.GONE
            modalScrollView.visibility = android.view.View.GONE
            modalScrollViewQR.visibility = android.view.View.GONE

            // Mostrar overlay lector
            android.util.Log.d("QRReader", "Buscando layoutLectorQR...")
            val overlay = findViewById<android.widget.RelativeLayout>(R.id.layoutLectorQR)
            if (overlay == null) {
                android.util.Log.e("QRReader", "layoutLectorQR no encontrado en el layout")
                android.widget.Toast.makeText(this, "No se encontró el layout del lector", android.widget.Toast.LENGTH_LONG).show()
                return
            }
            android.util.Log.d("QRReader", "layoutLectorQR encontrado, mostrando...")
            overlay.visibility = android.view.View.VISIBLE
            overlay.bringToFront()
            overlay.requestLayout()
            overlay.invalidate()
            overlay.alpha = 0f
            overlay.animate().alpha(1f).setDuration(200).withEndAction {
                android.util.Log.d("QRReader", "Overlay animación completada, forzando layout...")
                // Forzar layout completo
                overlay.requestLayout()
                overlay.invalidate()
                overlay.post {
                    android.util.Log.d("QRReader", "Layout forzado completado, iniciando cámara...")
                    startCameraPreviewForQR(useScanner)
                }
            }.start()
            android.util.Log.d("QRReader", "Overlay animación iniciada")

            // Esperar a que el PreviewView esté maquetado antes de iniciar la cámara
            android.util.Log.d("QRReader", "Buscando previewViewQr...")
            val previewView = findViewById<androidx.camera.view.PreviewView>(R.id.previewViewQr)
                            if (previewView != null) {
                    android.util.Log.d("QRReader", "previewViewQr encontrado")
                    android.util.Log.d("QRReader", "PreviewView dimensions: width=${previewView.width}, height=${previewView.height}")
                    android.util.Log.d("QRReader", "PreviewView visibility: ${previewView.visibility}")
                    android.util.Log.d("QRReader", "PreviewView parent visibility: ${previewView.parent?.let { (it as android.view.View).visibility }}")
                    
                    // Forzar layout completo
                    overlay.requestLayout()
                    overlay.invalidate()
                    previewView.requestLayout()
                    previewView.invalidate()
                    
                    // Esperar un poco y verificar
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        android.util.Log.d("QRReader", "Después del delay - PreviewView dimensions: width=${previewView.width}, height=${previewView.height}")
                        android.util.Log.d("QRReader", "Después del delay - isLaidOut: ${androidx.core.view.ViewCompat.isLaidOut(previewView)}")
                        
                                        // Ya no necesitamos verificar el layout aquí porque la cámara se iniciará desde la animación
                android.util.Log.d("QRReader", "PreviewView configurado, esperando animación para iniciar cámara...")
                    }, 500) // Esperar 500ms
                } else {
                    android.util.Log.e("QRReader", "previewViewQr no encontrado")
                }

            // Botón atrás
            android.util.Log.d("QRReader", "Configurando botón atrás...")
            findViewById<android.widget.TextView>(R.id.ivBackQr)?.setOnClickListener {
                cerrarLectorQRyVolverHome()
            }
            // Flash
            android.util.Log.d("QRReader", "Configurando botón flash...")
            findViewById<android.widget.Button>(R.id.btnTorchQr)?.setOnClickListener { toggleTorchQR() }

            // Permiso: solo aseguramos tenerlo; el inicio se hará cuando el PreviewView esté listo
            android.util.Log.d("QRReader", "Verificando permisos de cámara...")
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.d("QRReader", "Solicitando permiso de cámara...")
                requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            } else {
                android.util.Log.d("QRReader", "Permiso de cámara ya otorgado")
            }
        } catch (e: Exception) {
            android.util.Log.e("QRReader", "Error abriendo lector: ${e.message}", e)
        }
    }

    private fun cerrarLectorQRyVolverHome() {
        try {
            stopCameraPreviewForQR()
            findViewById<android.widget.RelativeLayout>(R.id.layoutLectorQR)?.visibility = android.view.View.GONE
            // Rehabilitar ivUser por si quedó deshabilitado
            findViewById<ImageView>(R.id.ivUser)?.isClickable = true
            findViewById<ImageView>(R.id.ivUser)?.isFocusable = true
        } catch (e: Exception) {
            android.util.Log.e("QRReader", "Error cerrando lector: ${e.message}", e)
        }
    }

    private fun startCameraPreviewForQR(useScanner: Boolean = true) {
        try {
            android.util.Log.d("QRReader", "startCameraPreviewForQR(useScanner=$useScanner)")
            
            // Verificar permiso de cámara explícitamente
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.w("QRReader", "Permiso de cámara no otorgado")
                return
            }
            
            val previewView = findViewById<androidx.camera.view.PreviewView>(R.id.previewViewQr)
            if (previewView == null) {
                android.util.Log.e("QRReader", "PreviewView no encontrado")
                return
            }
            
            android.util.Log.d("QRReader", "PreviewView encontrado, dimensiones: ${previewView.width}x${previewView.height}")
            previewView.implementationMode = androidx.camera.view.PreviewView.ImplementationMode.COMPATIBLE
            
            // Forzar que el PreviewView tenga dimensiones
            if (previewView.width <= 0 || previewView.height <= 0) {
                android.util.Log.d("QRReader", "PreviewView sin dimensiones, forzando layout...")
                // Forzar layout del PreviewView
                previewView.measure(
                    android.view.View.MeasureSpec.makeMeasureSpec(1080, android.view.View.MeasureSpec.EXACTLY),
                    android.view.View.MeasureSpec.makeMeasureSpec(2400, android.view.View.MeasureSpec.EXACTLY)
                )
                previewView.layout(0, 0, 1080, 2400)
                android.util.Log.d("QRReader", "PreviewView después del layout forzado, dimensiones: ${previewView.width}x${previewView.height}")
            }
            
            val cameraProviderFuture = androidx.camera.lifecycle.ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    android.util.Log.d("QRReader", "ProcessCameraProvider obtenido")
                    
                    val preview = androidx.camera.core.Preview.Builder()
                        .build()
                        .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                    val options = com.google.mlkit.vision.barcode.BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE)
                        .build()
                    val scanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient(options)

                    val analysisUseCase = androidx.camera.core.ImageAnalysis.Builder()
                        .setBackpressureStrategy(androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    analysisUseCase.setAnalyzer(qrAnalysisExecutor) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val image = com.google.mlkit.vision.common.InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                            scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    if (barcodes.isNotEmpty()) {
                                        val first = barcodes.firstOrNull()?.rawValue
                                        if (!first.isNullOrEmpty()) {
                                            onQrDetected(first, null)
                                        }
                                    }
                                }
                                .addOnCompleteListener { imageProxy.close() }
                        } else {
                            imageProxy.close()
                        }
                    }

                    val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        val camera = cameraProvider.bindToLifecycle(
                            this,
                            cameraSelector,
                            preview,
                            analysisUseCase
                        )
                        cameraX = camera
                        cameraQRIsPreviewing = true
                        android.util.Log.d("QRReader", "Camera bind OK")
                    } catch (exc: Exception) {
                        android.util.Log.e("QRReader", "Error binding camera: ${exc.message}", exc)
                        // Manejar SecurityException específicamente
                        if (exc is SecurityException) {
                            android.util.Log.e("QRReader", "SecurityException: Permiso de cámara denegado en runtime")
                            android.widget.Toast.makeText(this, "Error de permisos de cámara", android.widget.Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    android.util.Log.e("QRReader", "Error getting camera provider: ${e.message}", e)
                }
            }, androidx.core.content.ContextCompat.getMainExecutor(this))
        } catch (e: Exception) {
            android.util.Log.e("QRReader", "Error en startCameraPreviewForQR: ${e.message}", e)
        }
    }

    private fun stopCameraPreviewForQR() {
        try {
            cameraQRIsPreviewing = false
            cameraProvider?.unbindAll()
            analysisUseCase = null
            cameraX = null
        } catch (e: Exception) {
            android.util.Log.e("QRReader", "Error deteniendo cámara: ${e.message}", e)
        }
    }

    private fun toggleTorchQR() {
        try {
            // Verificar permiso de cámara antes de usar flash
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.w("QRReader", "Permiso de cámara no otorgado para flash")
                return
            }
            
            val info = cameraX?.cameraInfo
            val isOn = info?.torchState?.value == androidx.camera.core.TorchState.ON
            cameraX?.cameraControl?.enableTorch(!(isOn ?: false))
        } catch (e: Exception) {
            android.util.Log.e("QRReader", "Error cambiando flash: ${e.message}", e)
            // Manejar SecurityException específicamente
            if (e is SecurityException) {
                android.util.Log.e("QRReader", "SecurityException: Permiso de cámara denegado para flash")
            }
        }
    }

    private fun onQrDetected(texto: String, frameBitmap: android.graphics.Bitmap?) {
        runOnUiThread {
            try {
                // Cerrar modal QR si estuviera abierto y lector QR si activo
                ocultarModalQR()
                cerrarLectorQRyVolverHome()
                // Evitar mostrar texto completo escaneado en overlays/logs de UI
                // No mostrar Toast ni logs visuales con el texto del QR
                reproducirVideoAiBin {
                    mostrarLayoutPagoConDatos(texto)
                }
            } catch (e: Exception) {
                android.util.Log.e("QRReader", "Error en onQrDetected: ${e.message}", e)
            }
        }
    }

    private fun mostrarLayoutPagoConDatos(qrTexto: String) {
        try {
            // Al abrir el layout de Pago, permitir una nueva interacción
            isPagaProcessing = false
            // Preparar layoutPago con transición de derecha a izquierda
            layoutPago.visibility = View.VISIBLE
            layoutPago.translationX = layoutPago.width.toFloat()
            // Cargar imagen de disponible en layout Pago
            try {
                cargarImagenDesdeAssetsPorId(R.id.ivDisponiblePago, "disponible")
                findViewById<ImageView>(R.id.ivDisponiblePago)?.visibility = View.VISIBLE
            } catch (_: Exception) { }
            // Rellenar campos
            val etCelPago = findViewById<android.widget.EditText>(R.id.etCelPago)
            val nombre = extraerNombreDesdeQr(qrTexto)
            if (!nombre.isNullOrBlank()) {
                etCelPago?.setText(nombre)
            } else {
                etCelPago?.setText("")
            }
            etCelPago?.isEnabled = false
            etCelPago?.isClickable = false
            etCelPago?.isFocusable = false

            // Ocultar label externo y mostrar floating label dentro del rectángulo
            findViewById<TextView>(R.id.tvLabelPagoEn)?.visibility = View.GONE
            mostrarFloatingLabelPagoEn()

            // Desactivar ivUser dentro de layoutPago
            findViewById<ImageView>(R.id.ivUser)?.isClickable = false
            findViewById<ImageView>(R.id.ivUser)?.isFocusable = false

            // Configurar back del layoutPago para regresar al principal
            findViewById<ImageView>(R.id.ivBackPago)?.setOnClickListener {
                try {
                    // Habilitar nuevamente posibilidad de pagar en una futura entrada
                    isPagaProcessing = false
                    limpiarCamposPago()
                    layoutPago.animate()
                        .translationX(layoutPago.width.toFloat())
                        .setDuration(300)
                        .withEndAction {
                            layoutPago.visibility = View.INVISIBLE
                            layoutPago.translationX = 0f
                            // Rehabilitar ivUser al salir de Pago
                            findViewById<ImageView>(R.id.ivUser)?.isClickable = true
                            findViewById<ImageView>(R.id.ivUser)?.isFocusable = true
                        }
                        .start()
                } catch (_: Exception) {}
            }

            // Botón Paga habilitado cuando hay monto
            val etCuantoPago = findViewById<android.widget.EditText>(R.id.etCuantoPago)
            val btnPaga = findViewById<android.widget.TextView>(R.id.btnPaga)
            etCuantoPago?.addTextChangedListener(object: android.text.TextWatcher {
                private var editing = false
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    if (editing) return
                    val raw = s?.toString()?.replace("$", "")?.replace(".", "")?.replace(" ", "") ?: ""
                    val enabled = raw.isNotBlank()
                    btnPaga?.alpha = if (enabled) 1f else 0.5f
                    btnPaga?.isClickable = enabled
                    btnPaga?.isFocusable = enabled
                    // Formatear como $ 50.000
                    if (raw.isBlank()) return
                    editing = true
                    try {
                        val digits = raw.filter { it.isDigit() }
                        if (digits.isNotEmpty()) {
                            val formatted = formatearMontoPesos(digits)
                            etCuantoPago.setText(formatted)
                            etCuantoPago.setSelection(formatted.length)
                        }
                    } finally {
                        editing = false
                    }
                }
            })

            // Reproducir fo.bin al pulsar Paga con transición de scale y sin pantalla negra inicial
            btnPaga?.setOnClickListener {
                if (isPagaProcessing) return@setOnClickListener
                // Solo reproducir si está habilitado (alpha 1 y clickable)
                if ((btnPaga.alpha ?: 0f) < 1f || btnPaga.isClickable != true) return@setOnClickListener

                val cantidadTextoPago = try { etCuantoPago?.text?.toString() ?: "" } catch (_: Exception) { "" }

                // Validaciones de tope y saldo de Firebase ANTES de iniciar la animación/video
                CoroutineScope(Dispatchers.Main).launch {
                    // 1) Tope máximo 10.000.000
                    if (excedeMontoMaximo(cantidadTextoPago)) {
                        mostrarLayoutCancelado()
                        return@launch
                    }
                    // 2) Saldo suficiente en Firebase
                    val saldoSuficiente = withContext(Dispatchers.IO) { validarSaldoSuficienteAsync(cantidadTextoPago) }
                    if (!saldoSuficiente) {
                        mostrarLayoutCancelado()
                        return@launch
                    }

                    try {
                        // Marcar en proceso y deshabilitar el botón para evitar múltiples acciones
                        isPagaProcessing = true
                        btnPaga.isEnabled = false
                        btnPaga.isClickable = false
                        btnPaga.alpha = 0.5f
                        // Ya no ocultamos layoutPago inmediatamente; se ocultará 500 ms después de iniciar fo.bin
                        val rootView = findViewById<View>(android.R.id.content) as ViewGroup
                        val videoLayout = FrameLayout(this@InicioActivity).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            // Fondo blanco para el overlay del fo.bin
                            setBackgroundColor(android.graphics.Color.WHITE)
                            scaleX = 0f
                            scaleY = 0f
                            alpha = 0f
                        }
                        val videoView = VideoView(this@InicioActivity).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            ).apply { gravity = Gravity.CENTER }
                        }
                        videoLayout.addView(videoView)
                        rootView.addView(videoLayout)
                        var cadenaIniciada = false
                        var comprobanteMostradoFo = false
                        var envio5Guardado = false
                        var listoPantallaMostrado = false
                    fun iniciarCadenaPostVideo(origen: String) {
                        if (cadenaIniciada) return
                        cadenaIniciada = true
                        android.util.Log.d("Pago", "Iniciando cadena post-fo.bin (origen=$origen)")
                        videoLayout.animate().alpha(0f).scaleX(0f).scaleY(0f).setDuration(800)
                            .withEndAction {
                                try { rootView.removeView(videoLayout) } catch (_: Exception) {}
                                val nombreEscaneado = try { findViewById<android.widget.EditText>(R.id.etCelPago)?.text?.toString() ?: "" } catch (_: Exception) { "" }
                                val cantidadEscaneada = try { findViewById<android.widget.EditText>(R.id.etCuantoPago)?.text?.toString() ?: "" } catch (_: Exception) { "" }
                                if (!comprobanteMostradoFo) {
                                    android.util.Log.d("ComprobanteQR", "Mostrando comprobante QR (origen=$origen)")
                                    mostrarComprobanteQRAhora(nombreEscaneado, cantidadEscaneada)
                                    // Mostrar listo y listopantalla a la vez (listopantalla por encima)
                                    try {
                                        mostrarOverlayListo { try { limpiarCamposPago() } catch (_: Exception) {} }
                                        if (!listoPantallaMostrado) {
                                            mostrarListopantallaAnim3s { }
                                            listoPantallaMostrado = true
                                        }
                                    } catch (_: Exception) {}
                                    comprobanteMostradoFo = true
                                }
                                // Guardar envio5 una sola vez por ciclo de Paga
                                if (!envio5Guardado) {
                                    try { guardarEnvio5EnSharedPreferences(nombreEscaneado, cantidadEscaneada) } catch (_: Exception) {}
                                    envio5Guardado = true
                                }
                                // Limpiar marcadores
                                lastQrNombre = ""; lastQrCantidad = ""
                                // Ya mostramos listo.bin justo antes de terminar fo.bin; aquí solo mostramos listopantalla (una sola vez)
                                if (!listoPantallaMostrado) {
                                    android.util.Log.d("ListoPantalla", "Mostrando listopantalla.bin (origen=$origen)")
                                    mostrarListopantallaAnim3s { }
                                    listoPantallaMostrado = true
                                }
                            }.start()
                    }
                    // Preparar listeners ANTES de cargar el video para no perder el callback
                    videoView.setOnPreparedListener { mediaPlayer ->
                        android.util.Log.d("Pago", "onPrepared fo.bin, duration=${'$'}{mediaPlayer.duration}ms")
                        mediaPlayer.isLooping = false
                        try { mediaPlayer.seekTo(1) } catch (_: Exception) {}
                        mediaPlayer.setOnInfoListener { _, what, _ ->
                            if (what == android.media.MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                                // Animación de entrada (scale) al primer frame
                                videoLayout.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(400).start()
                                // Ocultar layoutPago 500 ms después de que empieza a renderizar el video
                                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                    try { layoutPago.visibility = View.GONE } catch (_: Exception) {}
                                }, 500)
                            }
                            false
                        }
                        videoView.start()
                        // Mostrar comprobante y overlay listo poco antes de terminar fo.bin
                        val preEndShow = (mediaPlayer.duration - 600).coerceAtLeast(200)
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            if (!comprobanteMostradoFo) {
                                val nombreEscaneado = try { findViewById<android.widget.EditText>(R.id.etCelPago)?.text?.toString() ?: "" } catch (_: Exception) { "" }
                                val cantidadEscaneada = try { findViewById<android.widget.EditText>(R.id.etCuantoPago)?.text?.toString() ?: "" } catch (_: Exception) { "" }
                                android.util.Log.d("ComprobanteQR", "Mostrando comprobante QR (origen=preEnd)")
                                mostrarComprobanteQRAhora(nombreEscaneado, cantidadEscaneada)
                                try {
                                    mostrarOverlayListo { try { limpiarCamposPago() } catch (_: Exception) {} }
                                    if (!listoPantallaMostrado) {
                                        mostrarListopantallaAnim3s { }
                                        listoPantallaMostrado = true
                                    }
                                } catch (_: Exception) {}
                                if (!envio5Guardado) {
                                    try { guardarEnvio5EnSharedPreferences(nombreEscaneado, cantidadEscaneada) } catch (_: Exception) {}
                                    envio5Guardado = true
                                }
                                // Restar del saldo al pulsar Paga (preEnd)
                                try { restarDelSaldo(cantidadEscaneada) } catch (_: Exception) {}
                                lastQrNombre = ""; lastQrCantidad = ""
                                comprobanteMostradoFo = true
                            }
                        }, preEndShow.toLong())
                        // Fallback: iniciar cadena solo DESPUÉS de la duración estimada del video
                        val fallback = if (mediaPlayer.duration in 400..20000) (mediaPlayer.duration + 300).toLong() else 2500L
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            if (videoLayout.parent != null) {
                                try { videoView.stopPlayback() } catch (_: Exception) {}
                                iniciarCadenaPostVideo("fallback_post_duration")
                            }
                        }, fallback)
                    }
                    videoView.setOnErrorListener { _, what, extra ->
                        android.util.Log.e("Pago", "Error en fo.bin what=${'$'}what extra=${'$'}extra; encadenando overlays y comprobante")
                        iniciarCadenaPostVideo("error")
                        // Reset para permitir otro intento
                        isPagaProcessing = false
                        try {
                            val btnPagaRef = findViewById<TextView>(R.id.btnPaga)
                            btnPagaRef?.isEnabled = true
                            btnPagaRef?.isClickable = true
                            btnPagaRef?.alpha = 1f
                        } catch (_: Exception) {}
                        true
                    }
                    // Cargar y reproducir fo.bin (después de configurar listeners)
                    VideoLoader.cargarVideoDesdeAssets(this@InicioActivity, videoView, "fo")
                    videoView.setOnCompletionListener {
                        android.util.Log.d("Pago", "fo.bin onCompletion")
                        iniciarCadenaPostVideo("completion")
                        // Reset para permitir otro pago posterior
                        isPagaProcessing = false
                        try {
                            val btnPagaRef = findViewById<TextView>(R.id.btnPaga)
                            btnPagaRef?.isEnabled = true
                            btnPagaRef?.isClickable = true
                            btnPagaRef?.alpha = 1f
                        } catch (_: Exception) {}
                    }
                } catch (e: Exception) {
                    android.util.Log.e("Pago", "Error reproduciendo fo.bin: ${e.message}")
                    // Reset para permitir otro intento
                    isPagaProcessing = false
                    try {
                        val btnPagaRef = findViewById<TextView>(R.id.btnPaga)
                        btnPagaRef?.isEnabled = true
                        btnPagaRef?.isClickable = true
                        btnPagaRef?.alpha = 1f
                    } catch (_: Exception) {}
                }
                }
            }

            // Floating label para ¿Cuanto? en layoutPago
            etCuantoPago?.setOnFocusChangeListener { _, hasFocus ->
                val hasContent = !etCuantoPago.text.isNullOrBlank()
                actualizarFloatingLabelCuantoPago(hasFocus, hasContent)
            }
            // Inicializar estado del label según contenido
            actualizarFloatingLabelCuantoPago(etCuantoPago?.hasFocus() == true, !etCuantoPago?.text.isNullOrBlank())

            layoutPago.animate()
                .translationX(0f)
                .setDuration(300)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        } catch (e: Exception) {
            android.util.Log.e("Pago", "Error mostrando layoutPago: ${e.message}", e)
        }
    }

    private fun mostrarFloatingLabelPagoEn() {
        try {
            val etCelPago = findViewById<EditText>(R.id.etCelPago) ?: return
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelPagoEn)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelPagoEn
                    text = "Paga en"
                    textSize = 12f
                    // Gris claro consistente con nombre escaneado
                    setTextColor(android.graphics.Color.parseColor("#9E9E9E"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                val parentLayout = etCelPago.parent as ViewGroup
                parentLayout.addView(floatingLabel)
            }
            // Posicionar el floating label más arriba dentro del rectángulo de etCelPago
            val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
            params.leftMargin = etCelPago.left + 35
            params.topMargin = etCelPago.top - 4
            floatingLabel.layoutParams = params
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.alpha = 0f
            floatingLabel.animate().alpha(1f).setDuration(200).start()
        } catch (_: Exception) { }
    }

    private fun limpiarCamposPago() {
        try {
            val etCelPago = findViewById<EditText>(R.id.etCelPago)
            val etCuantoPago = findViewById<EditText>(R.id.etCuantoPago)
            val btnPaga = findViewById<TextView>(R.id.btnPaga)
            etCelPago?.setText("")
            etCuantoPago?.setText("")
            // Restaurar label externo y ocultar floating label
            findViewById<TextView>(R.id.tvLabelPagoEn)?.visibility = View.VISIBLE
            findViewById<TextView>(R.id.tvFloatingLabelPagoEn)?.let { lbl ->
                lbl.animate().alpha(0f).setDuration(150).withEndAction { lbl.visibility = View.GONE }.start()
            }
            // Deshabilitar botón pagar hasta que haya monto
            btnPaga?.alpha = 0.5f
            btnPaga?.isClickable = false
            btnPaga?.isFocusable = false
            // Rehabilitar ivUser por si quedó deshabilitado en Pago
            findViewById<ImageView>(R.id.ivUser)?.isClickable = true
            findViewById<ImageView>(R.id.ivUser)?.isFocusable = true
        } catch (_: Exception) { }
    }

    // Extrae el nombre desde el texto del QR siguiendo la lógica solicitada
    private fun extraerNombreDesdeQr(textoOriginal: String): String? {
        val texto = textoOriginal.trim()
        try {
            // 1) Patrón 59 + 2 dígitos de longitud + resto del texto
            run {
                val regex = Regex("59(\\d{2})(.+)")
                val match = regex.find(texto)
                if (match != null) {
                    val longitud = match.groupValues[1].toIntOrNull()
                    val resto = match.groupValues[2]
                    if (longitud != null && longitud > 0 && longitud <= resto.length) {
                        val posible = resto.substring(0, longitud).trim()
                        if (posible.isNotEmpty()) return posible
                    }
                }
            }
            // 2) Secuencia de 2-4 palabras en mayúsculas/dígitos/guion_bajo
            run {
                val regex = Regex("([A-ZÁÉÍÓÚÑ0-9_]{2,}(?:\\s+[A-ZÁÉÍÓÚÑ0-9_]{2,}){1,3})")
                val match = regex.find(texto)
                if (match != null) {
                    val nombreCompuesto = match.groupValues[1].trim()
                    if (nombreCompuesto.replace(" ", "").any { !it.isDigit() }) {
                        return nombreCompuesto
                    }
                }
            }
            // 3) Fallback: primera palabra larga no solo dígitos
            run {
                val regex = Regex("[A-ZÁÉÍÓÚÑ0-9_]{4,}")
                val todos = regex.findAll(texto).toList()
                for (m in todos) {
                    val palabra = m.value
                    if (palabra.any { !it.isDigit() }) {
                        return palabra.trim()
                    }
                }
            }
        } catch (_: Exception) { }
        return null
    }

    private fun actualizarFloatingLabelCuantoPago(hasFocus: Boolean, hasContent: Boolean) {
        val etCuantoPago = findViewById<EditText>(R.id.etCuantoPago)
        if (etCuantoPago == null) return
        if (hasFocus || hasContent) {
            etCuantoPago.hint = ""
            var floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuantoPago)
            if (floatingLabel == null) {
                floatingLabel = TextView(this).apply {
                    id = R.id.tvFloatingLabelCuantoPago
                    text = "¿Cuanto?"
                    textSize = 12f
                    setTextColor(android.graphics.Color.parseColor("#db0082"))
                    layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                val parentLayout = etCuantoPago.parent as ViewGroup
                parentLayout.addView(floatingLabel)
                val params = floatingLabel.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = etCuantoPago.left + 45
                params.topMargin = etCuantoPago.top + 25
                floatingLabel.layoutParams = params
            }
            floatingLabel.visibility = View.VISIBLE
            floatingLabel.animate().alpha(1f).setDuration(200).start()
        } else {
            val floatingLabel = findViewById<TextView>(R.id.tvFloatingLabelCuantoPago)
            floatingLabel?.animate()?.alpha(0f)?.setDuration(200)?.withEndAction {
                floatingLabel?.visibility = View.INVISIBLE
            }?.start()
            etCuantoPago.hint = "¿Cuanto?"
        }
    }

    private fun formatearMontoPesos(digits: String): String {
        // Inserta separadores de miles y prefijo $
        val number = digits.trimStart('0')
        if (number.isEmpty()) return ""
        val reversed = number.reversed()
        val sb = StringBuilder()
        for (i in reversed.indices) {
            if (i > 0 && i % 3 == 0) sb.append('.')
            sb.append(reversed[i])
        }
        val miles = sb.reverse().toString()
        return "$ $miles"
    }

    private fun reproducirVideoAiBin(onComplete: () -> Unit) {
        try {
            // Crear layout para el video
            val videoLayout = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
            }
            
            // Crear VideoView
            val videoAI = VideoView(this).apply {
                layoutParams = ViewGroup.LayoutParams(800, 800)
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setZOrderOnTop(true)
            }
            
            // Centrar el video
            val videoContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            
            val videoParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            videoContainer.addView(videoAI, videoParams)
            videoLayout.addView(videoContainer)
            
            // Agregar el layout al root
            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
            rootView.addView(videoLayout)
            
            // Configurar el video
            VideoLoader.cargarVideoDesdeAssets(this, videoAI, "ai")
            
            // Configurar propiedades iniciales
            videoLayout.alpha = 0f
            videoLayout.scaleX = 0f
            videoLayout.scaleY = 0f
            
            // Agregar listener de error
            videoAI.setOnErrorListener { mp, what, extra ->
                android.util.Log.e("VideoAI", "Error reproduciendo video: what=$what, extra=$extra")
                // Continuar sin video si hay error
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        Handler(Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(450)
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    onComplete() // Llamar callback cuando termine
                                }.start()
                        }, 200)
                    }.start()
                true
            }
            
            videoAI.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = false
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                mediaPlayer.setVolume(0f, 0f) // Sin audio
                
                // Iniciar el video
                videoAI.start()
                
                // Mostrar el layout con animación
                videoLayout.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(850)
                    .withEndAction {
                        // Al finalizar AI, esperar 1 segundo y luego llamar callback
                        Handler(Looper.getMainLooper()).postDelayed({
                            videoLayout.animate()
                                .alpha(0f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(300)
                                .withEndAction {
                                    rootView.removeView(videoLayout)
                                    onComplete() // Llamar callback cuando termine
                                }.start()
                        }, 500) // Esperar 0.5 segundos
                    }.start()
            }
            
        } catch (e: Exception) {
            android.util.Log.e("VideoAI", "Error reproduciendo ai.bin: ${e.message}", e)
            // Si hay error, llamar callback inmediatamente
            onComplete()
        }
    }

} 