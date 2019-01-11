package com.jokysss.mirror.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jokysss.mirror.R

class PathHoverView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    lateinit var firstBitmap: Bitmap
    lateinit var secondBitmap: Bitmap

    init {
        firstBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ff)
        secondBitmap = BitmapFactory.decodeResource(resources, R.mipmap.hh)
    }

    override fun onDraw(canvas: Canvas) {
        var path = Path()
        path.moveTo((width / 3 * 2).toFloat(), 0F)
        path.quadTo((width / 3 * 2).toFloat(), height.toFloat(), (width / 3).toFloat(), height.toFloat())
        var leftPath = Path(path)
        leftPath.lineTo(0F, height.toFloat())
        leftPath.lineTo(0F, 0F)
        leftPath.close()

        var rightPath = Path(path)
        rightPath.lineTo(width.toFloat(), height.toFloat())
        rightPath.lineTo(width.toFloat(), 0F)
        rightPath.close()

        var paint = Paint()
        paint.strokeWidth = 10F

        paint.color = Color.RED

        canvas.drawPath(leftPath, paint)
        paint.color = Color.GREEN
        canvas.drawPath(rightPath, paint)

        canvas.save()
        canvas.clipPath(leftPath)
        canvas.drawBitmap(firstBitmap, 0F, 0F, null)
        canvas.restore()
        canvas.save()
        canvas.clipPath(rightPath)
        canvas.drawBitmap(secondBitmap, 0F, 0F, null)
        canvas.restore()

    }
}