package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint()
    private var path: MutableList<Pair<Float, Float>> = mutableListOf()
    private var bitmap: Bitmap? = null

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, paint)
        }
        for (i in 0 until path.size - 1) {
            val (x1, y1) = path[i]
            val (x2, y2) = path[i + 1]
            canvas.drawLine(x1, y1, x2, y2, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.clear()
                path.add(Pair(event.x, event.y))
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.add(Pair(event.x, event.y))
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap!!)
                draw(canvas)
                return true
            }
        }
        return false
    }

    fun clearCanvas() {
        path.clear()
        bitmap = null
        invalidate()
    }

    fun getBitmap(): Bitmap {
        return bitmap ?: Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }
}
