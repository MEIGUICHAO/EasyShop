package com.example.moguhaian.easyshop.weidge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;


public class MyWebView extends WebView {



    private Paint mPaint;
    private Path mPath;
    private float ACTION_DOWN_X;
    private float ACTION_DOWN_Y;

    public float getACTION_DOWN_X() {
        return ACTION_DOWN_X;
    }

    public float getACTION_DOWN_Y() {
        return ACTION_DOWN_Y;
    }

    public float getACTION_UP_X() {
        return ACTION_UP_X;
    }

    public float getACTION_UP_Y() {
        return ACTION_UP_Y;
    }

    private float ACTION_UP_X;

    public void setACTION_DOWN_X(float ACTION_DOWN_X) {
        this.ACTION_DOWN_X = ACTION_DOWN_X;
    }

    public void setACTION_DOWN_Y(float ACTION_DOWN_Y) {
        this.ACTION_DOWN_Y = ACTION_DOWN_Y;
    }

    public void setACTION_UP_X(float ACTION_UP_X) {
        this.ACTION_UP_X = ACTION_UP_X;
    }

    public void setACTION_UP_Y(float ACTION_UP_Y) {
        this.ACTION_UP_Y = ACTION_UP_Y;
    }

    private float ACTION_UP_Y;

    public boolean isNeedDraw() {
        return NeedDraw;
    }

    public void setNeedDraw(boolean needDraw) {
        NeedDraw = needDraw;
    }

    private boolean NeedDraw = false;


    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (NeedDraw) {
            initView(context, attrs, defStyleAttr);
        }
    }


    private void initView(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        int color = ta.getColor(R.styleable.MyView_paint_color, Color.RED);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (NeedDraw) {
            if (null == mPath) {
                mPath = new Path();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath.moveTo(event.getX(), event.getY());
                    ACTION_DOWN_X = event.getX();
                    ACTION_DOWN_Y = event.getY();
                    LogUtils.e("ACTION_DOWN_X:"+event.getX());
                    LogUtils.e("ACTION_DOWN_Y:" + event.getY());
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(event.getX(), event.getY());
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    ACTION_UP_X = event.getX();
                    ACTION_UP_Y = event.getY();
                    LogUtils.e("ACTION_UP_X:"+event.getX());
                    LogUtils.e("ACTION_UP_Y:" + event.getY());
                case MotionEvent.ACTION_CANCEL:
                    mPath.lineTo(event.getX(), event.getY());
                    invalidate();
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (NeedDraw) {
            if (null == mPath) {
                mPath = new Path();
            }
            if (null == mPaint) {

                mPaint = new Paint();
                mPaint.setAntiAlias(false);
                mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_red_dark));

                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(4);
            }
            canvas.drawPath(mPath, mPaint);
        }
    }

}
