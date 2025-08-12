package com.wts.nequicooficial

/**
 * EJEMPLO DE USO DEL SISTEMA DE ENCRIPTACIÓN DE PNGs
 * 
 * PASO 1: Encriptar todos los PNGs (ejecutar una vez durante desarrollo)
 * 
 * En Android Studio, abre la consola y ejecuta:
 * 
 * ```kotlin
 * // En la consola de Kotlin de Android Studio
 * import com.wts.nequicooficial.EncriptadorPNGs
 * EncriptadorPNGs.encriptarTodosLosPNGs()
 * ```
 * 
 * PASO 2: Usar PNGs desencriptados en el código
 * 
 * En lugar de:
 * imageView.setImageResource(R.drawable.mi_imagen)
 * 
 * Usar:
 * establecerPNGDesencriptado(imageView, R.drawable.mi_imagen)
 * 
 * PASO 3: Verificar que los PNGs se encriptaron correctamente
 * 
 * ```kotlin
 * // Verificar un PNG específico
 * val archivoPNG = "app/src/main/res/drawable/mi_imagen.png"
 * val esValido = EncriptadorPNGs.verificarPNGEncriptado(archivoPNG)
 * println("PNG válido: $esValido")
 * ```
 * 
 * NOTAS IMPORTANTES:
 * 
 * 1. La clave de encriptación está en ENCRYPTION_KEY = "NequiCo2024!@#"
 * 2. En producción, esta clave debería estar más segura y no hardcodeada
 * 3. El sistema tiene fallback: si la desencriptación falla, usa el método normal
 * 4. Solo los PNGs en drawable/ serán encriptados
 * 5. Los archivos se sobrescriben con su versión encriptada
 * 
 * FUNCIONES DISPONIBLES:
 * 
 * - EncriptadorPNGs.encriptarTodosLosPNGs(): Encripta todos los PNGs
 * - establecerPNGDesencriptado(imageView, resourceId): Carga PNG desencriptado
 * - obtenerPNGDesencriptado(resourceId): Obtiene Bitmap desencriptado
 * - verificarPNGEncriptado(filePath): Verifica que un PNG se puede desencriptar
 * 
 * EJEMPLO DE IMPLEMENTACIÓN:
 * 
 * ```kotlin
 * // En lugar de esto:
 * ivLogo.setImageResource(R.drawable.logo)
 * 
 * // Usar esto:
 * establecerPNGDesencriptado(ivLogo, R.drawable.logo)
 * ```
 */ 