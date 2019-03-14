package com.example.moguhaian.easyshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moguhaian.easyshop.Base.BaseFragment;

import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public ViewPageAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
