package com.wts.nequicooficial

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Camera
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.QrCode2
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.gms.tasks.Tasks
import androidx.camera.core.ImageProxy

class QrScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                // No UI here; Compose will react to permission state via remember
            }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        setContent {
            QrScannerScreen(
                onBackPressed = { finish() },
                onQrDetected = { qrText ->
                    // Aquí puedes manejar el QR detectado
                    // Por ejemplo, enviar el resultado de vuelta a InicioActivity
                    setResult(RESULT_OK, intent.putExtra("qr_text", qrText))
                    finish()
                }
            )
        }
    }
}

@Composable
fun QrScannerScreen(
    onBackPressed: () -> Unit,
    onQrDetected: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    // No mostrar el valor escaneado en pantalla
    var isTorchOn by remember { mutableStateOf(false) }
    val cameraRef = remember { mutableStateOf<Camera?>(null) }

    // Picker de imagen para escanear desde galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            scope.launch {
                val result = scanQrFromImageUri(context, uri)
                if (!result.isNullOrEmpty()) {
                    onQrDetected(result)
                } else {
                    try {
                        android.widget.Toast.makeText(context, "No se detectó un QR en la imagen", android.widget.Toast.LENGTH_SHORT).show()
                    } catch (_: Exception) { }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreviewWithAnalyzer(
            onCameraReady = { cam -> cameraRef.value = cam },
            onQrCodesDetected = { barcodes ->
                val first = barcodes.firstOrNull()?.rawValue
                if (!first.isNullOrEmpty()) {
                    onQrDetected(first)
                }
            }
        )

        ScannerOverlay(
            modifier = Modifier.fillMaxSize(),
            overlayColor = Color(0x33001E3A),
            frameSize = 280.dp,
            cornerRadius = 28.dp,
            strokeWidth = 6.dp,
            topExclusionHeight = 64.dp
        )

        // Encabezado con Nequi y botón X a la derecha
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Nequi",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.TopStart)
            )
            IconButton(onClick = onBackPressed, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Close,
                    contentDescription = "Cerrar",
                    tint = Color.White
                )
            }
        }

        // Rectángulo morado con texto guía (más grande)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 64.dp)
                .background(Color(0x80200020))
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Encuentra el codigo QR aqui",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Barra inferior con acciones: Codigo, Linterna, Imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color(0x80200020))
                .padding(vertical = 19.dp)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Código
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF200020)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Outlined.QrCode2,
                            contentDescription = "Codigo",
                            tint = Color.White
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(text = "Codigo", color = Color.White, style = MaterialTheme.typography.bodySmall)
                }

                // Linterna
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF200020))
                            .clickable {
                                cameraRef.value?.let { cam ->
                                    val newTorch = !isTorchOn
                                    cam.cameraControl.enableTorch(newTorch)
                                    isTorchOn = newTorch
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isTorchOn) Icons.Filled.FlashlightOn else Icons.Filled.FlashlightOff,
                            contentDescription = "Linterna",
                            tint = Color.White
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(text = "Linterna", color = Color.White, style = MaterialTheme.typography.bodySmall)
                }

                // Imagen
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF200020))
                            .clickable { imagePickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Outlined.Image,
                            contentDescription = "Imagen",
                            tint = Color.White
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(text = "Imagen", color = Color.White, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// ==== Helpers reutilizables (visibles para galería y cámara) ====
private fun scaleBitmapIfNeeded(src: Bitmap, maxDim: Int = 1600): Bitmap {
    val w = src.width
    val h = src.height
    val maxSide = maxOf(w, h)
    return if (maxSide <= maxDim) src else {
        val scale = maxDim.toFloat() / maxSide.toFloat()
        val newW = (w * scale).toInt().coerceAtLeast(1)
        val newH = (h * scale).toInt().coerceAtLeast(1)
        Bitmap.createScaledBitmap(src, newW, newH, true)
    }
}

private fun imageProxyToBitmap(proxy: ImageProxy): Bitmap? {
    return try {
        val yBuffer = proxy.planes[0].buffer
        val uBuffer = proxy.planes[1].buffer
        val vBuffer = proxy.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = android.graphics.YuvImage(
            nv21,
            android.graphics.ImageFormat.NV21,
            proxy.width,
            proxy.height,
            null
        )
        val out = java.io.ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, proxy.width, proxy.height), 90, out)
        val jpegBytes = out.toByteArray()
        BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
    } catch (_: Exception) { null }
}

