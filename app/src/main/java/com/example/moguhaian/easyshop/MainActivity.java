package com.example.moguhaian.easyshop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Search.MainBiz;
import com.example.moguhaian.easyshop.Utils.DateUtil;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.View.MainVu;
import com.example.moguhaian.easyshop.fragment.ShuaiShouFragment;
import com.example.moguhaian.easyshop.listener.AdapterClickListener;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.weidge.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class MainActivity extends BaseActivity<MainVu, MainBiz> implements LoadFinishListener {


    @BindView(R.id.main_left_drawer_layout)
    RelativeLayout mainLeftDrawerLayout;
    @BindView(R.id.main_right_drawer_layout)
    RelativeLayout mainRightDrawerLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.fl_vp)
    NoScrollViewPager flVp;
    @BindView(R.id.rcv_mian)
    RecyclerView rcvMian;
    @BindView(R.id.rcv_right)
    RecyclerView rcvRight;

    //    private String[] mainList = {"采集", "同款", "3", "1688"};
    private String[] mainList = {"top20w","同款标题","甩手", "1688", "1688 x5"};
    private String[] rightList = {};
    private ArrayList<BaseFragment> fragments;
    private ArrayList<String> mTitleList = new ArrayList<>();
    private String[] aliArray;

    public void setIndex(int index) {
        this.index = index;
    }

    private int index = 0;




    public String getTitleResult() {
        titleResult = SharedPreferencesUtils.getValue(Constants.TB_TITLE_RESULT);
        return titleResult;
    }

    private String titleResult;
    private String beginTime = " 07:07:07";

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    private String endTime = " 23:27:07";


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
        vu.setAdapter(this, rcvMian, mainList, true, new AdapterClickListener() {
            @Override
            public void onAdapterClick(int position) {
                onMainItemClick(position);
            }
        });
        vu.setAdapter(this, rcvRight, rightList, false, new AdapterClickListener() {
            @Override
            public void onAdapterClick(int position) {
                mainDrawerLayout.closeDrawer(mainRightDrawerLayout);
                fragments.get(vu.getLeftPosition()).fragmentRightClick(position);
            }
        });
        String dayFromat = DateUtil.getDayFromat(System.currentTimeMillis() + 86400000);
        LogUtils.e("dayFromat:" + dayFromat);
        beginTime = dayFromat + beginTime;
        endTime = dayFromat + endTime;
        aliArray = getResources().getStringArray(R.array.ali_name);
    }

    public void onMainItemClick(int position) {
        mainDrawerLayout.closeDrawer(mainLeftDrawerLayout);
        flVp.setCurrentItem(position,false);
        fragments.get(vu.getLeftPosition()).setItems();
        vu.notifyRightAdapter(fragments.get(vu.getLeftPosition()).getItems());
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

    @Override
    public void onBackPressed() {
        if (fragments.get(vu.getLeftPosition()).canGoback()) {
            fragments.get(vu.getLeftPosition()).webviewGoback();
            return;
        }
        super.onBackPressed();
    }

    public void setTitleList(ArrayList<String> titleList) {
        mTitleList = titleList;
    }

    public void setTitleResult(String titleOnly) {
        SharedPreferencesUtils.putValue(Constants.TB_TITLE_RESULT, titleOnly);
        titleResult = titleOnly;
    }

    public int getIndex() {
        return index;
    }

    public void switchFragment(final int positionFragment) {
        vu.onItemViewOnclick(true, positionFragment, new AdapterClickListener() {
            @Override
            public void onAdapterClick(int position) {
                onMainItemClick(position);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("getIndex():" + getIndex() + ",aliArray:" + aliArray.length + ",positionFragment:" + positionFragment);
                        if (getIndex() < aliArray.length) {
                            if (positionFragment == 1) {
                                fragments.get(positionFragment).fragmentRightClick(0);
                            } else {
                                if (fragments.get(positionFragment) instanceof ShuaiShouFragment) {
                                    ((ShuaiShouFragment) fragments.get(positionFragment)).setCacheAvailable(false);
                                    ((ShuaiShouFragment) fragments.get(positionFragment)).setAliOneKeyPublish(true);
                                    ((ShuaiShouFragment) fragments.get(positionFragment)).autoFragmentClick(R.string.go1688);
                                }

                            }
                        }
                    }
                }, 2000);
            }
        });
    }
}
