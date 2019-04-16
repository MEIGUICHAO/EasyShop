package com.example.moguhaian.easyshop.View;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.BaseVu;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.UiUtils;
import com.example.moguhaian.easyshop.adapter.CommomRecyclerAdapter;
import com.example.moguhaian.easyshop.adapter.CommonViewHolder;
import com.example.moguhaian.easyshop.adapter.ViewPageAdapter;
import com.example.moguhaian.easyshop.fragment.Ali1688Fragment;
import com.example.moguhaian.easyshop.listener.AdapterClickListener;
import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainVu extends BaseVu {

    private boolean openAnimator;

    public int getLeftPosition() {
        return leftPosition;
    }

    public int getRightPosition() {
        return rightPosition;
    }

    private int leftPosition = 0;
    private int rightPosition = 0;
    private CommomRecyclerAdapter<String> leftAdapter;
    private CommomRecyclerAdapter<String> rightAdapter;

    public void notifyRightAdapter(List<String> datas) {
        rightAdapter.getData().clear();
        rightAdapter.getData().addAll(datas);
        rightAdapter.notifyDataSetChanged();
    }

    public void initDrawerLayout(final DrawerLayout drawerLayout, AppCompatActivity activity) {

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(activity);
        indicator.setColor(Color.WHITE);
        activity.getSupportActionBar().setHomeAsUpIndicator(indicator);
//        setTransformer(activity);
//        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (openAnimator) {
                    indicator.setProgress(slideOffset);
                }
            }
        });

        ActionBarDrawerToggle drawerbar = new ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close) {

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

        drawerLayout.setDrawerListener(drawerbar);
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

    public ArrayList<BaseFragment> initViewPage(FragmentManager fm, ViewPager flVp) {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
//        fragments.add(new SelectionFragment());
//        fragments.add(new SameStyleFragment());
//        fragments.add(new Top20wFragment());
        fragments.add(new Ali1688Fragment());
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(fm, fragments);
        flVp.setAdapter(viewPageAdapter);
        flVp.setCurrentItem(0);
        return fragments;
    }



    public void toggleDrawerLayout(DrawerLayout mainDrawerLayout, RelativeLayout childDrawerLayout, RelativeLayout colseDrawerLayout) {
        if (mainDrawerLayout.isDrawerOpen(colseDrawerLayout)) {
            mainDrawerLayout.closeDrawer(colseDrawerLayout);
        }
        if (mainDrawerLayout.isDrawerOpen(childDrawerLayout)) {
            mainDrawerLayout.closeDrawer(childDrawerLayout);
        } else {
            mainDrawerLayout.openDrawer(childDrawerLayout);
        }
    }

    public void setArrowAnimatorEnable(boolean isOpen) {
        openAnimator = isOpen;
    }

    public void setAdapter(Activity activity, RecyclerView recyclerView, String[] strings, final boolean isLeft,final AdapterClickListener listener) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        if (isLeft) {
            leftAdapter = new CommomRecyclerAdapter<String>(activity, R.layout.item_tv, list) {
                @Override
                public void convert(CommonViewHolder holder, String str, final int position) {
                    holder.setText(R.id.tv_item, str);
                    TextView tvItem = holder.getView(R.id.tv_item);
                    tvItem.setTextSize((isLeft ? leftPosition == position : rightPosition == position) ? UiUtils.dp2px(8) : UiUtils.dp2px(6));
                    tvItem.setTextColor((isLeft ? leftPosition == position : rightPosition == position) ? UiUtils.getC(R.color.color_white_select) : UiUtils.getC(R.color.color_white));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isLeft) {
                                leftPosition = position;
                            } else {
                                rightPosition = position;
                            }
                            listener.onAdapterClick(position);
                            leftAdapter.notifyDataSetChanged();
                        }
                    });

                }

            };

        } else {
            rightAdapter = new CommomRecyclerAdapter<String>(activity, R.layout.item_tv, list) {
                @Override
                public void convert(CommonViewHolder holder, String str, final int position) {
                    holder.setText(R.id.tv_item, str);
                    TextView tvItem = holder.getView(R.id.tv_item);
                    tvItem.setTextSize((isLeft ? leftPosition == position : rightPosition == position) ? UiUtils.dp2px(8) : UiUtils.dp2px(6));
                    tvItem.setTextColor((isLeft ? leftPosition == position : rightPosition == position) ? UiUtils.getC(R.color.color_white_select) : UiUtils.getC(R.color.color_white));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isLeft) {
                                leftPosition = position;
                            } else {
                                rightPosition = position;
                            }
                            listener.onAdapterClick(position);
                            rightAdapter.notifyDataSetChanged();
                        }
                    });

                }

            };

        }
        recyclerView.setAdapter(isLeft?leftAdapter:rightAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
}