private fun rotateBitmap(src: Bitmap, degrees: Float): Bitmap {
    val matrix = android.graphics.Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
}

private fun toGrayscaleHighContrast(src: Bitmap): Bitmap {
    val bmp = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    val cm = ColorMatrix().apply { setSaturation(0f) }
    val contrast = 1.25f
    val translate = (-0.25f * 255f)
    val contrastMatrix = ColorMatrix(
        floatArrayOf(
            contrast, 0f, 0f, 0f, translate,
            0f, contrast, 0f, 0f, translate,
            0f, 0f, contrast, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        )
    )
    cm.postConcat(contrastMatrix)
    val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(cm) }
    canvas.drawBitmap(src, 0f, 0f, paint)
    return bmp
}

private fun cropCenter(src: Bitmap, factor: Float = 0.7f): Bitmap {
    val w = src.width
    val h = src.height
    val cw = (w * factor).toInt()
    val ch = (h * factor).toInt()
    val left = ((w - cw) / 2).coerceAtLeast(0)
    val top = ((h - ch) / 2).coerceAtLeast(0)
    val rect = Rect(left, top, (left + cw).coerceAtMost(w), (top + ch).coerceAtMost(h))
    return Bitmap.createBitmap(src, rect.left, rect.top, rect.width(), rect.height())
}

