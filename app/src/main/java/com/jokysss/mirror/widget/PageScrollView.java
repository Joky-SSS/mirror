package com.jokysss.mirror.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class PageScrollView extends ViewGroup {
    private static final String TAG = PageScrollView.class.getSimpleName();
    Scroller mScroller;
    int touchSlop = 0;
    int lastX;
    int maxScrollX;
    int spliteScrollX;
    VelocityTracker mTracker;
    int minFlingVelocity;
    int currentPos = 0;
    public PageScrollView(Context context) {
        this(context, null);
    }

    public PageScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        minFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int widthMeasureSpec) {
        int width = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("MeasureSpec.AT_MOST");
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("MeasureSpec.AT_MOST");
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        return height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int n = getChildCount();
        int w = r - l;
        int h = b - t;
        for (int i = 0; i < n; i++) {
            View child = getChildAt(i);
            child.layout(w * i, 0, w * i + w, h);
        }
        spliteScrollX = w;
        if (n > 1)
            maxScrollX = (n - 1) * w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null && !mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                lastX = (int) event.getX();
                if (mTracker == null)
                    mTracker = VelocityTracker.obtain();
                mTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);
                int dx = x - lastX;
                int currentScrollX = getScrollX();
                if (dx > 0) {
                    if (currentScrollX - dx < 0) {
                        dx = currentScrollX;
                    }
                } else {
                    if (currentScrollX - dx > maxScrollX) {
                        dx = currentScrollX - maxScrollX;
                    }
                }
                scrollBy(-dx, 0);
                Log.e(TAG, "lastX -> " + lastX + " ,x -> " + x + " ,dx -> " + dx);
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTracker.addMovement(event);
                Log.e(TAG, "getScrollX -> " + getScrollX());
                mTracker.computeCurrentVelocity(1000);
                int velocityX = (int) mTracker.getXVelocity();
                Log.e(TAG, "velocityX -> " + velocityX);
                if (velocityX > minFlingVelocity && currentPos > 0) {
                    moveToPrevious();
                } else if (velocityX < -minFlingVelocity && currentPos < getChildCount() - 1) {
                    moveToNext();
                } else {
                    moveToDestination();
                }
                if (mTracker != null) {
                    mTracker.clear();
                    mTracker.recycle();
                    mTracker = null;
                }
                break;
        }
        return true;
    }

    private void moveToPrevious() {
        moveToPostion(currentPos - 1);
    }

    private void moveToNext() {
        moveToPostion(currentPos + 1);
    }

    private void moveToDestination() {
        int pos = (int) ((getScrollX() + spliteScrollX / 2) / spliteScrollX);
        moveToPostion(pos);
    }

    private void moveToPostion(int pos) {
        if (pos < 0)
            pos = 0;
        if (pos >= getChildCount())
            pos = getChildCount() - 1;
        currentPos = pos;
        int scrollX = getScrollX();
        int dest = pos * spliteScrollX;
        int dx = dest - scrollX;
        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx));
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
