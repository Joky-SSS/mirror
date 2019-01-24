package com.jokysss.mirror.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jokysss.mirror.R
import kotlin.math.max
import kotlin.math.min

class PathHoverView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    lateinit var firstBitmap: Bitmap
    lateinit var secondBitmap: Bitmap
    var maxRadius = 0
    var progress = 0
        set(value) {
            var tempVal = min(max(value, 0), 100)
            field = tempVal
            postInvalidate()
        }
    init {
        firstBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ff)
        secondBitmap = BitmapFactory.decodeResource(resources, R.mipmap.hh)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maxRadius = Math.sqrt(w * w + h * h + 0.0).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        var leftPath = Path()
//        leftPath.lineTo(100F,100F)
//        leftPath.setLastPoint(0F,0F)
        leftPath.addRect(0F, 0F, width.toFloat(), height.toFloat(), Path.Direction.CW)
        var rightPath = Path(leftPath)

        var circlePath = Path()
//        circlePath.setLastPoint(0F,0F)
        circlePath.addCircle(0F, 0F, (maxRadius * progress / 100).toFloat(), Path.Direction.CW)
        leftPath.op(circlePath, Path.Op.INTERSECT)
        rightPath.op(circlePath, Path.Op.DIFFERENCE)

        var paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
        paint.color = Color.RED


        canvas.drawPath(leftPath, paint)
        paint.color = Color.GREEN
        canvas.drawPath(rightPath, paint)

//        canvas.save()
//        canvas.clipPath(leftPath)
//        canvas.drawBitmap(firstBitmap, 0F, 0F, null)
//        canvas.restore()
//        canvas.save()
//        canvas.clipPath(rightPath)
//        canvas.drawBitmap(secondBitmap, 0F, 0F, null)
//        canvas.restore()

    }
}