private fun padWithQuietZone(src: Bitmap, borderPx: Int): Bitmap {
    val padded = Bitmap.createBitmap(src.width + borderPx * 2, src.height + borderPx * 2, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(padded)
    val paint = Paint().apply { color = android.graphics.Color.WHITE }
    canvas.drawRect(0f, 0f, padded.width.toFloat(), padded.height.toFloat(), paint)
    canvas.drawBitmap(src, borderPx.toFloat(), borderPx.toFloat(), null)
    return padded
}

// Mejora robusta para leer QR desde imágenes de galería:
// - Escala la imagen si es muy grande
// - Prueba en color original, escala de grises con mayor contraste, y recorte central
// - Intenta rotaciones 0/90/180/270 para mejorar tasa de éxito
private suspend fun scanQrFromImageUri(context: android.content.Context, uri: Uri): String? = withContext(Dispatchers.IO) {
    try {
        val resolver = context.contentResolver
        val input = resolver.openInputStream(uri) ?: return@withContext null
        val originalBitmap = BitmapFactory.decodeStream(input)
        input.close()
        if (originalBitmap == null) return@withContext null

        fun scaleBitmapIfNeeded(src: Bitmap, maxDim: Int = 1600): Bitmap {
            val w = src.width
            val h = src.height
            val maxSide = maxOf(w, h)
            return if (maxSide <= maxDim) src else {
                val scale = maxDim.toFloat() / maxSide.toFloat()
                val newW = (w * scale).toInt().coerceAtLeast(1)
                val newH = (h * scale).toInt().coerceAtLeast(1)
                Bitmap.createScaledBitmap(src, newW, newH, true)
            }
        }
        
        // Helpers reutilizables en escaneo en vivo
        fun imageProxyToBitmap(proxy: androidx.camera.core.ImageProxy): Bitmap? {
            return try {
                val yBuffer = proxy.planes[0].buffer // Y
                val uBuffer = proxy.planes[1].buffer // U
                val vBuffer = proxy.planes[2].buffer // V

                val ySize = yBuffer.remaining()
                val uSize = uBuffer.remaining()
                val vSize = vBuffer.remaining()

                val nv21 = ByteArray(ySize + uSize + vSize)
                yBuffer.get(nv21, 0, ySize)
                vBuffer.get(nv21, ySize, vSize)
                uBuffer.get(nv21, ySize + vSize, uSize)

                val yuvImage = android.graphics.YuvImage(
                    nv21,
                    android.graphics.ImageFormat.NV21,
                    proxy.width,
                    proxy.height,
                    null
                )
                val out = java.io.ByteArrayOutputStream()
                yuvImage.compressToJpeg(android.graphics.Rect(0, 0, proxy.width, proxy.height), 90, out)
                val jpegBytes = out.toByteArray()
                BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
            } catch (_: Exception) { null }
        }
        
        fun rotateBitmap(src: Bitmap, degrees: Float): Bitmap {
            val matrix = android.graphics.Matrix().apply { postRotate(degrees) }
            return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        }

        fun toGrayscaleHighContrast(src: Bitmap): Bitmap {
            val bmp = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            val cm = ColorMatrix().apply { setSaturation(0f) }
            // Aumentar contraste ligeramente
            val contrast = 1.25f
            val translate = (-0.25f * 255f)
            val contrastMatrix = ColorMatrix(
                floatArrayOf(
                    contrast, 0f, 0f, 0f, translate,
                    0f, contrast, 0f, 0f, translate,
                    0f, 0f, contrast, 0f, translate,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            cm.postConcat(contrastMatrix)
            val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(cm) }
            canvas.drawBitmap(src, 0f, 0f, paint)
            return bmp
        }

        fun cropCenter(src: Bitmap, factor: Float = 0.7f): Bitmap {
            val w = src.width
            val h = src.height
            val cw = (w * factor).toInt()
            val ch = (h * factor).toInt()
            val left = ((w - cw) / 2).coerceAtLeast(0)
            val top = ((h - ch) / 2).coerceAtLeast(0)
            val rect = Rect(left, top, (left + cw).coerceAtMost(w), (top + ch).coerceAtMost(h))
            return Bitmap.createBitmap(src, rect.left, rect.top, rect.width(), rect.height())
        }

        fun cropAt(src: Bitmap, leftRatio: Float, topRatio: Float, factor: Float): Bitmap {
            val w = src.width
            val h = src.height
            val cw = (w * factor).toInt().coerceIn(1, w)
            val ch = (h * factor).toInt().coerceIn(1, h)
            val left = (w * leftRatio).toInt().coerceIn(0, w - cw)
            val top = (h * topRatio).toInt().coerceIn(0, h - ch)
            return Bitmap.createBitmap(src, left, top, cw, ch)
        }

        fun padWithQuietZone(src: Bitmap, borderPx: Int): Bitmap {
            val padded = Bitmap.createBitmap(src.width + borderPx * 2, src.height + borderPx * 2, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(padded)
            val paint = Paint().apply { color = android.graphics.Color.WHITE }
            canvas.drawRect(0f, 0f, padded.width.toFloat(), padded.height.toFloat(), paint)
            canvas.drawBitmap(src, borderPx.toFloat(), borderPx.toFloat(), null)
            return padded
        }

        fun tryScanBlocking(bitmap: Bitmap): String? {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
            val scanner = BarcodeScanning.getClient(options)
            val image = InputImage.fromBitmap(bitmap, 0)
            val task = scanner.process(image)
            val barcodes = try { com.google.android.gms.tasks.Tasks.await(task) } catch (_: Exception) { emptyList<Barcode>() }
            return barcodes.firstOrNull()?.rawValue
        }

        val scaled = scaleBitmapIfNeeded(originalBitmap)
        val variants = mutableListOf<Bitmap>()

        // Base + quiet zone
        val border = (minOf(scaled.width, scaled.height) * 0.06f).toInt().coerceAtLeast(40)
        val basePadded = padWithQuietZone(scaled, border)
        variants += basePadded
        variants += toGrayscaleHighContrast(basePadded)

        // Centro en varios niveles + padding
        val centers = listOf(0.9f, 0.8f, 0.7f)
        for (f in centers) {
            val c = cropCenter(scaled, f)
            val cp = padWithQuietZone(c, (minOf(c.width, c.height) * 0.08f).toInt().coerceAtLeast(36))
            variants += cp
            variants += toGrayscaleHighContrast(cp)
        }

        // Esquinas y bordes (para QR muy cerca o recortado), con padding
        val cornerFactor = 0.72f
        val corners = listOf(
            0f to 0f, // TL
            1f - cornerFactor to 0f, // TR
            0f to (1f - cornerFactor), // BL
            1f - cornerFactor to (1f - cornerFactor) // BR
        )
        for ((lx, ty) in corners) {
            val crop = cropAt(scaled, lx, ty, cornerFactor)
            val qp = padWithQuietZone(crop, (minOf(crop.width, crop.height) * 0.1f).toInt().coerceAtLeast(40))
            variants += qp
            variants += toGrayscaleHighContrast(qp)
        }
        // Bordes centrales (izq, der, arriba, abajo)
        val edgeFactor = 0.75f
        val leftEdge = cropAt(scaled, 0f, 0.125f, edgeFactor)
        val rightEdge = cropAt(scaled, 1f - edgeFactor, 0.125f, edgeFactor)
        val topEdge = cropAt(scaled, 0.125f, 0f, edgeFactor)
        val bottomEdge = cropAt(scaled, 0.125f, 1f - edgeFactor, edgeFactor)
        for (edge in listOf(leftEdge, rightEdge, topEdge, bottomEdge)) {
            val ep = padWithQuietZone(edge, (minOf(edge.width, edge.height) * 0.1f).toInt().coerceAtLeast(40))
            variants += ep
            variants += toGrayscaleHighContrast(ep)
        }

        // Rotaciones para cada variante
        fun rotate(src: Bitmap, degrees: Float): Bitmap {
            val matrix = android.graphics.Matrix().apply { postRotate(degrees) }
            return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        }

        val rotations = listOf(0f, 90f, 180f, 270f)
        for (variant in variants) {
            for (deg in rotations) {
                val rotated = if (deg == 0f) variant else rotate(variant, deg)
                val res = tryScanBlocking(rotated)
                if (!res.isNullOrEmpty()) return@withContext res
            }
        }

        null
    } catch (_: Exception) {
        null
    }
}

@androidx.camera.core.ExperimentalGetImage
@Composable
fun CameraPreviewWithAnalyzer(
    onCameraReady: (Camera) -> Unit,
    onQrCodesDetected: (List<Barcode>) -> Unit
) {
    val context = LocalContext.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()
                val scanner = BarcodeScanning.getClient(options)

                val analysisUseCase = ImageAnalysis.Builder()
                    .setTargetResolution(android.util.Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                analysisUseCase.setAnalyzer(cameraExecutor) { imageProxy ->
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        scanner.process(image)
                            .addOnSuccessListener { barcodes ->
                                if (barcodes.isNotEmpty()) {
                                    onQrCodesDetected(barcodes)
                                }
                            }
                            .addOnCompleteListener { imageProxy.close() }
                    } else {
                        imageProxy.close()
                    }
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    val camera = cameraProvider.bindToLifecycle(
                        context as ComponentActivity,
                        cameraSelector,
                        preview,
                        analysisUseCase
                    )
                    onCameraReady(camera)
                } catch (exc: Exception) {
                    // Ignore for now
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ScannerOverlay(
    modifier: Modifier = Modifier,
    overlayColor: Color = Color(0x99000000),
    frameSize: Dp = 240.dp,
    cornerRadius: Dp = 16.dp,
    strokeWidth: Dp = 4.dp,
    topExclusionHeight: Dp = 0.dp
) {
    val cornerLength = 50.dp
    val cornerColor = Color(0xFFDB0082)

    Box(
        modifier
            .fillMaxSize()
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithContent {
                drawContent()
                // Área general
                drawRect(color = overlayColor)
                // Limpiar barra superior (sin tinte azul) según exclusión
                if (topExclusionHeight > 0.dp) {
                    drawRect(
                        color = Color.Transparent,
                        topLeft = Offset(0f, 0f),
                        size = androidx.compose.ui.geometry.Size(size.width, topExclusionHeight.toPx()),
                        blendMode = BlendMode.Clear
                    )
                }
                val sizePx = frameSize.toPx()
                val cornerPx = cornerRadius.toPx()
                val cutoutLeft = (size.width - sizePx) / 2f
                val cutoutTop = (size.height - sizePx) / 2f
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = Offset(cutoutLeft, cutoutTop),
                    size = androidx.compose.ui.geometry.Size(sizePx, sizePx),
                    cornerRadius = CornerRadius(cornerPx, cornerPx),
                    blendMode = BlendMode.Clear
                )
            }
    )

    // Corner strokes container centered
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .size(frameSize)
                .align(Alignment.Center)
        ) {
            // Top-left
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .size(cornerLength, strokeWidth)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .size(strokeWidth, cornerLength)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            // Top-right
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .size(cornerLength, strokeWidth)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .size(strokeWidth, cornerLength)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            // Bottom-left
            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .size(cornerLength, strokeWidth)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .size(strokeWidth, cornerLength)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            // Bottom-right
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .size(cornerLength, strokeWidth)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .size(strokeWidth, cornerLength)
                    .background(cornerColor, RoundedCornerShape(strokeWidth))
            )
        }
    }
}

@Composable
private fun ResultCard(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xCC1E1E1E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            color = Color.White
        )
    }
}
