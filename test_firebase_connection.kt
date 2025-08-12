// Agregar esto temporalmente en InicioActivity.onCreate() para verificar Firebase
private fun testFirebaseConnection() {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            android.util.Log.d("TestFirebase", "=== TESTING FIREBASE CONNECTION ===")
            
            // Probar conexión
            val connectionTest = withContext(Dispatchers.IO) {
                firebaseManager.testFirebaseConnection()
            }
            android.util.Log.d("TestFirebase", "Conexión a Firebase: $connectionTest")
            
            // Obtener todos los usuarios
            val allUsers = withContext(Dispatchers.IO) {
                firebaseManager.getAllUsers()
            }
            android.util.Log.d("TestFirebase", "Usuarios en Firebase: ${allUsers.size}")
            for (user in allUsers) {
                android.util.Log.d("TestFirebase", "Usuario: ${user.number} - PIN: ${user.pin}")
            }
            
            // Obtener UID actual
            val currentUID = firebaseManager.getCurrentUserUID()
            android.util.Log.d("TestFirebase", "UID actual: $currentUID")
            
        } catch (e: Exception) {
            android.util.Log.e("TestFirebase", "Error en test: ${e.message}", e)
        }
    }
} 