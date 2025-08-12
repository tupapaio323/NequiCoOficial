package com.wts.nequicooficial.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class ScannerOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val dimPaint = Paint().apply {
        color = 0x99000000.toInt() // semi-transparent black
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        isAntiAlias = true
    }

    private val borderPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = dp(4f)
        isAntiAlias = true
    }

    private val cornerPaint = Paint().apply {
        color = Color.parseColor("#00E0FF")
        style = Paint.Style.STROKE
        strokeWidth = dp(4f)
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private val frameSizeDp = 260f
    private val cornerLen = dp(24f)
    private val cornerRadius = dp(20f)

    private fun dp(value: Float): Float = value * resources.displayMetrics.density

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Use a layer to apply CLEAR mode
        val save = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // Dim everything
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), dimPaint)

        // Compute centered square frame
        val sizePx = dp(frameSizeDp)
        val frame = computeCenteredSquare(sizePx)

        // Clear the cutout
        canvas.drawRoundRect(frame, cornerRadius, cornerRadius, clearPaint)

        // Draw white border
        canvas.drawRoundRect(frame, cornerRadius, cornerRadius, borderPaint)

        // Draw corners
        drawCorners(canvas, frame)

        canvas.restoreToCount(save)
    }

    private fun computeCenteredSquare(size: Float): RectF {
        val w = width.toFloat()
        val h = height.toFloat()
        val box = min(size, min(w, h))
        val left = (w - box) / 2f
        val top = (h - box) / 2f
        return RectF(left, top, left + box, top + box)
    }

    private fun drawCorners(canvas: Canvas, frame: RectF) {
        val l = frame.left
        val t = frame.top
        val r = frame.right
        val b = frame.bottom

        // Top-left
        canvas.drawLine(l, t, l + cornerLen, t, cornerPaint)
        canvas.drawLine(l, t, l, t + cornerLen, cornerPaint)

        // Top-right
        canvas.drawLine(r - cornerLen, t, r, t, cornerPaint)
        canvas.drawLine(r, t, r, t + cornerLen, cornerPaint)

        // Bottom-left
        canvas.drawLine(l, b, l + cornerLen, b, cornerPaint)
        canvas.drawLine(l, b - cornerLen, l, b, cornerPaint)

        // Bottom-right
        canvas.drawLine(r - cornerLen, b, r, b, cornerPaint)
        canvas.drawLine(r, b - cornerLen, r, b, cornerPaint)
    }
}


