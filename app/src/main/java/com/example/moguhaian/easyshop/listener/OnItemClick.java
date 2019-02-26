package com.example.moguhaian.easyshop.listener;

import android.view.View;
import android.view.ViewGroup;

public interface OnItemClick<T> {
    void onItemClick(ViewGroup parent, View view, T t, int position);

    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}