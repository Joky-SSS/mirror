package com.jokysss.mirror.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.ListView
import android.widget.Scroller
import kotlin.math.absoluteValue

class SwipeDeleteListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ListView(context, attrs, defStyleAttr) {

    private var velocityTracker: VelocityTracker? = null
    private var preX = 0F
    private var firstX = 0F
    private var firstY = 0F
    private var flingView: View? = null
    private var postion = INVALID_POSITION
    private var touchSlop: Int = 0
    private var snapVelocity = 600
    private var isSlide = false
    private var verticalSlide = false
    private var minVelocity = 0
    private var isRollback = false
    private var scroller: Scroller = Scroller(context)
    var removeListener: OnItemRemoveListener? = null

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
        obtainVelocityTracker(ev)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished) return super.dispatchTouchEvent(ev)
                firstX = x
                preX = x
                firstY = y
                postion = pointToPosition(x.toInt(), y.toInt())
                if (postion != INVALID_POSITION) {
                    var index = postion - firstVisiblePosition
                    flingView = getChildAt(index)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                velocityTracker!!.computeCurrentVelocity(1000)
                var velocity = velocityTracker!!.xVelocity
                if (!verticalSlide && x > firstX
                        && velocity.absoluteValue > snapVelocity
                        && (x - firstX).absoluteValue > touchSlop && (y - firstY).absoluteValue < touchSlop) {
                    isSlide = true
                }
                if ((x - firstX).absoluteValue < touchSlop && (y - firstY).absoluteValue > touchSlop) {
                    verticalSlide = true
                }
            }
            MotionEvent.ACTION_UP -> {
                verticalSlide = false
                releaseVelocityTracker()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (isSlide && postion != INVALID_POSITION) {
            var x = ev.x
            when (ev.action) {
                MotionEvent.ACTION_MOVE -> {
                    var dx = x - preX
                    flingView!!.scrollBy(-dx.toInt(), 0)
                    preX = x
                }
                MotionEvent.ACTION_UP -> {
                    releaseVelocityTracker()
                    var scrollX = flingView?.scrollX ?: 0
                    var listViewW = measuredWidth
                    if (scrollX < 0 && scrollX.absoluteValue * 2 > listViewW) {
                        var remain = listViewW + scrollX
                        scroller.startScroll(scrollX, 0, -remain, 0)
                        isRollback = false
                    } else {
                        scroller.startScroll(scrollX, 0, -scrollX, 0)
                        isRollback = true
                    }
                    postInvalidate()
//                    postion = INVALID_POSITION
//                    flingView = null
                    isSlide = false
                    verticalSlide = false
                }
            }
            return true
        }
        return super.onTouchEvent(ev)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            flingView?.scrollTo(scroller.currX, 0)
            invalidate()
            if (scroller.isFinished) {
                if (!isRollback) {
                    removeListener?.onItemRemoved(postion)
                    flingView?.scrollTo(0, 0)
                }
                flingView = null
                postion = INVALID_POSITION
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

    interface OnItemRemoveListener {
        fun onItemRemoved(position: Int)
    }
}
