package com.example.moguhaian.easyshop.weidge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyWebView extends WebView {

    private boolean slideRecord = false;

    private String loadUrl = "";

    public boolean isSlideRecord() {
        return slideRecord;
    }

    public void setSlideRecord(boolean slideRecord) {
        this.slideRecord = slideRecord;
    }

    public boolean isClickRecord() {
        return clickRecord;
    }

    public void setClickRecord(boolean clickRecord) {
        this.clickRecord = clickRecord;
    }

    private boolean clickRecord = false;


    private Paint mPaint;
    private Path mPath;
    private float ACTION_DOWN_X;
    private float ACTION_DOWN_Y;

    private float ACTION_CLICK_DOWN_X;

    public float getACTION_CLICK_DOWN_X() {
        return ACTION_CLICK_DOWN_X;
    }

    public void setACTION_CLICK_DOWN_X(float ACTION_CLICK_DOWN_X) {
        this.ACTION_CLICK_DOWN_X = ACTION_CLICK_DOWN_X;
    }

    public float getACTION_CLICK_DOWN_Y() {
        return ACTION_CLICK_DOWN_Y;
    }

    public void setACTION_CLICK_DOWN_Y(float ACTION_CLICK_DOWN_Y) {
        this.ACTION_CLICK_DOWN_Y = ACTION_CLICK_DOWN_Y;
    }

    private float ACTION_CLICK_DOWN_Y;

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

    public int getScrollYRange() {
        LogUtils.e("getScrollYRange");

        return computeVerticalScrollRange();
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
                    if (slideRecord) {
                        ACTION_DOWN_X = event.getX();
                        ACTION_DOWN_Y = event.getY();
                        LogUtils.e("ACTION_DOWN_X:"+ACTION_DOWN_X);
                        LogUtils.e("ACTION_DOWN_Y:" + ACTION_DOWN_Y);
                    }
                    if (clickRecord) {
                        ACTION_CLICK_DOWN_X = event.getX();
                        ACTION_CLICK_DOWN_Y = event.getY();
                        LogUtils.e("ACTION_CLICK_DOWN_X:"+ACTION_CLICK_DOWN_X);
                        LogUtils.e("ACTION_CLICK_DOWN_Y:" + ACTION_CLICK_DOWN_Y);
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(event.getX(), event.getY());
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (slideRecord) {
                        ACTION_UP_X = event.getX();
                        ACTION_UP_Y = event.getY();
                        LogUtils.e("ACTION_UP_X:"+event.getX());
                        LogUtils.e("ACTION_UP_Y:" + event.getY());
                    }
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



    public static String mDefaultEncoding = "UTF-8";

    public String mCss;
    String mHtml = null;

    public void loadMyUrl(String url, String css) {
        this.mCss = css;
        process(url);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    private String injectCss(String html, String css) {
        int headEnd = html.indexOf("</head>");
        String res = "";
        if (headEnd > 0) {
            res = html.substring(0, headEnd) + css + html.substring(headEnd, html.length());
        } else {
            res = "<head>" + css + "</head>" + html;
        }
        return res;
    }

    private void process(String url) {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
    }

    private String buildCss() {
        StringBuilder sb = new StringBuilder();

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(BaseApplication.getInstances().getAssets().open(mCss), mDefaultEncoding);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString().trim().replace("\n", "");

    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private URL mUrl;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder total = new StringBuilder();
            try {
                mUrl = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
                connection.setRequestProperty("User-Agent", Constants.UserAgentString);
                InputStream is = connection.getInputStream();

                String encoding = connection.getContentEncoding();

                if (encoding == null) {
                    encoding = mDefaultEncoding;
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
                String line;
                while ((line = br.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return total.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mHtml = s;
            String css = buildCss();
            mHtml = injectCss(mHtml, css);
            loadDataWithBaseURL(String.valueOf(mUrl), mHtml, null, mDefaultEncoding, null);
        }

    }

}
