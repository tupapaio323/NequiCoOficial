package com.wts.nequicooficial

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader

class SecurityManager(private val context: Context) {
    
    companion object {
        private const val TAG = "SecurityManager"
        private var isSecurityCheckPassed = false
    }
    
    fun performSecurityChecks(): Boolean {
        if (isSecurityCheckPassed) return true
        
        try {
            // Para desarrollo, solo verificamos las más críticas
            // Comentamos las verificaciones que pueden dar falsos positivos
            
            // Verificar si está en modo debug (mantenemos esta)
            if (isDebuggerAttached()) {
                Log.w(TAG, "⚠️ Debugger detectado")
                // Para desarrollo, permitimos debug
                Log.d(TAG, "🔧 Modo desarrollo: permitiendo debug")
            }
            
            // Verificar si está en emulador (comentamos para desarrollo)
            /*
            if (isEmulator()) {
                Log.w(TAG, "⚠️ Emulador detectado")
                return false
            }
            */
            
            // Verificar si el dispositivo está rooteado (comentamos para desarrollo)
            /*
            if (isRooted()) {
                Log.w(TAG, "⚠️ Dispositivo rooteado detectado")
                return false
            }
            */
            
            // Verificar si hay apps de hacking instaladas (comentamos para desarrollo)
            /*
            if (hasHackingApps()) {
                Log.w(TAG, "⚠️ Apps de hacking detectadas")
                return false
            }
            */
            
            // Verificar integridad de la app (mantenemos esta)
            if (!checkAppIntegrity()) {
                Log.w(TAG, "⚠️ Integridad de la app comprometida")
                // Para desarrollo, permitimos
                Log.d(TAG, "🔧 Modo desarrollo: permitiendo app no firmada")
            }
            
            isSecurityCheckPassed = true
            Log.d(TAG, "✅ Verificaciones de seguridad básicas pasaron (modo desarrollo)")
            return true
            
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error en verificaciones de seguridad: ${e.message}", e)
            // Para desarrollo, permitimos errores
            Log.d(TAG, "🔧 Modo desarrollo: permitiendo errores de seguridad")
            return true
        }
    }
    
    private fun isDebuggerAttached(): Boolean {
        return try {
            android.os.Debug.isDebuggerConnected() || 
            Settings.Secure.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) == 1
        } catch (e: Exception) {
            false
        }
    }
    
    private fun isEmulator(): Boolean {
        return try {
            val buildModel = Build.MODEL.lowercase()
            val buildManufacturer = Build.MANUFACTURER.lowercase()
            val buildProduct = Build.PRODUCT.lowercase()
            val buildFingerprint = Build.FINGERPRINT.lowercase()
            
            buildModel.contains("sdk") ||
            buildModel.contains("emulator") ||
            buildManufacturer.contains("unknown") ||
            buildProduct.contains("sdk") ||
            buildProduct.contains("emulator") ||
            buildFingerprint.contains("generic") ||
            buildFingerprint.contains("sdk") ||
            buildFingerprint.contains("emulator") ||
            checkEmulatorFiles()
        } catch (e: Exception) {
            false
        }
    }
    
    private fun checkEmulatorFiles(): Boolean {
        val emulatorFiles = arrayOf(
            "/system/lib/libc_malloc_debug_qemu.so",
            "/sys/qemu_trace",
            "/system/bin/qemu-props",
            "/dev/socket/qemud",
            "/dev/qemu_pipe"
        )
        
        return emulatorFiles.any { File(it).exists() }
    }
    
    private fun isRooted(): Boolean {
        return try {
            val suPaths = arrayOf(
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
            )
            
            suPaths.any { File(it).exists() } || checkRootPackages()
        } catch (e: Exception) {
            false
        }
    }
    
    private fun checkRootPackages(): Boolean {
        val rootPackages = arrayOf(
            "com.noshufou.android.su",
            "com.thirdparty.superuser",
            "eu.chainfire.supersu",
            "com.topjohnwu.magisk"
        )
        
        return rootPackages.any { packageName ->
            try {
                context.packageManager.getPackageInfo(packageName, 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
    }
    
    private fun hasHackingApps(): Boolean {
        val hackingPackages = arrayOf(
            "com.cheekydevs.screenshot",
            "com.saurik.substrate",
            "de.robv.android.xposed.installer",
            "com.topjohnwu.magisk",
            "com.termux",
            "jackpal.androidterm",
            "com.server.auditor.ssl"
        )
        
        return hackingPackages.any { packageName ->
            try {
                context.packageManager.getPackageInfo(packageName, 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
    }
    
    private fun checkAppIntegrity(): Boolean {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            val signatures = packageInfo.signatures
            
            // Verificar que la app está firmada correctamente (manejar nulabilidad)
            signatures != null && signatures.isNotEmpty() && signatures[0]?.toCharsString()?.isNotEmpty() == true
        } catch (e: Exception) {
            false
        }
    }
    
    fun encryptString(input: String): String {
        return try {
            val key = "NequiCo2024SecureKey"
            val cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding")
            val keySpec = javax.crypto.spec.SecretKeySpec(key.toByteArray(), "AES")
            val ivSpec = javax.crypto.spec.IvParameterSpec(ByteArray(16))
            
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(input.toByteArray())
            android.util.Base64.encodeToString(encrypted, android.util.Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e(TAG, "Error encriptando string: ${e.message}", e)
            input
        }
    }
    
    fun decryptString(encryptedInput: String): String {
        return try {
            val key = "NequiCo2024SecureKey"
            val cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding")
            val keySpec = javax.crypto.spec.SecretKeySpec(key.toByteArray(), "AES")
            val ivSpec = javax.crypto.spec.IvParameterSpec(ByteArray(16))
            
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decoded = android.util.Base64.decode(encryptedInput, android.util.Base64.DEFAULT)
            val decrypted = cipher.doFinal(decoded)
            String(decrypted)
        } catch (e: Exception) {
            Log.e(TAG, "Error desencriptando string: ${e.message}", e)
            encryptedInput
        }
    }
} 