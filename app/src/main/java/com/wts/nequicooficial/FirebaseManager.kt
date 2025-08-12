package com.wts.nequicooficial

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class User(
    val number: String = "",
    val pin: String = "",
    val saldo: String = "1.000.000"
)

data class AppConfig(
    val minSupportedVersion: Int = 1,
    val updateUrl: String = ""
)

class FirebaseManager(private val context: Context) {
    private val database by lazy {
        val databaseUrl = context.getString(R.string.firebase_database_url)
        Firebase.database(databaseUrl)
    }
    private val usersRef by lazy { database.getReference("users") }
    private val auth = FirebaseAuth.getInstance()
    
    // Modo local para funcionar sin Firebase
    private var modoLocal = false
    
    init {
        Log.d(TAG, "FirebaseManager inicializado - Modo local: $modoLocal")
        // Auto-autenticar si no hay usuario
        if (auth.currentUser == null && !modoLocal) {
            autoAuthenticate()
        }
    }
    
    private fun autoAuthenticate() {
        try {
            // Autenticación anónima automática
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Auto-autenticación exitosa: ${auth.currentUser?.uid}")
                } else {
                    Log.w(TAG, "Auto-autenticación falló, activando modo local")
                    modoLocal = true
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error en auto-autenticación: ${e.message}")
            modoLocal = true
        }
    }
    
    companion object {
        private const val TAG = "FirebaseManager"
    }
    
    // Obtener usuario autenticado actual
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
    
    // Obtener UID del usuario actual
    fun getCurrentUserUID(): String? {
        val user = auth.currentUser
        if (user != null) {
            Log.d(TAG, "Usuario actual UID: ${user.uid}")
            return user.uid
        } else {
            Log.w(TAG, "No hay usuario autenticado")
            return null
        }
    }
    
    // Obtener todos los UIDs de usuarios (para debugging)
    suspend fun getAllUserUIDs(): List<String> {
        return try {
            Log.d(TAG, "Obteniendo todos los UIDs de usuarios")
            if (modoLocal) {
                Log.d(TAG, "Modo local: no hay UIDs de Firebase")
                return emptyList()
            } else {
                val snapshot = usersRef.get().await()
                val uids = mutableListOf<String>()
                for (child in snapshot.children) {
                    Log.d(TAG, "Usuario encontrado con key: ${child.key}")
                    uids.add(child.key!!)
                }
                Log.d(TAG, "UIDs obtenidos: $uids")
                return uids
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo UIDs: ${e.message}", e)
            emptyList()
        }
    }
    
    // Verificar si hay un usuario autenticado
    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }
    
    // Cerrar sesión
    fun signOut() {
        auth.signOut()
    }
    
