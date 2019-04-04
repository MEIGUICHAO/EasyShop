package com.example.moguhaian.easyshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Shops;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.View.SelectionVu;
import com.example.moguhaian.easyshop.biz.SelectionBiz;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SelectionFragment extends BaseFragment<SelectionVu, SelectionBiz> implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.pb_process)
    ProgressBar pbProcess;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.btn_foreach_title)
    Button btnForeachTitle;
    @BindView(R.id.btn_get_exchange_title_result)
    Button btnGetExchangeTitleResult;
    private String[] split;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_caiji;
    }

    @Override
    protected void afterOnCreate() {
        split = Shops.shopName.split("\n");
        LogUtils.e("split_length:" + split.length);
        btnForeachTitle.setOnClickListener(this);
        btnGetExchangeTitleResult.setOnClickListener(this);
    }

    public void jsoupData(final int position) {

        biz.jsoupShop(split[position], position, new JsoupParseListener() {
            @Override
            public void complete() {
                LogUtils.e(position + "采集~~success!!!!!!!!!!!!!!!!!!!");
                if ((position + 1) < split.length) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvProgress.setText(((position + 1) * 100 / split.length) + "%");
                            pbProcess.setProgress((position + 1));
                            jsoupData(position + 1);
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvProgress.setText(((position + 1) * 100 / split.length) + "%");
                            pbProcess.setProgress((position + 1));
                            biz.updateCaijiExchageTitle();
                        }
                    });
                }
            }

            @Override
            public void onFail(String url) {
                LogUtils.e(position + "采集~~fail!!!!!!!!!!!!!!!!!!!");
                if (position < split.length) {
                    jsoupData(position + 1);
                }
            }
        });
    }

    @Override
    protected Class<SelectionVu> getVuClass() {
        return SelectionVu.class;
    }

    @Override
    protected Class<SelectionBiz> getBizClass() {
        return SelectionBiz.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_foreach_title:
                pbProcess.setMax(split.length);
                jsoupData(0);
                break;
            case R.id.btn_get_exchange_title_result:
                biz.updateCaijiExchageTitle();
//                jsoupData(0);
                break;
        }
    }
}
