package com.example.moguhaian.easyshop;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Search.MainBiz;
import com.example.moguhaian.easyshop.View.MainVu;
import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.github.mzule.fantasyslide.SideBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainVu, MainBiz> {


    @BindView(R.id.fl_vp)
    ViewPager flVp;
    @BindView(R.id.rcv_mian)
    RecyclerView rcvMian;
    @BindView(R.id.leftSideBar)
    SideBar leftSideBar;
    @BindView(R.id.rcv_left)
    RecyclerView rcvLeft;
    @BindView(R.id.rightSideBar)
    SideBar rightSideBar;
    @BindView(R.id.drawerLayout)
    FantasyDrawerLayout drawerLayout;
    @BindView(R.id.tipView)
    TextView tipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vu.initDrawerLayout(drawerLayout, this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterOnCreate() {
        vu.initViewPage(getSupportFragmentManager(),flVp);
//
//        new ListAdapter()
//        rcvMian.setAdapter();
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
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

}
