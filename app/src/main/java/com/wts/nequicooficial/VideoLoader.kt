package com.wts.nequicooficial

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import java.io.File
import java.io.FileOutputStream

object VideoLoader {
    
    private const val ENCRYPTION_PASSWORD = "ladroneshijueputas"
    
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
    
    // Función para desencriptar video .bin desde assets
    private fun desencriptarVideo(context: Context, nombreArchivo: String): ByteArray? {
        return try {
            Log.d("VideoLoader", "🔐 Intentando desencriptar video .bin: $nombreArchivo")
            val inputStream = context.assets.open(nombreArchivo)
            val encryptedBytes = inputStream.readBytes()
            inputStream.close()

            Log.d("VideoLoader", "📦 Bytes encriptados leídos: ${encryptedBytes.size}")

            // Generar clave AES
            val key = generarClaveAES(ENCRYPTION_PASSWORD)
            val cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding")
            val keySpec = javax.crypto.spec.SecretKeySpec(key, "AES")
            val ivSpec = javax.crypto.spec.IvParameterSpec(ByteArray(16) { 0 }) // IV de ceros
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, keySpec, ivSpec)

            // Desencriptar
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            Log.d("VideoLoader", "🔓 Bytes desencriptados: ${decryptedBytes.size}")

            decryptedBytes
        } catch (e: Exception) {
            Log.e("VideoLoader", "❌ Error desencriptando video .bin: ${e.message}", e)
            null
        }
    }
    
    // Función para cargar video desencriptado en VideoView
    fun cargarVideoDesdeAssets(context: Context, videoView: VideoView, nombreSinExtension: String) {
        try {
            Log.d("VideoLoader", "🔄 Cargando video: $nombreSinExtension")
            
            val nombreArchivo = "$nombreSinExtension.bin"
            val videoBytes = desencriptarVideo(context, nombreArchivo)
            
            if (videoBytes != null) {
                // Crear archivo temporal
                val tempFile = File(context.cacheDir, "$nombreSinExtension.mp4")
                FileOutputStream(tempFile).use { fos ->
                    fos.write(videoBytes)
                }
                
                // Configurar video en VideoView
                val videoUri = Uri.fromFile(tempFile)
                videoView.setVideoURI(videoUri)
                
                Log.d("VideoLoader", "✅ Video cargado exitosamente: $nombreSinExtension")
            } else {
                Log.e("VideoLoader", "❌ No se pudo desencriptar el video: $nombreSinExtension")
            }
        } catch (e: Exception) {
            Log.e("VideoLoader", "❌ Error cargando video $nombreSinExtension: ${e.message}", e)
        }
    }
} 