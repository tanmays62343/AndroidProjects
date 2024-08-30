package com.trx.consts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class OverlayView(context: Context, attr: AttributeSet) : View(context, attr) {
    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private var rects: List<Rect> = emptyList()

    fun setRects(rects: List<Rect>) {
        this.rects = rects
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rects.forEach { rect ->
            canvas.drawRect(rect, paint)
        }
    }
}
