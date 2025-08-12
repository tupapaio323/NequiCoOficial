package com.wts.nequicooficial.push

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wts.nequicooficial.InicioActivity
import com.wts.nequicooficial.R

class PromoNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            // Notificación 1: Canal de Telegram
            val title = "Únete al canal oficial"
            val body = "Noticias y precios de la app en Telegram. Toca para ir."
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("https://t.me/+d17o7xIvIqdkZGYx")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pending = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(applicationContext, "nequico_default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(Color.MAGENTA)
                .setContentIntent(pending)

            with(NotificationManagerCompat.from(applicationContext)) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }

            // Notificación 2: Promoción llamativa
            val promoTitle = "PROMOCIÓN: $1.000.000 por $25.000 COP"
            val promoBody = "🔥 Solo por tiempo limitado. Aprovecha y súbele el power a tu app hoy mismo. Toca para ver más."
            val promoIntent = Intent(applicationContext, InicioActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val promoPending = PendingIntent.getActivity(
                applicationContext,
                1,
                promoIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val promoBuilder = NotificationCompat.Builder(applicationContext, "nequico_default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(promoTitle)
                .setContentText(promoBody)
                .setStyle(NotificationCompat.BigTextStyle().bigText(promoBody))
                .setAutoCancel(true)
                .setColor(Color.parseColor("#db0082"))
                .setContentIntent(promoPending)

            with(NotificationManagerCompat.from(applicationContext)) {
                notify((System.currentTimeMillis() + 1).toInt(), promoBuilder.build())
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}


