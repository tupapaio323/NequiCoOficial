package com.wts.nequicooficial

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

/**
 * Clase helper para optimizaciones de rendimiento y memoria
 */
object OptimizationHelper {
    
    /**
     * Optimiza la carga de bitmaps para reducir el uso de memoria
     */
    fun loadOptimizedBitmap(context: Context, resourceId: Int, maxSize: Int = 1024): Bitmap? {
        return try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            
            BitmapFactory.decodeResource(context.resources, resourceId, options)
            
            // Calcular el factor de escala
            val scale = Math.max(1, Math.min(
                options.outWidth / maxSize,
                options.outHeight / maxSize
            ))
            
            val finalOptions = BitmapFactory.Options().apply {
                inSampleSize = scale
                inPreferredConfig = Bitmap.Config.RGB_565 // Usar menos memoria
            }
            
            BitmapFactory.decodeResource(context.resources, resourceId, finalOptions)
        } catch (e: Exception) {
            Log.e("OptimizationHelper", "Error loading bitmap: ${e.message}")
            null
        }
    }
    
    /**
     * Limpia bitmaps de la memoria
     */
    fun recycleBitmap(bitmap: Bitmap?) {
        bitmap?.let {
            if (!it.isRecycled) {
                it.recycle()
            }
        }
    }
    
    /**
     * Optimiza el uso de memoria para operaciones intensivas
     */
    fun optimizeMemoryUsage() {
        System.gc()
    }
}
