package com.example.moguhaian.easyshop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Search.MainBiz;
import com.example.moguhaian.easyshop.View.MainVu;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainVu, MainBiz> {


    @BindView(R.id.main_content_frame_parent)
    RelativeLayout mainContentFrameParent;
    @BindView(R.id.main_left_drawer_layout)
    RelativeLayout mainLeftDrawerLayout;
    @BindView(R.id.main_right_drawer_layout)
    RelativeLayout mainRightDrawerLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    private ActionBarDrawerToggle drawerbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        vu.initDrawerLayout(drawerLayout, this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_drawerlayout;
    }

    @Override
    protected void afterOnCreate() {
//        vu.initViewPage(getSupportFragmentManager(),flVp);
        vu.initDrawerLayout(mainDrawerLayout,this);

        drawerbar = new ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close) {

            //菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mainDrawerLayout.setDrawerListener(drawerbar);

    }

    //左边菜单开关事件
    public void openLeftLayout(View view) {
        if (mainDrawerLayout.isDrawerOpen(mainLeftDrawerLayout)) {
            mainDrawerLayout.closeDrawer(mainLeftDrawerLayout);
        } else {
            mainDrawerLayout.openDrawer(mainLeftDrawerLayout);
        }
    }

    // 右边菜单开关事件
    public void openRightLayout(View view) {
        if (mainDrawerLayout.isDrawerOpen(mainRightDrawerLayout)) {
            mainDrawerLayout.closeDrawer(mainRightDrawerLayout);
        } else {
            mainDrawerLayout.openDrawer(mainRightDrawerLayout);
        }
    }

    @Override
    protected Class<MainVu> getVuClass() {
        return MainVu.class;
    }

    @Override
    protected Class<MainBiz> getBizClass() {
        return MainBiz.class;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mainDrawerLayout.isDrawerOpen(mainLeftDrawerLayout)) {
                mainDrawerLayout.closeDrawer(mainLeftDrawerLayout);
            } else {
                mainDrawerLayout.openDrawer(mainLeftDrawerLayout);
            }
        }
        return true;
    }

}
