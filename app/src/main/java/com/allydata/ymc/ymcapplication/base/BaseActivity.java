package com.allydata.ymc.ymcapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.allydata.ymc.ymcapplication.Yapplicaton;
import com.allydata.ymc.ymcapplication.utils.LogUtil;

/**
 * Created by Administrator on 2016/5/5 0005.
 *
 * 基础 activity
 */
public class BaseActivity extends AppCompatActivity {

    public Yapplicaton yApp;
    protected static long lastClickTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yApp = Yapplicaton.getInstance();
        yApp.addActivityManager(this);
        String s = Yapplicaton.path.getDatabasePath();
        LogUtil.syso(s);
    }

    /**
     * 防止重复点击
     *
     * @return 是否重复点击
     */
    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 隐藏 输入法
     */
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
