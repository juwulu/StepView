package com.jwl.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author:  lujunwu
 * date:    2018/10/9
 * desc:    进度View
 */
public class StepView extends View {

    private int mStepFinishColor;
    private int mStepUnfinishColor;
    private int mStepCount;
    private int mStepNumberTextSize;
    private int mStepBottomTextSize;
    private int mStepTxtMarginTop;
    private Paint mPaint;
    private int mStepCircleRadius;

    public int getCurrentStep() {
        return mCurrentStep;
    }

    public void setCurrentStep(int currentStep) {
        mCurrentStep = currentStep;
        invalidate();
    }

    public void setBottomText(String[] bottomText) {
        this.bottomText = bottomText;
        invalidate();
    }

    private int mCurrentStep = 3   ;
    private String[] bottomText = {"开始","扫描", "开锁", "支付","完成"};


    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.StepView);
        mStepFinishColor = t.getColor(R.styleable.StepView_step_finish_color, 0xFF4DD5BD);
        mStepUnfinishColor = t.getColor(R.styleable.StepView_step_unfinish_color, 0XFFCCCCCC);
        mStepCount = t.getInteger(R.styleable.StepView_step_count, 0);
        mStepNumberTextSize = t.getDimensionPixelSize(R.styleable.StepView_step_number_text_size, dp2px(context, 10));
        mStepBottomTextSize = t.getDimensionPixelSize(R.styleable.StepView_step_bottom_text_size, dp2px(context, 10));
        mStepTxtMarginTop = t.getDimensionPixelSize(R.styleable.StepView_step_txt_margin_top, dp2px(context, 10));
        mStepCircleRadius = t.getDimensionPixelSize(R.styleable.StepView_step_circle_radius, dp2px(context, 10));
        init();
        t.recycle();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mStepNumberTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            int fontHeight = (int) (Math.ceil(mPaint.descent()) - Math.ceil(mPaint.ascent()));
            height = getPaddingTop() + mStepCircleRadius * 2 + mStepTxtMarginTop + fontHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int childWidth = getWidth() / mStepCount;
        for (int i = 1; i <= mStepCount; i++) {
            int centerX = childWidth * i - childWidth / 2;
            int startCircleY = getPaddingTop() + mStepCircleRadius;
            int fontHeight = (int) Math.ceil(mPaint.descent() - mPaint.ascent());
            int fontOffset = (int) -(mPaint.ascent() + mPaint.descent()) / 2;
            drawStep(canvas, i, centerX, startCircleY, fontHeight, fontOffset);
        }
    }

    private void drawStep(Canvas canvas, int i, int centerX, int startCircleY, int fontHeight, int fontOffset) {
        mPaint.setColor(mCurrentStep >= i ? mStepFinishColor : mStepUnfinishColor);
        canvas.drawCircle(centerX, startCircleY, mStepCircleRadius, mPaint);
        mPaint.setTextSize(mStepBottomTextSize);
        canvas.drawText(bottomText[i-1],centerX,mStepCircleRadius*2+mStepTxtMarginTop+fontOffset,mPaint);
        mPaint.setStrokeWidth(dp2px(getContext(), 3));
        if (mStepCount > i) {
            mPaint.setColor(mCurrentStep > i ? mStepFinishColor : mStepUnfinishColor);
            canvas.drawLine(centerX + mStepCircleRadius, startCircleY, centerX + getWidth() / mStepCount - mStepCircleRadius, startCircleY, mPaint);
        }
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(mStepNumberTextSize);
        canvas.drawText(String.valueOf(i), centerX, fontOffset + mStepCircleRadius, mPaint);
        mPaint.setColor(mStepUnfinishColor);
    }


    private int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dpValue + 0.5f);
    }
}
