package com.example.moguhaian.easyshop.Base;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.moguhaian.easyshop.R;


public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context, R.style.BaseDialog);
        if (getContentView() == null) {
            setContentView(getContentId());
        } else {
            setContentView(getContentView());
        }
        init();
    }

    /**
     * 布局资源
     *
     * @return
     */
    protected abstract int getContentId();

    /**
     * 初始化方法
     */
    protected abstract void init();

    /**
     * 布局View 返回null 时 getContentId 起作用
     *
     * @return
     */
    protected View getContentView() {
        return null;
    }

    /**
     * 设置动画
     */
    protected void setAnimStyle(int animStyleId) {
        Window window = getWindow();
        if (window != null)
            window.setWindowAnimations(animStyleId);
    }

    /**
     * 设置弹出的位置
     *
     * @param gravity
     */
    protected void setDialogGravity(int gravity) {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(gravity);
            WindowManager m = window.getWindowManager();

            DisplayMetrics dm = new DisplayMetrics();
            m.getDefaultDisplay().getMetrics(dm);

            WindowManager.LayoutParams p = window.getAttributes();
            p.width = dm.widthPixels;
            window.setAttributes(p);
        }
    }

}
