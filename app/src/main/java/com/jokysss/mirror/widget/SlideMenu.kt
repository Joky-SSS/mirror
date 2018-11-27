package com.jokysss.mirror.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.widget.Scroller
import com.jokysss.mirror.R
import kotlin.math.absoluteValue


class SlideMenu @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private val TAG = "SlideMenu"
    private val DO_MOVING = 0x001
    private val NOT_MOVING = 0x002
    private var moving = NOT_MOVING    //是否可以滑动，默认不能滑动
    private val FLAG_SEPARATOR = 0x1//标记变量，是否有分割线，占用最后一位
    private val FLAG_IS_OPEN = FLAG_SEPARATOR shl 1 //标记变量，是否已打开，占用倒数第二位
    private var flags = FLAG_SEPARATOR shr 1//存储标记变量
    private var slidingWidth: Int = 0//侧边栏宽度
    private var separator: Float = 0.toFloat()//分割线宽度
    private var touchWidth: Int = 0//感应宽度
    private var screenWidth: Int = 0//屏幕宽度
    private var paint: Paint? = null
    private var preX = 0
    private var firstX = 0
    private lateinit var scroller: Scroller
    private var minFlingVelocity = 0
    private var mTracker: VelocityTracker? = null
    private var mTouchslop = 0

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        var a = context.obtainStyledAttributes(attrs, R.styleable.SlideMenu)
        slidingWidth = a.getDimensionPixelSize(R.styleable.SlideMenu_sliding_width, dp2px(150))
        separator = a.getDimensionPixelSize(R.styleable.SlideMenu_separator, dp2px(1)).toFloat()
        touchWidth = a.getDimensionPixelSize(R.styleable.SlideMenu_touch_width, dp2px(50))
        if (separator > 0)
            flags = flags or FLAG_SEPARATOR
        a.recycle()
        screenWidth = getScreenWidth()
        setBackgroundColor(Color.alpha(255))
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.strokeWidth = separator
        paint!!.color = Color.GRAY
        scroller = Scroller(context)
        var configuration = ViewConfiguration.get(context)
        minFlingVelocity = configuration.scaledMinimumFlingVelocity
        mTouchslop = configuration.scaledTouchSlop
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        super.addView(child, index, params)
        if (childCount > 2)
            throw ArrayIndexOutOfBoundsException("Children count can't be more than 2.")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (i == 0) {
                var menuWMeasure = MeasureSpec.makeMeasureSpec(slidingWidth, MeasureSpec.EXACTLY)
                var menuHMeasure = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                child.measure(menuWMeasure, menuHMeasure)
            } else {
                var menuWMeasure = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY)
                var menuHMeasure = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                child.measure(menuWMeasure, menuHMeasure)
            }
        }
//        measureChildren(width, height)
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        var mode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        if (mode == MeasureSpec.AT_MOST) {
            throw IllegalArgumentException("layout_width can not be wrap_content")
        }
        return (screenWidth + slidingWidth + separator).toInt()
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var mode = MeasureSpec.getMode(heightMeasureSpec)
        var size = MeasureSpec.getSize(heightMeasureSpec)
        if (mode == MeasureSpec.AT_MOST) {
            throw IllegalArgumentException("layout_height can not be wrap_content")
        }
        var height = 0
        if (mode == MeasureSpec.EXACTLY)
            height = size
        return height
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val n = childCount
        val h = b - t
        var isOpen = flags and FLAG_IS_OPEN == FLAG_IS_OPEN
        for (i in 0 until n) {
            val child = getChildAt(i)
            if (i == 0) {
                if (isOpen) {
                    child.layout(0, 0, slidingWidth, h)
                } else {
                    child.layout(-slidingWidth, t, 0, h)
                }
            }
            if (i == 1) {
                if (isOpen) {
                    child.layout(slidingWidth + separator.toInt(), 0, width, h)
                } else {
                    child.layout(0, 0, screenWidth, h)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (flags and FLAG_SEPARATOR == FLAG_SEPARATOR) {
            var left = slidingWidth + separator / 2
            canvas.drawLine(left, 0.toFloat(), left, measuredHeight.toFloat(), paint)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (flags and FLAG_IS_OPEN == FLAG_IS_OPEN) {
                    moving = DO_MOVING
                    if (ev.x > slidingWidth) {
                        return true
                    }
                } else if (ev.x > slidingWidth)
                    moving = NOT_MOVING
                else moving = DO_MOVING

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> moving = NOT_MOVING
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (moving == NOT_MOVING) return false
        var x: Int = event.x.toInt()
        if (mTracker == null)
            mTracker = VelocityTracker.obtain()
        mTracker?.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished)
                    scroller.abortAnimation()
                preX = x
                firstX = x
            }
            MotionEvent.ACTION_MOVE -> {
                var dx = x - preX
                if (dx > 0) {
                    if (scrollX - dx < -slidingWidth) {
                        dx = scrollX + slidingWidth
                    }
                } else {
                    if (scrollX - dx > 0) {
                        dx = scrollX
                    }
                }
                scrollBy(-dx, 0)
                preX = x
            }
            MotionEvent.ACTION_UP -> {
                var dx = x - firstX
                if (dx.absoluteValue <= mTouchslop && x > slidingWidth) {
                    if (flags and FLAG_IS_OPEN == FLAG_IS_OPEN) {
                        close()
                        return true
                    }
                }
                mTracker?.computeCurrentVelocity(1000)
                val velocityX = mTracker?.xVelocity?.toInt() ?: 0
                if (velocityX > minFlingVelocity) {
                    open()
                } else if (velocityX < -minFlingVelocity) {
                    close()
                } else {
                    if (scrollX.absoluteValue * 2 > slidingWidth) {
                        open()
                    } else {
                        close()
                    }
                }
                if (mTracker != null) {
                    mTracker!!.clear()
                    mTracker!!.recycle()
                    mTracker = null
                }
            }
        }
        return true
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, 0)
            postInvalidate()
        }
    }

    fun open() {
        if (scrollX + slidingWidth > 0) {
            scroller.startScroll(scrollX, 0, -scrollX - slidingWidth, 0)
            postInvalidate()
        }
        flags = flags or FLAG_IS_OPEN
    }

    fun close() {
        if (scrollX < 0) {
            scroller.startScroll(scrollX, 0, -scrollX, 0)
            postInvalidate()
        }
        flags = flags and FLAG_IS_OPEN.inv()
    }

    fun toggle() {
        var isOpen = flags and FLAG_IS_OPEN == FLAG_IS_OPEN
        if (isOpen) {
            close()
        } else {
            open()
        }
    }

    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                resources.displayMetrics).toInt()
    }

    private fun getScreenWidth(): Int {
        val wm = getContext()
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.x
    }
}
