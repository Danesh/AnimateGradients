package com.example.danesh.gradienttransition;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class AnimateGradients extends View {
    private int mStartColor, mEndColor;
    private ValueAnimator mAnimator;
    private ArgbEvaluator mArgbEvaluator;
    private Paint mPaint;

    public AnimateGradients(Context context) {
        super(context);
        init();
    }

    public AnimateGradients(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimateGradients(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void startTransition(final int[] startGradient, final int[] endGradient, int duration) {
        if (startGradient == null || startGradient.length != 2) {
            throw new IllegalArgumentException("Start gradient must be of size 2");
        }
        if (endGradient == null || endGradient.length != 2) {
            throw new IllegalArgumentException("End gradient must be of size 2");
        }
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator.setDuration(duration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mStartColor = (Integer) mArgbEvaluator.evaluate(valueAnimator.getAnimatedFraction(),
                        startGradient[0], endGradient[0]);
                mEndColor = (Integer) mArgbEvaluator.evaluate(valueAnimator.getAnimatedFraction(),
                        startGradient[1], endGradient[1]);
                invalidate();
            }
        });
        mAnimator.start();
    }

    private void init() {
        mArgbEvaluator = new ArgbEvaluator();
        mAnimator = new ValueAnimator();
        mAnimator.setFloatValues(0, 1);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation")
        LinearGradient linearGradient = new LinearGradient(getWidth() / 2, 0, getWidth() / 2,
                getHeight(), new int[]{mStartColor, mEndColor}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

}
