package com.jokysss.mirror.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class HaloView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mWidth;
    private int mHeight;
    private LinearGradient mGradient;
    private float smooth_value = 1.0F;
    private Random random = new Random();

    public HaloView(Context context) {
        this(context, null);
    }

    public HaloView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HaloView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mGradient = new LinearGradient(0, 0, mWidth, 0, new int[]{0xEE8673E9, 0xEEE378C0}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mGradient);
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF, 20, 20, mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);

        int xOffset = mWidth / 15;
        int yOffset = mHeight / 2 / 5 * 3;
        int yMid = mHeight / 2;
        int count = 5;
        Path path1 = new Path();
        path1.moveTo(-xOffset, yMid);
        for (int i = 0; i < count; i++) {
            path1.quadTo(xOffset * i * 2 + (int) (getMinRandom() * xOffset), (float) (yMid - Math.pow(-1, i) * yOffset * getRandom()), xOffset * (i * 2 + 1), yMid);
        }
        path1.quadTo((mWidth - xOffset * (count * 2 - 1)) / 2 + xOffset * (count * 2 - 1), mHeight * 9 / 10, mWidth, mHeight * 9 / 10);
        path1.lineTo(0, mHeight * 9 / 10);
        path1.close();
        mPaint.setColor(0x22FFFFFF);
        canvas.drawPath(path1, mPaint);

        canvas.save();
        canvas.translate(0, mHeight / 20);
        canvas.drawPath(path1, mPaint);
        canvas.restore();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 200);
    }

    private double getRandom() {
        return Math.max(Math.random(), 0.3);
    }

    private double getMinRandom() {
        return Math.min(Math.random(), 0.3);
    }

}
