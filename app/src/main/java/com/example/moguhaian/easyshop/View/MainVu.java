package com.example.moguhaian.easyshop.View;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.example.moguhaian.easyshop.Base.BaseVu;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.adapter.ViewPageAdapter;
import com.example.moguhaian.easyshop.fragment.Top20wFragment;
import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.Transformer;

import java.util.ArrayList;

public class MainVu extends BaseVu {

    public void initDrawerLayout(FantasyDrawerLayout drawerLayout, AppCompatActivity activity) {

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(activity);
        indicator.setColor(Color.WHITE);
        activity.getSupportActionBar().setHomeAsUpIndicator(indicator);
        setTransformer(activity);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });
    }



    private void setTransformer(AppCompatActivity activity) {
        final float spacing = activity.getResources().getDimensionPixelSize(R.dimen.spacing);
        SideBar rightSideBar = (SideBar) activity.findViewById(R.id.rightSideBar);
        rightSideBar.setTransformer(new Transformer() {
            private View lastHoverView;

            @Override
            public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
                boolean hovered = itemView.isPressed();
                if (hovered && lastHoverView != itemView) {
                    animateIn(itemView);
                    animateOut(lastHoverView);
                    lastHoverView = itemView;
                }
            }

            private void animateOut(View view) {
                if (view == null) {
                    return;
                }
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -spacing, 0);
                translationX.setDuration(200);
                translationX.start();
            }

            private void animateIn(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -spacing);
                translationX.setDuration(200);
                translationX.start();
            }
        });
    }

    public void initViewPage(FragmentManager fm, ViewPager flVp) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Top20wFragment());
        fragments.add(new Top20wFragment());
        fragments.add(new Top20wFragment());
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(fm, fragments);
        flVp.setAdapter(viewPageAdapter);
        flVp.setCurrentItem(0);
    }
}