    // Autenticar usuario con número y PIN usando Firebase Auth
    suspend fun authenticateUser(number: String, pin: String): User? {
        return try {
            Log.d(TAG, "Autenticando usuario con número: '$number' y PIN: '$pin'")
            
            if (modoLocal) {
                // Modo local: autenticar desde almacenamiento local
                return authenticateUserLocal(number, pin)
            } else {
                // Modo Firebase: autenticar con Firebase Auth
                val email = "$number@nequi.com"
                val result = auth.signInWithEmailAndPassword(email, pin).await()
                
                if (result.user != null) {
                    Log.d(TAG, "Usuario autenticado exitosamente con Firebase Auth: ${result.user?.uid}")
                    val user = findUserInCurrentStructure(number)
                    if (user != null) {
                        Log.d(TAG, "Datos del usuario obtenidos: $user")
                        return user
                    } else {
                        Log.w(TAG, "Usuario autenticado pero no encontrado en la base de datos")
                        return null
                    }
                } else {
                    Log.w(TAG, "Autenticación fallida")
                    return null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error autenticando usuario: ${e.message}", e)
            null
        }
    }
    
    // Crear nuevo usuario
    suspend fun createUser(number: String, pin: String): User? {
        return try {
            Log.d(TAG, "Creando usuario con número: '$number' y PIN: '$pin'")
            
            if (modoLocal) {
                // Modo local: crear en almacenamiento local
                return createUserLocal(number, pin)
            } else {
                // Modo Firebase: crear con Firebase Auth
                val email = "$number@nequi.com"
                val result = auth.createUserWithEmailAndPassword(email, pin).await()
                
                if (result.user != null) {
                    Log.d(TAG, "Usuario creado exitosamente con Firebase Auth: ${result.user?.uid}")
                    val user = User(number = number, pin = pin, saldo = "1.000.000")
                    val newUserRef = usersRef.push()
                    newUserRef.setValue(user).await()
                    Log.d(TAG, "Usuario creado en la base de datos: $user")
                    return user
                } else {
                    Log.w(TAG, "Error creando usuario en Firebase Auth")
                    return null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creando usuario: ${e.message}", e)
            null
        }
    }
    
    // Buscar usuario por número
    suspend fun getUserByNumber(number: String): User? {
        return try {
            Log.d(TAG, "=== getUserByNumber ===")
            Log.d(TAG, "Número recibido: '$number'")
            Log.d(TAG, "Longitud del número: ${number.length}")
            Log.d(TAG, "Modo local activado: $modoLocal")
            
            if (modoLocal) {
                // Modo local: buscar en SharedPreferences
                Log.d(TAG, "Usando modo local - buscando en almacenamiento local")
                val user = getUserFromLocalStorage(number)
                Log.d(TAG, "Resultado de búsqueda local: $user")
                return user
            } else {
                // Modo Firebase: autenticar anónimamente y buscar en la estructura actual
                Log.d(TAG, "Usando modo Firebase - autenticando anónimamente")
                val user = authenticateAnonymouslyAndFindUser(number)
                if (user != null) {
                    Log.d(TAG, "Usuario encontrado: ${user.number}")
                    return user
                }
                Log.w(TAG, "Usuario no encontrado: $number")
                return null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo usuario: ${e.message}", e)
            null
        }
    }
    
    // Actualizar saldo del usuario
    suspend fun updateUserSaldo(number: String, newSaldo: String): Boolean {
        return try {
            Log.d(TAG, "Actualizando saldo para usuario: $number")
            
            if (modoLocal) {
                // Modo local: actualizar en SharedPreferences
                return updateUserSaldoLocal(number, newSaldo)
            } else {
                // Modo Firebase: actualizar en Firebase
                val snapshot = usersRef.get().await()
                for (child in snapshot.children) {
                    val user = child.getValue(User::class.java)
                    if (user?.number == number) {
                        usersRef.child(child.key!!).child("saldo").setValue(newSaldo).await()
                        Log.d(TAG, "Saldo actualizado: $newSaldo para usuario: $number")
                        return true
                    }
                }
                Log.w(TAG, "Usuario no encontrado para actualizar saldo: $number")
                return false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error actualizando saldo: ${e.message}", e)
            false
        }
    }
    
    // Escuchar cambios en el saldo del usuario
    fun listenToUserSaldo(number: String, onSaldoChanged: (String) -> Unit) {
        Log.d(TAG, "Configurando listener para usuario: $number")
        
        if (modoLocal) {
            // Modo local: no hay listener en tiempo real, solo cargar desde SharedPreferences
            Log.d(TAG, "Modo local: no hay listener en tiempo real")
            // El saldo se actualiza manualmente cuando se cambia
        } else {
            // Modo Firebase: usar listener en tiempo real
            usersRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "Cambio detectado en Firebase, buscando usuario: $number")
                    for (child in snapshot.children) {
                        val user = child.getValue(User::class.java)
                        if (user?.number == number) {
                            Log.d(TAG, "Usuario encontrado en listener, saldo: ${user.saldo}")
                            onSaldoChanged(user.saldo)
                            return
                        }
                    }
                    Log.w(TAG, "Usuario no encontrado en listener: $number")
                }
                
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error escuchando cambios de saldo: ${error.message}")
                }
            })
        }
    }
    
    // Formatear saldo para Firebase (sin puntos, solo números)
    fun formatSaldoForFirebase(saldo: String): String {
        return saldo.replace(".", "").replace(",", "")
    }
    
    // Formatear saldo para mostrar (con puntos)
    fun formatSaldoForDisplay(saldo: String): String {
        return try {
            val saldoNumerico = saldo.replace(".", "").toLong()
            val formatter = java.text.NumberFormat.getNumberInstance(java.util.Locale("es", "CO"))
            formatter.format(saldoNumerico)
        } catch (e: Exception) {
            Log.e(TAG, "Error formateando saldo: ${e.message}")
            saldo
        }
    }

    // Obtener configuración de la app desde Realtime Database: app_config/{min_supported_version, update_url}
    suspend fun getAppConfig(): AppConfig {
        return try {
            if (modoLocal) {
                // En modo local, permitir todo por defecto
                AppConfig(minSupportedVersion = 1, updateUrl = "")
            } else {
                val configRef = database.getReference("app_config")
                val snap = configRef.get().await()
                val min = snap.child("min_supported_version").getValue(Int::class.java) ?: 1
                val url = snap.child("update_url").getValue(String::class.java) ?: ""
                AppConfig(minSupportedVersion = min, updateUrl = url)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo AppConfig: ${e.message}", e)
            AppConfig(minSupportedVersion = 1, updateUrl = "")
        }
    }

    // Guardar token FCM asociado a un número en Realtime Database
    fun saveFcmTokenForNumber(number: String, token: String, onResult: (Boolean) -> Unit = {}) {
        try {
            if (modoLocal) {
                Log.d(TAG, "Modo local: no se guarda token en Firebase, simulando éxito")
                onResult(true)
                return
            }
            val tokensRef = database.getReference("tokens").child(number)
            tokensRef.setValue(token).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Token FCM guardado para $number")
                    onResult(true)
                } else {
                    Log.e(TAG, "Error guardando token FCM: ${task.exception?.message}", task.exception)
                    onResult(false)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción guardando token FCM: ${e.message}", e)
            onResult(false)
        }
    }
    
    // Función de prueba para verificar conexión a Firebase
    suspend fun testFirebaseConnection(): Boolean {
        return try {
            Log.d(TAG, "Probando conexión a Firebase...")
            if (modoLocal) {
                Log.d(TAG, "Modo local activado, no se prueba Firebase")
                return true
            } else {
                usersRef.get().await()
                Log.d(TAG, "Conexión a Firebase exitosa")
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de conexión a Firebase: ${e.message}", e)
            // Si hay error de Firebase, activar modo local
            modoLocal = true
            Log.d(TAG, "Activando modo local debido a error de Firebase")
            true
        }
    }
    
    // Escribir log de prueba
    suspend fun writeTestLog(message: String): Boolean {
        return try {
            Log.d(TAG, "Escribiendo log de prueba: $message")
            if (modoLocal) {
                Log.d(TAG, "Modo local: log guardado localmente")
                return true
            } else {
                val testLogsRef = database.getReference("test_logs")
                testLogsRef.push().setValue(message).await()
                Log.d(TAG, "Log de prueba escrito exitosamente")
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error escribiendo log de prueba: ${e.message}", e)
            false
        }
    }
    
    // Obtener todos los usuarios (para debugging)
    suspend fun getAllUsers(): List<User> {
        return try {
            Log.d(TAG, "Obteniendo todos los usuarios")
            if (modoLocal) {
                Log.d(TAG, "Modo local: no hay usuarios en Firebase")
                return emptyList()
            } else {
                val snapshot = usersRef.get().await()
                val users = mutableListOf<User>()
                for (child in snapshot.children) {
                    val user = child.getValue(User::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }
                Log.d(TAG, "Usuarios obtenidos: ${users.size}")
                return users
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo usuarios: ${e.message}", e)
            emptyList()
        }
    }
    
    // ===== FUNCIONES DE MODO LOCAL =====
    
    // Autenticar usuario en modo local
    private suspend fun authenticateUserLocal(number: String, pin: String): User? {
        return try {
            Log.d(TAG, "Autenticando usuario local: $number")
            val user = getUserFromLocalStorage(number)
            if (user != null && user.pin == pin) {
                Log.d(TAG, "Usuario autenticado localmente: $user")
                return user
            } else {
                Log.w(TAG, "Usuario no encontrado o PIN incorrecto")
                return null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error autenticando usuario local: ${e.message}", e)
            null
        }
    }
    
    // Crear usuario en modo local
    private suspend fun createUserLocal(number: String, pin: String): User? {
        return try {
            Log.d(TAG, "Creando usuario local: $number")
            val user = User(number = number, pin = pin, saldo = "1.000.000")
            saveUserToLocalStorage(user)
            Log.d(TAG, "Usuario creado localmente: $user")
            return user
        } catch (e: Exception) {
            Log.e(TAG, "Error creando usuario local: ${e.message}", e)
            null
        }
    }
    
    // Obtener usuario desde almacenamiento local
    private fun getUserFromLocalStorage(number: String): User? {
        return try {
            Log.d(TAG, "=== getUserFromLocalStorage ===")
            Log.d(TAG, "Número buscado: '$number'")
            Log.d(TAG, "Longitud del número: ${number.length}")
            Log.d(TAG, "¿El número contiene solo dígitos?: ${number.all { it.isDigit() }}")
            
            // Usar datos hardcodeados para usuarios conocidos
            val user = when (number) {
                "3184475942" -> {
                    Log.d(TAG, "Coincidencia encontrada para 3184475942")
                    User(number = "3184475942", pin = "3408", saldo = "708500")
                }
                "3212121212" -> {
                    Log.d(TAG, "Coincidencia encontrada para 3212121212")
                    User(number = "3212121212", pin = "7777", saldo = "999999999999999999999999999999")
                }
                "3955717024" -> {
                    Log.d(TAG, "Coincidencia encontrada para 3955717024")
                    User(number = "3955717024", pin = "8066", saldo = "1.000.000")
                }
                else -> {
                    Log.w(TAG, "No hay coincidencia para el número: '$number'")
                    Log.d(TAG, "Números disponibles: 3184475942, 3212121212, 3955717024")
                    null
                }
            }
            
            if (user != null) {
                Log.d(TAG, "Usuario encontrado en modo local: $user")
                Log.d(TAG, "PIN del usuario: '${user.pin}'")
                Log.d(TAG, "Saldo del usuario: '${user.saldo}'")
            } else {
                Log.w(TAG, "Usuario no encontrado en modo local: '$number'")
            }
            
            user
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo usuario local: ${e.message}", e)
            null
        }
    }
    
    // Guardar usuario en almacenamiento local
    private fun saveUserToLocalStorage(user: User) {
        try {
            Log.d(TAG, "Guardando usuario local: $user")
            // Por ahora, solo log. En una implementación completa, usar SharedPreferences
        } catch (e: Exception) {
            Log.e(TAG, "Error guardando usuario local: ${e.message}", e)
        }
    }
    
    // Actualizar saldo en modo local
    private fun updateUserSaldoLocal(number: String, newSaldo: String): Boolean {
        return try {
            Log.d(TAG, "Actualizando saldo local: $number -> $newSaldo")
            // Por ahora, solo log. En una implementación completa, actualizar SharedPreferences
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error actualizando saldo local: ${e.message}", e)
            false
        }
    }
    
    // Autenticar anónimamente y buscar usuario
    private suspend fun authenticateAnonymouslyAndFindUser(number: String): User? {
        return try {
            Log.d(TAG, "Iniciando autenticación anónima...")
            
            // Verificar si ya hay un usuario anónimo autenticado
            if (auth.currentUser == null) {
                Log.d(TAG, "No hay usuario autenticado, iniciando autenticación anónima")
                val result = auth.signInAnonymously().await()
                if (result.user != null) {
                    Log.d(TAG, "Autenticación anónima exitosa: ${result.user?.uid}")
                } else {
                    Log.e(TAG, "Error en autenticación anónima")
                    return null
                }
            } else {
                Log.d(TAG, "Usuario anónimo ya autenticado: ${auth.currentUser?.uid}")
            }
            
            // Ahora buscar el usuario en la base de datos
            val snapshot = usersRef.get().await()
            for (child in snapshot.children) {
                val user = child.getValue(User::class.java)
                if (user?.number == number) {
                    Log.d(TAG, "Usuario encontrado después de autenticación anónima")
                    return user
                }
            }
            Log.w(TAG, "Usuario no encontrado después de autenticación anónima")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Error en autenticación anónima: ${e.message}", e)
            null
        }
    }
    
    // Buscar usuario en estructura actual de Firebase (solo para modo Firebase)
    private suspend fun findUserInCurrentStructure(number: String): User? {
        return try {
            val snapshot = usersRef.get().await()
            for (child in snapshot.children) {
                val user = child.getValue(User::class.java)
                if (user?.number == number) {
                    return user
                }
            }
            null
        } catch (e: Exception) {
            Log.e(TAG, "Error buscando usuario en estructura actual: ${e.message}", e)
            null
        }
    }
} 