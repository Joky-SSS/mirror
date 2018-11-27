package com.jokysss.mirror.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.ListView
import android.widget.Scroller
import kotlin.math.absoluteValue

/**
 * 侧滑菜单ListView
 */
class SwipeMenuListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ListView(context, attrs, defStyleAttr) {

    private var velocityTracker: VelocityTracker? = null
    private var preX = 0F
    private var firstX = 0F
    private var firstY = 0F
    private var flingView: SwipeMenuLayout? = null
    private var position = INVALID_POSITION
    private var touchSlop: Int = 0
    private var isSlide = false
    private var verticalSlide = false
    private var minVelocity = 0
    private var isOpen = false
    private var forceReturn = false
    private var scroller: Scroller = Scroller(context)
    private var hasSlide = false
    var onMenuClickListener: IOnMenuClickListener? = null

    init {
        init(context)
    }

    private fun init(context: Context) {
        val configuration = ViewConfiguration.get(context)
        touchSlop = configuration.scaledTouchSlop
        minVelocity = configuration.scaledMinimumFlingVelocity
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var action = ev.action
        var x = ev.x
        var y = ev.y
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished) return super.dispatchTouchEvent(ev)
                firstX = x
                preX = x
                firstY = y
                position = pointToPosition(x.toInt(), y.toInt())
                var view: View? = null
                if (position != INVALID_POSITION) {
                    var index = position - firstVisiblePosition
                    view = getChildAt(index)
                }
                if (isOpen) {
                    if (view != flingView) {
                        close()
                        forceReturn = true
                        return true
                    } else {
                        isSlide = true
                    }
                }
                if (view is SwipeMenuLayout) flingView = view
            }
            MotionEvent.ACTION_MOVE -> {
                if (forceReturn) return true
                if (!verticalSlide
                        && (x - firstX).absoluteValue > touchSlop && (y - firstY).absoluteValue < touchSlop) {
                    isSlide = true
                }
                if ((x - firstX).absoluteValue < touchSlop && (y - firstY).absoluteValue > touchSlop) {
                    verticalSlide = true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                var needReturn = forceReturn
                forceReturn = false
                verticalSlide = false
                if (needReturn) {
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (isOpen || isSlide) {
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (forceReturn) return true
        if (isSlide && position != INVALID_POSITION) {
            obtainVelocityTracker(ev)
            var x = ev.x
            when (ev.action) {
                MotionEvent.ACTION_MOVE -> {
                    hasSlide = true
                    var dx = x - preX
                    if (dx > 0) {
                        if (flingView!!.scrollX - dx < 0) dx = flingView!!.scrollX.toFloat()
                    } else if (dx < 0) {
                        if (flingView!!.scrollX - dx > flingView!!.rightMenuWidth) dx = (flingView!!.scrollX - flingView!!.rightMenuWidth).toFloat()
                    }
                    flingView!!.scrollBy(-dx.toInt(), 0)
                    preX = x
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    velocityTracker?.computeCurrentVelocity(1000)
                    var velocity = velocityTracker?.xVelocity?.toInt() ?: 0
                    releaseVelocityTracker()
                    if (hasSlide) {
                        var scrollX = flingView?.scrollX ?: 0
                        if (velocity > 0) {
                            if (velocity > minVelocity || scrollX < flingView!!.limit) {
                                close()
                            } else {
                                open()
                            }
                        } else if (velocity < 0) {
                            if (velocity.absoluteValue > minVelocity || scrollX > flingView!!.limit) {
                                open()
                            } else {
                                close()
                            }
                        } else {
                            if (scrollX > flingView!!.limit) {
                                open()
                            } else {
                                close()
                            }
                        }
                    } else {
                        if (isOpen) {
                            if (firstX < flingView!!.contentWidth - flingView!!.rightMenuWidth) {

                            } else {
                                for (child in flingView!!.childSet) {
                                    var rect = Rect()
                                    child.getGlobalVisibleRect(rect)
                                    if (rect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                                        onMenuClickListener?.onMenuClick(child, position)
                                        break
                                    }
                                }
                            }
                            close()
                        }
                    }
//                    position = INVALID_POSITION
//                    flingView = null
                    isSlide = false
                    hasSlide = false
                }
            }
            return true
        }
        return super.onTouchEvent(ev)
    }

    fun open() {
        if (flingView == null) return
        var dx = flingView!!.rightMenuWidth - flingView!!.scrollX
        scroller.startScroll(flingView!!.scrollX, 0, dx, 0, dx)
        isOpen = true
        postInvalidate()
    }

    fun close() {
        if (flingView == null) return
        var dx = -flingView!!.scrollX
        scroller.startScroll(flingView!!.scrollX, 0, dx, 0, -dx)
        isOpen = false
        postInvalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            flingView?.scrollTo(scroller.currX, 0)
            invalidate()
            if (scroller.isFinished) {
                if (isOpen) {

                } else {
                    flingView = null
                    position = INVALID_POSITION
                }
            }
        }
    }

    private fun obtainVelocityTracker(motionEvent: MotionEvent) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(motionEvent)
    }

    private fun releaseVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker!!.clear()
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    interface IOnMenuClickListener {
        fun onMenuClick(menuView: View, position: Int)
    }

    interface OnItemRemoveListener {
        fun onItemRemoved(position: Int)
    }

}
