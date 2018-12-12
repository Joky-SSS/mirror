package com.jokysss.mirror.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jokysss.mirror.R


class TabIcon @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    val paint = Paint()
    var mIcon: Bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.icon_found)
    var colorAlpha = 0

    init {
        paint.color = Color.rgb(0, 255, 0)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(mIcon, 0F, 0F, null)
        setupTargetBitmap(50)
        canvas.drawBitmap(mBitmap, 0F, 0F, null)

    }

    private var mBitmap: Bitmap? = null

    private fun setupTargetBitmap(alpha: Int) {
        mBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight,
                Bitmap.Config.ARGB_8888)
        var mCanvas = Canvas(mBitmap)
        var mPaint = Paint()
        mPaint.color = Color.rgb(0, 255, 0)
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.alpha = alpha
        mCanvas.drawRect(Rect(0, 0, mIcon.width, mIcon.height), mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mPaint.alpha = 255
        mCanvas.drawBitmap(mIcon, null, Rect(0, 0, mIcon.width, mIcon.height), mPaint)
    }
}