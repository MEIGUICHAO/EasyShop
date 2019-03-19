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


public class MyWebView extends WebView {



    private Paint mPaint;
    private Path mPath;
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
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath.moveTo(event.getX(), event.getY());
                    LogUtils.e("ACTION_DOWN_X:"+event.getX());
                    LogUtils.e("ACTION_DOWN_Y:" + event.getY());
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(event.getX(), event.getY());
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
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
