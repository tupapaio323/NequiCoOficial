package com.wts.nequicooficial

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseApp

class NequiCoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        // Crear canal de notificación para Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "nequico_default"
            val channelName = "Notificaciones"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Notificaciones de la app"
            }
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }

        // Programar notificación promocional periódica (cada 1 hora)
        val workRequest = PeriodicWorkRequestBuilder<com.wts.nequicooficial.push.PromoNotificationWorker>(1, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "promo_notification_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
} 