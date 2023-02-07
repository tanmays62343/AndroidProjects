package com.TrX

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context : Context, attrs : AttributeSet) : View(context,attrs){

    private var mDrawPath : CustomPath? = null
    private var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint : Paint? = null
    private var mCanvasPaint : Paint? = null
    private var mBrushSize : Float = 0.toFloat()
    private var mColor = Color.BLACK
    private var mCanvas : Canvas? = null

    init{
        SetUpDrawing()
    }

    private fun SetUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(mColor,mBrushSize)
        mDrawPaint!!.color = mColor
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mCanvasBitmap!!)
    }

    //change canva to canvas?
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)
        if(!mDrawPath!!.isEmpty) {
            mDrawPaint!!.strokeWidth = mDrawPath!!.BrushThickness
            mDrawPaint!!.color = mDrawPath!!.Color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                mDrawPath!!.Color = mColor
                mDrawPath!!.BrushThickness = mBrushSize

                mDrawPath!!.reset()
                mDrawPath!!.moveTo(touchX!!,touchY!!)
            }
            MotionEvent.ACTION_MOVE ->{
                mDrawPath!!.lineTo(touchX!!,touchY!!)
            }
            MotionEvent.ACTION_UP ->{
                mDrawPath = CustomPath(mColor, mBrushSize)
            }
            else -> return false
        }
        invalidate()

        return true
    }

    internal inner class CustomPath(var Color : Int, var BrushThickness: Float ) : Path(){

    }

}