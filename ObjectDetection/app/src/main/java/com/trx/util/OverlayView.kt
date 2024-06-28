package com.trx.util

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.trx.R
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.LinkedList
import kotlin.math.max

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: List<Detection> = LinkedList()
    private val boxPaint = Paint()
    private val arrowColor = Paint()
    private var scaleFactor: Float = 1f
    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        arrowColor.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        arrowColor.color = Color.RED
        arrowColor.style = Paint.Style.FILL
        arrowColor.textSize = 50f

        //If we want to make BOX
        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        results.forEachIndexed { index, result ->
            val boundingBox = result.boundingBox

            val top = boundingBox.top * scaleFactor
            val bottom = boundingBox.bottom * scaleFactor
            val left = boundingBox.left * scaleFactor
            val right = boundingBox.right * scaleFactor

            // Draw bounding box around detected objects
            val drawableRect = RectF(left, top, right, bottom)
            //canvas.drawRect(drawableRect, boxPaint)

            val boxWidth = calculateDistance(left, right)
            Log.d("Distance: ", boxWidth.toString())

            val screenWidth = getScreenWidthInPixels(context)
            Log.d("ScreenWidth", screenWidth.toString())

            if (boxWidth > 0.7 * screenWidth && boxWidth < 0.85 * screenWidth) {
                arrowColor.color = Color.GREEN
            } else {
                arrowColor.color = Color.RED
            }

            // Draw arrow-like corners for the first and last detected boxes
            if (index == 0) {
                drawTopLeftArrow(canvas, left, top)
                drawTopRightArrow(canvas, right, top)
            }
            if (index == results.size - 1) {
                drawBottomRightArrow(canvas, right, bottom)
                drawBottomLeftArrow(canvas, left, bottom)
            }
        }
    }

    private fun calculateDistance(left: Float, right: Float): Float {
        return right - left
    }

    private fun getScreenWidthInPixels(context: Context?): Int {
        val displayMetrics = context?.resources?.displayMetrics
        return displayMetrics?.widthPixels ?: 0
    }

    fun setResults(
        detectionResults: MutableList<Detection>,
        imageHeight: Int,
        imageWidth: Int,
    ) {
        results = detectionResults

        // PreviewView is in FILL_START mode. So we need to scale up the bounding box to match with
        // the size that the captured images will be displayed.
        scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)

        // Only invalidate the view when there are new results
        if (detectionResults.isNotEmpty()) {
            postInvalidateOnAnimation()
        }
    }

    //TOP : Right
    private fun drawTopRightArrow(canvas: Canvas, right: Float, top: Float) {
        val offset = 80f  // Increased offset distance
        val path = Path().apply {
            moveTo(right + offset, top - offset)
            lineTo(right - 80 + offset, top - offset)
            lineTo(right + offset, top + 80 - offset)
            close()
        }
        canvas.drawPath(path, arrowColor)
    }

    //TOP : Left
    private fun drawTopLeftArrow(canvas: Canvas, left: Float, top: Float) {
        val offset = 80f  // Increased offset distance
        val path = Path().apply {
            moveTo(left - offset, top - offset)
            lineTo(left + 80 - offset, top - offset)
            lineTo(left - offset, top + 80 - offset)
            close()
        }
        canvas.drawPath(path, arrowColor)
    }

    //BOTTOM : Right
    private fun drawBottomRightArrow(canvas: Canvas, right: Float, bottom: Float) {
        val offset = 60f  // Increased offset distance
        val path = Path().apply {
            moveTo(right + offset, bottom + offset)
            lineTo(right - 80 + offset, bottom + offset)
            lineTo(right + offset, bottom - 80 + offset)
            close()
        }
        canvas.drawPath(path, arrowColor)
    }

    //BOTTOM : Left
    private fun drawBottomLeftArrow(canvas: Canvas, left: Float, bottom: Float) {
        val offset = 60f  // Increased offset distance
        val path = Path().apply {
            moveTo(left - offset, bottom + offset)
            lineTo(left + 80 - offset, bottom + offset)
            lineTo(left - offset, bottom - 80 + offset)
            close()
        }
        canvas.drawPath(path, arrowColor)
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}
