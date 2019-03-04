package com.example.moguhaian.easyshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Search.MainBiz;
import com.example.moguhaian.easyshop.View.MainVu;
import com.example.moguhaian.easyshop.fragment.SelectionFragment;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainVu, MainBiz> implements LoadFinishListener {


    @BindView(R.id.main_left_drawer_layout)
    RelativeLayout mainLeftDrawerLayout;
    @BindView(R.id.main_right_drawer_layout)
    RelativeLayout mainRightDrawerLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.fl_vp)
    ViewPager flVp;
    @BindView(R.id.rcv_mian)
    RecyclerView rcvMian;
    @BindView(R.id.rcv_right)
    RecyclerView rcvRight;

    private String[] mainList = {"1", "2", "3"};
    private String[] rightList = {"4", "5", "6"};
    private ArrayList<Fragment> fragments;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_drawerlayout;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void afterOnCreate() {
        fragments = vu.initViewPage(getSupportFragmentManager(), flVp);
        vu.initDrawerLayout(mainDrawerLayout, this);
        vu.setAdapter(this, rcvMian, mainList, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.closeDrawer(mainLeftDrawerLayout);
            }
        });
        vu.setAdapter(this, rcvRight, rightList, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.closeDrawer(mainRightDrawerLayout);
                ((SelectionFragment)fragments.get(0)).test();

            }
        });
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
        switch (item.getItemId()) {
            case android.R.id.home:
                vu.setArrowAnimatorEnable(true);
                vu.toggleDrawerLayout(mainDrawerLayout, mainLeftDrawerLayout,mainRightDrawerLayout);
                break;
            case R.id.filter:
                vu.setArrowAnimatorEnable(false);
                vu.toggleDrawerLayout(mainDrawerLayout, mainRightDrawerLayout,mainLeftDrawerLayout);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void loadFinish(WebView wv, String url) {

    }
}
