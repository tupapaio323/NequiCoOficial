package com.wts.nequicooficial

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Utilidad para encriptar PNGs durante el desarrollo
 * Uso: EncriptadorPNGs.encriptarTodosLosPNGs()
 */
object EncriptadorPNGs {
    
    // Clave de encriptación (debe coincidir con la de InicioActivity)
    // AES requiere clave de 16, 24 o 32 bytes
    private const val ENCRYPTION_KEY = "NequiCo2024!@#$%"
    
    // Función para encriptar un archivo PNG
    private fun encriptarPNG(inputStream: InputStream): ByteArray {
        try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(ENCRYPTION_KEY.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            
            val inputBytes = inputStream.readBytes()
            return cipher.doFinal(inputBytes)
        } catch (e: Exception) {
            println("Error encriptando PNG: ${e.message}")
            throw e
        }
    }
    
    // Función para encriptar todos los PNGs del proyecto
    fun encriptarTodosLosPNGs() {
        println("🔐 INICIANDO ENCRIPTACIÓN DE TODOS LOS PNGs")
        
        val drawableDir = File("app/src/main/res/drawable")
        if (!drawableDir.exists()) {
            println("❌ Directorio drawable no encontrado: ${drawableDir.absolutePath}")
            return
        }
        
        var contador = 0
        var errores = 0
        
        drawableDir.listFiles()?.forEach { file ->
            if (file.extension.lowercase() == "png") {
                try {
                    val inputStream = FileInputStream(file)
                    val encryptedBytes = encriptarPNG(inputStream)
                    inputStream.close()
                    
                    // Guardar archivo encriptado
                    val outputStream = FileOutputStream(file)
                    outputStream.write(encryptedBytes)
                    outputStream.close()
                    
                    contador++
                    println("✅ Encriptado: ${file.name}")
                } catch (e: Exception) {
                    errores++
                    println("❌ Error encriptando ${file.name}: ${e.message}")
                }
            }
        }
        
        println("🎉 ENCRIPTACIÓN COMPLETADA:")
        println("   ✅ Archivos encriptados: $contador")
        println("   ❌ Errores: $errores")
        println("   📁 Directorio procesado: ${drawableDir.absolutePath}")
    }
    
    // Función para desencriptar un PNG (para verificar)
    fun desencriptarPNG(encryptedBytes: ByteArray): ByteArray {
        try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(ENCRYPTION_KEY.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            
            return cipher.doFinal(encryptedBytes)
        } catch (e: Exception) {
            println("Error desencriptando PNG: ${e.message}")
            throw e
        }
    }
    
    // Función para verificar que un PNG se puede desencriptar
    fun verificarPNGEncriptado(filePath: String): Boolean {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                println("❌ Archivo no encontrado: $filePath")
                return false
            }
            
            val inputStream = FileInputStream(file)
            val encryptedBytes = inputStream.readBytes()
            inputStream.close()
            
            val decryptedBytes = desencriptarPNG(encryptedBytes)
            
            // Verificar que los bytes desencriptados parecen un PNG válido
            val pngHeader = byteArrayOf(0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte())
            val isPNG = decryptedBytes.size >= 8 && 
                       decryptedBytes.take(4).toByteArray().contentEquals(pngHeader)
            
            if (isPNG) {
                println("✅ PNG verificado correctamente: ${file.name}")
            } else {
                println("❌ El archivo no parece ser un PNG válido: ${file.name}")
            }
            
            isPNG
        } catch (e: Exception) {
            println("❌ Error verificando PNG: ${e.message}")
            false
        }
    }
} 