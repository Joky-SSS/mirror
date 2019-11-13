package com.jokysss.mirror.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollLinearLayout extends LinearLayout {
    private Scroller mScroller;

    public ScrollLinearLayout(Context context) {
        this(context, null);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context, new LinearInterpolator());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void startScroll() {
        mScroller.startScroll(getScrollX(), getScrollY(), -getWidth(), 0, 10000);
        postInvalidate();
    }

    public void stopScroll() {
        mScroller.abortAnimation();
    }
}
