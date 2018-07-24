package com.jokysss.mirror.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jokysss.mirror.R;

public class FancyView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private Camera camera = new Camera();
    private int mImgW;
    private int mImgH;
    private int mWidth;
    private int mHeight;
    private int xOffset;
    private int yOffset;
    private float rotation;
    private ValueAnimator animator;

    public FancyView(Context context) {
        this(context, null);
    }

    public FancyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FancyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fancy);
        mImgH = mBitmap.getHeight();
        mImgW = mBitmap.getWidth();
        camera.setLocation(0, 0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -6, getResources().getDisplayMetrics()));
        mRectPaint.setColor(Color.BLUE);
        mRectPaint.setStyle(Paint.Style.STROKE);
        animator = ValueAnimator.ofFloat(360);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(20000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setRotation((Float) animation.getAnimatedValue());
            }
        });
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.pause();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        xOffset = mImgW / 2;
        yOffset = mImgH / 2;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        camera.save();
        camera.rotateY(0);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(-rotation);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -mImgH, -mImgW, mImgH);
        canvas.drawRect(0, -mImgH, -mImgW, mImgH, mRectPaint);
        canvas.drawRect(0, -mImgH / 2, -mImgW / 2, mImgH / 2, mRectPaint);
        canvas.rotate(rotation);
        canvas.drawBitmap(mBitmap, -xOffset, -yOffset, mPaint);
        canvas.translate(-mWidth / 2, -mHeight / 2);
        canvas.restore();
        camera.restore();

        camera.save();
        camera.rotateY(-45);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(-rotation);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -mImgH, mImgW, mImgH);
        canvas.drawRect(0, -mImgH, mImgW, mImgH, mRectPaint);
        canvas.drawRect(0, -mImgH / 2, mImgW / 2, mImgH / 2, mRectPaint);
        canvas.rotate(rotation);
        canvas.drawBitmap(mBitmap, -xOffset, -yOffset, mPaint);
        canvas.translate(-mWidth / 2, -mHeight / 2);
        canvas.restore();
        camera.restore();

        //        canvas.rotate(-flipRotation);
        //        camera.applyToCanvas(canvas);
        //        canvas.clipRect(0, -IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
        //        canvas.rotate(flipRotation);
        //        canvas.translate(-offset, -offset);

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, 10, mRectPaint);
        canvas.drawRect(-xOffset, -yOffset, xOffset, yOffset, mRectPaint);
        canvas.drawLine(-xOffset, -yOffset, xOffset, yOffset, mRectPaint);
        canvas.drawLine(xOffset, -yOffset, -xOffset, yOffset, mRectPaint);
        canvas.drawLine(0, -yOffset, 0, yOffset, mRectPaint);
        canvas.drawLine(xOffset, 0, -xOffset, 0, mRectPaint);
    }
}
