// Agregar esto temporalmente en InicioActivity.onCreate() para ver UIDs
private fun debugUserUIDs() {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            // Obtener UID del usuario actual
            val currentUID = firebaseManager.getCurrentUserUID()
            android.util.Log.d("DebugUIDs", "UID del usuario actual: $currentUID")
            
            // Obtener todos los UIDs
            val allUIDs = withContext(Dispatchers.IO) {
                firebaseManager.getAllUserUIDs()
            }
            android.util.Log.d("DebugUIDs", "Todos los UIDs: $allUIDs")
            
        } catch (e: Exception) {
            android.util.Log.e("DebugUIDs", "Error obteniendo UIDs: ${e.message}", e)
        }
    }
} 