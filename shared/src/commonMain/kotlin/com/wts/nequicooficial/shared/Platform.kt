package com.wts.nequicooficial.shared

/**
 * Simple plataforma utilitaria para verificar integración KMM.
 * Se expandirá con la lógica compartida de la app.
 */
expect class Platform() {
    val name: String
}

fun sharedGreeting(): String = "Hola desde ${Platform().name}"


