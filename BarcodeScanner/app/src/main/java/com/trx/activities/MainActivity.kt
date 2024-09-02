package com.trx.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.impl.utils.MatrixExt.postRotate
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.trx.R
import com.trx.consts.OverlayView
import com.trx.databinding.ActivityMainBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var imageCapture: ImageCapture
    private lateinit var capturedImageView: ImageView

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var overlayView: OverlayView
    private lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overlayView = findViewById(R.id.overlay_view)
        previewView = findViewById(R.id.preview_view)
        capturedImageView = findViewById(R.id.captured_image_view)

        imageCapture = ImageCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnCapture.setOnClickListener {
            takePhoto()
        }

    }

    override fun onBackPressed() {
        if (capturedImageView.visibility == View.VISIBLE) {
            // If the captured image is visible, hide it and show the preview
            capturedImageView.visibility = View.GONE
            previewView.visibility = View.VISIBLE
        } else {
            // Otherwise, handle the back press normally
            super.onBackPressed()
        }
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            /*val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(1920, 1080))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer(previewView) { rects ->
                        overlayView.setRects(rects)
                    })
                }*/

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                Log.d(TAG, "Camera bound to lifecycle.")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to bind camera use cases", e)
                Toast.makeText(this, "Failed to bind camera use cases", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto(){
        val photoFile = File(externalMediaDirs.first(),"${System.currentTimeMillis()}.jpg")
        val outputOptions = OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)

                    analyzeImageForBarcodes(photoFile)
                }
                override fun onError(exception: ImageCaptureException) {
                    Log.d(TAG, exception.toString())
                }
            }
        )

    }

    private fun analyzeImageForBarcodes(photoFile : File){
        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
        val rotatedBitmap = rotateBitmapIfRequired(bitmap, photoFile)
        //val processedBitmap = toGrayscale(rotatedBitmap)
        val enhancedBitmap = enhanceImage(rotatedBitmap)
        val image = InputImage.fromBitmap(enhancedBitmap, 0)

        val barcodeAnalyzer = BarcodeAnalyzer(previewView){rects ->
            // Display the image with highlighted barcodes
            val annotatedBitmap = drawRectanglesOnBitmap(enhancedBitmap, rects)
            runOnUiThread {
                capturedImageView.setImageBitmap(annotatedBitmap)
                capturedImageView.visibility = View.VISIBLE
                previewView.visibility = View.GONE // Hide preview view
            }
        }

        barcodeAnalyzer.analyzeBitmap(image)

    }

    private fun drawRectanglesOnBitmap(bitmap: Bitmap, rects: List<Rect>): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            color = ContextCompat.getColor(this@MainActivity, R.color.white)
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        rects.forEach { rect ->
            val rectF = RectF(rect)
            canvas.drawRect(rectF, paint)
        }

        return mutableBitmap
    }

    private fun rotateBitmapIfRequired(bitmap: Bitmap, photoFile: File): Bitmap {
        val exif = ExifInterface(photoFile.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun toGrayscale(bitmap: Bitmap): Bitmap {
        val grayscaleBitmap = Bitmap.createBitmap(
            bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(grayscaleBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return grayscaleBitmap
    }

    // Enhancing the image contrast
    private fun enhanceImage(bitmap: Bitmap): Bitmap {
        val bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(bmp)
        val paint = Paint()
        val colorMatrix = ColorMatrix().apply {
            setSaturation(1.5f)
            val contrast = 1.2f
            val brightness = 10f
            set(floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            ))
        }
        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorFilter
        canvas.drawBitmap(bmp, 0f, 0f, paint)
        return bmp
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "BRB"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private class BarcodeAnalyzer(
        private val previewView: PreviewView,
        private val onBarcodesDetected: (barcodes: List<Rect>) -> Unit
    ) : ImageAnalysis.Analyzer {

        val options = BarcodeScannerOptions.Builder()
            .enableAllPotentialBarcodes()
            .setBarcodeFormats(
                Barcode.FORMAT_ALL_FORMATS, // This will scan for all barcode formats
                Barcode.FORMAT_CODE_128
            )
            .build()

        private val scanner: BarcodeScanner = BarcodeScanning.getClient(options)

        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->

                        val rects = barcodes.mapNotNull { barcode ->
                            barcode.boundingBox?.let { boundingBox ->
                                mapRectToPreviewView(boundingBox, imageProxy, previewView)
                            }
                        }

                        for(barcode in barcodes){
                            Log.d("BarcodeAnalyzer", barcode.rawValue.toString())
                        }

                        onBarcodesDetected(rects)
                    }
                    .addOnFailureListener {
                        Log.e("BarcodeAnalyzer", "Failed to process image", it)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                Log.e("BarcodeAnalyzer", "Image is null")
                imageProxy.close()
            }
        }

        fun analyzeBitmap(image : InputImage){
            scanner.process(image)
                .addOnSuccessListener { barcodes ->

                    barcodes.mapNotNull { barcode ->
                        Log.d(TAG, barcode.rawValue.toString())
                    }

                    val rects = barcodes.mapNotNull { barcode ->
                        barcode.boundingBox?.let { boundingBox ->
                            boundingBox
                        }
                    }
                    onBarcodesDetected(rects)
                }
                .addOnFailureListener {
                    Log.d(TAG, it.toString())
                }
        }

        private fun mapRectToPreviewView(rect: Rect, imageProxy: ImageProxy, previewView: PreviewView): Rect {
            val previewWidth = previewView.width.toFloat()
            val previewHeight = previewView.height.toFloat()

            val imageWidth = imageProxy.width.toFloat()
            val imageHeight = imageProxy.height.toFloat()

            val scaleX = previewWidth / imageHeight // Notice the swapped width and height
            val scaleY = previewHeight / imageWidth // Notice the swapped width and height

            val left = rect.left * scaleX
            val top = rect.top * scaleY
            val right = rect.right * scaleX
            val bottom = rect.bottom * scaleY

            return Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        }
    }
}
