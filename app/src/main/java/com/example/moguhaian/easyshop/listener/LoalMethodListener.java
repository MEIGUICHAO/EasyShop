package com.example.moguhaian.easyshop.listener;

public interface LoalMethodListener {
    void afterGetJson(String json);
    void afterClick();

    void inputFinish();
    void errorOccur();
}
