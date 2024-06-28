package com.trx.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.trx.R
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.LinkedList
import kotlin.math.max

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: List<Detection> = LinkedList<Detection>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var scaleFactor: Float = 1f

    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.RED
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        results.forEachIndexed { index, result ->
            val boundingBox = result.boundingBox

            val top = boundingBox.top * scaleFactor
            val bottom = boundingBox.bottom * scaleFactor
            val left = boundingBox.left * scaleFactor
            val right = boundingBox.right * scaleFactor

            // Draw bounding box around detected objects
            val drawableRect = RectF(left, top, right, bottom)
            //canvas.drawRect(drawableRect, boxPaint)

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

    fun setResults(
        detectionResults: MutableList<Detection>,
        imageHeight: Int,
        imageWidth: Int,
    ) {
        results = detectionResults

        // PreviewView is in FILL_START mode. So we need to scale up the bounding box to match with
        // the size that the captured images will be displayed.
        scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)
    }

    //TOP : Right
    private fun drawTopRightArrow(canvas: Canvas, right: Float, top: Float) {
        val offset = 80f  // Increased offset distance
        val path = Path()
        path.moveTo(right + offset, top - offset)
        path.lineTo(right - 80 + offset, top - offset)
        path.lineTo(right + offset, top + 80 - offset)
        path.close()
        canvas.drawPath(path, textBackgroundPaint)
    }

    //TOP : Left
    private fun drawTopLeftArrow(canvas: Canvas, left: Float, top: Float) {
        val offset = 80f  // Increased offset distance
        val path = Path()
        path.moveTo(left - offset, top - offset)
        path.lineTo(left + 80 - offset, top - offset)
        path.lineTo(left - offset, top + 80 - offset)
        path.close()
        canvas.drawPath(path, textBackgroundPaint)
    }

    //BOTTOM : Right
    private fun drawBottomRightArrow(canvas: Canvas, right: Float, bottom: Float) {
        val offset = 60f  // Increased offset distance
        val path = Path()
        path.moveTo(right + offset, bottom + offset)
        path.lineTo(right - 80 + offset, bottom + offset)
        path.lineTo(right + offset, bottom - 80 + offset)
        path.close()
        canvas.drawPath(path, textBackgroundPaint)
    }

    //BOTTOM : Left
    private fun drawBottomLeftArrow(canvas: Canvas, left: Float, bottom: Float) {
        val offset = 60f  // Increased offset distance
        val path = Path()
        path.moveTo(left - offset, bottom + offset)
        path.lineTo(left + 80 - offset, bottom + offset)
        path.lineTo(left - offset, bottom - 80 + offset)
        path.close()
        canvas.drawPath(path, textBackgroundPaint)
    }


    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}