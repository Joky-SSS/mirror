package com.jokysss.mirror.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SearchView extends View {
    private Paint mPaint;
    private Path mSearchPath;
    private Path mCirclePath;
    private PathMeasure mMeasure;
    private int mWidth;
    private int mHeight;
    private State mState = State.NONE;
    private ValueAnimator mValueAnimator;
    private float mAnimatorValue = 0.0F;
    private Path tempPath = new Path();
    private int mCount = 0;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public enum State {
        NONE, START, SEARCH, END
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(16);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        RectF rectF1 = new RectF(-50, -50, 50, 50);
        RectF rectF2 = new RectF(-100, -100, 100, 100);
        mSearchPath = new Path();
        mSearchPath.addArc(rectF1, 45F, 359.9F);
        mCirclePath = new Path();
        mCirclePath.addArc(rectF2, 45F, -359.9F);
        float[] pos = new float[2];
        mMeasure = new PathMeasure();
        mMeasure.setPath(mCirclePath, false);
        mMeasure.getPosTan(0, pos, null);
        mSearchPath.lineTo(pos[0], pos[1]);
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(1500);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(999);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                switch (mState) {
                    case NONE:
                        mState = State.START;
                        break;
                    case START:
                        mState = State.SEARCH;
                        break;
                    case SEARCH:
                        mCount++;
                        if(mCount == 3){
                            mCount = 0;
                            mState = State.END;
                        }
                        break;
                    case END:
                        mState = State.NONE;
                        break;
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawColor(Color.parseColor("#1E8AE8"));
        switch (mState) {
            case NONE:
                canvas.drawPath(mSearchPath, mPaint);
                break;
            case START:
                tempPath.reset();
                mMeasure.setPath(mSearchPath, false);
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), tempPath, true);
                canvas.drawPath(tempPath, mPaint);
                break;
            case SEARCH:
                tempPath.reset();
                mMeasure.setPath(mCirclePath, false);
                float stop = mMeasure.getLength() * mAnimatorValue;
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mMeasure.getLength() * 0.4));
                mMeasure.getSegment(start, stop, tempPath, true);
                canvas.drawPath(tempPath, mPaint);
                break;
            case END:
                tempPath.reset();
                mMeasure.setPath(mSearchPath, false);
                mMeasure.getSegment(mMeasure.getLength() * (1 - mAnimatorValue), mMeasure.getLength(), tempPath, true);
                canvas.drawPath(tempPath, mPaint);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mValueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.pause();
    }
}
